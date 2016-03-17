package com.phenix.cr.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.util.StringUtils;

/*
 * 配置文件
 */
final public class Configure {
	public static final String DEFAULT_CONFIG_TYPE = "BIBOX";
	private static Configure configure = new Configure();
	private static Map<String, Object> modifiedTimeMap = new HashMap<String, Object>();
	private static Map<String, Object> fileNameMap = new HashMap<String, Object>();
	private static Map<String, Object> absPathMap = new HashMap<String, Object>();
	private static Map<String, Object> configMap = new HashMap<String, Object>();

	private Configure() {
	}

	public static Configure getInstance() {
		return configure;
	}

	public void addConfFileName(String configType, String fileName) throws Exception {
		if (!DEFAULT_CONFIG_TYPE.equals(configType)
				|| (DEFAULT_CONFIG_TYPE.equals(configType) && configMap.get(configType) == null)) {
			initProperties(configType, fileName);
		}
	}
	
	public void addConfByStram(String configType, String path) throws Exception {
		Properties props = new Properties();
		props.load( this.getClass().getResourceAsStream(path));
		Properties properties = (Properties) configMap.get(configType);
		if (properties != null) {
			properties.putAll(props);
			configMap.put(configType, properties);
		} else {
			configMap.put(configType, props);
		}
	}

	public String getProperty(String strKey) {
		try {
			return getProperty(DEFAULT_CONFIG_TYPE, strKey);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getProperty(String configType, String strKey) throws Exception {
		if (StringUtils.isEmpty(configType)) {
			throw new Exception("----Configure--err-------:configType is null");
		}
		try {
			if (configMap.get(configType) == null) {
				throw new Exception((new StringBuilder()).append("----Configure--err-------:configType[")
						.append(configType).append("]is not initialized").toString());
			}
			if(fileNameMap.containsKey(configType)){
				File fileObj = new File((String) fileNameMap.get(configType));
				if (fileObj.lastModified() > ((Long) modifiedTimeMap.get(configType)).longValue()) {
					initProperties(configType, (String) fileNameMap.get(configType));
				}
			}
			Properties properties = (Properties) configMap.get(configType);
			return properties.getProperty(strKey);
		} catch (Exception excep) {
			excep.printStackTrace();
		}
		return "";
	}

	private synchronized boolean initProperties(String configType, String fileName) throws Exception {
		if (StringUtils.isEmpty(configType)) {
			throw new Exception("----Configure--err-------:configType is null");
		}
		if (StringUtils.isEmpty(fileName)) {
			throw new Exception("----Configure--err-------:fileName is null");
		}
		File fileObj = new File(fileName);
		if (!fileObj.exists()) {
			throw new Exception((new StringBuilder()).append("parameter file not found:").append(fileName)
					.append("\r\nAbsolute Path:").append(fileObj.getAbsolutePath()).toString());
		} else {
			readFromFile(configType, fileName, fileObj);
			return true;
		}
	}

	private void readFromFile(String configType, String fileName, File fileObj) throws FileNotFoundException,
			IOException {
		Properties props = new Properties();
		FileInputStream fis = new FileInputStream(fileName);
		props.load(fis);
		fis.close();
		modifiedTimeMap.put(configType, Long.valueOf(fileObj.lastModified()));
		fileNameMap.put(configType, fileName);
		absPathMap.put(configType, fileObj.getAbsolutePath());
		Properties properties = (Properties) configMap.get(configType);
		if (properties != null) {
			properties.putAll(props);
			configMap.put(configType, properties);
		} else {
			configMap.put(configType, props);
		}
	}

	public String getAbsPath() {
		return getAbsPath(DEFAULT_CONFIG_TYPE);
	}

	public String getAbsPath(String configType) {
		return (String) absPathMap.get(configType);
	}
	
}
