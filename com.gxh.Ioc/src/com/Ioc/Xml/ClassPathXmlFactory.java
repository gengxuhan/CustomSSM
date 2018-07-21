package com.Ioc.Xml;

import java.io.InputStream;

import com.Ioc.core.BeanFactory;



public class ClassPathXmlFactory {


	private InputStream inputStream;
	
	
	public ClassPathXmlFactory(String Classpath) {
		this.inputStream=ClassPathXmlFactory.class.getResourceAsStream("/"+Classpath);
		
	}
	
	public BeanFactory getBeanFactory(){
		XmlReader reader=new XmlReader(inputStream);
		reader.readXml();
		BeanFactory beanFactory = BeanFactory.getBeanFactory();
		return beanFactory;
	}
	
}
