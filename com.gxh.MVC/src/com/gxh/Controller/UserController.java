package com.gxh.Controller;

import java.io.File;
import java.util.List;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;

import com.Ioc.core.BeanFactory;
import com.gxh.Ioc.Annoation.Resource;
import com.gxh.bean.Style;
import com.gxh.mapper.BookMapper;
import com.gxh.service.UserService;
import com.mvc.Annotaction.Controller;
import com.mvc.Annotaction.RequestMapping;
import com.mvc.core.ModeAndView;

@Controller
public class UserController {

	@Resource(name="user")
	private UserService service;
	
	@RequestMapping(url="/jsp/add")
	public String adduser(){
	service.add();
		return "/NewFile.html";
	}
	
	@RequestMapping(url="/ee")
    public void addusers(){
	
    }
}
