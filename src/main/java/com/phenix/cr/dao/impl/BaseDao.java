package com.phenix.cr.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;

import com.phenix.cr.util.DataBaseAdapter;
import com.phenix.cr.util.Page;

public abstract class BaseDao {
	private static final Logger LOGGER = LoggerFactory.getLogger(BaseDao.class);

	/**
	 * 要求子类实现
	 * 
	 * @return
	 */
	protected abstract JdbcTemplate getJdbcTemplate();

	/**
	 * 批量添加 insert List<Object>
	 * 
	 * @author GengXubo
	 * @param <T>
	 *            插入数据
	 * @param sql
	 *            insertSQL
	 * @param list
	 *            插入数据
	 * @Date Apr 21, 2014
	 */
	public <T> void insertBatch(String sql, List<T> list) {
		LOGGER.debug(sql);
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
				getJdbcTemplate());
		SqlParameterSource[] paramSource = SqlParameterSourceUtils
				.createBatch(list.toArray());
		namedParameterJdbcTemplate.batchUpdate(sql.toString(), paramSource);
	}

	public <T> void insertBatch(String sql, Map[] data) {
		LOGGER.debug(sql);
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
				getJdbcTemplate());
		SqlParameterSource[] paramSource = SqlParameterSourceUtils
				.createBatch(data);
		namedParameterJdbcTemplate.batchUpdate(sql.toString(), paramSource);
	}

	/*
	 * insert object
	 */
	public void insert(String sql, Object obj) {
		LOGGER.debug(sql);
		LOGGER.debug(String.valueOf(obj));
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
				getJdbcTemplate());
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(obj);
		namedParameterJdbcTemplate.update(sql, paramSource);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Page queryPage(String sql, int currentPage, int pageSize,
			Class clazz, Object paras) {
		long start = System.currentTimeMillis();
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
				getJdbcTemplate());
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(
				paras);
		if (currentPage < 1) {
			currentPage = 1;
		}
		int totalCount = 0;
		DataBaseAdapter dba = new DataBaseAdapter(DataBaseAdapter.DBMS_ORACLE);
		long con = System.currentTimeMillis() - start;
		long count = System.currentTimeMillis();
		// 获取记录总数
		String countSql = dba.getSQLCount(sql);
		totalCount = namedParameterJdbcTemplate.queryForObject(countSql,
				paramSource, Integer.class);
		LOGGER.debug(countSql);
		Page p = new Page(totalCount, pageSize, currentPage);
		if (totalCount == 0) {
			LOGGER.debug(countSql);
			LOGGER.debug(paras.toString());
			p.setDatas(new ArrayList());
			LOGGER.debug("cost:" + (System.currentTimeMillis() - start) + "ms");
			return p;
		}
		logCost(countSql, count, paras);
		long starts = System.currentTimeMillis();
		String paginateSql = dba.getPagedSql(sql, p.getCurrentPage(), pageSize);
		LOGGER.debug(paginateSql);
		List datas = namedParameterJdbcTemplate.query(paginateSql, paramSource,
				ParameterizedBeanPropertyRowMapper.newInstance(clazz));
		p.setDatas(datas);
		LOGGER.debug(sql + "\ncost:" + (System.currentTimeMillis() - start)
				+ "ms, con" + con + "ms, count" + (starts - count)
				+ "ms, query:" + (System.currentTimeMillis() - starts));
		logCost(paginateSql, starts, paras);
		return p;
	}

	public <T> List<T> queryFirstPage(String sql, int pageSize, Class<T> clazz,
			Object paras) {
		long start = System.currentTimeMillis();
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(
				paras);
		DataBaseAdapter dba = new DataBaseAdapter(DataBaseAdapter.DBMS_ORACLE);
		String paginateSql = dba.getPagedSql(sql, 1, pageSize);
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
				getJdbcTemplate());
		List<T> datas = namedParameterJdbcTemplate.query(paginateSql,
				paramSource,
				ParameterizedBeanPropertyRowMapper.newInstance(clazz));
		logCost(sql, start, paras);
		return datas;
	}

	public void logCost(String sql, long start, Object... args) {
		long cost = System.currentTimeMillis() - start;
		LOGGER.debug(sql);
		if (cost < 2000) {
			LOGGER.debug("cost:" + (cost));
		} else {
			StringBuffer detail = new StringBuffer("sql：" + sql);
			detail.append("\n参数：");
			if (args != null) {
				for (Object arg : args) {
					detail.append(String.valueOf(arg)).append(",");
				}
			}
			LOGGER.warn(detail + "cost:" + (cost));
		}
	}

	public <T> List<T> queryForList(String sql, Class<T> clazz, Object paras) {
		long start = System.currentTimeMillis();
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(
				paras);
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
				getJdbcTemplate());
		List<T> datas = namedParameterJdbcTemplate.query(sql, paramSource,
				ParameterizedBeanPropertyRowMapper.newInstance(clazz));
		logCost(sql, start, paras);
		return datas;
	}

	/**
	 * 
	 * @param clazz
	 *            复杂的bean
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <T> List<T> queryForList(String sql, Class<T> clazz, Map paras) {
		long start = System.currentTimeMillis();
		SqlParameterSource paramSource = new MapSqlParameterSource(paras);
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
				getJdbcTemplate());
		List<T> datas = namedParameterJdbcTemplate.query(sql, paramSource,
				ParameterizedBeanPropertyRowMapper.newInstance(clazz));
		logCost(sql, start, paras);
		return datas;
	}

	/**
	 * 
	 * @param clazz
	 *            基本对象 String Long 等
	 */
	public <T> List<T> queryForList(String sql, Map<String, ?> paras,
			Class<T> clazz) {
		long start = System.currentTimeMillis();
		MapSqlParameterSource paramSource = new MapSqlParameterSource(paras);
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
				getJdbcTemplate());
		List<T> data = namedParameterJdbcTemplate.queryForList(sql,
				paramSource, clazz);
		LOGGER.debug(sql + "\ncost:" + (System.currentTimeMillis() - start)
				+ "ms");
		logCost(sql, start, paras);
		return data;
	}

	public List<Map<String, Object>> queryForList(String sql,
			Map<String, ?> paras) {
		long start = System.currentTimeMillis();
		MapSqlParameterSource paramSource = new MapSqlParameterSource(paras);
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
				getJdbcTemplate());
		List<Map<String, Object>> data = namedParameterJdbcTemplate
				.queryForList(sql, paramSource);
		LOGGER.debug(sql + "\ncost:" + (System.currentTimeMillis() - start)
				+ "ms");
		logCost(sql, start, paras);
		return data;
	}
}
