package com.blackcrystal.platform.main;

import com.blackcrystal.platform.net.NettyServer;

public class PlatformServiceStartup {

	public static void main(String[] args) {

		NettyServer server = new NettyServer(8080);

		server.start();
	}

}
