package com.gxh.service;

import com.Ioc.Xml.ClassPathXmlFactory;
import com.Ioc.core.BeanFactory;

public class Test2 {

	
	public static void main(String[] args) {
		ClassPathXmlFactory classPathXmlFactory=new ClassPathXmlFactory("config/application.xml");
		BeanFactory beanFactory = classPathXmlFactory.getBeanFactory();
		Test test=(Test) beanFactory.get("test");
		test.test();
		 UserService service = (UserService) beanFactory.get("user");
		 service.add();
	}
}
