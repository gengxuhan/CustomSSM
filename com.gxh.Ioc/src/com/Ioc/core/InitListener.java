package com.Ioc.core;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.Ioc.PackageScanner.Scanner;
import com.Ioc.Xml.ClassPathXmlFactory;


public class InitListener  implements ServletContextListener{

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		ServletContext servletContext = event.getServletContext();
		String contextConfigLocation = servletContext.getInitParameter("contextConfigLocation");
		ClassPathXmlFactory classPathXmlFactory=new ClassPathXmlFactory(contextConfigLocation);
		BeanFactory beanFactory = classPathXmlFactory.getBeanFactory();
		Scanner scanner = (Scanner) beanFactory.get("mapperScaner");
		if(scanner!=null){
			scanner.ContantDi();	
		}
		
	}

}
