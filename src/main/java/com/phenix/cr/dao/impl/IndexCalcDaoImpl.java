package com.phenix.cr.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.phenix.cr.dao.IndexCalcDao;

@Repository
public class IndexCalcDaoImpl extends BaseDao implements IndexCalcDao {
	@Resource(name = "crwebJdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	public <T> T calc(String str, Map<String, Object> params, Class<T> cls) {
		MapSqlParameterSource paramSource = new MapSqlParameterSource(params);
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
				getJdbcTemplate());
		return namedParameterJdbcTemplate.queryForObject(str, paramSource, cls);
	}

	protected JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	@Override
	public java.util.List<Map<String, Object>> calc(String str,
			Map<String, Object> params) {

		return super.queryForList(str, params);
	}

	@Override
	public void insertBatchMap(String sql, List<Map> datas) {
		Map[] data = new Map[datas.size()];
		data = ((ArrayList<Map>) datas).toArray(data);
		super.insertBatch(sql, data);

	}

}
