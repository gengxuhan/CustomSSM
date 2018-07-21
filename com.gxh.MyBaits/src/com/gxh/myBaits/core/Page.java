package com.gxh.myBaits.core;

public class Page {

	
	private  String orderbycolumnName; 
	
	private  String orderby;
	
	private  int   statRow;
	
	private  int   endRow;
	
	public Page( String orderbycolumnName,String orderby,int   statRow,int   endRow) {
		this.orderbycolumnName=orderbycolumnName;
		this.orderby=orderby;
		this.statRow=statRow;
		this.endRow=endRow;
	}
	
	public Page(int   statRow,int   endRow) {
		
		this.statRow=statRow;
		this.endRow=endRow;
	}

	public String getOrderbycolumnName() {
		return orderbycolumnName;
	}

	public void setOrderbycolumnName(String orderbycolumnName) {
		this.orderbycolumnName = orderbycolumnName;
	}

	public String getOrderby() {
		return orderby;
	}

	public void setOrderby(String orderby) {
		this.orderby = orderby;
	}

	public int getStatRow() {
		return statRow;
	}

	public void setStatRow(int statRow) {
		this.statRow = statRow;
	}

	public int getEndRow() {
		return endRow;
	}

	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}
	
	
}
