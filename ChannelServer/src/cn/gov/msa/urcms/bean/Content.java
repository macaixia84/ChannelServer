package cn.gov.msa.urcms.bean;

public class Content {

	private int content_id;
	
	//栏目ID
	private int channel_id;
	
	//用户ID
	private int user_id;
	
	//属性ID
	private int type_id;
	
	//模型ID
	private int model_id;
	
	//站点ID
	private int site_id;
    
	//排序日期
	private String sort_date;
	
	//固顶级别
	private int top_level;
	
	//是否有标题图
	private int has_title_img;
	
	//是否推荐
	private int id_recommend;
	
	//状态(0:草稿;1:审核中;2:审核通过;3:回收站；4:投稿)
	private int status;
	
	//日访问数
	private int views_day;
	
	//日评论数
	private int comments_day;
	
	//日下载数
	private int downloads_day;
	
	//日顶数
	private int ups_day;
	
	//得分
	private int score;
	
	//BPM taskid
	private String taskid;
	
	//审核意见
	private String opinion;
	
	//审核人
	private String check_user;
	
	//系统ID
	private int app_id;
	
	public int getContent_id() {
		return content_id;
	}
	public void setContent_id(int content_id) {
		this.content_id = content_id;
	}
	public int getChannel_id() {
		return channel_id;
	}
	public void setChannel_id(int channel_id) {
		this.channel_id = channel_id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public int getType_id() {
		return type_id;
	}
	public void setType_id(int type_id) {
		this.type_id = type_id;
	}
	public int getModel_id() {
		return model_id;
	}
	public void setModel_id(int model_id) {
		this.model_id = model_id;
	}
	public int getSite_id() {
		return site_id;
	}
	public void setSite_id(int site_id) {
		this.site_id = site_id;
	}
	public String getSort_date() {
		return sort_date;
	}
	public void setSort_date(String sort_date) {
		this.sort_date = sort_date;
	}
	public int getTop_level() {
		return top_level;
	}
	public void setTop_level(int top_level) {
		this.top_level = top_level;
	}
	public int getHas_title_img() {
		return has_title_img;
	}
	public void setHas_title_img(int has_title_img) {
		this.has_title_img = has_title_img;
	}
	public int getId_recommend() {
		return id_recommend;
	}
	public void setId_recommend(int id_recommend) {
		this.id_recommend = id_recommend;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getViews_day() {
		return views_day;
	}
	public void setViews_day(int views_day) {
		this.views_day = views_day;
	}
	public int getComments_day() {
		return comments_day;
	}
	public void setComments_day(int comments_day) {
		this.comments_day = comments_day;
	}
	public int getDownloads_day() {
		return downloads_day;
	}
	public void setDownloads_day(int downloads_day) {
		this.downloads_day = downloads_day;
	}
	public int getUps_day() {
		return ups_day;
	}
	public void setUps_day(int ups_day) {
		this.ups_day = ups_day;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public String getTaskid() {
		return taskid;
	}
	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}
	public String getOpinion() {
		return opinion;
	}
	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}
	public String getCheck_user() {
		return check_user;
	}
	public void setCheck_user(String check_user) {
		this.check_user = check_user;
	}
	public int getApp_id() {
		return app_id;
	}
	public void setApp_id(int app_id) {
		this.app_id = app_id;
	}
	
	
}
