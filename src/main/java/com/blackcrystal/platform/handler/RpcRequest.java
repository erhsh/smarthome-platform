package com.blackcrystal.platform.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.multipart.Attribute;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder.NotEnoughDataDecoderException;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;

import java.io.IOException;
import java.util.List;

public class RpcRequest {
	private final String url;
	private final HttpPostRequestDecoder reqestDecoder;
	private final HttpHeaders headers;
	private final String remoteAddr;

	public String getUrl() {
		return url;
	}

	public HttpHeaders getHeaders() {
		return headers;
	}

	public String getRemoteAddr() {
		return remoteAddr;
	}

	/**
	 * 
	 * @param url
	 *            请求的地址
	 * @param params
	 *            Post参数
	 * @param headers
	 *            http头信息
	 * @param remoteAddr
	 *            远程地址
	 */
	public RpcRequest(ChannelHandlerContext ctx, FullHttpRequest request) {
		this.url = request.getUri();
		this.reqestDecoder = new HttpPostRequestDecoder(request);
		this.headers = request.headers();
		this.remoteAddr = ctx.channel().remoteAddress().toString();
	}

	public final String getParameter(String name) {
		if (reqestDecoder == null)
			return "";
		try {
			InterfaceHttpData bodyHttpData = reqestDecoder
					.getBodyHttpData(name);
			if (null != bodyHttpData) {
				return ((Attribute) bodyHttpData).getValue();
			}
			return "";
		} catch (NotEnoughDataDecoderException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	@Override
	public String toString() {
		if (this.reqestDecoder != null) {
			List<InterfaceHttpData> l = this.reqestDecoder.getBodyHttpDatas();
			StringBuilder r = new StringBuilder();
			for (InterfaceHttpData i : l) {
				try {
					r.append(i.getName()).append(":");
					if (i instanceof Attribute)
						r.append(((Attribute) i).getValue()).append("|");
					else
						r.append("|");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return r.toString();
		} else {
			return "no params";
		}
	}
}
