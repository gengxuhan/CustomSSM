package com.gxh.mapper;

import java.util.List;

import com.gxh.bean.news;
import com.gxh.myBaits.core.Page;



public interface NewsMapper {

	
	public void insertNews(news news,String type)throws Exception;
	
	public int selectCount(Object o,String type)throws Exception;
	
	public List<news> selectnews(Object o,String type,Page page)throws Exception;
	
	public void  updatenews(news news,String type)throws Exception;
}
