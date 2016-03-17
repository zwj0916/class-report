package com.phenix.cr.dao;

import com.phenix.cr.model.User;

/**
 * 用户权限相关的DAO
 * @author ZhaiWeijin
 */
public interface UserRightDao {

    /**
     * 获取用户信息
     * @author ZhaiWeijin
     * @param userName 用户名
     * @return 用户信息（Bi）
     * @Date Mar 4, 2014
     */
    User findUser(String userName);

}
