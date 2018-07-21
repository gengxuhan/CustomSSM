package com.gxh.myBaits.Sql;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class UpdateSqlchange {

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
			property=new ArrayList<String>();
		    String[] elements = sql.split(" ");
		    String wherevalue=null;
		    String setvalue=null;
		    for (String value : elements) {
		    	if(value.equalsIgnoreCase("set")){
		    		setvalue=value;
		    		continue;
		    	}
				if( value.equalsIgnoreCase("where")){
					wherevalue=value;
					break;
				}
			}
		    
		    
		  String valuess = sql.substring(sql.indexOf(wherevalue)+5,sql.length()).trim();
		  String[] values = valuess.split(" ");
		  String setbefore = sql.substring(0,sql.indexOf(setvalue)+3);
		  String condiation=sql.substring(0, sql.indexOf(wherevalue))+" ";
		  String condiationset = sql.substring(sql.indexOf(setvalue)+3,sql.indexOf(wherevalue));
		  String[] split = condiationset.split(",");
		  for (String value : split) {
			  if( value.contains("#")){
				  String replacevalue=null;
				  Object valueobj = getValue(o, value.substring(value.indexOf("{")+1, value.indexOf("}")));
				  if( valueobj instanceof  String){
					  replacevalue  = value.replace(value.substring(value.indexOf("#"),value.length()),"'"+valueobj+"'"); 
				  }else{
					 replacevalue = value.replace(value.substring(value.indexOf("#"),value.length()),valueobj+"");
				  }		  
				  setbefore+=replacevalue+",";
			  }else if( value.contains("$")){
				  String fieldname = value.substring(value.indexOf("$")+2,value.length()-2);
				  property.add(fieldname);
				  String replace = value.replace(value.substring(value.indexOf("$"),value.length()),"?"); 
				  setbefore+=replace+",";
			  }
		}
		  setbefore=setbefore.substring(0,setbefore.length()-1);
		  setbefore=setbefore+" "+wherevalue+" ";
		  for (String value : values) {
			  if( value.contains("#")){
				  String replacevalue=null;
				  Object valueobj = getValue(o, value.substring(value.indexOf("{")+1, value.indexOf("}")));
				  if( valueobj instanceof  String){
					  replacevalue  = value.replace(value.substring(value.indexOf("#"),value.length()),"'"+valueobj+"'"); 
				  }else{
					 replacevalue = value.replace(value.substring(value.indexOf("#"),value.length()),valueobj+"");
				  }		  
				  setbefore+=replacevalue+" ";
			  }else if( value.contains("$")){
				  String fieldname = value.substring(value.indexOf("$")+2,value.length()-1);
				  property.add(fieldname);
				  String replace = value.replace(value.substring(value.indexOf("$"),value.length()),"?"); 
				  setbefore+=replace+" ";
			  }else{
				  setbefore+=value+" ";
			  }
 		}
		 sql=setbefore;

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
