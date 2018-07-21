package com.mvc.Intercepter;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class IntercepterChain {
	
private List<Intercepter> intercepters;

private Object handler;

private int intercepterindex=-1;

public IntercepterChain(List<Intercepter> intercepters,Object handler){
		this.intercepters=intercepters;
		this.handler=handler;		
	}


public boolean preHandle(HttpServletRequest request, HttpServletResponse response)throws Exception{
	if(intercepters !=null){
		
		for (int i = 0; i < intercepters.size(); i++) {
			boolean preHandle = intercepters.get(i).preHandle(request, response, handler);
			if(!preHandle){
				return false;
			
			}	
		}
		
	}
	return true;
	
}

public void PostHandle(HttpServletRequest request, HttpServletResponse response) throws Exception {
	if (intercepters == null) {
		return;
	}
	for (int i = intercepters.size() - 1; i >= 0; i--) {
		Intercepter intercepter = intercepters.get(i);
		intercepter.postHandle(request, response,handler);
	}
}


}
