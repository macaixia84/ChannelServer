package cn.gov.msa.urcms.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import cn.gov.msa.urcms.dao.ColumnDao;
import cn.gov.msa.urcms.service.ColumnServices;


public class ColumnServicesImpl implements ColumnServices{
	
	@Resource
	private ColumnDao columnDao;
	
	
	
   public ColumnDao getColumnDao() {
		return columnDao;
	}



	public void setColumnDao(ColumnDao columnDao) {
		this.columnDao = columnDao;
	}



@Override
	public List<Map<String, Object>> columnQuery(){
		return columnDao.columnQuery();
	}

@Override
public List<Map<String, Object>> getArticleListByid(String columnId,String userId,int pageSize,int Pages,String publish){
	return columnDao.getArticleListByid(columnId,userId,pageSize,Pages,publish);
}

@Override
public String addContent(String Title,String shortName,String author,String target,String ContentDetail,String alter ,String createTime,String updateTime,String reorderTime,String text,byte[][] files,String[] filename,String Appid,String Appname,String source,String assortment,String columnId,String publish){
   return columnDao.addContent(Title, shortName, author, target, ContentDetail, alter, createTime, updateTime, reorderTime, text, files, filename, Appid, Appname, source, assortment, columnId,publish);		   
}

@Override
public Map<String,Object> queryContentById(String contentId,String siteName){
	return columnDao.queryContentById(contentId,siteName);
}
@Override
public String deleteContentById(String contentId,String userId,String appId){
   return columnDao.deleteContentById(contentId, userId, appId);	
}

@Override
public String updateContent(String contentId,String Title,String shortName,String author,String target,String ContentDetail,String alter,String createTime,String updateTime,String reorderTime,String text,byte[][] files,String[] filename,String Appid,String Appname,String source,String assortment,String columnId,String publish){
	return columnDao.updateContent(contentId, Title, shortName, author, target, ContentDetail, alter, createTime, updateTime, reorderTime, text, files, filename, Appid, Appname, source, assortment, columnId,publish);			
}

@Override
public List<Map<String, Object>> assortmentsQuery() {
	return columnDao.assortmentsQuery();
}

@Override
public List<Map<String, Object>> getArticleListByAppid(String appId,
		String userId, int pageSize, int Pages,String publish) {
	return columnDao.getArticleListByAppid(appId, userId, pageSize, Pages,publish);
}

@Override
public List<Map<String, Object>> getArticleListByTypeIdAndChannelId(
		String typeId, String channelId, int pageSize, int Pages,String publish) {
	return columnDao.getArticleListByTypeIdAndChannelId(typeId, channelId, pageSize, Pages,publish);
}

}