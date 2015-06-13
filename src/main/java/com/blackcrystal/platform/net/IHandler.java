package com.blackcrystal.platform.net;

import io.netty.handler.codec.http.FullHttpRequest;

public interface IHandler {
	public Object rpc(FullHttpRequest req) throws Exception;
}
