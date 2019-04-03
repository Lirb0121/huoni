package com.huonilaifu.commons.string;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;


/**
 * <div style="color:green;font-weight: bolder;">
 * 	<h3>java字符串操作</h3>
 * 	&emsp;&emsp;描述：集合java开发中常见的字符串操作工具。
 * </div>
 * @author xiongyan
 * @email  javadevyan@163.com
 * @date 2016年2月25日 下午1:46:59
 * @history
 * 	<add>
 * 		@functionName 
 *  	@author 
 *  	@date 2016年2月25日 下午1:46:59
 *  	@remark 
 * 	</add>
 * 	<upd> 
 * 		@functionName 
 *  	@author 
 *  	@date 2016年2月25日 下午1:46:59
 *  	@remark 
 * 	</upd>
 */
public class StringUtil {
	static boolean boo;
	
	/**
	 * 把Ψ和替换回\n和\r
	 * @param str
	 * @return
	 */
	public static String replaceBackBlankLineCharacter(String str) {
		str = str.replace("<extnewline />", "\n").replace("<extenter />", "\r").replace("''", "'");
		return str;
	}
	
	public static String formatStaticRootPath(String rootPath){
		if (rootPath == null) return "";
		if (StringUtil.isBlank(rootPath)) return "";
		
		rootPath = rootPath.replace("\\", "/");
		if (rootPath.endsWith("/")) {
			rootPath = rootPath.substring(0, rootPath.length() - 1);
		}
		
		return rootPath;
	}
	
	/**  
     *设置首字母为大写  
     */  
	public static String capitalize(String str) {  
	    return changeFirstCharacterCase(str, true);  
	}  
	
	/**  
	     *设置str首字母为小写  
	 */  
	public static String uncapitalize(String str) {  
	    return changeFirstCharacterCase(str, false);  
	}  
	
	private static String changeFirstCharacterCase(String str, boolean capitalize) {  
	    if (str == null || str.length() == 0) {  
	        return str;  
	    }  
	    StringBuilder sb = new StringBuilder(str.length());  
	    if (capitalize) {//如果首字母要求大写的话  
	        sb.append(Character.toUpperCase(str.charAt(0)));  
	    }  
	    else {   //否则首字母设置为小写  
	        sb.append(Character.toLowerCase(str.charAt(0)));  
	    }  
	            //拼接首字母剩下的字符串  
	    sb.append(str.substring(1));  
	    return sb.toString();  
	}  
	
	/**
	 * <div style="color:green;font-weight: bolder;">
	 * 	<h3>判空</h3>
	 * 	&emsp;&emsp;描述：判断数据的字符串是否空字符/null。true：是空字符串/null，false：不是空字符串/null。
	 * </div>
	 * @author xiongyan
	 * @date 2016年2月25日下午1:48:37
	 * @param 
	 * @throws
	 * @return
	 */
	public static boolean isEmpty(String str) {
		return str == null || "".equals(str) ? true : false;
	}
	
	public static boolean isNotBlank(String str) {
		if (str == null) return false;
		if (str.equals("")) return false;
		if (str.trim().length() <= 0) return false;
		if (trimToEmpty(str).equals("")) return false;
		
		return true;
	}
	
	public static boolean isBlank(String str) {
		return !isNotBlank(str);
	}
	
	/**
	 * 字符串去两端空格，null替换成""
	 * @param str
	 * @return
	 */
	public static String trimToEmpty(String str) {
		if (str == null) return "";
		if (str.equals("")) return "";
		if (str.trim().length() <= 0) return "";
		
		str = str.trim();
		return str.equals("null")? "": str;
	}

	/**
	 * <div style="color:green;font-weight: bolder;">
	 * 	<h3>获取扩展名</h3>
	 * 	&emsp;&emsp;描述：根据输入的文件名，获取其扩展名。如输入的文件名为filename.name，则返回扩展名name。
	 * </div>
	 * @author xiongyan
	 * @date 2016年2月25日下午1:50:27
	 * @param 
	 * @throws
	 * @return
	 */
	public static String getExtension(String filename) {
		if (isEmpty(filename)) {
			new RuntimeException("空文件名").getMessage();
			return null;
		} else {
			if (filename.contains("."))
				return filename.substring(filename.lastIndexOf(".") + 1, filename.length());
			else
				return null;
		}
	}
	
	/**
	 * <div style="color:green;font-weight: bolder;">
	 * 	<h3>获取引用对象的类型</h3>
	 * 	&emsp;&emsp;描述：
	 * </div>
	 * @author xiongyan
	 * @date 2016年2月25日下午1:53:07
	 * @param 
	 * @throws
	 * @return
	 */
	public static String getObjectClassName(Object o) {
		return o.getClass().getName().trim();
	}
	
