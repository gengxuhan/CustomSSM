package com.mvc.HandlerAdpter;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;

import com.mvc.Context.Context;
import com.mvc.core.ModeAndView;


public class HandlerAdpter {


	public Object handle(HttpServletRequest request, HttpServletResponse response, Object handler,HashMap<String, Method> AdpterMap) throws Exception {
		String url = request.getServletPath();
		url = url.substring(0, url.lastIndexOf("."));
		Method method = AdpterMap.get(url);
		Object[] VALUE = setParameters(method, request,response);	
		Object o= method.invoke(handler, VALUE);
		if( o == null){
			return null;
		}
		if( o instanceof ModeAndView){
		  return (ModeAndView)o;
		}else if( o instanceof String){
			ModeAndView modeAndView = new ModeAndView();
			modeAndView.setViewname((String) o);
		return  modeAndView;
		}
		return o;
		
	}
	
	private Object[] setParameters(Method method,HttpServletRequest request,HttpServletResponse response){
		Parameter[] parameters = method.getParameters();
		Object VALUE []=new Object[parameters.length];
		for (int i = 0; i < parameters.length; i++) {
			Class<?> type = parameters[i].getType();
			if( type == java.util.List.class){
				/*System.out.println("½øÈë");			;
				System.out.println(((ParameterizedType)type.getGenericSuperclass()).getActualTypeArguments()[0]);*/	
				String[] parameterValues = request.getParameterValues(parameters[i].getName());
					List list=new ArrayList();
					for (String string : parameterValues) {
						list.add(string);
					}
					VALUE[i]=list;
				
					continue;}
				
			
			if( type == Integer.class ){
				String parameter = request.getParameter(parameters[i].getName());
				if( parameters == null ){
					parameter= (String) request.getAttribute(parameters[i].getName());
				}
				int value = Integer.parseInt(parameter);
				VALUE[i]=value;
			}else if( type == String.class){
				String parameter = request.getParameter(parameters[i].getName());
				if( parameter == null ){
					parameter= (String) request.getAttribute(parameters[i].getName());
				}
				VALUE[i]=parameter;	
			}
			else if( type == FileItem.class){
				FileItem fileItem = Context.multiFiles.get(parameters[i].getName());
				VALUE[i]=fileItem;
			}else if(type == HttpServletRequest.class ){
				VALUE[i]=request;	
			}else if(type == HttpServletResponse.class ){
				VALUE[i]=response;	
			}else if(type == HttpSession.class ){
				VALUE[i]=request.getSession();
			}else if(type == Cookie.class){
				
			}else{
				try {
					Object o = type.newInstance();
					Field[] fields = type.getDeclaredFields();
					for (Field field : fields) {
						String name = field.getName();
						String value=null;
						value = request.getParameter(name);
						if( value == null ){
							value =(String) request.getAttribute(name);
						}
						if(parameters.length>1){
						String classname=type.getName().substring(type.getName().lastIndexOf(".")+1, type.getName().length());	
						value = request.getParameter(classname+"."+name);
						}	
						if(value != null){
						field.setAccessible(true);
						field.set(o, value);
						
						}
					}
					VALUE[i]=o;
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
		}
		return VALUE;
	}
	
}
