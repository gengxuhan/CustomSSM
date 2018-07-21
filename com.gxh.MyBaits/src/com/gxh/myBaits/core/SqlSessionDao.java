package com.gxh.myBaits.core;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.swing.plaf.SliderUI;

import org.apache.commons.lang.StringUtils;

import com.gxh.myBaits.Context.Context;
import com.gxh.myBaits.Context.Insert;
import com.gxh.myBaits.Proxy.LazyIniter;
import com.gxh.myBaits.Proxy.MapperProxyFactory;
import com.gxh.myBaits.Sql.DeleteSqlchange;
import com.gxh.myBaits.Sql.InsertSqlchange;
import com.gxh.myBaits.Sql.SelectSqlexchange;
import com.gxh.myBaits.cache.Cache;
import com.gxh.myBaits.dataSource.JdbcDbControl;
import com.gxh.myBaits.mapping.mapping;

public class SqlSessionDao  implements SqlSession{

private JdbcDbControl jdbcDbControl;	
	@Override
	public Object selectOne(String mappernameAndSelectname, Object o) {
		if( Cache.get(mappernameAndSelectname) !=null ){
			return Cache.get(mappernameAndSelectname);
		}
		List<?> list = selectList(mappernameAndSelectname, o);
		if(list.size()>0){
			Cache.put(mappernameAndSelectname, list.get(0));
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<?> selectList(String mappernameAndSelectname, Object o) {
		if( Cache.get(mappernameAndSelectname) !=null ){
			return (List<?>) Cache.get(mappernameAndSelectname);
		}
		SelectSqlexchange selectSqlexchange=new SelectSqlexchange();
		String sql = selectSqlexchange.change(Context.Sqlinfo, mappernameAndSelectname, o);
		if(jdbcDbControl==null){
			jdbcDbControl=new JdbcDbControl();	
		}
		ResultSet resultSet = jdbcDbControl.queryData(sql, o, selectSqlexchange.getProperty(), null);
		mapping mapping=new mapping();
		List<?> result = mapping.MappingResult(resultSet, mappernameAndSelectname,jdbcDbControl.selectColumn);
		Cache.put(mappernameAndSelectname, result);
		return result;
		
	}

	

	@Override
	public Object insert(String mappernameAndSelectname, Object o) {
		InsertSqlchange insertSqlchange=new InsertSqlchange();
		String sql = insertSqlchange.change(Context.Sqlinfo, mappernameAndSelectname,o);
		if(jdbcDbControl==null){
			jdbcDbControl=new JdbcDbControl();	
		}
		Insert insert = Context.Inserts.get(mappernameAndSelectname);
		if( insert.getSelectKey() == null){
			jdbcDbControl.setData(sql, o, insertSqlchange.getProperty(),null);
		}else{
			Object data= jdbcDbControl.setData(sql, o, insertSqlchange.getProperty(),insert.getSelectKey().getResultType());
			return data;
		}
		
	   return null;
		
	}

	@Override
	public void commit() {
		JdbcDbControl.commiteTranaction();
		
	}

	@Override
	public void delete(String mappernameAndSelectname, Object o) {
		DeleteSqlchange deleteSqlchange=new DeleteSqlchange();
		String sql = deleteSqlchange.change(Context.Sqlinfo, mappernameAndSelectname, o);
		if(jdbcDbControl==null){
			jdbcDbControl=new JdbcDbControl();	
		}
		
		jdbcDbControl.setData(sql, o, deleteSqlchange.getProperty(),null);
		
	}

	@Override
	public Object selectLazy(String mappernameAndSelectname, Class resultObj,Object condiation) {
		LazyIniter initer=new LazyIniter();
		Object result = initer.init(mappernameAndSelectname,resultObj,condiation);
		return result;
	}

	@Override
	public  <T> T getMapper(Class<T> type) {
		MapperProxyFactory<T> factory=new MapperProxyFactory<T>();
		T o = (T)factory.createMapper(type);
	    return o;
		
	}

	public JdbcDbControl getJdbcDbControl() {
		return jdbcDbControl;
	}

	public void setJdbcDbControl(JdbcDbControl jdbcDbControl) {
		this.jdbcDbControl = jdbcDbControl;
	}

}
