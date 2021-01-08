package com.kodark.news.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/*
 * title : 모든 요청과 응답을 로깅
 * dec : 모든 요청과 응답의 url과 ip를 로그에 남긴다.
 * 작성자 : 류제욱
 * 작성일 : 2020-01-06
 */
@Component
public class LogInterceptor implements HandlerInterceptor {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if(logger.isInfoEnabled()) 
			logger.info("Request : '{}' From : '{}'", request.getRequestURI(), request.getRemoteAddr());
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		if(logger.isInfoEnabled()) 
			if(modelAndView != null)
				logger.info("Processed Request : '{}' From : '{}'. Called view : {}"
						, request.getRequestURI(), request.getRemoteAddr(), modelAndView.getViewName());			
			else
				logger.info("Processed Request : '{}' From : '{}'. There is nothing to call."
						, request.getRequestURI(), request.getRemoteAddr());
	}
	
}
