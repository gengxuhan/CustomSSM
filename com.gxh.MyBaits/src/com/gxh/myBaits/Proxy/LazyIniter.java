package com.gxh.myBaits.Proxy;

import net.sf.cglib.proxy.Enhancer;

public class LazyIniter {

	
	public static Object init(String mappernameAndSelectname, Class resultObj,Object condiation){
		Enhancer enhancer=new Enhancer();
		enhancer.setSuperclass(resultObj);
		enhancer.setCallback(new AdviceLazy(mappernameAndSelectname, condiation, resultObj));
		return enhancer.create();
	}
}
