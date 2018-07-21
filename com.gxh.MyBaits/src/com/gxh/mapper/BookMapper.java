package com.gxh.mapper;

import java.util.List;

import com.gxh.bean.Book;
import com.gxh.bean.Style;

public interface BookMapper {

	public List<Style> findbookBystyle(int  id,String type);
}
