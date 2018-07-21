package com.gxh.myBaits.Context;

import java.util.List;

public class Mapper {

	private  String namespace;
	
	private List<Select> selects;
	
	private List<Insert> inserts;
	
	private List<Delete> deletes;
	
	public List<Delete> getDeletes() {
		return deletes;
	}

	public void setDeletes(List<Delete> deletes) {
		this.deletes = deletes;
	}

	public List<Update> getUpdates() {
		return updates;
	}

	public void setUpdates(List<Update> updates) {
		this.updates = updates;
	}

	private List<Update> updates;
	
	public List<Insert> getInserts() {
		return inserts;
	}

	public void setInserts(List<Insert> inserts) {
		this.inserts = inserts;
	}

	private  List<ResultMap> resultMaps;

	public String getNamespace() {
		return namespace;
	}

	public List<Select> getSelects() {
		return selects;
	}

	public void setSelects(List<Select> selects) {
		this.selects = selects;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public List<ResultMap> getResultMaps() {
		return resultMaps;
	}

	public void setResultMaps(List<ResultMap> resultMaps) {
		this.resultMaps = resultMaps;
	}



	


	
    
}
