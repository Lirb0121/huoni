package com.huonilaifu.upload.util;

import com.huonilaifu.commons.string.StringUtil;
import org.apache.commons.lang3.StringUtils;


public enum ExcelUploadTypeEnum {

	COMPANY_MEMBER(1, "企业成员",new String[]{"成员","拼音","手机号","入住日期","平台账户","身份","权限","是否在职"});

	private int type;
	private int column;
	private String table;
	private String[] title;

	ExcelUploadTypeEnum(int type, String table,String[] title) {
		this.type = type;
		this.table = table;
		this.title = title;
		this.column = title.length;
	}

	public int getType() {
		return type;
	}

	public String getTable() {
		return table;
	}
	
	public int getColumn() {
		return column;
	}
	
	/**
	 * 验证excel数据的头信息是否正确
	 * @param title
	 * @return
	 */
	public boolean verifyTitleData(String[] title) {
		int len = this.title.length;
		if (title != null && title.length >= len) {
			for (int i=0;i<len;i++) {
				if (!StringUtils.equals(this.title[i], title[i])) {
					System.out.println(this.title[i] + "   " + title[i]);
					return false;
				}
			}
			return true;
		}
		return false;
	}

	public static ExcelUploadTypeEnum getByType(Integer type) {
		if (type != null) {
			int typeInt = type.intValue();
			ExcelUploadTypeEnum[] values = ExcelUploadTypeEnum.values();
			for (ExcelUploadTypeEnum eu : values) {
				if (eu.getType() == typeInt) {
					return eu;
				}
			}
		}
		return null;
	}
	
	public static void main(String[] args) {
		ExcelUploadTypeEnum[] values = ExcelUploadTypeEnum.values();
		for (ExcelUploadTypeEnum v:values) {
			System.out.print(v.getType() + ":" + v.getTable() +",");
		}
		
	}
	
	/**
	 * 简单验证单行数据
	 * @param data
	 * @param excelType
	 * @param response
	 * @param lineNum	当前检测所在excel的行数
	 * @return	true:存在错误  false:数据正常
	 */
	public static boolean verifyData(String[] data,ExcelUploadTypeEnum excelType,ImportDataResponse response,int lineNum) {
		if (StringUtil.isAllBlank(data)){
			response.incrFail();
			response.pushFailItem(excelType.getTable(), lineNum, "整行数据为空");
			return true;
		}
		if (data.length < excelType.column) {
			response.incrFail();
			response.pushFailItem(excelType.getTable(), lineNum, "行数据单元格数据不足");
			return true;
		}
		
		return false;
	}

}
