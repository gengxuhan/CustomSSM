package com.gxh.Ioc.MyBaits;

import java.io.ObjectInputStream.GetField;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

public class DataSourceTrancation {

private DataSource dataSource;

private Connection connection;
public DataSourceTrancation(DataSource dataSource) {
	this.dataSource=dataSource;
}

public Connection getConnection(){
	try {
		connection = dataSource.getConnection();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return connection;
}

public void  commit(){
try {
	if(connection!=null){
		connection.commit();	
	}
	
} catch (SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
}

public void rollback(){
	try {
		if(connection != null){
		connection.rollback();
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

public void closeConn(){
	if( connection!=null ){
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
}
