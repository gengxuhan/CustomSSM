package com.mvc.HandlerMapping;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.Ioc.core.BeanFactory;
import com.gxh.Ioc.Annoation.Resource;
import com.mvc.Annotaction.Controller;
import com.mvc.Annotaction.RequestMapping;
import com.mvc.Context.Context;
import com.mvc.Intercepter.Intercepter;
import com.mvc.Intercepter.IntercepterChain;
import com.mvc.core.Dispather;

public class MappingHandler  implements HandlerMapping {

	@Override
	public void mappingHandler(List<String> nameslist,HashMap<String,Object>  handlerMap,HashMap<String, Method>  AdpterMap) {
		if( nameslist!=null){
			System.out.println("处理器映射器正在查找handeler.........");
		for (String name : nameslist) {
			try {
				Class<?> c = Class.forName(name);
				Object handler= c.newInstance();
				Controller controller = c.getAnnotation(Controller.class);
				if(controller!=null){
				String uri="";
				Field[] fields = c.getDeclaredFields();
				for (Field field : fields) {
					Resource resource = field.getAnnotation(Resource.class);
					if(resource!=null){
						String resname = resource.name();
						Object o = BeanFactory.getBeanFactory().get(resname);
						field.setAccessible(true);
						field.set(handler, o);
					}
				}
				Method[] methods = c.getDeclaredMethods();
				for (Method method : methods) {
					RequestMapping requestMapping = method.getDeclaredAnnotation(RequestMapping.class);
					String url = requestMapping.url();
					AdpterMap.put(url, method);
					uri+=url+"#";
				}
				   System.out.println(uri);
				     handlerMap.put(uri, handler);
					}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		}
		
	}
	
	
	public IntercepterChain getHandlerChain(List<Intercepter> allintercepters,Map<String,List<Intercepter>> intercepters,Object handler,String url){
		List<Intercepter> list=new ArrayList<Intercepter>();
		if( allintercepters !=null){
		for (Intercepter intercepter : allintercepters) {
			list.add(intercepter);
		}
		}
		if(intercepters != null){
		Iterator<String> iterator = intercepters.keySet().iterator();
		while( iterator.hasNext() ){
			String key = iterator.next();
			if(key.equals(url)){
			List<Intercepter> lt = intercepters.get(key);
			for (Intercepter intercepter : lt) {
				list.add(intercepter);
			}
			}
		}
		}
		IntercepterChain intercepterChain=new IntercepterChain(list, handler);
		return intercepterChain ;
	}

}
