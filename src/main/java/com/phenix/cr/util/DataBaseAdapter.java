package com.phenix.cr.util;

/**
 * <p>
 * Title:DBAdapter.java
 * </p>
 * <p>
 * Description:数据库适配器，提供大量工具方法，用来屏蔽数据库之间的差异。
 * <p>
 * 本类通过spring配置，必须指定dbType参数，支持setter注入。 <br>
 * 参数值从MYSQL、ORACLE、ACCESS、SQLSERVER、DB2、SYBASE中选择。
 * </p>
 * <p>
 * 时间格式语法 Java时间格式语法： 使用一个 time pattern 字符串指定时间格式。 在这种方式下，所有的 ASCII
 * 字母被保留为模式字母，定义如下：
 * 
 * 符号 含义 表示 示例 ------ ------- ------------ ------- G 年代标志符 (Text) AD y 年
 * (Number) 1996 M 月 (Text & Number) July & 07 d 日 (Number) 10 h 时 在上午或下午 (1~12)
 * (Number) 12 H 时 在一天中 (0~23) (Number) 0 m 分 (Number) 30 s 秒 (Number) 55 S 毫秒
 * (Number) 978 E 星期 (Text) Tuesday D 一年中的第几天 (Number) 189 F 一月中第几个星期几 (Number)
 * 2 (2nd Wed in July) w 一年中第几个星期 (Number) 27 W 一月中第几个星期 (Number) 2 a 上午 / 下午
 * 标记符 (Text) PM k 时 在一天中 (1~24) (Number) 24 K 时 在上午或下午 (0~11) (Number) 0 z 时区
 * (Text) Pacific Standard Time ' 文本转义符 (Delimiter) '' 单引号 (Literal)
 * 
 * </p>
 */

public class DataBaseAdapter {

	// 日期字符串格式
	/**
	 * 格式：yyyy-MM-dd
	 */
	public static final String DATA_FORMAT1 = "yyyy-MM-dd";
	/**
	 * 格式：yyyy/MM/dd
	 */
	public static final String DATA_FORMAT2 = "yyyy/MM/dd";
	/**
	 * 格式：yyyyMMdd
	 */
	public static final String DATA_FORMAT3 = "yyyyMMdd";
	// 时间单位常量
	/**
	 * 秒
	 */
	public static final String SECOND = "SECOND";
	/**
	 * 分
	 */
	public static final String MINUTE = "MINUTE";
	/**
	 * 小时
	 */
	public static final String HOUR = "HOUR";
	/**
	 * 天
	 */
	public static final String DAY = "DAY";
	/**
	 * 月
	 */
	public static final String MONTH = "MONTH";
	/**
	 * 年
	 */
	public static final String YEAR = "YEAR";

	/**
	 * 指定数据库类型，参数值从MYSQL、ORACLE、ACCESS、SQLSERVER、DB2、SYBASE中选择
	 */
	private String dbType;

	/**
	 * ORACLE
	 */
	public static final String DBMS_ORACLE = "ORACLE";
	/**
	 * ODBC
	 */
	public static final String DBMS_ODBC = "ODBC";
	/**
	 * ACESS
	 */
	public static final String DBMS_ACESS = "ACESS";
	/**
	 * MYSQL
	 */
	public static final String DBMS_MYSQL = "MYSQL";
	/**
	 * DB2
	 */
	public static final String DBMS_DB2 = "DB2";
	/**
	 * SQLSERVER
	 */
	public static final String DBMS_SQLSERVER = "SQLSERVER";
	/**
	 * TERA
	 */
	public static final String DBMS_TERA = "TERA";
	/**
	 * SYBASE
	 */
	public static final String DBMS_SYBASE = "SYBASE";

	public DataBaseAdapter(String dbType) {
		this.dbType = dbType;
	}

