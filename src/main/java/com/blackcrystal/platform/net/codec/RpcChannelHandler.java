package com.blackcrystal.platform.net.codec;

import static io.netty.handler.codec.http.HttpHeaders.is100ContinueExpected;
import static io.netty.handler.codec.http.HttpHeaders.isKeepAlive;
import static io.netty.handler.codec.http.HttpHeaders.setContentLength;
import static io.netty.handler.codec.http.HttpResponseStatus.BAD_REQUEST;
import static io.netty.handler.codec.http.HttpResponseStatus.CONTINUE;
import static io.netty.handler.codec.http.HttpResponseStatus.NOT_FOUND;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blackcrystal.platform.net.IHandler;

public class RpcChannelHandler extends ChannelInboundHandlerAdapter {
	protected static final Logger logger = LoggerFactory
			.getLogger(RpcChannelHandler.class);

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		try {
			if (msg instanceof FullHttpRequest) {
				handleHttp(ctx, (FullHttpRequest) msg);
			} else {
				logger.debug("Not http request, msg={}", msg);
				ctx.close();
			}
		} finally {
			ReferenceCountUtil.release(msg);
		}
	}

	private void handleHttp(ChannelHandlerContext ctx, FullHttpRequest req) {
		DecoderResult decoderResult = req.getDecoderResult();

		// Decoder Check
		if (!decoderResult.isSuccess()) {
			logger.debug("decode not success");
			sendHttpResponse(ctx, req, newResponse(BAD_REQUEST));
			return;
		}

		// 100 continue Check
		if (is100ContinueExpected(req)) {
			ctx.write(newResponse(CONTINUE));
		}

		// Map request uri
		String uri = req.getUri();
		logger.info("request uri = {}", uri);

		IHandler handler = getHandler(uri);
		if (null == handler) {
			logger.warn("Unkown uri = {}", uri);
			sendHttpResponse(ctx, req, newResponse(NOT_FOUND));
			return;
		}

		// deal request
		Object ret = null;
		try {
			ret = handler.rpc(req);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null == ret) {
				logger.error("ret is null, {}", uri);
				return;
			}
			byte[] content = ret.toString().getBytes();
			sendHttpResponse(ctx, req, newResponse(OK, content));
		}
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		super.exceptionCaught(ctx, cause);
	}

	private static void sendHttpResponse(ChannelHandlerContext ctx,
			FullHttpRequest req, FullHttpResponse res) {
		if (res.getStatus().code() != 200) {
			ByteBuf buf = Unpooled.copiedBuffer(res.getStatus().toString(),
					CharsetUtil.UTF_8);
			res.content().writeBytes(buf);
			buf.release();
		}
		setContentLength(res, res.content().readableBytes());
		ChannelFuture f = ctx.channel().writeAndFlush(res);
		if (!isKeepAlive(req) || res.getStatus().code() != 200) {
			f.addListener(ChannelFutureListener.CLOSE);
		}
	}

	private static FullHttpResponse newResponse(HttpResponseStatus status) {
		return new DefaultFullHttpResponse(HTTP_1_1, status);
	}

	private static FullHttpResponse newResponse(HttpResponseStatus status,
			byte[] content) {
		return new DefaultFullHttpResponse(HTTP_1_1, status,
				Unpooled.wrappedBuffer(content));
	}

	private IHandler getHandler(String uri) {
		return null;
	}
}
