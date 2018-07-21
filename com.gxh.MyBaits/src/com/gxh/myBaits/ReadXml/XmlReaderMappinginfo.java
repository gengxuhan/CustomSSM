package com.gxh.myBaits.ReadXml;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.gxh.myBaits.Context.Association;
import com.gxh.myBaits.Context.Collection;
import com.gxh.myBaits.Context.Context;
import com.gxh.myBaits.Context.Delete;
import com.gxh.myBaits.Context.ID;
import com.gxh.myBaits.Context.Insert;
import com.gxh.myBaits.Context.Mapper;
import com.gxh.myBaits.Context.Result;
import com.gxh.myBaits.Context.ResultMap;
import com.gxh.myBaits.Context.Select;
import com.gxh.myBaits.Context.SelectKey;
import com.gxh.myBaits.Context.Update;

public class XmlReaderMappinginfo  implements XmlReadHandler {

	private  HashMap<String,Mapper> Mappers=null;
	
	@Override
	public void readXml(InputStream resource) {
		
		try {
			if(Mappers == null){
				Mappers=new HashMap<String, Mapper>();
			}
			
			SAXReader reader=new SAXReader();
			Document document = reader.read(resource);
			Mapper mapper=new Mapper();
			Element root = document.getRootElement();
			String mappernamespace = root.attributeValue("namespace");
			mapper.setNamespace(mappernamespace);
		    readSelects(root, mapper);
		    readResultMaps(root, mapper);
		    readInserts(root, mapper);
		    readDeletes(root, mapper);
		    readUpdate(root, mapper);
		    Mappers.put(mappernamespace,mapper);
		    Context.Mappers=Mappers;
			
			
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void readSelects(Element root,Mapper mapper){
		List<Element> selectelements = root.elements("select");
	     if( selectelements !=null){
	    	List<Select> selects=new ArrayList<Select>();
	    for (Element selectElement : selectelements) {
	    	if(selectElement != null){
				Select  select=new Select();
				Attribute parameterTypeattribute = selectElement.attribute("parameterType");
				if(parameterTypeattribute != null){
					select.setParameterType(parameterTypeattribute.getValue());
				}else{
					select.setParameterType(null);
				}
				select.setId(selectElement.attributeValue("id"));
				Attribute resultTypeattribute = selectElement.attribute("resultType");
				if(resultTypeattribute != null){
					select.setResultType(resultTypeattribute.getValue());
				}else{
					select.setResultMap(selectElement.attributeValue("resultMap"));
				}
				
			   select.setSql(selectElement.getText().trim());
			   select.setMappername(mapper.getNamespace());
              selects.add(select);
	    	}
		}
	     mapper.setSelects(selects);
	    }
	}
	
	private  void readInserts(Element root,Mapper mapper){
		List<Element> Insertelements = root.elements("insert");
		if(Insertelements != null){
			List<Insert> inserts=new ArrayList<Insert>();
			for (Element insertelement : Insertelements) {
				Insert insert=new Insert();
				insert.setId(insertelement.attributeValue("id"));
				insert.setParameterType(insertelement.attributeValue("parameterType"));
				insert.setSql(insertelement.getText());
				Element selectKeyelement = insertelement.element("selectKey");
				if(selectKeyelement != null){
					SelectKey  selectKey=new SelectKey();
					selectKey.setKeyProperty(selectKeyelement.attributeValue("keyProperty"));
					selectKey.setOrder(selectKeyelement.attributeValue("order"));
					selectKey.setResultType(selectKeyelement.attributeValue("resultType"));
					selectKey.setSql(selectKeyelement.getText().trim());
					insert.setSelectKey(selectKey);
				}
				inserts.add(insert);
			}
			mapper.setInserts(inserts);;
		}
	}
	
	private void readResultMaps(Element root,Mapper mapper){
		 List<Element> resultMapElements = root.elements("resultMap") ;
		    if( resultMapElements != null){
		    	List<ResultMap> resultMaps=new ArrayList<ResultMap>();
		    	for (Element resultMapelement : resultMapElements) {
		    		ResultMap resultMap=new ResultMap();
					resultMap.setIdname(resultMapelement.attributeValue("id"));
					resultMap.setType(resultMapelement.attributeValue("type"));
					Element idelement = resultMapelement.element("id");
					ID id=new ID();
					id.setProperty(idelement.attributeValue("property"));
					id.setColumn(idelement.attributeValue("column"));
					resultMap.setId(id);
					List<Element> resultelements = resultMapelement.elements("result");
					if(resultelements != null){
						List<Result> results=new ArrayList<Result>();
						for (Element resultelement : resultelements) {
							Result result=new Result();
							result.setProperty(resultelement.attributeValue("property"));
							result.setColumn(resultelement.attributeValue("column"));
							results.add(result);
						}
						resultMap.setResults(results);
					}
					List<Element> associationelements = resultMapelement.elements("association");
					if(associationelements != null){
						List<Association> associations=new ArrayList<Association>();
						for (Element associationelement : associationelements) {
							Association association=new Association();
							association.setSelect(associationelement.attribute("select")==null?null:associationelement.attributeValue("select"));
							association.setColumn(associationelement.attribute("column")==null?null:associationelement.attributeValue("column"));
							association.setProperty(associationelement.attributeValue("property"));
							association.setJavaType(associationelement.attributeValue("javaType"));
							Element associationelementid = associationelement.element("id");
							if( associationelementid != null){
							ID assassociationId=new ID();
							assassociationId.setProperty(associationelementid.attributeValue("property"));
							assassociationId.setColumn(associationelementid.attributeValue("column"));
							association.setId(assassociationId);
							}
							List<Element> Mapresultelements = associationelement.elements("result");
							if( Mapresultelements!= null){
								List<Result> results=new ArrayList<Result>();
								for (Element resultelement : Mapresultelements) {
									Result result=new Result();
									result.setColumn(resultelement.attributeValue("column"));
									result.setProperty(resultelement.attributeValue("property"));
									results.add(result);
								}
								association.setResults(results);
							}
							associations.add(association);
						
						}
						resultMap.setAssociations(associations);
					}
					
					List<Element> Collectionelements = resultMapelement.elements("collection");
					if(Collectionelements!=null){
						List<Collection> collections=new ArrayList<Collection>();
						for (Element collectionelement : Collectionelements) {
							Collection collection=new Collection();
							collection.setProperty(collectionelement.attributeValue("property"));
							collection.setOfType(collectionelement.attributeValue("ofType"));
							Element elementid = collectionelement.element("id");
							ID collectionid=new ID();
							collectionid.setColumn(elementid.attributeValue("column"));
							collectionid.setProperty(elementid.attributeValue("property"));
							collection.setId(collectionid);
							List<Element> Resultelements = collectionelement.elements("result");
							if(Resultelements != null){
								List<Result> resultList=new ArrayList<Result>();
								for (Element resultelement : Resultelements) {
									Result result=new Result();
									result.setColumn(resultelement.attributeValue("column"));
									result.setProperty(resultelement.attributeValue("property"));
									resultList.add(result);
								}
								collection.setResults(resultList);
							}
							collections.add(collection);
						}
						resultMap.setCollections(collections);
					}
					
					resultMaps.add(resultMap);			
				}
		    	mapper.setResultMaps(resultMaps);
		    	
		    }
	}


    private void readDeletes(Element root,Mapper mapper){
    	List<Element> deleteelements = root.elements("delete");
    	if(deleteelements != null){
    		List<Delete> deletes=new ArrayList<Delete>();
    		for (Element deleteelement : deleteelements) {
				Delete delete=new Delete();
				delete.setId(deleteelement.attributeValue("id"));
				delete.setParameterType(deleteelement.attributeValue("parameterType"));
				delete.setSql(deleteelement.getText().trim());
				deletes.add(delete);
			}
    	mapper.setDeletes(deletes);
    	}
    	
    }
    
    private void readUpdate(Element root,Mapper mapper){
    	List<Element> Updateelements = root.elements("update");
    	if(Updateelements!=null){
    		List<Update> updates=new ArrayList<Update>();
    		for (Element updateelement : Updateelements) {
				Update update=new Update();
				update.setId(updateelement.attributeValue("id"));
				update.setParameterType(updateelement.attributeValue("parameterType"));
				update.setSql(updateelement.getText().trim());
				updates.add(update);
			}
           mapper.setUpdates(updates);
    	}
    }
}
