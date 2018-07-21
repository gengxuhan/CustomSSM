package com.gxh.bean;


import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.jws.Oneway;

import com.gxh.bean.Book;
import com.gxh.bean.Style;
import com.gxh.mapper.BookMapper;
import com.gxh.myBaits.Context.Context;
import com.gxh.myBaits.core.SqlSession;
import com.gxh.myBaits.core.SqlSessionFactory;



public class Test {

	
	public static void main(String[] args) {
		SqlSessionFactory.build("/config/SqlMapConfig.xml");
		SqlSession session = SqlSessionFactory.OpenSession();
	/*	Book book=new Book();
		Style style=new Style();
		style.setStyleid(6);
		book.setBookId(1);
		book.setAuthor("ÎåÈý");
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
		  
		/*Book book = (Book) session.selectLazy("com.gxh.Dao.UserMapper.findbook",Book.class,2);
		System.out.println(book.getBookname());
		Style style = book.getStyle();
		System.out.println(style.getStyleid());
		System.out.println(style.getStylename());*/
		BookMapper o = session.getMapper(BookMapper.class);
		List<Style> list = o.findbookBystyle(2,"select");
		for (Style style : list) {
			 System.out.println(style.getStylename());
			 List<Book> booklist = style.getBooklist();
			 for (Book book : booklist) {
				System.out.println(book.getBookname());
			}
		}
		
	}
}
