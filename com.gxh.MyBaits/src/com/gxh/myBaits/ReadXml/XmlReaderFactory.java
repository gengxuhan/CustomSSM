package com.gxh.myBaits.ReadXml;

import java.io.InputStream;
import java.util.List;

import com.gxh.myBaits.Context.Context;

public class XmlReaderFactory {

	
	private static XmlReadHandler DbreadHandler=null;
	
	private static XmlReadHandler mappingHandler=null;
	 
	
	public static void bulid(String resource){
		DbreadHandler=new XmlReaderDbinfo();
		 InputStream classPath = Resourse.getClassPath(resource);
		DbreadHandler.readXml(classPath);
		mappingHandler=new XmlReaderMappinginfo();
		List<String> xmlResourse=Context.xmlResourse;
		for (String dir : xmlResourse) {
			InputStream in = Resourse.getClassPath(dir);
			mappingHandler.readXml(in);
		}
		Context.init();

	}
}
