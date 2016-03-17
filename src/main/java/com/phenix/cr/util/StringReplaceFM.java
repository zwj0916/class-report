package com.phenix.cr.util;

import java.io.StringWriter;
import java.util.Map;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

public class StringReplaceFM {
	private static Configuration cfg = new Configuration(
			Configuration.VERSION_2_3_22);
	private static StringTemplateLoader stringLoader = new StringTemplateLoader();

	public static String replaceString(String templateName, String templateStr,
			Map<String, Object> values) {
		if (stringLoader.findTemplateSource(templateName) == null) {
			stringLoader.putTemplate(templateName, templateStr);
			cfg.setTemplateLoader(stringLoader);
		}
		try {
			Template templateCon = cfg.getTemplate(templateName, "UTF-8");
			StringWriter writer = new StringWriter();
			templateCon.process(values, writer);
			return writer.toString();
		} catch (Exception e) {
			throw new RuntimeException(
					"formate by freemarker error,templateStr:" + templateStr
							+ ",values:" + values + e.getMessage(), e);
		}
	}

}
