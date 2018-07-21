package com.mvc.Intercepter;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.mvc.Context.Context;
import com.mvc.upload.MultiPartFile;

public class UploadIntercepter extends IntercepterAdpter {

	private String SizeThreshold="4396";
	
	
	
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
	
	  DiskFileItemFactory diskFileItemFactory=new DiskFileItemFactory();
	  //缓存文件大小
	  diskFileItemFactory.setSizeThreshold(Integer.parseInt(SizeThreshold));
	  File file=new File(request.getSession().getServletContext().getRealPath("/")+"/temp");
	  //如果目录不存在则创建
	  if( !file.exists() ){
		  file.mkdir();
	  }
	  //设置临时目录
	  diskFileItemFactory.setRepository(file);  
	  ServletFileUpload fileUpload =new ServletFileUpload();
	  
	  fileUpload.setFileItemFactory(diskFileItemFactory);
	  List<FileItem> FileItems = fileUpload.parseRequest(request);
	  if( FileItems !=null ){
		  Context.multiFiles=new HashMap<String, FileItem>();
		  for (FileItem fileItem : FileItems) {
			  
				if( fileItem.isFormField() ){
					request.setAttribute(fileItem.getFieldName(), fileItem.getString());
				}else{
					System.out.println(fileItem.getFieldName());
				Context.multiFiles.put(fileItem.getFieldName(),fileItem);
				
				}
			}	  
	  }
	 
		
		return true;
	}



}
