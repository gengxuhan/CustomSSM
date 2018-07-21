package com.gxh.myBaits.Context;



public class Select {
	
	private String mappername;

	public String getMappername() {
		return mappername;
	}

	public void setMappername(String mappername) {
		this.mappername = mappername;
	}

	private  String  id;
	
	private  String  resultType;
		
	private  String  parameterType;
	
	private  String  resultMap;
	
	private  String sql;

	public String getId() {
		return id;
	}

	public String getResultMap() {
		return resultMap;
	}

	public void setResultMap(String resultMap) {
		this.resultMap = resultMap;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getResultType() {
		return resultType;
	}

	public void setResultType(String resultType) {
		this.resultType = resultType;
	}

	public String getParameterType() {
		return parameterType;
	}

	public void setParameterType(String parameterType) {
		this.parameterType = parameterType;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}
	
	
	
}
