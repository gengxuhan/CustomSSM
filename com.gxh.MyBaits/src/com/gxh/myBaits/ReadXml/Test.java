package com.gxh.myBaits.ReadXml;


import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.jws.Oneway;

import com.gxh.bean.Book;
import com.gxh.bean.StringUitls;
import com.gxh.bean.Style;
import com.gxh.bean.User;
import com.gxh.bean.news;
import com.gxh.mapper.BookMapper;
import com.gxh.mapper.NewsMapper;
import com.gxh.mapper.Usermapper;
import com.gxh.myBaits.Context.Context;
import com.gxh.myBaits.core.Page;
import com.gxh.myBaits.core.SqlSession;
import com.gxh.myBaits.core.SqlSessionFactory;



public class Test {

	
	public static void main(String[] args) {
		SqlSessionFactory.build("/conf/SqlMapConfig.xml");
		SqlSession session = SqlSessionFactory.OpenSession();
	/*	Book book=new Book();
		Style style=new Style();
		style.setStyleid(6);
		book.setBookId(1);
		book.setAuthor("五三");
		book.setStyle(style);
		List<Book> selectList = (List<Book>) session.selectList("com.gxh.Dao.UserMapper.findbookBystyle",book);
		for (Book book2 : selectList) {
			System.out.println(book2.getBookname()+book2.getStyle().getStyleid());
		}*/
  /* Style style=new Style();
		style.setStyleid(2);
		List<Style> selectList =(List<Style>) session.selectList("com.gxh.mapper.BookMapper.findbookBystyle",2);
		
		for (Style style2 : selectList) {
			System.out.println(style2.getStyleid());
			List<Book> booklist = style2.getBooklist();
			for (Book book : booklist) {
				System.out.println(book.getBookname());
			}
		}*/
		/*  
		Book book = (Book) session.selectLazy("com.gxh.Dao.UserMapper.findbook",Book.class,2);
		System.out.println(book.getBookname());
		Style style = book.getStyle();
		System.out.println(style.getStyleid());
		System.out.println(style.getStylename());*/
		NewsMapper mapper = session.getMapper(NewsMapper.class);
		news news=new news();
		news.setNewsid("be2157f7149c4cf9aa0aa678b57cb201");
		news.setCreatetime(StringUitls.getCreateTime());
		news.setNewstitle("新闻标题测试");
		try {
			mapper.updatenews(news,"update");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	/*	User user2=new User();
		user2.setUsername("libai");
		user2.setPassword("456");
		mapper.insertuser(user2,"insert");*/
		
		/*BookMapper mapper = session.getMapper(BookMapper.class);
		mapper.findbookBystyle(2);*/
		
	}
	
}
