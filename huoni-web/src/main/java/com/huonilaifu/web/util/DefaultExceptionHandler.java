package com.huonilaifu.web.util;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

@Component
public class DefaultExceptionHandler implements HandlerExceptionResolver,Ordered {
	private static Logger logger = LoggerFactory.getLogger(DefaultExceptionHandler.class);

	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		ModelAndView mv = new ModelAndView();
		/* 使用response返回 */
		response.setStatus(HttpStatus.OK.value()); // 设置状态码
		response.setContentType(MediaType.APPLICATION_JSON_VALUE); // 设置ContentType
		response.setCharacterEncoding("UTF-8"); // 避免乱码
		response.setHeader("Cache-Control", "no-cache, must-revalidate");
		try {
			if (ex instanceof MissingServletRequestParameterException || ex instanceof HttpMessageNotReadableException) {
				// 缺少参数
				response.getWriter().write(ResultStatusEnum.MISS_PARAM_ERROR.toString());
			} else if (ex instanceof MethodArgumentTypeMismatchException) {
				response.getWriter().write(ResultStatusEnum.PARAM_TYPE_ERROR.toString());
			} else if (ex instanceof HttpMediaTypeNotSupportedException) {
				response.getWriter().write(ResultStatusEnum.MEDIA_TYPE_NOT_SUPPORT.toString());
			}else if (ex instanceof HttpRequestMethodNotSupportedException) {
				response.getWriter().write(ResultStatusEnum.REQUEST_METHOD_NOT_SUPPORT.toString());
			} else {
				// 其他错误
				response.getWriter().write(ResultStatusEnum.SERVER_ERROR.toString());
			}
		} catch (IOException e) {
			logger.error("与客户端通讯异常:" + e.getMessage(), e);
		}

		logger.debug("异常:" + ex.getMessage(), ex);
		return mv;
	}


	public int getOrder() {
		return 0;
	}
}