	/**
	 * <div style="color:green;font-weight: bolder;">
	 * 	<h3></h3>
	 * 	&emsp;&emsp;描述：获取工程的根目录。
	 * </div>
	 * @author xiongyan
	 * @date 2016年2月25日下午1:54:00
	 * @param 
	 * @throws
	 * @return
	 */
	public static String classpath() {
		String path = StringUtil.class.getClassLoader().getResource("").getPath();
		
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
	
	/**
	 * <div style="color:green;font-weight: bolder;">
	 * 	<h3>中文操作</h3>
	 * 	&emsp;&emsp;描述：判断字符串中是否包含中文字符。true：包含，false：不包含
	 * </div>
	 * @author xiongyan
	 * @date 2016年2月25日上午10:07:10
	 * @param 
	 * @throws
	 * @return
	 */
	public static boolean isContainChinese(String str) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }
	
	/**
	 * <div style="color:green;font-weight: bolder;">
	 * 	<h3>中文操作</h3>
	 * 	&emsp;&emsp;描述：根据Unicode编码完美的判断中文汉字和符号。true：是，false：不是
	 * </div>
	 * @author xiongyan
	 * @date 2016年2月25日上午10:18:49
	 * @param 
	 * @throws
	 * @return
	 */
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
	
	/**
	 * <div style="color:green;font-weight: bolder;">
	 * 	<h3>中文操作</h3>
	 * 	&emsp;&emsp;描述：提取字符串中所包含的中文。
	 * </div>
	 * @author xiongyan
	 * @date 2016年2月25日上午10:55:23
	 * @param 
	 * @throws
	 * @return
	 */
	public static String extractChinese(String str) {
		StringBuilder strBuil = new StringBuilder();
		if(isContainChinese(str)){
			char[] charStr = str.toCharArray();
			for (char c : charStr) {
				if(isChinese(c)){
					strBuil.append(c);
				}
			}
		}
		return strBuil.toString().trim();
	}
	
	/**
	 * <div style="color:green;font-weight: bolder;">
	 * 	<h3>中文操作</h3>
	 * 	&emsp;&emsp;描述：提取字符串中所包含的中文，并返回其长度。
	 * </div>
	 * @author xiongyan
	 * @date 2016年2月25日上午10:55:23
	 * @param 
	 * @throws
	 * @return
	 */
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
	
	/**
	 * 对字符文本中指定字符进行值替换。<br>
	 * 	str         ：字符文本。<br>
	 * 	regex       ：要替换的子字符。可以是子字符文本或是正则表达式。<br>
	 * 				    注意在使用子字符指定时，内容中如果出现正则表达式中的特殊字符时，要先进行转义。<br>
	 * 				    如指定替换字符中若出现“\^$*+?(){}[].:”中的任意一个，均要进行转义，如：{java:name}，转义为\\{java\\:name\\}<br>
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
	public static String replaceStrByFirstAppear(String str, String regex, String replacement) {
		int step = 1;
		if(regex.contains("{") ||
		   regex.contains("}") ||
		   regex.contains("[") ||
		   regex.contains("]") ||
		   regex.contains("(") ||
		   regex.contains(")") ||
		   regex.contains("$") ||
		   regex.contains("*") ||
		   regex.contains(".") ||
		   regex.contains("?") ||
		   regex.contains("+") ||
		   regex.contains("\\") ||
		   regex.contains("^") ||
		   regex.contains("|") ||
		   regex.contains("/") ||
		   regex.contains(":")){
			step = regex.length();
		}
		int mentIndex = str.indexOf(regex);
		StringBuilder strbuil = new StringBuilder(str);
		strbuil = strbuil.replace(mentIndex, mentIndex + step, replacement);
		return strbuil.toString();
	}
	
	/**
	 * <div style="color:green;font-weight: bolder;">
	 * 	<h3>可执行性脚本</h3>
	 * 	&emsp;&emsp;描述：执行可运算的计算脚本并返回计算结果
	 * </div>
	 * @author xiongyan
	 * @date 2016年2月26日下午5:14:36
	 * @param 
	 * @throws
	 * @return
	 */
	public static Object scriptEngine(String script) {
		ScriptEngineManager factory = new ScriptEngineManager();
		ScriptEngine engine = factory.getEngineByName("JavaScript");
		
		Object obj = null;
		try {
			obj = engine.eval(script);
		} catch (ScriptException e) {
			e.printStackTrace();
		}
		return obj;
	}
	
	/**
	 * <div style="color:green;font-weight: bolder;">
	 * 	<h3></h3>
	 * 	&emsp;&emsp;描述：生成UUID字符串并去掉“-”
	 * </div>
	 * @author xiongyan
	 * @date 2016年2月29日上午9:39:16
	 * @param 
	 * @throws
	 * @return
	 */
	public static String generaterUUIDStr() {
		return replaceAll(UUID.randomUUID().toString(), "-", "");
	}
	
	/**
	 * <div style="color:green;font-weight: bolder;">
	 * 	<h3></h3>
	 * 	&emsp;&emsp;描述：获取项目的classpath路径
	 * </div>
	 * @author xiongyan
	 * @email javadevyan@163.com
	 * @date 2016年4月7日上午11:24:55
	 * @param 
	 * @throws
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String getClassPath(Class clazz) {
		String classpath = clazz.getClassLoader().getResource("").getPath();
		if(classpath.contains("%20")){
			classpath = classpath.replace("%20", " ");
		}
		classpath = classpath.replace("/", File.separator).replace("test-", "");
		classpath = classpath.substring(1, classpath.length());
		return classpath;
	}
	
	/*public static char randomGroupName() {
		String groupName = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		return groupName.charAt(MathUtil.randomInt(groupName.length()));
	}*/
	
