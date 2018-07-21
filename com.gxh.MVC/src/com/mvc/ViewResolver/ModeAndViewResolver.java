package com.mvc.ViewResolver;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mvc.core.ModeAndView;

public class ModeAndViewResolver {

private ModeAndView modeAndView;

private HttpServletRequest request;

private HttpServletResponse response;

public ModeAndViewResolver(ModeAndView modeAndView,HttpServletRequest request,HttpServletResponse response) {
	this.modeAndView=modeAndView;
	this.request=request;
	this.response=response;
}

private void setParameterValues(){
	HashMap<String, Object> modeMap = modeAndView.getModeMap();
	if( modeMap !=null ){
		Iterator<String> iterator = modeMap.keySet().iterator();
		while(iterator.hasNext()){
			String key = iterator.next();
			Object o = modeMap.get(key);
			request.setAttribute(key, o);
		}
	}
	
}

public  void  resolverView(){
	setParameterValues();
	String viewname = modeAndView.getViewname();
	if(viewname.contains("redirect:")){
		try {
			 response.sendRedirect(viewname);
			 return;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	try {
		request.getRequestDispatcher(viewname).forward(request, response);
	} catch (ServletException | IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}
	
}
