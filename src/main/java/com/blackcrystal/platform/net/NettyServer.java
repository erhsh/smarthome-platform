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

	public NettyServer(int port) {
		this.port = port;
	}

	public void start() {
		EventLoopGroup parentGroup = new NioEventLoopGroup();
		EventLoopGroup childGroup = new NioEventLoopGroup();

		try {
			ServerBootstrap bootstrap = new ServerBootstrap();

			bootstrap.option(ChannelOption.SO_BACKLOG, 1024);
			bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
			bootstrap.option(ChannelOption.TCP_NODELAY, true);

			bootstrap.group(parentGroup, childGroup);
			bootstrap.channel(NioServerSocketChannel.class);
			bootstrap.childHandler(new RpcChannelInitializer());

			//
			ChannelFuture defaultFuture = bootstrap.bind(port);
			defaultFuture.sync();

			//
			Channel ch = defaultFuture.channel();

			//
			logger.info("Server Start, port is {}", port);
			ChannelFuture closeFuture = ch.closeFuture();
			closeFuture.sync();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			childGroup.shutdownGracefully();
			parentGroup.shutdownGracefully();
		}

	}

	public static void main(String[] args) {
		NettyServer server = new NettyServer(8080);
		server.start();
	}

}