	/**
	 * <div style="color:green;font-weight: bolder;">
	 * 	<h3></h3>
	 * 	&emsp;&emsp;描述：将数据库表字段名转换为javabean属性名。比如login_time --> loginTime
	 * </div>
	 * @author xiongyan
	 * @email javadevyan@163.com
	 * @date 2016年4月26日上午9:15:42
	 * @param 
	 * @throws
	 * @return
	 */
	public static String dbFiled2JavaFiled(String str, String biaozh) {
		StringBuilder builder = new StringBuilder();
		if(str.contains(biaozh)){
			String [] strArray = str.split(biaozh);
			char [] biaozhArr = biaozh.toCharArray();
			final char _biaozh = biaozhArr[0];//将字符串转换为char []
			
			/**
			 * 判断第一个字符是否为给定的biaozh
			 */
			if(_biaozh == str.charAt(0)){
				builder.append(biaozh);
				builder.append(strArray[1]);
				boo = true;
			} else {
				builder.append(strArray[0]);
			}
			
			for (int i = 0; i < strArray.length; i++) {
				if(boo){
					if(i > 1) append(builder, strArray, i);
				} else {
					if(i > 0) append(builder, strArray, i);
				}
			}
			
			/**
			 * 判断最后一个字符是否为给定的biaozh
			 */
			if(_biaozh == str.charAt(str.length() - 1)){
				builder.append(biaozh);
			}
		}
		
		return builder.toString();
	}

	/**
	 * <div style="color:green;font-weight: bolder;">
	 * 	<h3></h3>
	 * 	&emsp;&emsp;描述：为dbFiled2JavaFiled方法服务
	 * </div>
	 * @author xiongyan
	 * @email javadevyan@163.com
	 * @date 2016年4月26日上午9:47:11
	 * @param 
	 * @throws
	 * @return
	 */
	private static void append(StringBuilder builder, String[] strArray, int i) {
		String subStr = strArray[i];
		String firstChar = subStr.substring(0, 1);
		String otherChar = subStr.substring(1);
		builder.append(firstChar.toUpperCase()).append(otherChar);
	}
	
	/**
	 * <div style="color:green;font-weight: bolder;">
	 * 	<h3></h3>
	 * 	&emsp;&emsp;描述：将字符串转换成二进制字符串，以空格相隔
	 * </div>
	 * @author xiongyan
	 * @date 2016年5月31日下午4:00:27
	 * @param 
	 * @throws
	 * @return
	 */
	public static String strToBinstr(String str) {
        char[] strChar=str.toCharArray();
        String result="";
        for(int i=0;i<strChar.length;i++){
            result += Integer.toBinaryString(strChar[i])/*+ " "*/;
        }
        return result;
    }
	
    /**
     * <div style="color:green;font-weight: bolder;">
     * 	<h3></h3>
     * 	&emsp;&emsp;描述：将二进制字符串转换成Unicode字符串
     * </div>
     * @author xiongyan
     * @date 2016年5月31日下午4:00:42
     * @param 
     * @throws
     * @return
     */
	public static String binstrToStr(String binStr) {
        String[] tempStr=strToStrArray(binStr);
        char[] tempChar=new char[tempStr.length];
        for(int i=0;i<tempStr.length;i++) {
            tempChar[i]=binstrToChar(tempStr[i]);
        }
        return String.valueOf(tempChar);
    }
	
	//将初始二进制字符串转换成字符串数组，以空格相隔
    private static String[] strToStrArray(String str) {
        return str.split(" ");
    }
    
    //将二进制字符串转换为char
    private static char binstrToChar(String binStr){
        int[] temp=binstrToIntArray(binStr);
        int sum=0;   
        for(int i=0; i<temp.length;i++){
            sum +=temp[temp.length-1-i]<<i;
        }   
        return (char)sum;
    }
    
    //将二进制字符串转换成int数组
    private static int[] binstrToIntArray(String binStr) {       
        char[] temp=binStr.toCharArray();
        int[] result=new int[temp.length];   
        for(int i=0;i<temp.length;i++) {
            result[i]=temp[i]-48;
        }
        return result;
    }

    /**
     * <summary>
     * 将字符串按照指定的字符进行分割，并以list进行返回
     * </summary>
     * @author Xiongyan
     * @return
     * @Date 2015年8月6日
     */
	public static List<String> string2List(String str, String c) {
		String[] _str = null;
		if(!isEmpty(str)){
			_str = str.split(c);
		}else{
			new RuntimeException("参数为null").getStackTrace();
		}
		return Arrays.asList(_str);
	}
    
	
	public static boolean isAllBlank(final CharSequence... css) {
		if (ArrayUtils.isEmpty(css)) {
			return true;
		}
		for (final CharSequence cs : css) {
			if (StringUtils.isNotBlank(cs)) {
				return false;
			}
		}
		return true;
	}
	
}