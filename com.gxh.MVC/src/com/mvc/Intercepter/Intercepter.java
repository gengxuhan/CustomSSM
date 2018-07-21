package com.mvc.Intercepter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Intercepter {
	
	
	boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
		    throws Exception;
	
	void postHandle(
			HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception;

}
