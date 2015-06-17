package com.blackcrystal.platform.handler.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import com.blackcrystal.platform.handler.HandlerAdapter;
import com.blackcrystal.platform.handler.RpcRequest;
import com.blackcrystal.platform.service.IUserService;

@Controller("/login")
public class UserLoginApi extends HandlerAdapter {

	@Autowired
	IUserService userService;

	@Override
	@Transactional
	public Object rpc(RpcRequest req) throws Exception {
		String username = req.getParameter("email");
		String password = req.getParameter("passwd");

		if (userService.login(username, password)) {
		}

		return null;
	}
}
