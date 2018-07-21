package com.gxh.myBaits.Proxy;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.util.List;

import com.gxh.myBaits.Context.Association;
import com.gxh.myBaits.Context.Context;
import com.gxh.myBaits.Context.ResultMap;
import com.gxh.myBaits.Context.Select;
import com.gxh.myBaits.Sql.SelectSqlexchange;
import com.gxh.myBaits.dataSource.JdbcDbControl;
import com.gxh.myBaits.mapping.mapping;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class AdviceLazy implements MethodInterceptor {

	private  String sql;
	
	private String mappernameAndSelectname;
	
	private Object condiation;
	
	private Class resultobj;
	
	private Object  OBJ;
	
	private boolean flag=true;
	
	public AdviceLazy(String mappernameAndSelectname,Object condiation,Class resultobj) {
		this.mappernameAndSelectname=mappernameAndSelectname;
		this.condiation=condiation;
		this.resultobj=resultobj;
	}
	@Override
	public Object intercept(Object obj, Method method, Object[] args,
			MethodProxy proxy) throws Throwable {
		Select select = Context.Selects.get(mappernameAndSelectname);
		String resultMapname = select.getMappername()+"."+select.getResultMap();
		ResultMap resultMap = Context.resultMaps.get(resultMapname);
		List<Association> associations = resultMap.getAssociations();
		boolean isInter = isInterceptor(proxy, associations);
		if( OBJ == null){
			selValue();
		}
		if( isInter ){
			if( flag ){
			SelectSqlexchange selectSqlexchange=new SelectSqlexchange();
			for (Association association : associations) {
				String selectname = association.getSelect();
				Object object = getValue(OBJ,association.getProperty());
				if(object != null){
					object = getValue(object,association.getColumn());
				}
				String sql = selectSqlexchange.change(Context.Sqlinfo,select.getMappername()+"."+selectname,object);
			    JdbcDbControl control=new JdbcDbControl();
			    ResultSet resultSet = control.queryData(sql,object,selectSqlexchange.getProperty(),null);
			    mapping mapping=new mapping();
			    List<?> result = mapping.MappingResult(resultSet, select.getMappername()+"."+selectname, JdbcDbControl.selectColumn);
			   Object oo= result.get(0);
			   Class c = OBJ.getClass();
			  Method meth= c.getDeclaredMethod("set"+association.getProperty().substring(0,1).toUpperCase()+association.getProperty().substring(1),oo.getClass());
			  meth.invoke(OBJ, oo);
			}
			flag=false;
			}
			return  method.invoke(OBJ, args);
			
		}else{
			return method.invoke(OBJ, args);
		}
	}
	
	private void selValue() {
		SelectSqlexchange selectSqlexchange=new SelectSqlexchange();
		String sql = selectSqlexchange.change(Context.Sqlinfo, mappernameAndSelectname, condiation);
		JdbcDbControl control=new JdbcDbControl();
		ResultSet resultSet = control.queryData(sql,condiation, selectSqlexchange.getProperty(), null);
		mapping mapping=new mapping();
		List<?> result = mapping.MappingResult(resultSet, mappernameAndSelectname, JdbcDbControl.selectColumn);
		OBJ=result.get(0);
		
	}
	private boolean isInterceptor(MethodProxy proxy,List<Association> associations){
		String superName = proxy.getSuperName();
		String methodname = superName.substring(superName.indexOf("$")+1, superName.lastIndexOf("$"));
		for (Association association : associations) {
			String propertyname ="get"+association.getProperty().substring(0, 1).toUpperCase()+association.getProperty().substring(1);
		    if(methodname.equals(propertyname)){
		    	return true;
		    }
		}
		return false;
	}
	
	private Object getValue(Object o,String fieldname){
		try {
			Class<? extends Object> c = o.getClass();
			Field field = c.getDeclaredField(fieldname);
			field.setAccessible(true);
			return field.get(o);
		} catch (NoSuchFieldException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
