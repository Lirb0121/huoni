package com.huonilaifu.commons;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtil {
	private final static Logger logger = LoggerFactory.getLogger(FileUtil.class);
	private final static String[] EXCEL_CONTENT_TYPE = new String[] {
			"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "application/octet-stream" ,"application/vnd.ms-excel"};
	private final static String[] EXCEL_SUFFIX = new String[] { ".xls", ".xlsx" };

	/**
	 * 判断文件名称是否是excel的文件后缀
	 * 
	 * @param fileName
	 * @return
	 */
	public static boolean isExcelSuffixFile(String fileName) {
		for (String suffix : EXCEL_SUFFIX) {
			if (StringUtils.endsWithIgnoreCase(fileName, suffix)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断文件名称类型是否是excel的文件类型
	 * 
	 * @param contentType
	 * @return
	 */
	public static boolean isExcelContentType(String contentType) {
		for (String ct : EXCEL_CONTENT_TYPE) {
			if (StringUtils.equalsIgnoreCase(contentType, ct)) {
				return true;
			}
		}
		return false;
	}

	public static String fileNameAppendRandomStr(String fileName) {
		String uuidStr = UUID.randomUUID().toString();

		int pointIndex = fileName.lastIndexOf(".");

		StringBuilder sBuilder = new StringBuilder();
		if (pointIndex != -1) {
			sBuilder.append(fileName.substring(0, pointIndex));
			sBuilder.append(uuidStr);
			sBuilder.append(".").append(fileName.substring(pointIndex + 1));
		} else {
			sBuilder.append(fileName).append(uuidStr);
		}

		return sBuilder.toString();
	}

	public static File saveFile(InputStream is, String parentPath, String originName) throws IOException {
		File saveFile = null;
		String randomFileName = null;
		do {
			randomFileName = FileUtil.fileNameAppendRandomStr(originName);
			saveFile = new File(parentPath, randomFileName);
			if (!(saveFile.exists() && saveFile.isFile())) {
				break;
			}
		} while (true);
		FileUtils.copyInputStreamToFile(is, saveFile);
		return saveFile;
	}

	/**
	 * 
	 * 删除父级目录下的所有超过10分钟的文件
	 * 
	 * @param parentPath
	 */
	public static int delBefore2HourFiles(String parentPath) {
		int result = 0;
		File file = new File(parentPath);
		if (file.exists() && file.isDirectory()) {
			File[] files = file.listFiles();
			for (File f : files) {
				long currentTimeMillis = System.currentTimeMillis();
				if (FileUtils.isFileOlder(f, currentTimeMillis - _2HOUR)) {
					try {
						FileUtils.forceDelete(f);
					} catch (IOException e) {
						logger.error(e.getMessage());
					}
					result++;
				}
			}
		}
		return result;
	}
	

	private final static long _2HOUR = 2 * 60 * 60 * 1000;
	
	
}
