package com.gxh.myBaits.Context;

import java.util.List;

public class ResultMap {

	private String idname;
	
	private  String type;
	
	private   ID id;
	
	private  List<Result> results;
	
	private List<Collection> collections;
	
	public List<Collection> getCollections() {
		return collections;
	}

	public void setCollections(List<Collection> collections) {
		this.collections = collections;
	}

	public List<Association> getAssociations() {
		return associations;
	}

	public void setAssociations(List<Association> associations) {
		this.associations = associations;
	}

	private List<Association> associations;

	public String getIdname() {
		return idname;
	}

	public void setIdname(String idname) {
		this.idname = idname;
	}

   

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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
	
	
	
}
