package com.mvc.HandlerMapping;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

public interface HandlerMapping {

public void  mappingHandler(List<String> nameslist,HashMap<String,Object>  handlerMap,HashMap<String, Method>  AdpterMap);
}
