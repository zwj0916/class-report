package com.phenix.cr.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "index_define")
public class IndexDefine extends EntityId {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String countSql;
	private String note;
	private String type;
	private String resultType;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getResultType() {
		return resultType;
	}

	public void setResultType(String resultType) {
		this.resultType = resultType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCountSql() {
		return countSql;
	}

	public void setCountSql(String countSql) {
		this.countSql = countSql;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}
