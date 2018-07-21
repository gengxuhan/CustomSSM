package com.gxh.bean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class StringUitls {

	
	
	public static String getUUid(){
		String uuid = UUID.randomUUID().toString();
		String replaceAll = uuid.replaceAll("-", "");
		return replaceAll;
	}
	
	public static String getCreateTime(){
		SimpleDateFormat dateFormat=new SimpleDateFormat(" yyyy-MM-dd hh-mm-ss");
		String format = dateFormat.format(new Date());
		return format;
	}
	

}
