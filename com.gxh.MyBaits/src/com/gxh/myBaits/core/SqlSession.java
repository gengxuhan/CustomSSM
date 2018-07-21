package com.gxh.myBaits.core;

import java.util.List;

public interface SqlSession {

	public Object  selectOne(String mappernameAndSelectname,Object o);
	
	public List<?>  selectList(String mappernameAndSelectname,Object o);
	
	public <T> T getMapper(Class<T> type);
	
	public Object insert(String mappernameAndSelectname,Object o);
	
	public void delete(String mappernameAndSelectname,Object o);
	
	public Object selectLazy(String mappernameAndSelectname, Class resultObj,Object condiation);
	
	public void commit();
}
