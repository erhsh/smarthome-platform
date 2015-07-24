package com.blackcrystal.platform.handler.api;

import static com.blackcrystal.platform.enums.ServiceErrorCode.SUCCESS;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.blackcrystal.platform.exception.ServiceException;
import com.blackcrystal.platform.handler.HandlerAdapter;
import com.blackcrystal.platform.handler.RpcRequest;
import com.blackcrystal.platform.pojo.User;
import com.blackcrystal.platform.service.IUserService;

@Controller("/login")
public class UserLoginApi extends HandlerAdapter {

	private Logger logger = LoggerFactory.getLogger(UserLoginApi.class);

	@Autowired
	IUserService userService;

	@Override
	public Object rpc(RpcRequest req) {
		Map<String, Object> ret = new HashMap<String, Object>();

		String username = req.getParameter("email");
		String password = req.getParameter("passwd");

		try {
			User user = userService.login(username, password);
			ret.put("userId", user.getId());
			ret.put("cookie", "xxx");
			ret.put("status", SUCCESS);
		} catch (ServiceException se) {
			logger.error("Login failed!!! ", se);
			ret.put("status", se.getErrorCode());
			return ret;
		}

		return ret;
	}
}
