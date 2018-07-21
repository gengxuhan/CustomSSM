package com.gxh.myBaits.mapping;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.gxh.myBaits.Context.Association;
import com.gxh.myBaits.Context.Collection;
import com.gxh.myBaits.Context.Context;
import com.gxh.myBaits.Context.ID;
import com.gxh.myBaits.Context.Result;
import com.gxh.myBaits.Context.ResultMap;
import com.gxh.myBaits.Context.Select;

public class mapping {

	
	public List<?> MappingResult(ResultSet resultSet,String name,List<String> selectcolumn){
	  HashMap<String,Select> selects=Context.Selects;
	  HashMap<String,ResultMap> resultMaps=Context.resultMaps;
	  Select select = selects.get(name);
	  String resultMapname = select.getMappername()+"."+select.getResultMap();
	  ResultMap resultMap = resultMaps.get(resultMapname);
	  List  resultList=new ArrayList();
	  if(resultMap!=null){  
		 resultList =mappingResultMap(resultSet, resultList, resultMap);
		 return resultList;
	  }else{
		resultList=mappingResultType(select, resultSet, selectcolumn, resultList);
	  }
	    
		return resultList;
		
	}
	
	private List<?> mappingResultType(Select select,ResultSet resultSet,List<String> selectcolumn,List<Object> resultList){
		 String resultType = select.getResultType();  
		 
		 if(resultType.equals("int")){
			 try {
				 while(resultSet.next()){
				Object o = resultSet.getInt(1);
				resultList.add(o);
				return resultList;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
		  try {
			 while( resultSet.next() ){
			Class  c= Class.forName(resultType);
			Object o = c.newInstance();
			 Field[] fields = c.getDeclaredFields();
			 for (Field field : fields) {
				 if( selectcolumn!=null){
				 for (String columnname : selectcolumn) {
					if(field.getName().equals(columnname)){
						Object result = resultSet.getObject(field.getName());
						if(result!=null){
						field.setAccessible(true);
						field.set(o, result);
						break;	}
				}
				
				}}
			}
			 resultList.add(o);
			 }
		 return resultList;
			 
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  return null;
	}



    private List<?>  mappingResultMap(ResultSet resultSet,List<Object> resultList,ResultMap resultMap){
    	ID id = resultMap.getId();
    	List<Result> results = resultMap.getResults();
    	String classname = resultMap.getType();
    	try {
    		while( resultSet.next() ){
    			Class  c= Class.forName(classname);
    			Object o = c.newInstance();
    			String idcolumNname = id.getColumn();
    			String idpropertyName = id.getProperty();
    			Field field = c.getDeclaredField(idpropertyName);
    			Class idtype = field.getType();
    			Method method=c.getDeclaredMethod("set"+idpropertyName.substring(0,1).toUpperCase()+idpropertyName.substring(1), idtype);
    		    method.invoke(o, resultSet.getObject(idcolumNname));
    		    if(results !=null){
    		    	for (Result result : results) {
    					String resultcolumnName = result.getColumn();
    					String resultpropertyName = result.getProperty();
    					Field resultfield = c.getDeclaredField(resultpropertyName);
    					Class resulttype = resultfield.getType();
    					Method resultmethod=c.getDeclaredMethod("set"+resultpropertyName.substring(0,1).toUpperCase()+resultpropertyName.substring(1), resulttype);
    					resultmethod.invoke(o, resultSet.getObject(resultcolumnName));
    				}
    		    }
    		    List<Collection> collections = resultMap.getCollections();
    		    if(collections != null){
    		    	List list=new ArrayList();
    		    	for (Collection collection : collections) {
						String ofType = collection.getOfType();
						String property = collection.getProperty();
						Class  cc = Class.forName(ofType);
						Object oo = cc.newInstance();
						ID collId= collection.getId();
						String propertyname = collId.getProperty();
						Field Collidtype = cc.getDeclaredField(propertyname);
					    Method Collmethod=cc.getDeclaredMethod("set"+propertyname.substring(0,1).toUpperCase()+propertyname.substring(1),Collidtype.getType());
					    if(   resultSet.getObject(collId.getColumn()) !=null ){
					    Collmethod.invoke(oo, resultSet.getObject(collId.getColumn()));
					    }
					    List<Result> Collresults= collection.getResults();
					    if(Collresults != null){
					    for (Result result : Collresults) {
					    	String resultcolumnName = result.getColumn();
	    					String resultpropertyName = result.getProperty();
	    					Field resultfield = cc.getDeclaredField(resultpropertyName);
	    					Class resulttype = resultfield.getType();
	    					Method resultmethod=cc.getDeclaredMethod("set"+resultpropertyName.substring(0,1).toUpperCase()+resultpropertyName.substring(1), resulttype);
	    					resultmethod.invoke(oo, resultSet.getObject(resultcolumnName));
						}
					    }
					    list.add(oo);
					    Field listField = c.getDeclaredField(property);
					   Method methodlist= c.getDeclaredMethod("set"+property.substring(0,1).toUpperCase()+property.substring(1),listField.getType());
					   methodlist.invoke(o, list);
    		    	}
    		    }
    		    mappingAssoaction(resultMap, resultSet, c, o);
    		    
    		    resultList.add(o);
    		    }
    		return resultList;
    			
    		} catch (ClassNotFoundException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} catch (InstantiationException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} catch (IllegalAccessException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} catch (NoSuchFieldException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} catch (SecurityException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} catch (NoSuchMethodException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} catch (IllegalArgumentException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} catch (InvocationTargetException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} catch (SQLException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    	
    	
    	
    	return null;
    	
    }
    
    private void mappingAssoaction(ResultMap resultMap,ResultSet resultSet,Class c,Object o){
    	List<Association> associations = resultMap.getAssociations();
	    if(associations !=null){
	    	for (Association association : associations) {
				String javaType = association.getJavaType();
				ID assid= association.getId();
				String property = association.getProperty();
				Class Assc;
				try {
					Assc = Class.forName(javaType);
					Object AssObj = Assc.newInstance();
					if( assid != null){
					String AssIdcolumn = assid.getColumn();
					String AssIdproperty = assid.getProperty();
					Field AssIdField = Assc.getDeclaredField(AssIdproperty);
					Class  Assidtype = AssIdField.getType();
					Method Assidmethod=Assc.getDeclaredMethod("set"+AssIdproperty.substring(0,1).toUpperCase()+AssIdproperty.substring(1), Assidtype);
				    Assidmethod.invoke(AssObj,resultSet.getObject(AssIdcolumn));
				    }
				    List<Result> resultsAss = association.getResults();
				    if(resultsAss != null){
				    	for (Result result : resultsAss) {
				    		String resultcolumnName = result.getColumn();
							String resultpropertyName = result.getProperty();
							Field resultfield = Assc.getDeclaredField(resultpropertyName);
							Class resulttype = resultfield.getType();
							Method resultmethod=Assc.getDeclaredMethod("set"+resultpropertyName.substring(0,1).toUpperCase()+resultpropertyName.substring(1), resulttype);
							resultmethod.invoke(AssObj, resultSet.getObject(resultcolumnName));	
						}
				    Method  Promerymethod=c.getDeclaredMethod("set"+property.substring(0,1).toUpperCase()+property.substring(1), Assc);
				    Promerymethod.invoke(o, AssObj);
				    }
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchFieldException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
	    	}
	    }
    }



}
