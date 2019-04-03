package com.huonilaifu.commons;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ExcelReader {
	private static final Logger logger = LoggerFactory.getLogger(ExcelReader.class);

	public String[] readExcelTitle(InputStream inp)
			throws EncryptedDocumentException, InvalidFormatException, IOException {
		if (inp == null) {
			return null;
		}
		Workbook workbook = WorkbookFactory.create(inp);
		Sheet shell = workbook.getSheetAt(0);
		short topRow = shell.getTopRow();
		return readExcelRowContent(shell, topRow);
	}
	
	public List<String[]> readExcel(File file)
			throws EncryptedDocumentException, InvalidFormatException, IOException {
		return readExcel(new FileInputStream(file));
	}

	public List<String[]> readExcel(InputStream inp)
			throws EncryptedDocumentException, InvalidFormatException, IOException {
		List<String[]> list = new ArrayList<String[]>();
		if (inp != null) {
			Workbook workbook = WorkbookFactory.create(inp);
			list.addAll(readExcel(workbook, 0));
		}
		return list;
	}

	public List<String[]> readExcel(Workbook workbook, int startRowNum)
			throws EncryptedDocumentException, InvalidFormatException, IOException {
		List<String[]> list = new ArrayList<String[]>();
		Sheet shell = workbook.getSheetAt(0);
		int firstRowNum = shell.getFirstRowNum();
		int lastRowNum = shell.getLastRowNum();
		for (int i = startRowNum < firstRowNum ? firstRowNum : startRowNum; i <= lastRowNum; i++) {
			Row row = shell.getRow(i);
			if (row != null) {
				list.add(readExcelRowContent(row));
			} else {
				list.add(null);
			}
		}
		return list;
	}

	public List<String[]> readExcelBody(InputStream inp)
			throws EncryptedDocumentException, InvalidFormatException, IOException {
		List<String[]> list = new ArrayList<String[]>();
		if (inp != null) {
			Workbook workbook = WorkbookFactory.create(inp);
			Sheet shell = workbook.getSheetAt(0);
			short topRow = shell.getTopRow();
			list.addAll(readExcel(workbook, topRow + 1));
		}
		return list;
	}

	private String[] readExcelRowContent(Sheet shell, int rowNum)
			throws EncryptedDocumentException, InvalidFormatException, IOException {
		Row row = shell.getRow(rowNum);
		return readExcelRowContent(row);
	}

	private String[] readExcelRowContent(Row row)
			throws EncryptedDocumentException, InvalidFormatException, IOException {
		int cells = row.getPhysicalNumberOfCells();
		String[] result = null;
		if (cells > 0) {
			result = new String[cells];
			for (int i = 0; i < cells; i++) {
				Cell cell = row.getCell(i);
				result[i] = getCellFormatValue(cell);
			}
		}
		return result;
	}

	/**
	 * 根据HSSFCell类型设置数据
	 * 
	 * @param cell
	 * @return
	 */
	private String getCellFormatValue(Cell cell) {
		String cellvalue = null;
		if (cell != null) {
			switch (cell.getCellTypeEnum()) {
			case STRING:
				cellvalue = cell.getStringCellValue();
				break;
			case NUMERIC:
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					Date date = cell.getDateCellValue();
					cellvalue = DateUtil.formatDateToString(date, DateUtil.FULL_FORMAT_STR);
				} else {
					cellvalue = String.valueOf(cell.getNumericCellValue());
				}
				break;
			case FORMULA: // 公式或日期等
				try {
					if (HSSFDateUtil.isCellDateFormatted(cell)) {
						Date date = cell.getDateCellValue();
						cellvalue = DateUtil.formatDateToString(date, null);
					} else {
						cellvalue = String.valueOf(cell.getNumericCellValue());
					}
				} catch (IllegalStateException e) {
					cellvalue = String.valueOf(cell.getRichStringCellValue());
				}
				break;
			case BOOLEAN:
				cellvalue = String.valueOf(cell.getBooleanCellValue());
				break;
			default:
				cellvalue = null;
			}
		}
		return StringUtils.trimToNull(cellvalue);
	}
	
	public static void main(String[] args) {
		String str = "项目开始　 ";
		System.out.println(str.length() + str);
		System.out.println(str.trim().length());
//		str = StringUtils.trimToNull(str);
		str = "  项目  \r\n开始       ".replaceAll("\\s", "");
		System.out.println(str.length() + str);
		
		System.out.println("项目开始".length());
	}
	


}