	/**
	 * 得到数据库类型
	 * 
	 * @return
	 * @throws RuntimeException
	 */
	public String getDbType() throws RuntimeException {
		if (dbType == null || dbType.trim().equals(""))
			throw new RuntimeException("类DBAdapter的属性dbType必须注入，不能为空");
		dbType = dbType.toUpperCase();
		// MYSQL、ORACLE、ACCESS、SQLSERVER、DB2、SYBASE
		if (dbType.indexOf("MYSQL") >= 0) {
			dbType = DBMS_MYSQL;
		} else if (dbType.indexOf("ORACLE") >= 0) {
			dbType = DBMS_ORACLE;
		} else if (dbType.indexOf("ACCESS") >= 0) {
			dbType = DBMS_ACESS;
		} else if (dbType.indexOf("SQL SERVER") >= 0) {
			dbType = DBMS_SQLSERVER;
		} else if (dbType.indexOf("DB2") >= 0) {
			dbType = DBMS_DB2;
		} else if (dbType.indexOf("TERA") >= 0) {
			dbType = DBMS_TERA;
		} else if (dbType.indexOf("SYBASE") >= 0) {
			dbType = DBMS_SYBASE;
		} else {
			throw new RuntimeException("不支持的数据库类型！" + dbType);
		}
		return dbType;
	}

	/**
	 * 取得各种数据库的分页SQL
	 * 
	 * @param sql
	 *            原始SQL
	 * @param currPage
	 *            当前页（取值从1开始，即第一页currpage=1）
	 * @param pageSize
	 *            每页的记录数
	 * @return
	 * @throws RuntimeException
	 */
	public String getPagedSql(String srcSql, int currPage, int pageSize) {
		String dbType = getDbType(); // Must call
		int begin = (currPage - 1) * pageSize;
		int end = begin + pageSize;
		String strRet = srcSql;
		if (dbType.equals(DBMS_MYSQL)) {
			strRet = srcSql + " limit " + begin + "," + pageSize;
		} else if (dbType.equals(DBMS_ORACLE)) {
			// 如果end是Integer的最大值，再自加1变成Integer的最小值，因此，这里加判断
			if (end < Integer.MAX_VALUE) {
				end++;
			}
			strRet = "SELECT * FROM (SELECT ROWNUM AS NUMROW, d.* from ("
					+ srcSql + ") d) WHERE NUMROW >" + begin + " AND NUMROW <"
					+ end;
		} else if (dbType.equals(DBMS_DB2)) {
			StringBuffer rownumber = new StringBuffer(" rownumber() over(");
			int orderByIndex = srcSql.toLowerCase().indexOf("order by");

			if (orderByIndex > 0) {
				String[] tempStr = srcSql.substring(orderByIndex).split("\\.");
				for (int i = 0; i < tempStr.length - 1; i++) {
					int dotIndex = tempStr[i].lastIndexOf(",");
					if (dotIndex < 0)
						dotIndex = tempStr[i].lastIndexOf(" ");
					String result = tempStr[i].substring(0, dotIndex + 1);
					rownumber.append(result).append(" temp_.");
				}
				rownumber.append(tempStr[tempStr.length - 1]);
			}

			rownumber.append(") as row_,");

			StringBuffer pagingSelect = new StringBuffer(srcSql.length() + 100)
					.append("select * from ( ").append(" select ")
					.append(rownumber.toString()).append("temp_.* from (")
					.append(srcSql).append(" ) as temp_")
					.append(" ) as temp2_")
					.append(" where row_  between " + begin + "+1 and " + end);
			strRet = pagingSelect.toString();
		} else if (DBMS_TERA.equalsIgnoreCase(dbType)) {
			StringBuffer buffer = new StringBuffer(srcSql.length() + 100);
			buffer.append(srcSql);
			int orderByIndex = buffer.toString().toLowerCase()
					.lastIndexOf("order by");
			if (orderByIndex > 0) {
				String orderBy = buffer.substring(orderByIndex);
				buffer.insert(orderByIndex, " QUALIFY row_number() OVER( ");
				buffer.append(" ) ");
				buffer.append(" between " + begin + " and " + end);
				buffer.append(orderBy);
			} else {
				buffer.append(" QUALIFY sum(1) over (rows unbounded preceding) between "
						+ begin + " and " + end);
			}
			strRet = buffer.toString();
		}
		return strRet;
	}

	/**
	 * 获取count sql
	 * 
	 * @param sql
	 * @return
	 */
	public String getSQLCount(String sql) {
		String sqlCount = "select count(*) from (" + sql + ")";
		return sqlCount;
	}
}
