package com.gxh.service;

import com.gxh.Dao.NewsDao;
import com.gxh.Dao.UserDao;
import com.gxh.Ioc.Annoation.Resource;
import com.gxh.Ioc.Annoation.Service;

@Service(name="user")
public class userSericeimpl  implements UserService{

	@Resource(name="newsdao")
	private NewsDao newsDao;
	
	@Resource(name="userdao")
	private UserDao userDao;
	@Override
	public void add() {
		newsDao.test();
		System.out.println("µÚ¶þ´Î");
		userDao.test();
		
	}

}
