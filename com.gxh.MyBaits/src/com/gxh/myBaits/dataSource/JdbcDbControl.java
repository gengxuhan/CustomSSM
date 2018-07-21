package com.gxh.myBaits.dataSource;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.sql.DataSource;

import com.gxh.myBaits.Context.Context;
import com.mysql.jdbc.Statement;

public class JdbcDbControl  extends dataControl implements Transaction {

	public  static Connection conn=null;
	
	private DataSource dataSource;
	
	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public  static HashMap<String,String> dbMap=null;
	

	public static Boolean flag=false;
	
	static{
		
		if( dbMap==null ){
		dbMap=Context.DBinfo;
		}
	}
	public  JdbcDbControl(){
		if( dataSource==null){
		if(dbMap.containsKey("dataSource")){
			if( dbMap.containsKey("dataSource")){
				if(dataSource == null){
				String dsClass = dbMap.get("dataSource");
				try {
					Class c = Class.forName(dsClass);
					dataSource =(DataSource) c.newInstance();
					Iterator<String> iterator = dbMap.keySet().iterator();
					while( iterator.hasNext() ){
						String key = iterator.next();
						if( !"dataSource".equals(key)&&!"transactionManager".equals(key)){
							Method method=c.getDeclaredMethod("set"+key.substring(0, 1).toUpperCase()+key.substring(1),String.class);
							method.invoke(dataSource,dbMap.get(key));
						}
					}
					getdataSourceConn();
					
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}else{
				getdataSourceConn();
				}
			}
		}else{
		getConn();
		}
		}else{
	     getdataSourceConn();
		}
	}
	
	public Connection getConn(){
		try {
			Class.forName(dbMap.get("driver"));
			conn = DriverManager.getConnection(dbMap.get("url"),dbMap.get("username"),dbMap.get("password"));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return conn;
	}
	public Connection  getdataSourceConn(){
		try {
			conn= dataSource.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
	
	public  Object setData(String sql,Object o,List<String> valuelist,String KeyType){
		
			PreparedStatement ps;
			try {
				openTransfer();
				ps = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
				if( o != null){
				if(o.getClass().isInstance(String.class)){
					ps.setString(1, (String)o);
				}else if( o.getClass().isInstance(int.class)){
					ps.setInt(1,(int) o);
				}else{
				for( int j=0;j<valuelist.size();j++){
					String fieldname = valuelist.get(j);
					Field field = o.getClass().getDeclaredField(fieldname);
					Class<?> type = field.getType();
					if(!type.isInstance(String.class)){
						 Object value = getValue(o, fieldname);
						 if(value!=null){
						ps.setString(j+1,value.toString());
						}else{
							ps.setString(j+1,null);	
						}
					}else{
						ps.setInt(j+1,(int)getValue(o, fieldname));
					}
				}
				
				}
				}
				ps.executeUpdate();
				Object result=null;
				if( KeyType !=null){
				ResultSet resultSet = ps.getGeneratedKeys();
					
				if("int".equalsIgnoreCase(KeyType)){
					while(resultSet.next()){
					result=resultSet.getInt(1);
					}
				}else{
					while(resultSet.next()){
					result=resultSet.getString(1);}
				}
				}
				if(!flag){
					commite();
				}
				return result;
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				rollback();
				e.printStackTrace();
			}
			
		
			return null;
		
	}
	
	public  ResultSet queryData(String sql,Object o,List<String> valuelist,String KeyType){
		
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement(sql);
			if( o != null){
			if( valuelist.size()>0){
				for( int j=0;j<valuelist.size();j++){
					String fieldname = valuelist.get(j);
					Field field = o.getClass().getDeclaredField(fieldname);
					Class<?> type = field.getType();
					if(!type.isInstance(String.class)){
						ps.setString(j+1, getValue(o, fieldname).toString());
					}else{
						ps.setInt(j+1,(int)getValue(o, fieldname));
					}
				}	
			}
				
			}
			
			
			ResultSet resultSet = ps.executeQuery();
			ResultSetMetaData metaData = resultSet.getMetaData();
			int columnCount = metaData.getColumnCount();
			selectColumn=new ArrayList<String>();
			for (int i = 0; i <columnCount; i++) {
				String columnName = metaData.getColumnName(i+1);
				selectColumn.add(columnName);			
			}
			
			return resultSet;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			rollback();
			e.printStackTrace();
		}
		
	
		return null;
	
}
	
	
	
	

	
	public void commite(){
		
			try {
				conn.commit();
				flag=true;
			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				closeConn();
			}
		
	}
	public static void commiteTranaction(){
		try {
			conn.commit();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if( conn != null){
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public void openTransfer(){
		
			try {
				conn.setAutoCommit(false);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		

	}
	
	public Object getValue(Object o,String fieldname) throws Exception{
		   
		    Object object =null;	
			Class<? extends Object> c = o.getClass();
			Field field = c.getDeclaredField(fieldname);
			field.setAccessible(true);
			object= field.get(o);
		
		return object;

}
	
public  void closeConn(){
	if( conn != null ){
		
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
	}
}

public void rollback(){
	try {
		conn.rollback();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

}
	
	
