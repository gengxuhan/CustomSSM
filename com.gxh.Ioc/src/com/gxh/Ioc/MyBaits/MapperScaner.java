package com.gxh.Ioc.MyBaits;

import java.io.IOException;
import java.util.List;

import com.Ioc.PackageScanner.ClasspathPackageScanner;
import com.Ioc.PackageScanner.Scanner;
import com.Ioc.core.BeanFactory;


public class MapperScaner implements Scanner{
	
private  SqlSessionFactoryBean sessionFactoryBean;

private  String  packagename;


public SqlSessionFactoryBean getSessionFactoryBean() {
	return sessionFactoryBean;
}

public void setSessionFactoryBean(SqlSessionFactoryBean sessionFactoryBean) {
	this.sessionFactoryBean = sessionFactoryBean;
}

public String getPackagename() {
	return packagename;
}

public void setPackagename(String packagename) {
	this.packagename = packagename;
}

public void ContantDi(){
	System.out.println(packagename+"°ü¿ªÊ¼É¨Ãè..........");
	ClasspathPackageScanner scanner=new ClasspathPackageScanner(packagename);
	try {
		List<String> classNameList = scanner.getFullyQualifiedClassNameList();
		BeanFactory beanFactory = BeanFactory.getBeanFactory();
		for (String classname : classNameList) {
			Object mapper = sessionFactoryBean.getMapper(Class.forName(classname));
			beanFactory.putBean(classname, mapper);
		}
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

}
