


package com.Ioc.core;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.Ioc.PackageScanner.Scanner;
import com.Ioc.Xml.Bean;
import com.Ioc.Xml.ContantConfig;
import com.Ioc.Xml.ListPropery;
import com.Ioc.Xml.Property;
import com.gxh.Ioc.Annoation.Resource;
import com.gxh.Ioc.Annoation.Service;


public class BeanFactory {

	private  final Map<String, Object> contant = new HashMap<String, Object>();
	private volatile static BeanFactory beanFactory;
	private Scanner scanner;

	private BeanFactory() {

	};

	public static BeanFactory getBeanFactory() {
		if (beanFactory == null) {
			synchronized (BeanFactory.class) {
				if (beanFactory == null) {
					beanFactory = new BeanFactory();
				}
			}
		}
		return beanFactory;
	}

	public Object get(String name) {

		Object bean = contant.get(name);
		if (bean == null) {
			bean = dependenceInjection(name);
			if (bean == null) {
				return DiAnnoationbean(name);
			}
		}
		return bean;
	}

	private Object dependenceInjection(String name) {

		Bean beanconfig = ContantConfig.beanConfig.get(name);
		if (beanconfig != null) {
			Class beanc;
			try {
				System.out.println(beanconfig.getClassname());
				beanc = Class.forName(beanconfig.getClassname());
				Object bean = beanc.newInstance();
				List<Property> properties = beanconfig.getProperties();
				if (properties != null) {
					for (Property property : properties) {
						String propertyname = property.getName();
						Field field = beanc.getDeclaredField(propertyname);
						Class<?> type = field.getType();
						Method method = beanc.getDeclaredMethod("set"
								+ propertyname.substring(0, 1).toUpperCase()
								+ propertyname.substring(1), type);
						String value = property.getValue();
						if (StringUtils.isNotBlank(value)) {
							method.invoke(bean, value);
							continue;
						}
						String ref = property.getRef();
						if (StringUtils.isNotBlank(ref)) {
							method.invoke(bean, beanFactory.get(ref));
							continue;
						}
					}
				}

				ListPropery listPropery = beanconfig.getListPropery();
				if (listPropery != null) {
					String listProperyname = listPropery.getName();
					Field field = beanc.getDeclaredField(listProperyname);
					Class<?> type = field.getType();
					Method method = beanc.getDeclaredMethod("set"
							+ listProperyname.substring(0, 1).toUpperCase()
							+ listProperyname.substring(1), type);
					List<String> values = listPropery.getValues();
					if (values != null && values.size() > 0) {
						method.invoke(bean, values);
					}
					List<String> refs = listPropery.getRefs();
					if (refs != null && refs.size() > 0) {
						List list = new ArrayList();
						for (String ref : refs) {
							list.add(beanFactory.get(ref));
						}
						method.invoke(bean, list);
					}
				}

				return bean;
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return null;
	}

	public void putBean(String name, Object o) {
		contant.put(name, o);
	}

	private Object DiAnnoationbean(String resourcename) {

		List<String> classnames = ContantConfig.classnames;
		List<String> beans=new ArrayList<String>();
		for (String classname : classnames) {
			Class<?> c;
			try {
				c = Class.forName(classname);
				Object bean = null;
				com.gxh.Ioc.Annoation.Bean beanAnno = c
						.getAnnotation(com.gxh.Ioc.Annoation.Bean.class);
				if (beanAnno != null) {
					String beanAnnoname = beanAnno.name();
					bean=c.newInstance();
					beans.add(beanAnnoname);
					beanFactory.putBean(beanAnnoname, bean);
				}
				Service service = c.getAnnotation(Service.class);
				if (service != null) {
					String servicename = service.name();
					bean=c.newInstance();
					beans.add(servicename);
					beanFactory.putBean(servicename, bean);
				}
				
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for (String name : beans) {
			Difield(name);
		}
		return beanFactory.get(resourcename);
		
		
	}
	
	private Object Difield(String resourcename){
		System.out.println(resourcename);
		Object bean = contant.get(resourcename);
		Class<? extends Object> c = bean.getClass();
		Field[] fields = c.getDeclaredFields();
		for (Field field : fields) {
			Resource resource = field.getAnnotation(Resource.class);
			if (resource != null) {
				String resourcename1 = resource.name();
				Object bwano = beanFactory.get(resourcename1);
				field.setAccessible(true);
				try {
					field.set(bean, bwano);
					Difield(resourcename1);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
		return bean;
	}
	
	

}
