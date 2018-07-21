package com.gxh.Ioc.MyBaits;

import javax.sql.DataSource;

import com.Ioc.Xml.XmlReader;
import com.gxh.myBaits.ReadXml.XmlReaderFactory;
import com.gxh.myBaits.core.SqlSession;
import com.gxh.myBaits.core.SqlSessionDao;
import com.gxh.myBaits.dataSource.JdbcDbControl;

public class SqlSessionFactoryBean {

public static  String configLocation;

public static  DataSource dataSource;




public String getConfigLocation() {
	return configLocation;
}

public void setConfigLocation(String configLocation) {
	this.configLocation = configLocation;
}

public DataSource getDataSource() {
	return dataSource;
}

public void setDataSource(DataSource dataSource) {
	this.dataSource = dataSource;
}

private static SqlSessionIOCdao sqlSession;




public  Object getMapper(Class c){
	XmlReaderFactory.bulid(configLocation);
	sqlSession=new SqlSessionIOCdao();
	DBcontrol jdbcDbControl=new DBcontrol(new DataSourceTrancation(dataSource));
	sqlSession.setdBcontrol(jdbcDbControl);
	return sqlSession.getMapper(c);
}


	
}
