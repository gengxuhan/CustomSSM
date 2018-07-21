package com.gxh.myBaits.Context;

public class SelectKey {

	
	private  String resultType;
	
	private  String keyProperty;
	
	private  String order;
	
	private  String sql;
	
	
	
	public String getSql() {
		return sql;
	}



	public void setSql(String sql) {
		this.sql = sql;
	}



	public enum order{
		
	AFTER,BEFORE;	
	}



	public String getResultType() {
		return resultType;
	}



	public void setResultType(String resultType) {
		this.resultType = resultType;
	}



	public String getKeyProperty() {
		return keyProperty;
	}



	public void setKeyProperty(String keyProperty) {
		this.keyProperty = keyProperty;
	}



	public String getOrder() {
		return order;
	}



	public void setOrder(String order) {
		this.order = order;
	}
	
	
}
