package com.gxh.Dao;

import com.gxh.Ioc.Annoation.Bean;
import com.gxh.Ioc.Annoation.Resource;
import com.gxh.mapper.BookMapper;

@Bean(name="userdao")
public class UserDao {

	@Resource(name="com.gxh.mapper.BookMapper")
	private BookMapper bookMapper;
	
	public void test(){
		bookMapper.findbookBystyle(2,"select");
	}
}
