package com.Ioc.Xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.Ioc.PackageScanner.ClasspathPackageScanner;


public class XmlReader {

private InputStream inputStream;

public XmlReader(InputStream inputStream) {
		this.inputStream=inputStream;
	}

public void readXml(){
	
	SAXReader reader=new SAXReader();
	try {
		Document doc = reader.read(inputStream);
		Element beans = doc.getRootElement();
		List<Element> beanelements = beans.elements("bean");
		if( beanelements != null){
			ContantConfig.beanConfig=new HashMap<String, Bean>();
			for (Element beanelement : beanelements) {
				Bean bean=new Bean();
				String name = beanelement.attributeValue("name");
				String classname = beanelement.attributeValue("class");
				bean.setName(name);
				bean.setClassname(classname);
				List<Element> propertyelements = beanelement.elements("property");
				if( propertyelements!=null ){
					List<Property> properties=new ArrayList<Property>();
					for (Element propertyelement : propertyelements) {
						Property property=new Property();
						String propertyname = propertyelement.attributeValue("name");
						property.setName(propertyname);
						Attribute propertyvalueattribute = propertyelement.attribute("value");
						properties.add(property);	
						if( propertyvalueattribute!= null ){
                          property.setValue(propertyvalueattribute.getValue());
                          continue;
						}
						Attribute propertyrefattribute = propertyelement.attribute("ref");
						if( propertyrefattribute!=null ){
							property.setRef(propertyrefattribute.getValue());
							continue;
						}
						Element listelement = propertyelement.element("list");
						if(listelement!=null){
							ListPropery listPropery=new ListPropery();
							listPropery.setName(propertyname);
							List<Element> valueelements = listelement.elements("value");
							if(valueelements!=null){
								List<String> valuelist=new ArrayList<String>();
								for (Element valueelement : valueelements) {
									valuelist.add(valueelement.getText());
								}
								listPropery.setValues(valuelist);
							}
							List<Element> refelements = listelement.elements("ref");
							if(refelements!=null){
								List<String> refs=new ArrayList<String>();
								for (Element refelement : refelements) {
									refs.add(refelement.getText());
								}
								listPropery.setRefs(refs);
							}
							bean.setListPropery(listPropery);
						}
								
					}
					bean.setProperties(properties);
				}
				ContantConfig.beanConfig.put(name, bean);
			}
			System.out.println("--------------½âÎö½áÊø------------");
		}
		List<Element> Scanelements = beans.elements("component-scan");
		if( Scanelements!= null ){
			List<String> classnamelist=new ArrayList<String>();
			for (Element scanelement : Scanelements) {
				String packagename = scanelement.attributeValue("base-package");
				ClasspathPackageScanner scanner=new ClasspathPackageScanner(packagename);
				List<String> list = scanner.getFullyQualifiedClassNameList();
				for (String string : list) {
					classnamelist.add(string);
				}
			}
		ContantConfig.classnames=classnamelist;
		}
		Map<String, Bean> map=ContantConfig.beanConfig;
		
	} catch (DocumentException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
}
