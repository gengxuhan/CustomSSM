package com.gxh.myBaits.dataSource;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface Transaction {

	public  Connection getConn();

	public void commite();

	public void openTransfer();

	public void closeConn();

	public void rollback();
}
