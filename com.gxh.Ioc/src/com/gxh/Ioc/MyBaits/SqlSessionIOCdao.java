package com.gxh.Ioc.MyBaits;

import java.sql.ResultSet;
import java.util.List;

import com.gxh.myBaits.Context.Context;
import com.gxh.myBaits.Context.Insert;
import com.gxh.myBaits.Proxy.LazyIniter;
import com.gxh.myBaits.Proxy.MapperProxyFactory;
import com.gxh.myBaits.Sql.DeleteSqlchange;
import com.gxh.myBaits.Sql.InsertSqlchange;
import com.gxh.myBaits.Sql.SelectSqlexchange;
import com.gxh.myBaits.cache.Cache;
import com.gxh.myBaits.core.SqlSession;
import com.gxh.myBaits.dataSource.JdbcDbControl;
import com.gxh.myBaits.mapping.mapping;

public class SqlSessionIOCdao  implements  SqlSession {
private DBcontrol dBcontrol	;


	@Override
	public  <T> T getMapper(Class<T> type) {
		MapperProxyFactory<T> factory=new MapperProxyFactory<T>();
		T o = (T)factory.createMapper(type,dBcontrol);
	    return o;
		
	}


	@Override
	public Object selectOne(String mappernameAndSelectname, Object o) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<?> selectList(String mappernameAndSelectname, Object o) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Object insert(String mappernameAndSelectname, Object o) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void delete(String mappernameAndSelectname, Object o) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public Object selectLazy(String mappernameAndSelectname, Class resultObj,
			Object condiation) {
		// TODO Auto-generated method stub
		return null;
	}


	public DBcontrol getdBcontrol() {
		return dBcontrol;
	}


	public void setdBcontrol(DBcontrol dBcontrol) {
		this.dBcontrol = dBcontrol;
	}


	@Override
	public void commit() {
		dBcontrol.commiteTranaction();
		
	}


}
