package com.gxh.myBaits.Sql;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class InsertSqlchange {

	private String sql=null;
	private List<String> property=null;
	
	public void  findSql(HashMap<String,String> Sqlinfo,String name){
		
		Iterator<String> iterator = Sqlinfo.keySet().iterator();
		while( iterator.hasNext() ){
			String key = iterator.next();
			if( key.equals(name) ){
				this.sql=Sqlinfo.get(key);
				break;
			}
		}
	}
	
	public String change(HashMap<String,String> Sqlinfo,String name,Object o){
		if(sql == null){
			findSql(Sqlinfo, name);	
			System.out.println(sql);
			String columns=sql.substring(0, sql.lastIndexOf("(")+1);
			String valuess = sql.substring(sql.lastIndexOf("(")+1,sql.lastIndexOf(")"));
			String[] values = valuess.split("\\,");
			property=new ArrayList<String>();
			for (String value : values) {
				if( value.contains("$")){
					String fieldname = value.substring(value.lastIndexOf("{")+1, value.lastIndexOf("}"));
					property.add(fieldname);
					value="?";
					columns+=value+",";	
				}else{
					if(o instanceof String){
						value="'"+o+"'";
						columns+=value+",";
					}else if(o instanceof Integer){
						value=String.valueOf(o);
						columns+=value+",";
					}else{
					String fieldname = value.substring(value.lastIndexOf("{")+1, value.lastIndexOf("}"));
					Object valueobject = getValue(o, fieldname);
					if( valueobject instanceof String){
						value="'"+valueobject+"'";
						columns+=value+",";
					
					}else{
						value=String.valueOf(valueobject);
						columns+=value+",";
					}
				}
					}
			}
			 sql = columns.substring(0,columns.length()-1)+")"+";";
			

		}
		System.out.println(sql);
		return sql;
	}
	
	public Object getValue(Object o,String fieldname){
		Object object =null;
		try {
			Class<? extends Object> c = o.getClass();
			Field field = c.getDeclaredField(fieldname);
			field.setAccessible(true);
			object= field.get(o);
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
		return object;
	}

	public List<String> getProperty() {
		return property;
	}

	public void setProperty(List<String> property) {
		this.property = property;
	}
	
	
}
