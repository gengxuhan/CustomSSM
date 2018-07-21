package com.gxh.myBaits.Context;

import java.util.List;

public class Association {

	private ID id;

	private List<Result>  results;
	
	private String column;
	
	private String select;

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}

	public String getSelect() {
		return select;
	}

	public void setSelect(String select) {
		this.select = select;
	}

	private String property;
	
	private String javaType;

	public ID getId() {
		return id;
	}

	public void setId(ID id) {
		this.id = id;
	}



	public List<Result> getResults() {
		return results;
	}

	public void setResults(List<Result> results) {
		this.results = results;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getJavaType() {
		return javaType;
	}

	public void setJavaType(String javaType) {
		this.javaType = javaType;
	}

}
