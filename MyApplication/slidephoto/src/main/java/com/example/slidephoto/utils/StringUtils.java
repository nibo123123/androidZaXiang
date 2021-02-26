package com.example.slidephoto.utils;

public class StringUtils {
	/** 判断字符串是否有值，如果为null或者是空字符串或者只有空格或者为"null"字符串，则返回true，否则则返回false */
	public static boolean isEmpty(String value) {
		if (value != null && !"".equalsIgnoreCase(value.trim())
				&& !"null".equalsIgnoreCase(value.trim())) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 判断当前是否为空
	 * @param objects
	 * @return
     */
	public static boolean isNull(Object... objects){
		boolean result = false;
		if(objects!=null || objects.length==0){
			result = true;
		}

		if(objects!=null || objects.length>0){
			for (int i = 0; i < objects.length; i++) {
				if(objects[i] != null){
					result = false;
					break;
				}
			}
		}
		return result;

	}

	public static boolean isNotNull(Object... objects){
		return !isNull(objects);
	}
}
