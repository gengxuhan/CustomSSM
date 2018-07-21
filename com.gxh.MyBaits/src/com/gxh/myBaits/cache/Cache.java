package com.gxh.myBaits.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Cache {
	
	private static ConcurrentHashMap<String, Object> cacheMap=new ConcurrentHashMap<String, Object>();
	
	public static void put(String key,Object value){
		cacheMap.put(key, value);
	}
	
	public static Object get(String key){
	  return cacheMap.get(key);
	}
	
	   
     
}
