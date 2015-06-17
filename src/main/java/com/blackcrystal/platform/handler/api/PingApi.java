package com.blackcrystal.platform.handler.api;

import org.springframework.stereotype.Service;

import com.blackcrystal.platform.handler.HandlerAdapter;
import com.blackcrystal.platform.handler.RpcRequest;

@Service("/ping")
public class PingApi extends HandlerAdapter{
	@Override
	public Object rpc(RpcRequest req) throws Exception {
		return "Pong";
	}
}
