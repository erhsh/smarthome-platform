package com.blackcrystal.platform.service;

import com.blackcrystal.platform.pojo.User;

public interface IUserService {
	boolean login(String username, String password);

	long regist(User user);
}
