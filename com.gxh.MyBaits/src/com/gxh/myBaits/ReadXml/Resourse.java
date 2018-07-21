package com.gxh.myBaits.ReadXml;

import java.io.InputStream;

public class Resourse {

	public static InputStream getClassPath(String resource){
		 InputStream resourceAsStream = Resourse.class.getResourceAsStream(resource);
		return resourceAsStream;
	}
	
	
}
