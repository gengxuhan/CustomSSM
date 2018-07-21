package com.gxh.myBaits.Proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.sql.ResultSet;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.gxh.myBaits.Context.Context;
import com.gxh.myBaits.Context.Insert;
import com.gxh.myBaits.Context.Select;
import com.gxh.myBaits.Context.Update;
import com.gxh.myBaits.Sql.DeleteSqlchange;
import com.gxh.myBaits.Sql.InsertSqlchange;
import com.gxh.myBaits.Sql.SelectSqlexchange;
import com.gxh.myBaits.Sql.UpdateSqlchange;
import com.gxh.myBaits.core.Page;
import com.gxh.myBaits.dataSource.JdbcDbControl;
import com.gxh.myBaits.dataSource.dataControl;
import com.gxh.myBaits.mapping.mapping;

public class MapperProxy implements InvocationHandler {

	private static final String KeyType = null;

	public MapperProxy(String mappernameAndSelectname,
			dataControl jdbdDataControl) {
		this.jdbdDataControl = jdbdDataControl;
		this.mappername = mappernameAndSelectname;
	}

	private String mappername;

	private dataControl jdbdDataControl;

	public MapperProxy(String mappernameAndSelectname) {
		this.mappername = mappernameAndSelectname;
	}

	@Override
	public Object invoke(Object obj, Method method, Object[] args)
			throws Throwable {
		String name = mappername + "." + method.getName();
		if (jdbdDataControl == null) {
			jdbdDataControl = new JdbcDbControl();
		}
		String excutename =(String) args[1];
		
		if ("select".equalsIgnoreCase(excutename)) {
			SelectSqlexchange selectSqlexchange = new SelectSqlexchange();
			
			String sql = selectSqlexchange.change(Context.Sqlinfo, name, args[0]);
			if(args.length>=3&&args[2]!=null){
				Page page=(Page) args[2];
				String orderbycolumnName = page.getOrderbycolumnName();
				String orderby = page.getOrderby();
				if(StringUtils.isNotBlank(orderbycolumnName)&&StringUtils.isNotBlank(orderby)){
					sql+=" order by "+orderbycolumnName+" "+orderby+" limit "+page.getStatRow()+","+page.getEndRow();
				}else{
					sql+=" limit "+page.getStatRow()+","+page.getEndRow();
				}
			}
			ResultSet resultSet = jdbdDataControl.queryData(sql, args[0],
					selectSqlexchange.getProperty(), null);
			mapping mapping = new mapping();
			List<?> result = mapping.MappingResult(resultSet, name,
					jdbdDataControl.selectColumn);
			String typeName = method.getGenericReturnType().getTypeName();
			if (result.size()>0) {
				if (typeName.contains("List")) {
					return result;
				} else {
					return result.get(0);
				}
			}
			
		}
		
	    if("delete".equalsIgnoreCase(excutename)){
	    	DeleteSqlchange deleteSqlchange=new DeleteSqlchange();
			String sql = deleteSqlchange.change(Context.Sqlinfo, name, args[0]);	
			jdbdDataControl.setData(sql, args[0], deleteSqlchange.getProperty(),null);
	    }
	    
	    if("insert".equalsIgnoreCase(excutename)){
	    	InsertSqlchange insertSqlchange=new InsertSqlchange();
			String sql = insertSqlchange.change(Context.Sqlinfo, name,args[0]);
			Insert insert = Context.Inserts.get(name);
			if( insert.getSelectKey() == null){
				jdbdDataControl.setData(sql, args[0], insertSqlchange.getProperty(),null);
			}else{
				Object data= jdbdDataControl.setData(sql,args[0], insertSqlchange.getProperty(),insert.getSelectKey().getResultType());
				return data;
			}
	    }
	    
	    if("update".equalsIgnoreCase(excutename)){
	    	UpdateSqlchange updateSqlchange=new UpdateSqlchange();
	    	String sql = updateSqlchange.change(Context.Sqlinfo, name, args[0]);
	    	Update update = Context.Updates.get(name);
	    	jdbdDataControl.setData(sql,args[0], updateSqlchange.getProperty(), null);
	    	return null;
	    }

		return null;

	}
	
	

}
