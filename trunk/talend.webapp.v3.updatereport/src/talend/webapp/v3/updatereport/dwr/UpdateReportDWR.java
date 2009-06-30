package talend.webapp.v3.updatereport.dwr;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;

import talend.webapp.v3.updatereport.bean.DataChangeLog;

import com.amalto.webapp.core.bean.ListRange;
import com.amalto.webapp.core.json.JSONObject;
import com.amalto.webapp.core.util.Util;
import com.amalto.webapp.core.util.XSystemObjects;
import com.amalto.webapp.util.webservices.WSDataClusterPK;
import com.amalto.webapp.util.webservices.WSGetItems;
import com.amalto.webapp.util.webservices.WSStringPredicate;
import com.amalto.webapp.util.webservices.WSWhereAnd;
import com.amalto.webapp.util.webservices.WSWhereCondition;
import com.amalto.webapp.util.webservices.WSWhereItem;
import com.amalto.webapp.util.webservices.WSWhereOperator;

public class UpdateReportDWR {
	
	private Logger logger=org.apache.log4j.Logger.getLogger(this.getClass());
	
	public UpdateReportDWR() {
		
	}
	
	public ListRange getUpdateReportList(int start, int limit,String sort,String dir,String regex)throws Exception{
		ListRange listRange = new ListRange();
		

 		WSDataClusterPK wsDataClusterPK=new WSDataClusterPK(XSystemObjects.DC_UPDATE_PREPORT.getName());
 		String conceptName="Update";//Hard Code
 		
 		
 		//Where condition
 		ArrayList<WSWhereItem> conditions=new ArrayList<WSWhereItem>();
 		if(regex!=null&&regex.length()>0){
 			JSONObject criteria=new JSONObject(regex);
 			
            if(!criteria.isNull("concept")){
            	String concept=(String) criteria.get("concept");
            	WSWhereCondition wc=new WSWhereCondition(
        				"/Update/Concept",
        				WSWhereOperator.CONTAINS,
        				concept.trim(),
        				WSStringPredicate.NONE,
        				false
        				);
         		WSWhereItem wsWhereItem=new WSWhereItem(wc,null,null);
        		conditions.add(wsWhereItem);
            }
            
            if(!criteria.isNull("key")){
            	String key=(String) criteria.get("key");
            	WSWhereCondition wc=new WSWhereCondition(
        				"/Update/Key",
        				WSWhereOperator.CONTAINS,
        				key.trim(),
        				WSStringPredicate.NONE,
        				false
        				);
         		WSWhereItem wsWhereItem=new WSWhereItem(wc,null,null);
        		conditions.add(wsWhereItem);
            }
            
            if(!criteria.isNull("source")){
            	String source=(String) criteria.get("source");
            	WSWhereCondition wc=new WSWhereCondition(
        				"/Update/Source",
        				WSWhereOperator.EQUALS,
        				source.trim(),
        				WSStringPredicate.NONE,
        				false
        				);
         		WSWhereItem wsWhereItem=new WSWhereItem(wc,null,null);
        		conditions.add(wsWhereItem);
            }
            
            if(!criteria.isNull("operationType")){
            	String operationType=(String) criteria.get("operationType");
            	WSWhereCondition wc=new WSWhereCondition(
        				"/Update/OperationType",
        				WSWhereOperator.EQUALS,
        				operationType.trim(),
        				WSStringPredicate.NONE,
        				false
        				);
         		WSWhereItem wsWhereItem=new WSWhereItem(wc,null,null);
        		conditions.add(wsWhereItem);
            }
            
            if(!criteria.isNull("startDate")){
            	String startDate=(String) criteria.get("startDate");
            	SimpleDateFormat dataFmt=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        		Date date=dataFmt.parse(startDate);    
            	WSWhereCondition wc=new WSWhereCondition(
        				"/Update/TimeInMillis",
        				WSWhereOperator.GREATER_THAN_OR_EQUAL,
        				date.getTime()+"",
        				WSStringPredicate.NONE,
        				false
        				);
         		WSWhereItem wsWhereItem=new WSWhereItem(wc,null,null);
        		conditions.add(wsWhereItem);
            }
            
            if(!criteria.isNull("endDate")){
            	String endDate=(String) criteria.get("endDate");
            	SimpleDateFormat dataFmt=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        		Date date=dataFmt.parse(endDate);    
            	WSWhereCondition wc=new WSWhereCondition(
        				"/Update/TimeInMillis",
        				WSWhereOperator.LOWER_THAN_OR_EQUAL,
        				date.getTime()+"",
        				WSStringPredicate.NONE,
        				false
        				);
         		WSWhereItem wsWhereItem=new WSWhereItem(wc,null,null);
        		conditions.add(wsWhereItem);
            }
         
 			
 		}
 		
 	    
		WSWhereItem wi=null;
		if(conditions.size()==0) wi=null;
		else{
			WSWhereAnd and=new WSWhereAnd(conditions.toArray(new WSWhereItem[conditions.size()]));
			wi=new WSWhereItem(null,and,null);
		}
 		
 		String[] results =
			Util.getPort().getItems(new WSGetItems(
					wsDataClusterPK,
					conceptName,
					wi,
            		-1,
            		0,
            		Integer.MAX_VALUE
            	)
            ).getStrings();
		
		DataChangeLog[] data=new DataChangeLog[results.length];
		for (int i = 0; i < data.length; i++) {
			DataChangeLog item=new DataChangeLog();
			
			String result=results[i];
			if(result!=null){
				//Not very OO
				Document doc=Util.parse(result);
				String source=Util.getFirstTextNode(doc, "/Update/Source");
				String timeInMillis=Util.getFirstTextNode(doc, "/Update/TimeInMillis");
				String operationType=Util.getFirstTextNode(doc, "/Update/OperationType");
				String concept=Util.getFirstTextNode(doc, "/Update/Concept");
				String key=Util.getFirstTextNode(doc, "/Update/Key");
				
				
				item.setSource(source);
				item.setTimeInMillis(timeInMillis);
				item.setOperationType(operationType);
				item.setConcept(concept);
				item.setKey(key);
				//item.setXmlSource(result);
				item.setIds(Util.joinStrings(new String[]{source,timeInMillis}, "."));
				
			}
			
			data[i]=item;
		}
		
		if(data.length<=0){
			listRange.setData(new DataChangeLog[0]);
			listRange.setTotalSize(0);
			return listRange;
		}
		
		start=start<data.length?start:data.length-1;
		int end=data.length<(start+limit)?data.length-1:(start+limit-1);
		
		DataChangeLog[] subData=end+1-start<limit?new DataChangeLog[end+1-start]:new DataChangeLog[limit];
		for (int i = start,j=0; i < end+1; i++,j++) {
			subData[j]=data[i];
		}
		
		listRange.setData(subData);
		listRange.setTotalSize(results.length);			
		return listRange;
	}
	

}
