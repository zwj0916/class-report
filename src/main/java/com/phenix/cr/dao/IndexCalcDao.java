package com.phenix.cr.dao;

import java.util.List;
import java.util.Map;

public interface IndexCalcDao {
	<T> T calc(String str, Map<String, Object> params, Class<T> cls);

	List<Map<String, Object>> calc(String str, Map<String, Object> params);

	void insertBatchMap(String sql, List<Map> datas);
}
