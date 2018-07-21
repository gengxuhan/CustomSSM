package com.gxh.myBaits.Context;

import java.util.List;

public class Collection {

	
	private String property;
	
	private String ofType;
	
	private ID id;
	
	private List<Result> results;
	
	private Association association;
	


	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getOfType() {
		return ofType;
	}

	public void setOfType(String ofType) {
		this.ofType = ofType;
	}

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

	public Association getAssociation() {
		return association;
	}

	public void setAssociation(Association association) {
		this.association = association;
	}


	
	
}
