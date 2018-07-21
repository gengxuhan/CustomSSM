package com.mvc.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.fileupload.FileItem;

import com.mvc.Intercepter.Intercepter;
import com.mvc.Intercepter.Parameter;
import com.mvc.upload.MultiPartFile;

public class Context {
	
	public static List<String> packagenames=null;
	
	public static Map<String,List<String>> interMap=null;
	
	public static Map<String,List<Parameter>> IntercepterParameters=null;
	
	public static Map<String,FileItem>  multiFiles=null;

}
