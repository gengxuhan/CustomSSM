package com.gxh.myBaits.ReadXml;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.gxh.myBaits.Context.Context;

public class XmlReaderDbinfo implements XmlReadHandler {

	private  HashMap<String,String>  DBinfo =null;
	
	private List<String> xmlResourse=null;
	
	@Override
	public void readXml(InputStream resource) {
	
	try {
		SAXReader reader=new SAXReader();
		Document document = reader.read(resource);
		DBinfo=new HashMap<String,String>();
		Element root= document.getRootElement();
		Element mapping = root.element("mapping-resourse");
		List<Element> values= mapping.elements();
		xmlResourse=new ArrayList<String>();
		for (Element value : values) {
			if(StringUtils.isNotBlank(value.getText())){
			xmlResourse.add(value.getText());
			}
		}
		Context.xmlResourse=xmlResourse;
		Element dataSource = root.element("dataSource");
		Element transactionManager = root.element("transactionManager");
		DBinfo.put("transactionManager",transactionManager.attributeValue("type"));
		if( dataSource != null){
			DBinfo.put(dataSource.attributeValue("name"), dataSource.attributeValue("class"));
			List<Element> propertys = dataSource.elements();
			for (Element e : propertys) {
				String name = e.attributeValue("name");
			    Attribute value = e.attribute("value");
			    if( value == null){
			    	DBinfo.put(name, e.getText());
			    }else{
			    	DBinfo.put(name,value.getText());
			    }
			}
		}else{
			Element jdbc = root.element("jdbc");
			if(jdbc!=null){
			List<Element> propertys = jdbc.elements();
			for (Element e : propertys) {
				String name = e.attributeValue("name");
			    Attribute value = e.attribute("value");
			    if( value == null){
			    	DBinfo.put(name, e.getText());
			    }else{
			    	DBinfo.put(name,value.getText());
			    }
			}
			}
		}
		Context.DBinfo=DBinfo;
	} catch (DocumentException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
	}



	

	
	

}
