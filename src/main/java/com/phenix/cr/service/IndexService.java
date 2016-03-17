package com.phenix.cr.service;

import java.io.File;
import java.util.Map;

public interface IndexService {
	Map<String, Object> calcAllIndex(String batchId);

	String importValues(String className, File f);
}
