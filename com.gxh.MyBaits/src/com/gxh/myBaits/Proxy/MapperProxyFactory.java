package com.gxh.myBaits.Proxy;

import java.lang.reflect.Proxy;

import org.apache.commons.dbcp.DbcpException;

import com.gxh.myBaits.dataSource.dataControl;

public class MapperProxyFactory<T>{

	 public  T createMapper(Class<T> type){
		
		 return (T)Proxy.newProxyInstance(type.getClassLoader(), new Class[] {type}, new MapperProxy(type.getName()));
		 
	 }
	 
	 public  T createMapper(Class<T> type,dataControl dataControl){
			
		 return (T)Proxy.newProxyInstance(type.getClassLoader(), new Class[] {type}, new MapperProxy(type.getName(),dataControl));
		 
	 }
	
}
