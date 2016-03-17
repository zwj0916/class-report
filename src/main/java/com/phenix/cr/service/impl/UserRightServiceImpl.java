package com.phenix.cr.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.phenix.cr.dao.UserRightDao;
import com.phenix.cr.model.User;
import com.phenix.cr.service.UserRightService;

/**
 * 用户权限Service实现类
 * 
 * @author ZhaiWeijin
 * @version 1.0
 */
@Service
@Transactional
public class UserRightServiceImpl implements UserRightService {
	@Autowired
	private UserRightDao userRightDao;

	public User findByUserName(String userName) {
		return userRightDao.findUser(userName);
	}

}
