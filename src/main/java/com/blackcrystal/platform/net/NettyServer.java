package com.blackcrystal.platform.net;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.blackcrystal.platform.net.codec.RpcChannelInitializer;

/**
 * Netty服务启动器
 * 
 * @author j
 * 
 */
public class NettyServer {
	protected static final Logger logger = LoggerFactory
			.getLogger(NettyServer.class);

	protected final int port;

	private AbstractApplicationContext ctx;

	public NettyServer(int port) {
		this.port = port;

		initContext();
	}

	public void start() {

		EventLoopGroup parentGroup = new NioEventLoopGroup();
		EventLoopGroup childGroup = new NioEventLoopGroup();

		try {
			ServerBootstrap b = new ServerBootstrap();

			b.option(ChannelOption.SO_BACKLOG, 1024);
			b.option(ChannelOption.SO_KEEPALIVE, true);
			b.option(ChannelOption.TCP_NODELAY, true);

			b.group(parentGroup, childGroup);
			b.channel(NioServerSocketChannel.class);
			b.childHandler(ctx.getBean(RpcChannelInitializer.class));

			//
			ChannelFuture defaultFuture = b.bind(port);
			defaultFuture.sync();

			//
			Channel ch = defaultFuture.channel();

			//
			logger.info("Server Start, port is {}", port);
			ChannelFuture closeFuture = ch.closeFuture();
			closeFuture.sync();

		} catch (Exception e) {
			logger.error("Server run exception!!! e = ", e);
		} finally {
			childGroup.shutdownGracefully();
			parentGroup.shutdownGracefully();

			ctx.close();
		}

	}

	private void initContext() {
		ctx = new ClassPathXmlApplicationContext("beans.xml");
		ctx.refresh();
	}

}
