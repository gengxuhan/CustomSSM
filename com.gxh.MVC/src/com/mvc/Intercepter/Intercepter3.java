package com.mvc.Intercepter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Intercepter3 extends IntercepterAdpter  {

	private String username;
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
	
		System.out.println(username);
		
		return true;
	}



}
