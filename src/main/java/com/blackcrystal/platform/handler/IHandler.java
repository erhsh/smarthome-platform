package com.blackcrystal.platform.handler;


public interface IHandler {
	public Object rpc(RpcRequest req) throws Exception;
}
