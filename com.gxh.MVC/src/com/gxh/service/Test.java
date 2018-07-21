package com.gxh.service;

import com.gxh.Ioc.Annoation.Bean;
import com.gxh.Ioc.Annoation.Resource;

@Bean(name="test")
public class Test {

@Resource(name="user")
private  UserService service;
@Resource(name="user2")
private  UserService service2;

public void test(){
	service.add();
	service2.add();
}
}
