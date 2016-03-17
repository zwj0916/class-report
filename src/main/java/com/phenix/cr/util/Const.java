package com.phenix.cr.util;

/**
 * 常量
 * 
 * @author zhaiweijin
 */
public class Const {
	/**
	 * 权限数据库JNDI
	 */
	public static final String USER_PRIVALIAGE_JNDI_NAME = "USER_PRIVALIAGE_JNDI_NAME";

	/**
	 * 人力数据库JNDI
	 */
	public static final String HR_JNDI_NAME = "HR_JNDI_NAME";

	/**
	 * 数据库JNDI
	 */
	public static final String CR_JNDI_NAME = "CR_JNDI_NAME";

	/**
	 * CAS相关参数
	 */
	public static final String ASSERTION_KEY = "_const_cas_assertion_";

	/**
	 * CAS相关参数
	 */
	public static final String SERVICE_KEY = "SERVICE";

	public static final String CR_USER_SESSION_KEY = "bi_user";

	public static final String LOGOUT_URL = "LOGOUT_URL";

	public static final String DEFAULT_PHOTO_URL = "DEFAULT_PHOTO_URL";

	/**
	 * 员工信息的模块
	 */
	public static final String MODELS_BASIC = "BASIC";

	public static final String MODELS_CAREER = "CAREER";

	public static final String MODELS_PERFORMANCE = "PERFORMANCE";

	public static final String MODELS_IDP = "IDP";

	public static final String MODELS_WORKEXPERIENCE = "WORKEXPERIENCE";

	public static final String MODELS_EDU = "EDU";

	public static final String MODELS_CERTIFY = "CERTIFY";

	public static final String MODELS_REWARDS = "REWARDS";

	public static final String MODELS_TRAINING = "TRAINING";

	/**
	 * 员工对比模块-session中保存用户比对的员工id
	 */
	public static final String CONTRAST_IDS_SESSION_KEY = "contrast_ids";

	public static final String CONTRAST_COOKIE_KEY = "currCompareList";

	/**
	 * 邮件列表的两种类型，1为反馈邮件，2为预警邮件
	 */
	public static final Integer FEEDBACK_MAIL = 1;

	public static final Integer WARNING_MAIL = 2;

}
