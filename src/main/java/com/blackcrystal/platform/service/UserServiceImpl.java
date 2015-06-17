package com.blackcrystal.platform.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.blackcrystal.platform.dao.UserDao;
import com.blackcrystal.platform.pojo.User;

@Repository
public class UserServiceImpl implements IUserService {

	private static final Logger logger = LoggerFactory
			.getLogger(UserServiceImpl.class);

	@Autowired
	private UserDao userDao;

	@Transactional
	public boolean login(String username, String password) {

		logger.info("User >>> {} login~", username);

		User user = userDao.getUserByName(username);

		// TODO
		String shadow = password;
		return user.getShadow().equals(shadow);
	}

	public long regist(User user) {
		userDao.save(user);
		String userId = userDao.getUserByName(user.getUsername()).getId();
		return Long.valueOf(userId);
	}

}
