package com.Ioc.Xml;

import java.util.List;

public class Bean {

	private String name;
	private String classname;
	private List<Property> properties;
	private ListPropery listPropery;
	
	public List<Property> getProperties() {
		return properties;
	}
	public void setProperties(List<Property> properties) {
		this.properties = properties;
	}
	public ListPropery getListPropery() {
		return listPropery;
	}
	public void setListPropery(ListPropery listPropery) {
		this.listPropery = listPropery;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getClassname() {
		return classname;
	}
	public void setClassname(String classname) {
		this.classname = classname;
	}
	
}
