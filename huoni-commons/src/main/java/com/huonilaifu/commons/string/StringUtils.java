package com.huonilaifu.commons.string;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * 字符串工具类
 * @author xiongyan
 * @date 2015年11月2日 下午5:12:18
 * @history
 * 	<add>
 * 		@functionName com.common.util.StringUtils.replaceAll(String, String, String)
 *  	@author xiongyan
 *  	@date 2015年11月2日 下午5:12:18
 *  	@remark 详情见具体方法注释
 * 	</add>
 * 	<add>
 * 		@functionName com.common.util.StringUtils.split(String, String)
 *  	@author xiongyan
 *  	@date 2015年11月12日下午3:14:09
 *  	@remark 详情见具体方法注释
 * 	</add>
 * 	<add>
 * 		@functionName com.common.util.StringUtils.replaceStrByIndex(String, String, String)
 *  	@author gaoyang
 *  	@date 2015年11月17日下午3:14:09
 *  	@remark 详情见具体方法注释
 * 	</add>
 * 	<add>
 * 		@functionName com.common.util.StringUtils.generationRandomNum()
 *  	@author xiongyan
 *  	@date 2015年11月25日下午3:48:36
 *  	@remark 详情见具体方法注释
 * 	</add>
 * 	<upd> 
 * 		@functionName 
 *  	@author 
 *  	@date 2015年11月2日 下午5:12:18
 *  	@remark 
 * 	</upd>
 */
public class StringUtils {
	
	/**
	 * <summary>
	 * 去掉字符串末尾指定的字符。
	 * </summary>
	 * @author Xiongyan
	 * @return
	 * @Date 2015年8月6日
	 */
	public static String removeTailChar(String str,String c) {
		return (!"".equals(str) && str != null) && str.endsWith(c) ? str.substring(0, str.length() - 1) : str;
	}
	
	/**
	 * <summary>
	 * 对指定的字符串进行分割再组合。
	 * </summary>
	 * @author Xiongyan
	 * @return
	 * @Date 2015年8月6日
	 */
	public static String stringJointByChar(String str ,String c) {
		StringBuilder builder = new StringBuilder();
		String [] strs = str.split(c);
		for (String s : strs) {
			builder.append("'").append(s).append("',");
		}
		return removeTailChar(builder.toString() ,",");
	}
	
	/**
	 * <summary>
	 * 判断字符串是否为空。
	 * </summary>
	 * @author Xiongyan
	 * @return
	 * @Date 2015年8月6日
	 */
	public static boolean isEmpty(String str) {
		return str == null || "".equals(str) ? true : false;
	}
	
	/**
	 * <summary>
	 * 	处理HTML字符串的特殊字符
	 * </summary>
	 * @author Xiongyan
	 * @return
	 * @Date 2015年8月6日
	 */
	public static String htmlSpecialChars(String str) {
		str = str.replaceAll("&", "&amp;");
		str = str.replaceAll("<", "&lt;");
		str = str.replaceAll(">", "&gt;");
		str = str.replaceAll("\"", "&quot;");
		return str;
	}
	
	/**
	 * <summary>
	 * 获取文件扩展名
	 * </summary>
	 * @author Xiongyan
	 * @return
	 * @Date 2015年8月6日
	 */
	public static String getExtension (String filename) {
		if (isEmpty(filename)) {
			new RuntimeException("空文件名").getMessage();
			return null;
		}
		return filename.substring(filename.lastIndexOf(".") + 1, filename.length());
	}
	
	/**
	 * <summary>
	 * 将集合元素拼接成字符串
	 * </summary>
	 * @author Xiongyan
	 * @return
	 * @Date 2015年8月6日
	 */
	public static String listElmentToString(List<String> list ,String c/*拼接字符串，分隔字符*/) {
		StringBuilder builder = new StringBuilder();
		for (String string : list) {
			builder.append(string).append(c);
		}
		String str = builder.toString();
		return str.substring(0 ,str.length()-1);
	}
	
	/**
	 * <summary>
	 * 将字符串按照指定的字符进行分割，并以list进行返回
	 * </summary>
	 * @author Xiongyan
	 * @return
	 * @Date 2015年8月6日
	 */
	public static List<String> stringToList(String str ,String c) {
		String[] _str = null;
		if(!isEmpty(str)){
			_str = str.split(c);
		}else{
			new RuntimeException("参数为null").getStackTrace();
		}
		return Arrays.asList(_str);
	}
	
	/**
	 * 替换字符中指定标签为指定的值
	 * 
	 * @author Xiongyan
	 * @date 2015年8月28日 下午5:23:24
	 * @throws 
	 * @return String 返回类型
	 */
	public static String replaceLabel(String str, String label, String replaceVal) {
		StringBuilder builder = new StringBuilder(label);
		builder.insert(0, "\\");
		builder.insert(builder.length()-1, "\\");
		return str.replaceAll(builder.toString(), replaceVal);
	}
	
	/**
	 * 去除list集合中的重复数据，不论list中包含的是什么类型的数据。
	 * 
	 * @author Xiongyan
	 * @date 2015年8月25日 下午1:14:20
	 * @throws 
	 * @return void 返回类型
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void removeDuplicate(List list) {
        HashSet hs= new HashSet<Object>(list);
		list.clear();
		list.addAll(hs);  
	} 
	
	/**
	 * 将String字符串解析为JavaScript脚本并返回执行结果
	 * 
	 * @author Xiongyan
	 * @throws ScriptException 
	 * @date 2015年8月31日 上午9:07:20
	 * @throws 
	 * @return Object 返回类型
	 */
	public static Object strToJavascriptToResult (String strExp) throws ScriptException {
		ScriptEngineManager factory = new ScriptEngineManager();
		ScriptEngine engine = factory.getEngineByName("JavaScript");
		
		Object obj = engine.eval(strExp);
		if(null == obj) throw new NullPointerException();
		else return formartReVal(obj);
		
	}
	
