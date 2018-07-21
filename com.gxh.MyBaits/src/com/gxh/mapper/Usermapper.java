package com.gxh.mapper;

import com.gxh.bean.User;

public interface Usermapper {

	public User finduserByid (String string,String type);
	
	public int insertuser(User user,String type);
}
