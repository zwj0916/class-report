package com.phenix.cr.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class SessionHolder {
	public static HttpSession getSession() {
		ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes();
		if (sra == null) {
			return null;
		}
		HttpServletRequest request = sra.getRequest();
		if (request == null) {
			return null;
		}
		return request.getSession(false);
	}

}
