package com.phenix.cr.util;

import java.io.Serializable;
import java.util.List;

/**
 * 分页数据Bean
 * 
 * @version 1.0
 * @since 1.0 Mar 1, 2014
 */
public class Page implements Serializable {
	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 默认每页显示记录数
	 */
	private static final int DEFAULT_PAGE_SIZE = 8;

	/**
	 * 总记录数
	 */
	private Integer totalCount = 0;

	/**
	 * 当前页
	 */
	private Integer currentPage = 1;

	/**
	 * 每页显示记录数
	 */
	private Integer pageSize = DEFAULT_PAGE_SIZE;

	/**
	 * 总页数
	 */
	@SuppressWarnings("unused")
	private Integer totalPage;

	/**
	 * 数据
	 */
	@SuppressWarnings("rawtypes")
	private List datas;

	/**
	 * 无参构造器-作为前端请求参数时spring初始化使用
	 * 
	 * @author GengXubo
	 */
	public Page() {
	}

	/**
	 * 构造器 包括总页数、当前页码、每页显示记录数 若每页显示记录数小于1则为默认记录数（10）
	 * 
	 * @param totalCount
	 *            总页数
	 * @param pageSize
	 *            每页显示记录
	 * @param currentPage
	 *            当前页码
	 */
	public Page(int totalCount, int pageSize, Integer currentPage) {
		this.totalCount = totalCount;
		this.currentPage = currentPage;
		if (pageSize < 1) {
			this.pageSize = Page.DEFAULT_PAGE_SIZE;
		} else {
			this.pageSize = pageSize;
		}
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		if (pageSize < 1) {
			this.pageSize = Page.DEFAULT_PAGE_SIZE;
		} else {
			this.pageSize = pageSize;
		}
	}

	public Integer getTotalPage() {
		return totalCount % pageSize == 0 ? totalCount / pageSize : totalCount
				/ pageSize + 1;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}

	@SuppressWarnings("rawtypes")
	public List getDatas() {
		return datas;
	}

	@SuppressWarnings("rawtypes")
	public void setDatas(List datas) {
		this.datas = datas;
	}

	/**
	 * 获取当前页码 若小于1则默认为1
	 * 
	 * @return 当前页码
	 * @Date Mar 1, 2014
	 */
	public Integer getCurrentPage() {
		if (currentPage < 1) {
			currentPage = 1;
		}
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
}
