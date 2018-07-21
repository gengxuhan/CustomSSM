package com.gxh.myBaits.Context;

public class Insert {

	
	private  String id;
	
	private  String parameterType;
	
	public SelectKey getSelectKey() {
		return selectKey;
	}

	public void setSelectKey(SelectKey selectKey) {
		this.selectKey = selectKey;
	}

	private SelectKey selectKey;
	
	private  String sql;

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParameterType() {
		return parameterType;
	}

	public void setParameterType(String parameterType) {
		this.parameterType = parameterType;
	}
	
	
}
