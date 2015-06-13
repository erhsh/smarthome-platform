package com.blackcrystal.platform.net;

import io.netty.handler.codec.http.FullHttpRequest;

public abstract class HandlerAdapter implements IHandler {

	public Object rpc(FullHttpRequest req) throws Exception {
		return null;
	}

}
