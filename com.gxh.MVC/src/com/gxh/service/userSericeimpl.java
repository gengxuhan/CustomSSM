package com.gxh.service;

import com.gxh.Dao.UserDao;
import com.gxh.Ioc.Annoation.Resource;
import com.gxh.Ioc.Annoation.Service;

@Service(name="user")
public class userSericeimpl  implements UserService{

	@Resource(name="userdao")
	private UserDao userdao;
	
	@Override
	public void add() {
		userdao.test();
		
	}

}
