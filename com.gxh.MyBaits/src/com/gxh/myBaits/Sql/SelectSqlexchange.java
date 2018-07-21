package com.gxh.myBaits.Sql;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.gxh.myBaits.Context.Context;
import com.gxh.myBaits.Context.Mapper;
import com.gxh.myBaits.Context.Select;

/**
 * 
 * @author GengXuHan
 * @Description
 * @Date 2018年6月8日 下午7:05:25
 */
public class SelectSqlexchange {
    
private String sql=null;
private List<String> property=null;

public String change(HashMap<String,String> Sqlinfo,String name,Object o){
	if(sql == null){
		findSql(Sqlinfo, name);
		Boolean isWhere = isWhere();
		property=new ArrayList<String>();
		if( isWhere == true ){
			String[] element = sql.split(" ");
			String valueWhere=null;
			String valueFrom=null;
			for (String value : element) {
				if( value.equalsIgnoreCase("from")){
					valueFrom=value;
				}
				if( value.equalsIgnoreCase("where")){
					valueWhere=value;
					break;
				}
			}
			  String valuess = sql.substring(sql.indexOf(valueWhere)+5,sql.length()).trim();
			  String[] values = valuess.split(" ");
			  String condiation=sql.substring(0, sql.indexOf(valueWhere)+5)+" ";
			  for (String value : values) {
				  if( value.contains("#")){
					  String replacevalue=null;
					  if( o instanceof String){
						  replacevalue  = value.replace(value.substring(value.indexOf("#"),value.length()),"'"+o+"'");
					  }else if( o instanceof Integer){
						  replacevalue = value.replace(value.substring(value.indexOf("#"),value.length()),o+"");
					  }else {			  
						  String FieldandValue = value.substring(value.indexOf("{")+1, value.indexOf("}"));
						  String[] Fieldnames = FieldandValue.split("\\.");
						  Object valueobj=null;
						  if(Fieldnames.length>1){
							  valueobj  = getValue(o,Fieldnames[1]);  
						  }else{
							 valueobj = getValue(o,Fieldnames[0]);
						  }
				
						  if(valueobj == null){
							  valueobj=getValue(getValue(o, Fieldnames[0]), Fieldnames[1]);
						  }
						  if( valueobj instanceof  String){
							  replacevalue  = value.replace(value.substring(value.indexOf("#"),value.length()),"'"+valueobj+"'"); 
						  }else{
							 replacevalue = value.replace(value.substring(value.indexOf("#"),value.length()),valueobj+"");
						  }  
					  }
					 		  
					condiation+=replacevalue+" ";
				  }else if( value.contains("$")){
					  String fieldname = value.substring(value.indexOf("$")+2,value.lastIndexOf("}"));
					  System.out.println(fieldname);
					  property.add(fieldname);
					  System.out.println(fieldname);
					  String replace = value.replace(value.substring(value.indexOf("$"),value.lastIndexOf("}")+1),"?"); 
					  condiation+=replace+" ";
				  }else{
				  condiation+=value+" ";
				  }
	 		}
			 sql=condiation;
		}else{
			return this.sql;
		}
	}
	System.out.println(sql+"值域填充完毕即将开始执行......");
	return sql;
}

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

public List<String> getProperty() {
	return property;
}

public void setProperty(List<String> property) {
	this.property = property;
}

public  Boolean   isWhere(){
	if( sql != null){
		if(sql.contains("where")||sql.contains("WHERE")){
			return true;
		}
	}
	return false;
}
public Object getValue(Object o,String fieldname){
	Object object =null;
	try {
		Class<? extends Object> c = o.getClass();
		Field[] fields = c.getDeclaredFields();
		for (Field field : fields) {
			if(field.getName().equals(fieldname)){
				field.setAccessible(true);
				object= field.get(o);
				return object;				
		}
		
		}
		} catch (SecurityException e) {
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



}
