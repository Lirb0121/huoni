package com.huonilaifu.web.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 统一返回的结果集合
 */
public class Result<T> {
	private int code;
	private String msg;
	private T data;

	public Result() {
		this(ResultStatusEnum.SUCCESS);
	}
	
	public Result(ResultStatusEnum resultStatus) {
		this.code = resultStatus.getCode();
		this.msg = resultStatus.getMsg();
	}

	public Result(T data) {
		this.setStatus(ResultStatusEnum.SUCCESS);
		this.data = data;
	}

	public Result<T> setStatus(ResultStatusEnum resultStatus) {
		this.code = resultStatus.getCode();
		this.msg = resultStatus.getMsg();
		return this;
	}

	public int getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

	public T getData() {
		return data;
	}

	public Result<T> setData(T data) {
		this.data = data;
		return this;
	}

	@Override
	public String toString() {
		return "Result [code=" + code + ", msg=" + msg + ", data=" + data + "]";
	}

}
