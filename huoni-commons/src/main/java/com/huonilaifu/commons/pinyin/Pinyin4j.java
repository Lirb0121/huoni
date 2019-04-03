package com.huonilaifu.commons.pinyin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class Pinyin4j {
	private static Map<String, List<String>> pinyinMap = new HashMap<String, List<String>>();
	
	/**
	 * <div style="color:green;font-weight: bolder;">
	 * 	<h3></h3>
	 * 	&emsp;&emsp;描述：获取汉字转拼音的结果
	 * </div>
	 * @author xiongyan
	 * @date 2016年3月8日上午10:42:21
	 * @param 
	 * @throws
	 * @return
	 */
	public static String getPinyin(String str) {
		initPinyin("/duoyinzi_dic.txt");
		return convertChineseToPinyin(str);
	}
	
	/**
	 * <div style="color:green;font-weight: bolder;">
	 * 	<h3></h3>
	 * 	&emsp;&emsp;描述：将某个字符串的首字母 大写
	 * </div>
	 * @author xiongyan
	 * @date 2016年3月8日上午10:23:11
	 * @param 
	 * @throws
	 * @return
	 */
	public static String convertInitialToUpperCase(String str){
		if(str==null){
			return null;
		}
		StringBuffer sb = new StringBuffer();
		char[] arr = str.toCharArray();
		for(int i=0;i<arr.length;i++){
			char ch = arr[i];
			if(i==0){
				sb.append(String.valueOf(ch).toUpperCase());
			}else{
				sb.append(ch);
			}
		}
		return sb.toString();
	}

	/**
	 * <div style="color:green;font-weight: bolder;">
	 * 	<h3></h3>
	 * 	&emsp;&emsp;描述：汉字转拼音 最大匹配优先
	 * </div>
	 * @author xiongyan
	 * @date 2016年3月8日上午10:23:28
	 * @param 
	 * @throws
	 * @return
	 */
	public static String convertChineseToPinyin(String chinese) {
		StringBuffer pinyin = new StringBuffer();

		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);

		char[] arr = chinese.toCharArray();

		for (int i = 0; i < arr.length; i++) {
			char ch = arr[i];
			if (ch > 128) { // 非ASCII码
				// 取得当前汉字的所有全拼
				try {
					String[] results = PinyinHelper.toHanyuPinyinStringArray(ch, defaultFormat);
					if (results == null) {	//非中文
						return "";
					} else {
						int len = results.length;
						if (len == 1) { // 不是多音字
							String py = results[0];		
							if(py.contains("u:")){	//过滤 u:
								py = py.replace("u:", "v");
							}
							pinyin.append(convertInitialToUpperCase(py));
						}else if(results[0].equals(results[1])){	//非多音字 有多个音，取第一个
							pinyin.append(convertInitialToUpperCase(results[0]));
						}else { // 多音字
							int length = chinese.length();
							boolean flag = false;
							String s = null;
							List<String> keyList =null;
							for (int x = 0; x < len; x++) {
								String py = results[x];
								if(py.contains("u:")){	//过滤 u:
									py = py.replace("u:", "v");
								}
								keyList = pinyinMap.get(py);
								if (i + 3 <= length) {	//后向匹配2个汉字  大西洋 
									s = chinese.substring(i, i + 3);
									if (keyList != null && (keyList.contains(s))) {
										pinyin.append(convertInitialToUpperCase(py));
										flag = true;
										break;
									}
								}
								
								if (i + 2 <= length) {	//后向匹配 1个汉字  大西
									s = chinese.substring(i, i + 2);
									if (keyList != null && (keyList.contains(s))) {
										pinyin.append(convertInitialToUpperCase(py));
										flag = true;
										break;
									}
								}
								
								if ((i - 2 >= 0) && (i+1<=length)) {	// 前向匹配2个汉字 龙固大
									s = chinese.substring(i - 2, i+1);
									if (keyList != null && (keyList.contains(s))) {
										pinyin.append(convertInitialToUpperCase(py));
										flag = true;
										break;
									}
								}
								
								if ((i - 1 >= 0) && (i+1<=length)) {	// 前向匹配1个汉字   固大
									s = chinese.substring(i - 1, i+1);
									if (keyList != null && (keyList.contains(s))) {
										pinyin.append(convertInitialToUpperCase(py));
										flag = true;
										break;
									}
								}
								
								if ((i - 1 >= 0) && (i+2<=length)) {	//前向1个，后向1个      固大西
									s = chinese.substring(i - 1, i+2);
									if (keyList != null && (keyList.contains(s))) {
										pinyin.append(convertInitialToUpperCase(py));
										flag = true;
										break;
									}
								}
							}
							
							if (!flag) {	//都没有找到，匹配默认的 读音  大 
								s = String.valueOf(ch);
								for (int x = 0; x < len; x++) {
									String py = results[x];
									if(py.contains("u:")){	//过滤 u:
										py = py.replace("u:", "v");
									}
									keyList = pinyinMap.get(py);
									if (keyList != null && (keyList.contains(s))) {
										pinyin.append(convertInitialToUpperCase(py));//拼音首字母 大写
										break;
									}
								}
							}
						}
					}
				} catch (BadHanyuPinyinOutputFormatCombination e) {
					e.printStackTrace();
				}
			} else {
				pinyin.append(arr[i]);
			}
		}
		return pinyin.toString();
	}

	/**
	 * <div style="color:green;font-weight: bolder;">
	 * 	<h3></h3>
	 * 	&emsp;&emsp;描述：初始化 所有的多音字词组
	 * </div>
	 * @author xiongyan
	 * @date 2016年3月8日上午10:23:50
	 * @param 
	 * @throws
	 * @return
	 */
	public static void initPinyin(String fileName) {
		// 读取多音字的全部拼音表;
		InputStream file = PinyinHelper.class.getResourceAsStream(fileName);
		BufferedReader br = null;
		
		try {
			br = new BufferedReader(new InputStreamReader(file, "utf-8"));
			
			String s = null;
			while ((s = br.readLine()) != null) {
				if (s != null) {
					String[] arr = s.split("#");
					String pinyin = arr[0];
					String chinese = arr[1];

					if(chinese!=null){
						String[] strs = chinese.split(" ");
						List<String> list = Arrays.asList(strs);
						pinyinMap.put(pinyin, list);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}