package com.phenix.cr.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.phenix.cr.dao.IndexCalcDao;
import com.phenix.cr.dao.IndexDefineDao;
import com.phenix.cr.model.IndexDefine;
import com.phenix.cr.service.IndexService;
import com.phenix.cr.util.StringReplaceFM;

@Service
@Transactional
public class IndexServiceImpl implements IndexService {
	@Autowired
	private IndexDefineDao indexDefineDao;

	@Autowired
	private IndexCalcDao indexCalcDao;

	@Override
	public Map<String, Object> calcAllIndex(String batchId) {
		List<IndexDefine> defines = indexDefineDao.findAll();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("batchId", batchId);
		for (IndexDefine df : defines) {
			if (df.getType().equalsIgnoreCase("sql")) {
				if (df.getResultType().equalsIgnoreCase("list")) {
					List<Map<String, Object>> data = indexCalcDao.calc(
							df.getCountSql(), param);
					result.put(df.getName(), data);
				} else if (df.getResultType().equalsIgnoreCase("integer")) {
					Integer data = indexCalcDao.calc(df.getCountSql(), param,
							Integer.class);
					result.put(df.getName(), data);
				} else if (df.getResultType().equalsIgnoreCase("double")) {
					Double data = indexCalcDao.calc(df.getCountSql(), param,
							Double.class);
					result.put(df.getName(), data);
				} else if (df.getResultType().equalsIgnoreCase("string")) {
					String data = indexCalcDao.calc(df.getCountSql(), param,
							String.class);
					result.put(df.getName(), data);
				}
			}
		}
		for (IndexDefine df : defines) {
			if (df.getType().equalsIgnoreCase("el")) {
				String strResult = StringReplaceFM.replaceString(df.getName(),
						df.getCountSql(), result);
				result.put(df.getName(), strResult);
			}
		}
		return result;
	}

	@Override
	public String importValues(String className, File f) {
		InputStream in = null;
		Workbook workbook = null;
		Map<String, String> data = null;
		String uuid = UUID.randomUUID().toString();
		try {
			in = new FileInputStream(f);
			workbook = WorkbookFactory.create(in);
			in.close();
			Sheet sheet0 = workbook.getSheetAt(0);
			int startRow = getHeadRowIndex(sheet0);

			List<Map> datas = readData2ListOfMap(sheet0, startRow);
			String sql = "INSERT INTO course_score "
					+ "(class_name, No, last_name, dept, v_p, v_score, j_p, j_score, t_count, t_score, "
					+ "d_count, d_score, total, batch_id  ) VALUES (  "
					+ ":className, :学号, :姓名, :行政班级, :视频完成率, :视频得分, :作业提交数, :作业得分, :测验提交数, :测验得分, "
					+ ":讨论提交数, :讨论得分, :总计, :batchId )";
			for (Map<String, String> row : datas) {
				row.put("batchId", uuid);
				row.put("className", className);
			}
			indexCalcDao.insertBatchMap(sql, datas);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return uuid;
	}

	private List<Map> readData2ListOfMap(Sheet sheet0, int startRow) {
		Map<Integer, String> headRow = readHeadRow(sheet0.getRow(startRow++));
		Map<Integer, String> headRow2 = readHeadRow(sheet0.getRow(startRow++));
		// 前两行加起来才是表头
		mearge(headRow, headRow2);
		System.out.println("-------------------headRow-------------");
		System.out.println(headRow2);
		List<Map> datas = new ArrayList<Map>();
		for (int i = startRow; i <= sheet0.getLastRowNum(); i++) {
			Map<String, String> data = readRow(headRow2, sheet0.getRow(i));
			datas.add(data);
		}
		return datas;
	}

	private void mearge(Map<Integer, String> headRow,
			Map<Integer, String> headRow2) {
		for (Entry<Integer, String> entry : headRow.entrySet()) {
			if (entry.getValue() != null && entry.getValue().contains("(")) {
				entry.setValue(entry.getValue().substring(0,
						entry.getValue().indexOf("(")));
			}
			if (StringUtils.isEmpty(entry.getValue())) {
				entry.setValue(headRow.get(entry.getKey() - 1));
			}

			if (!StringUtils.isEmpty(headRow2.get(entry.getKey()))) {
				headRow2.put(entry.getKey(),
						entry.getValue() + headRow2.get(entry.getKey()));
			} else {
				headRow2.put(entry.getKey(), entry.getValue());
			}
		}
	}

	private int getHeadRowIndex(Sheet sheet0) {
		int index = -1;
		for (int i = 0; i < sheet0.getLastRowNum(); i++) {
			Row row = sheet0.getRow(i);
			boolean emplyeeNumber = false;
			boolean emplyeeName = false;
			if (row != null) {
				Map<Integer, String> headRow = readHeadRow(row);
				System.out.println(index);
				System.out.println(headRow);
				for (Entry<Integer, String> t : headRow.entrySet()) {
					if ("学号".equals(t.getValue())) {
						emplyeeNumber = true;
						continue;
					}
					if ("姓名".equals(t.getValue())) {
						emplyeeName = true;
						continue;
					}

				}
				if (emplyeeNumber && emplyeeName) {
					index = i;
					break;
				}
			}
		}
		return index;
	}

	private Map<Integer, String> readHeadRow(Row row) {
		Map<Integer, String> teMap = new HashMap<Integer, String>();
		if (row != null) {
			int cellCount = row.getLastCellNum();
			for (int i = 0; i < cellCount; i++) {
				Cell cell = row.getCell(i);
				if (cell != null) {
					cell.setCellType(Cell.CELL_TYPE_STRING);
					teMap.put(
							i,
							cell.getStringCellValue().trim()
									.replaceAll("\\s", " "));
				} else {
					teMap.put(i, null);
				}
			}
		}
		return teMap;
	}

	private Map<String, String> readRow(Map<Integer, String> template,
			Row dataRow) {
		DateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Map<String, String> data = new HashMap<String, String>();
		for (Integer key : template.keySet()) {
			String headValue = template.get(key);
			if (!StringUtils.isEmpty(headValue)) {
				Cell cell = dataRow.getCell(key);
				if (cell != null) {
					String value = null;
					if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC
							&& HSSFDateUtil.isCellDateFormatted(cell)) {
						Date d = cell.getDateCellValue();
						value = formater.format(d);
					} else {
						cell.setCellType(Cell.CELL_TYPE_STRING);
						value = cell.getStringCellValue().trim()
								.replaceAll("\\n", "").replaceAll("\\r", "");
						if (value.endsWith("%")) {
							String v = value.substring(0, value.indexOf("%"));
							Double d = Double.valueOf(v) / 100;
							value = d.toString();
						}
					}
					data.put(headValue, value.trim());
				} else {
					data.put(headValue, null);
				}
			}
		}
		return data;

	}
}
