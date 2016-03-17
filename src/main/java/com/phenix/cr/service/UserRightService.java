package com.phenix.cr.service;

import com.phenix.cr.model.User;

/**
 * 用户权限
 * 
 * @author ZhaiWeijin
 */
public interface UserRightService {
	/**
	 * 根据用户名查找用户
	 * 
	 * @author ZhaiWeijin
	 * @param userName
	 *            用户名
	 * @return 员工个人信息（Bi）
	 */
	User findByUserName(String userName);

}
