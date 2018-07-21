package com.mvc.Context;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.mvc.Intercepter.Intercepter;
import com.mvc.Intercepter.Parameter;
import com.mvc.core.Dispather;

public class XmlReader {

  
	public static void main(String[] args) {
		XmlReader reader=new XmlReader();
		reader.read("/config/mvc.xml");
	}
	
	public void  read(String filename){
		
		InputStream in = XmlReader.class.getResourceAsStream(filename);
		SAXReader reader=new SAXReader();
		try {
			Document doc = reader.read(in);
			Element rootElement = doc.getRootElement();
			List<Element> Scanelements = rootElement.elements("component-scan");
			if(Scanelements != null){
				List<String> packagenames=new ArrayList<String>();
				
				for (Element element : Scanelements) {
					String packagename = element.attributeValue("base-package");
				
					packagenames.add(packagename);
					
				}
				Context.packagenames=packagenames;
			}
			Element interceptorselement = rootElement.element("interceptors");
			if(interceptorselement !=null){
				Context.interMap=new HashMap<String, List<String>>();
				Context.IntercepterParameters=new HashMap<String, List<Parameter>>();
				List<Element> interceptorelements = interceptorselement.elements("interceptor");
				if(interceptorelements!=null){

					for (Element interceptorelement : interceptorelements) {
						List<Element> parelements = interceptorelement.elements("parameter");
						String classname = interceptorelement.attributeValue("class");
						if(parelements != null){
							List<Parameter> parameters=new ArrayList<Parameter>();
							for (Element parelement : parelements) {
								Parameter parameter=new Parameter();
								parameter.setName(parelement.attributeValue("name"));
								parameter.setValue(parelement.attributeValue("value"));
								parameters.add(parameter);
							}
							Context.IntercepterParameters.put(classname, parameters);
						}
						String path = interceptorelement.attributeValue("path");
						List<String> list = Context.interMap.get(path);
						if(list==null){
							list=new ArrayList<String>();
							list.add(classname);
							Context.interMap.put(path, list);
						}else{
						list.add(classname);
						}
						
					}
				}
			}
			
			
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
