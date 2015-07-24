package com.blackcrystal.platform.service;

import com.blackcrystal.platform.pojo.User;

public interface IUserService {

	/**
	 * 用户登录
	 * 
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @return User 成功登陆的用户
	 */
	User login(String username, String password);

	/**
	 * 用户注册
	 * 
	 * @param user
	 *            用户
	 * @return 带有Id的用户
	 */
	User regist(User user);
}
