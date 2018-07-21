package com.gxh.myBaits.Context;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
/**
 * 
 * @author GengXuHan
 * @Description
 * @Date 2018年6月8日 下午8:05:39
 */
public class Context {

	
	public static HashMap<String,String>  DBinfo=null;
	
	//加载执行顺序
	public static List<String> xmlResourse =null;
	
	public static HashMap<String, Mapper> Mappers=null;
		
	//KEY  为  namespace+select(name)  value = sql
	public static  HashMap<String,String>  Sqlinfo=null;
	
	public static HashMap<String,ResultMap> resultMaps=null;
	
	public static  HashMap<String,Insert>  Inserts=null;
	
	public  static HashMap<String,Select>  Selects=null;
	
	public static HashMap<String,Delete>  Deletes=null;
	
	public static HashMap<String,Update>  Updates=null;
		
	public static void init (){
			Sqlinfo=new HashMap<String, String>();
			HashMap<String,Mapper> Mappers=Context.Mappers;
			Iterator<String> iterator = Mappers.keySet().iterator();
			StringBuffer buffer=new StringBuffer();
			Inserts =new HashMap<String, Insert>();
			Deletes =new HashMap<String, Delete>();
			Selects =new HashMap<String, Select>();
			Updates=new HashMap<String, Update>();
			resultMaps=new HashMap<String, ResultMap>();
			while( iterator.hasNext() ){
			String namespace = iterator.next();
			Mapper mapper = Mappers.get(namespace);
			List<Select> selects = mapper.getSelects();
			List<Insert> inserts = mapper.getInserts();
			List<Delete> deletes= mapper.getDeletes();
			List<Update> updates=mapper.getUpdates();
			List<ResultMap> resultMapss = mapper.getResultMaps();
			for (ResultMap resultMap : resultMapss) {
				String id = resultMap.getIdname();
				buffer.append(namespace).append(".").append(id);
				resultMaps.put(buffer.toString(), resultMap);
				buffer.delete(0, buffer.length());
			}
			for (Delete delete : deletes) {
				String sql = delete.getSql();
				String id = delete.getId();
				buffer.append(namespace).append(".").append(id);
				Sqlinfo.put(buffer.toString(), sql);
				Deletes.put(buffer.toString(), delete);
				buffer.delete(0, buffer.length());
			}
			for (Insert insert : inserts) {	
				String sql = insert.getSql();
				String id = insert.getId();
				buffer.append(namespace).append(".").append(id);
				Sqlinfo.put(buffer.toString(), sql);
				Inserts.put(buffer.toString(), insert);
				buffer.delete(0, buffer.length());
			}
			for (Select select : selects) {
				String sql = select.getSql();
				String id = select.getId();
				buffer.append(namespace).append(".").append(id);
				Sqlinfo.put(buffer.toString(), sql);
				Selects.put(buffer.toString(), select);
				buffer.delete(0, buffer.length());
			}
			
			for(Update update:updates){
				String sql=update.getSql();
				String id=update.getId();
				buffer.append(namespace).append(".").append(id);
				Sqlinfo.put(buffer.toString(),sql);
				Updates.put(buffer.toString(),update);
				buffer.delete(0,buffer.length());
			}
		
			}
			}
	
	
}
