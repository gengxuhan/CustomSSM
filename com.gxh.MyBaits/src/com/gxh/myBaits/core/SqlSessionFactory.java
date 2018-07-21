package com.gxh.myBaits.core;

import com.gxh.myBaits.ReadXml.XmlReaderFactory;

public class SqlSessionFactory {

	public static void build(String resource) {
		XmlReaderFactory.bulid(resource);
	}
	

	public static SqlSession OpenSession() {
		
		SqlSession session = (SqlSession) new SqlSessionDao();
          
		return session;

	}

}
