package com.gxh.service;

import com.Ioc.Xml.ClassPathXmlFactory;
import com.Ioc.core.BeanFactory;
import com.mvc.PackageScanner.Scanner;

public class Test2 {

	
	public static void main(String[] args) {
		ClassPathXmlFactory classPathXmlFactory=new ClassPathXmlFactory("config/application.xml");
		BeanFactory beanFactory = classPathXmlFactory.getBeanFactory();
		Scanner scanner = (Scanner) beanFactory.get("mapperScaner");
		scanner.ContantDi();
		Test test=(Test) beanFactory.get("test");
		test.test();
		
	}
}
