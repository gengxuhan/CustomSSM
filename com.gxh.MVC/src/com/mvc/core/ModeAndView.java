package com.mvc.core;

import java.util.HashMap;

public class ModeAndView {

	
	private String viewname;
	
	private HashMap<String,Object> modeMap;
	
	public void setAttribute(String key,Object value){
		if(modeMap==null){
			modeMap=new HashMap<String, Object>();
		}
		modeMap.put(key, value);
	}
	
	public void getAttribute(String key){
		if(modeMap==null){
			modeMap=new HashMap<String, Object>();
		}
		modeMap.get(key);
	}

	public String getViewname() {
		return viewname;
	}

	public void setViewname(String viewname) {
		this.viewname = viewname;
	}

	public HashMap<String, Object> getModeMap() {
		return modeMap;
	}
	
	
	
	
	
}
