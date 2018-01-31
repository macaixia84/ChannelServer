package cn.gov.msa.urcms.ws;




import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.rpc.ServiceException;

import cn.gov.msa.urcms.service.ColumnServices;







import org.springframework.remoting.jaxrpc.ServletEndpointSupport;

@SuppressWarnings("deprecation")
public class URCmsService extends ServletEndpointSupport{
	
	private ColumnServices columnServices;
	private XmlHelper xmlHelper = new XmlHelper();
	

	
	@Override
	protected void onInit() throws ServiceException {
		columnServices = (ColumnServices)getApplicationContext().getBean("columnServices");
	}
	
	public String queryColumnNames(){
		
		List<Map<String, Object>> columnResults = new ArrayList<Map<String,Object>>();
		columnResults = columnServices.columnQuery();			
		return xmlHelper.list2json(columnResults);
	}
	
	public String queryArticleList(String columnId,String userId,int pageSize,int Pages,String publish){
		List<Map<String, Object>> articleResults = new ArrayList<Map<String,Object>>();
		articleResults = columnServices.getArticleListByid(columnId,userId,pageSize,Pages,publish);
		return xmlHelper.list3json(articleResults);
	}
	
	public String addArticle(String Title,String shortName,String author,String target,String ContentDetail,String alter ,String createTime,String updateTime,String reorderTime,String text,byte[][] files,String[] filename,String Appid,String Appname,String source,String assortment,String columnId,String publish){
		String result = columnServices.addContent(Title, shortName, author, target, ContentDetail, alter, createTime, updateTime, reorderTime, text, files, filename, Appid, Appname, source, assortment, columnId,publish);
		return xmlHelper.addContentResult2json(result);
	}
	public String queryArticle(String contentId,String siteName){
		Map<String, Object> queryResults = columnServices.queryContentById(contentId,siteName);
		return xmlHelper.queryContents2json(queryResults);
	}
	public String deleteArticleById(String contentId,String userId,String appId){
		String result = columnServices.deleteContentById(contentId, userId, appId);
		return xmlHelper.deleteArticle2json(result);
	}
	public String updateArticle(String contentId,String Title,String shortName,String author,String target,String ContentDetail,String alter,String createTime,String updateTime,String reorderTime,String text,byte[][] files,String[] filename,String Appid,String Appname,String source,String assortment,String columnId,String publish){
		String result = columnServices.updateContent(contentId, Title, shortName, author, target, ContentDetail, alter, createTime, updateTime, reorderTime, text, files, filename, Appid, Appname, source, assortment, columnId,publish);
		return xmlHelper.updateArticle2json(result);
	}
	public String queryAssortments(){
		List<Map<String, Object>> assortmentResults = new ArrayList<Map<String,Object>>();
		assortmentResults = columnServices.assortmentsQuery();
		return xmlHelper.listAssortjson(assortmentResults);
	}
	
	public String queryArticleListByAppid(String appId,String userId, int pageSize, int Pages,String publish){
		List<Map<String, Object>> articleResults = new ArrayList<Map<String,Object>>();
		articleResults = columnServices.getArticleListByAppid(appId,userId,pageSize,Pages,publish);
		return xmlHelper.listByAppidjson(articleResults);
	}
	public String queryArticleListByTypeIdAndChannelId(String typeId, String channelId, int pageSize, int Pages,String publish){
		List<Map<String, Object>> articleResults = new ArrayList<Map<String,Object>>();
		articleResults = columnServices.getArticleListByTypeIdAndChannelId(typeId, channelId, pageSize, Pages,publish);
		return xmlHelper.listByTypeIdAndChannelIdJson(articleResults);
	}
}
