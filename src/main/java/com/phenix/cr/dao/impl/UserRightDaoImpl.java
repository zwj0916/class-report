package com.phenix.cr.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.phenix.cr.dao.UserRightDao;
import com.phenix.cr.model.User;

/**
 * 此处为类说明
 * 
 * @version 1.0
 */
@Repository
public class UserRightDaoImpl extends BaseDao implements UserRightDao {
	private static final Logger logger = LoggerFactory
			.getLogger(UserRightDaoImpl.class);

	@Resource(name = "crweb2JdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	protected JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public User findUser(String userName) {
		String sql = "select userName,employeeid as personid, t.employeenumber as employeeNumber, "
				+ "t.displayname as lastName from BA_USERS t "
				+ "where SOURCE = 'UUAP' and username = ? and active = 'Y' and "
				+ "exists (SELECT * FROM BA_USER2ROLE WHERE ROLEID IN  "
				+ "(select ROLEID from ba_roles WHERE nvl(isdelete,'N') = 'N' and APPID = "
				+ "(select APPID from ba_application where appname='EPS')) and  userid = t.userid)";
		logger.debug(sql);
		List<User> users = this.getJdbcTemplate().query(sql,
				ParameterizedBeanPropertyRowMapper.newInstance(User.class),
				userName);
		if (users.size() > 0) {
			return users.get(0);
		} else {
			return null;
		}
	}

}
