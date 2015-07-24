package com.blackcrystal.platform.service;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.blackcrystal.platform.dao.UserDao;
import com.blackcrystal.platform.enums.ServiceErrorCode;
import com.blackcrystal.platform.exception.ServiceException;
import com.blackcrystal.platform.pojo.User;

@Repository
public class UserServiceImpl implements IUserService {

	protected static final Logger logger = LoggerFactory
			.getLogger(UserServiceImpl.class);

	@Autowired
	private UserDao userDao;

	@Transactional(readOnly = true)
	public User login(String username, String password) {

		// 1. check name
		if (StringUtils.isBlank(username)) {
			throw new ServiceException(ServiceErrorCode.SYSERROR,
					"Username is blank.");
		}

		// 2. check password
		if (StringUtils.isBlank(password)) {
			throw new ServiceException(ServiceErrorCode.SYSERROR,
					"Password is black.");
		}

		// 3. check name exist
		if (!userDao.isNameExist(username)) {
			throw new ServiceException("name not exist");
		}

		User user = userDao.getUserByName(username);

		// TODO
		String shadow = password;
		if (!user.getShadow().equals(shadow)) {
			throw new ServiceException("password error!");
		}

		return user;
	}

	public User regist(User user) {
		userDao.save(user);
		return userDao.getUserByName(user.getUsername());
	}

}
