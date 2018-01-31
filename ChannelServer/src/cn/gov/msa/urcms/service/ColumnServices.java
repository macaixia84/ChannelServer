package cn.gov.msa.urcms.service;

import java.util.List;
import java.util.Map;

public interface ColumnServices{
	
	//获取栏目名称列表
	public List<Map<String, Object>> columnQuery();
	
	//查询指定栏目的相关内容
	public List<Map<String, Object>> getArticleListByid(String columnId,String userId,int pageSize,int Pages,String publish);
	
	
	//内容资源添加
		/*
		 * author:macaixia
		 * input:内容标题,副标题,作者,展现系统,摘要,修改者,创建时间,修改时间,排序时间
		 * 主体内容,附件,附件名称,系统id,系统名称,来源,业务分类,栏目id
		 * output:success,failed
		 */
	public String addContent(String Title,String shortName,String author,String target,String ContentDetail,String alter ,String createTime,String updateTime,String reorderTime,String text,byte[][] files,String[] filename,String Appid,String Appname,String source,String assortment,String columnId,String pubish);

	//内容资源查询
    public Map<String,Object> queryContentById(String contentId,String siteName);
    
    //内容资源删除
    public String deleteContentById(String contentId,String userId,String appId);
    
    //内容资源修改
    public String updateContent(String contentId,String Title,String shortName,String author,String target,String ContentDetail,String alter,String createTime,String updateTime,String reorderTime,String text,byte[][] files,String[] filename,String Appid,String Appname,String source,String assortment,String columnId,String publish);

    //查询业务分类
    public List<Map<String, Object>> assortmentsQuery();
    
  //根据业务系统id查询内容列表
    public List<Map<String,Object>> getArticleListByAppid(String appId,String userId,int pageSize,int Pages,String publish);

    public List<Map<String, Object>> getArticleListByTypeIdAndChannelId(String typeId,String channelId,int pageSize, int Pages,String publish);

}