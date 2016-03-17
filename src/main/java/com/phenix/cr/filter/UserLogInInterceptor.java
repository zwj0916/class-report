package com.phenix.cr.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.phenix.cr.service.IndexService;
import com.phenix.cr.service.UserRightService;

public class UserLogInInterceptor extends HandlerInterceptorAdapter {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(UserLogInInterceptor.class);
	@Autowired
	private UserRightService userRightService;
	@Autowired
	private IndexService indexService;

	public boolean preHandle(HttpServletRequest req,
			HttpServletResponse response, Object handler) throws Exception {
		LOGGER.debug("--->Begin to Filter session---");
		return super.preHandle(req, response, handler);
	}

}