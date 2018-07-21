package com.mvc.core;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mvc.Context.Context;
import com.mvc.Context.XmlReader;
import com.mvc.HandlerAdpter.HandlerAdpter;
import com.mvc.HandlerMapping.MappingHandler;
import com.mvc.Intercepter.Intercepter;
import com.mvc.Intercepter.IntercepterChain;
import com.mvc.Intercepter.Parameter;
import com.mvc.PackageScanner.ClasspathPackageScanner;
import com.mvc.PackageScanner.PackageScanner;
import com.mvc.ViewResolver.ModeAndViewResolver;

/**
 * Servlet implementation class Dispather
 */

public class Dispather extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private String configlocation = null;

	private HashMap<String, Object> handlerMap = null;

	private HashMap<String, Method> AdpterMap = null;

	private MappingHandler mappingHandler = null;

	private Map<String, List<Intercepter>> intercepterMap = null;

	private List<Intercepter> allinIntercepters = null;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Dispather() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init() throws ServletException {
		super.init();
		configlocation = this.getInitParameter("configlocation");
		if (handlerMap == null) {
			XmlReader reader = new XmlReader();
			reader.read(configlocation);
			initHanlerMapping(Context.packagenames);
			initIntercepters();

		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		dispather(request, response);

	}
	
	 Object getHandler(String url){
		Set<String> keySet = handlerMap.keySet();
		Iterator<String> iterator = keySet.iterator();
		while( iterator.hasNext() ){
			String key = iterator.next();
			String[] strings = key.split("#");
			for (String mapping : strings) {
			if(url.equals(mapping)){
				return handlerMap.get(key);
				
			}	
			}
		}
		return null;
	}

	public void dispather(HttpServletRequest request,
			HttpServletResponse response) {
		String url = request.getServletPath();
		url=url.substring(0,url.lastIndexOf("."));
		System.out.println(url);
		Object handler = getHandler(url);
		if( handler == null) return;
		IntercepterChain handlerChain = mappingHandler.getHandlerChain(
				allinIntercepters, intercepterMap,handler,
				url);
		try {
			boolean preHandle = handlerChain.preHandle(request, response);
			if (!preHandle) {
				handlerChain.PostHandle(request, response);
				return;
			}
			HandlerAdpter adpter=new HandlerAdpter();
			Object mv = adpter.handle(request, response,handler , AdpterMap);
			handlerChain.PostHandle(request, response);
			if( mv == null) return;
			if(mv instanceof ModeAndView){
				ModeAndViewResolver resolver=new ModeAndViewResolver((ModeAndView) mv, request, response);
				resolver.resolverView();
				return;
			}else{
				
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void initHanlerMapping(List<String> packagenames) {
		for (String packagename : packagenames) {
			PackageScanner scan = new ClasspathPackageScanner(packagename);
			try {
				List<String> nameList = scan.getFullyQualifiedClassNameList();
				mappingHandler = new MappingHandler();
				if (handlerMap == null) {
					handlerMap = new HashMap<String, Object>();
				}
				if (AdpterMap == null) {
					AdpterMap = new HashMap<String, Method>();
				}
				mappingHandler.mappingHandler(nameList, handlerMap, AdpterMap);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void initIntercepters() {
		Map<String, List<String>> interMap = Context.interMap;
	    Map<String,List<Parameter>> parmMap=Context.IntercepterParameters;
	    if( interMap!=null){
		Set<String> keySet = interMap.keySet();
		Iterator<String> iterator = keySet.iterator();
		while (iterator.hasNext()) {
			String path = iterator.next();
			try {
				if (path.equals("/**")) {
					if (allinIntercepters == null) {
						allinIntercepters = new ArrayList<Intercepter>();
					}
					List<String> list = interMap.get(path);
					for (String classname : list) {
						Class<?> c = Class.forName(classname);
						Intercepter in=(Intercepter) c.newInstance();
						List<Parameter> parameters = parmMap.get(classname);
						for (Parameter parameter : parameters) {
							Field field = c.getDeclaredField(parameter.getName());
							field.setAccessible(false);
							field.set(in, parameter.getValue());
						}
						
						allinIntercepters.add(in);
					}

				} else {
					if (intercepterMap == null) {
						intercepterMap = new HashMap<String, List<Intercepter>>();
					}
					List<Intercepter> list = intercepterMap.get(path);
					List<String> clnamelist = interMap.get(path);
					for (String classname : clnamelist) {
						Class<?> c = Class.forName(classname);
						Intercepter in=(Intercepter) c.newInstance();
						List<Parameter> parameters = parmMap.get(classname);
						for (Parameter parameter : parameters) {
							Field field = c.getDeclaredField(parameter.getName());
							field.setAccessible(true);
							field.set(in, parameter.getValue());
						}
						if (list == null) {
							list = new ArrayList<Intercepter>();
							list.add(in);
							intercepterMap.put(path, list);
						} else {
							list.add(in);
						}
					}

				}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
	    }
}
