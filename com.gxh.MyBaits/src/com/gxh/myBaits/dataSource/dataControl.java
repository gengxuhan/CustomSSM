package com.gxh.myBaits.dataSource;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Statement;

public abstract class dataControl {
public static List<String>  selectColumn=null;
	
public abstract Object setData(String sql,Object o,List<String> valuelist,String KeyType) ;

public abstract ResultSet queryData(String sql,Object o,List<String> valuelist,String KeyType);







}