	/**
	 * 通过java对象的全路径将其反射为java对象。
	 * 
	 * @author xiongyan
	 * @date 2015年10月8日上午10:33:34
	 * @throws 
	 * @param qualiname java对象全路径名
	 * @return
	 */
	public static Object qualiname2Obj(String qualiname) {
		Object object = null;
		try {
			object = Class.forName(qualiname).newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return object;
	}
	
	/**
	 * 
	 * 
	 * @author Xiongyan
	 * @date 2015年8月31日 上午11:12:23
	 * @throws 
	 * @return Object 返回类型
	 */
	public static Object formartReVal(Object obj) {
		DecimalFormat df = new DecimalFormat("######0.00"); 
		return df.format(obj);
	}
	
	/**
	 * 对字符文本中指定字符进行值替换。
	 * 	str         ：字符文本。
	 * 	regex       ：要替换的子字符。可以是子字符文本或是正则表达式。
	 * 				    注意在使用子字符指定时，内容中如果出现正则表达式中的特殊字符时，要先进行转义。
	 * 				    如指定替换字符中若出现“\^$*+?(){}[].:”中的任意一个，均要进行转义，如：{java:name}，转义为\\{java\\:name\\}
	 * 	replacement ：替换值。
	 * @author xiongyan
	 * @date 2015年11月2日下午4:30:36
	 * @param 
	 * @throws
	 * @return 
	 */ 
	public static String replaceAll(String str, String regex, String replacement) {
		return str.replaceAll(regex, replacement);
	}
	
	/**
	 * 将一个字符串按照指定的分分隔符进行分隔，并你List<String>集合返回。<br>
	 * 参数说明：<br>
	 * &emsp;str   ：操作的字符串<br>
	 * &emsp;regex ：指定的分隔符
	 * @author xiongyan
	 * @date 2015年11月12日下午3:14:09
	 * @param 
	 * @throws
	 * @return 
	 */
	public static List<String> split(String str, String regex) {
		if(!isEmpty(str) && !isEmpty(regex))
			return Arrays.asList(str.split(regex));
		else
			return null;
	}
	
	/**
	 * 替换文本第一次出现字符的值<br>
	 * 	str         ：字符文本。<br>
	 * 	regex       ：要替换的子字符。可以是子字符文本或是正则表达式。<br>
	 * 				    注意在使用子字符指定时，内容中如果出现正则表达式中的特殊字符时，要先进行转义。<br>
	 * 				    如指定替换字符中若出现“\^$*+?(){}[].:”中的任意一个，均要进行转义，如：{java:name}，转义为\\{java\\:name\\}<br>
	 * 	replacement ：替换值。<br>
	 * @author gaoyang
	 * @date 2015年11月17日下午4:30:36
	 * @param 
	 * @throws
	 * @return 
	 */ 
	public static String replaceStrByIndex(String str, String regex, String replacement) {
		int mentIndex = str.indexOf(regex);
		StringBuilder sb = new StringBuilder(str);
		sb = sb.replace(mentIndex, mentIndex + 1, replacement);
		return sb.toString();
	}
	
	/**
	 * <div style="color:green;font-weight: bolder;">
	 * 	<h3>生成随机数</h3>
	 * 	&emsp;&emsp;描述：生成100000-999999（包括）以内的任意一个随机数。
	 * </div>
	 * @author xiongyan
	 * @date 2015年11月25日下午3:48:36
	 * @param 
	 * @throws
	 * @return
	 */
	public static String generationRandomNum() {
		int max = 999999;
        int min = 100000;
        Random random = new Random();
        Integer randNum = random.nextInt(max) % (max - min + 1) + min;
		return randNum.toString();
	}
	
	public static String classpath() {
		String path = StringUtils.class.getClassLoader().getResource("").getPath();
		
		if(path.contains("WebRoot")){
			path = path.substring(1, path.indexOf("WebRoot"));
		} else if(path.contains("WebContent")){
			path = path.substring(1, path.indexOf("WebContent"));
		} else if(path.contains("target")){
			path = path.substring(1, path.indexOf("target"));
		} else if(path.contains("build")){
			path = path.substring(1, path.indexOf("build"));
		} else if(path.contains("WEB-INF")){
			path = path.substring(1, path.indexOf("WEB-INF"));
		} else if(path.contains("bin")){
			path = path.substring(1, path.indexOf("bin"));
		} else {
			try {
				throw new Exception("路径获取错误");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(path.contains("%20"))
			path = path.replaceAll("%20", " ");
		return path;
	}
	
	public static boolean isContainChinese(String str) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }
	
	public static Integer returnLengthByExtractChinese(String str) {
		StringBuilder strBuil = new StringBuilder();
		if(isContainChinese(str)){
			char[] charStr = str.toCharArray();
			for (char c : charStr) {
				if(isChinese(c)){
					strBuil.append(c);
				}
			}
		}
		return strBuil.toString().trim().length();
	}
	
	public static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (
			ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS 
			|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
			|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A 
			|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
			|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION 
			|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
			|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
			return true;
		}
		return false;
	}
	
	public static String getObjectClassName(Object o) {
		return o.getClass().getName().trim();
	}
	
	public static String getClassPath(String classpath) {
		classpath = classpath.replace("/", File.separator).replace("test-", "");
		classpath = classpath.substring(1, classpath.length());
		return classpath;
	}
}