package com.phenix.cr.listener;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.phenix.cr.util.Configure;

public class SystemInitListener implements ServletContextListener {
	private static final Logger logger = LoggerFactory.getLogger(SystemInitListener.class);
	
	public void contextDestroyed(ServletContextEvent arg0) {
		//do nothing
	}

	public void contextInitialized(ServletContextEvent arg0) {
		try {
			String configLocation = arg0.getServletContext().getInitParameter("config_location");
			Configure.getInstance().addConfByStram(Configure.DEFAULT_CONFIG_TYPE, configLocation);
		} catch (Exception e) {
			System.err.println(e);
			logger.error("contextInitialized error", e);
		}
		try {
			Properties ps = new Properties();
			try {
				String log4jConfigLocation = arg0.getServletContext().getInitParameter("log4jConfigLocation");
				ps.load(this.getClass().getResourceAsStream(log4jConfigLocation));
				PropertyConfigurator.configure(ps);
			} catch (IOException e) {
				e.printStackTrace();
				logger.error("log4j initialized error", e);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("log4j initialized error", e);
		}
	}

}
