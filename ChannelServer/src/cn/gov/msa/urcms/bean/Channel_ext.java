package cn.gov.msa.urcms.bean;

//栏目内容表
public class Channel_ext {
 
	private int channel_id;
	
	//名称
	private String channel_name;
	
	//终审级别
	private int final_step;
	
	//审核后(1:不能修改删除;2:修改后退回;3:修改后不变)
	private int after_check;
	
	//是否栏目静态化
	private String is_static_channel;
	
	//是否内容静态化
	private String is_static_content;
	
	//是否使用目录访问
	private String is_access_by_dir;
	
	//是否使用子栏目列表
	private String is_list_child;
	
	//每页多少条记录
	private int page_size;
	
	//栏目页生成规则
	private String channel_rule;
	
	//内容页生成规则
	private String content_rule;
	
	//外部链接
	private String link;
	
	//栏目页模板
	private String tpl_channel;
	
	//内容页模板
	private String tpl_content;
	
	//缩略图
	private String title_img;
	
	//内容图
	private String content_img;
	
	//内容是否有缩略图
	private int has_title_img;
	
	//内容是否有内容图
	private int has_content_img;
	
	//内容标题图宽度
	private int title_img_width;
	
	//内容标题图高度
	private int title_img_height;
	
	//内容内容图宽度
	private int content_img_width;
	
	//内容内容图高度
	private int content_img_height;
	
	//评论(0:匿名;1:会员;2:关闭)
	private int comment_control;
	
	//顶踩(true:开放;false:关闭)
	private int allow_updown;
	
	//是否新窗口打开
	private int is_blank;
	
	private String title;
	
	private String keywords;
	
	private String description;
	
	//分享(true:开放;false:关闭)
	private int allow_share;
	
	//评分(true:开放;false:关闭)
	private int allow_scope;
	
	public int getChannel_id() {
		return channel_id;
	}
	public void setChannel_id(int channel_id) {
		this.channel_id = channel_id;
	}
	public String getChannel_name() {
		return channel_name;
	}
	public void setChannel_name(String channel_name) {
		this.channel_name = channel_name;
	}
	public int getFinal_step() {
		return final_step;
	}
	public void setFinal_step(int final_step) {
		this.final_step = final_step;
	}
	public int getAfter_check() {
		return after_check;
	}
	public void setAfter_check(int after_check) {
		this.after_check = after_check;
	}
	public String getIs_static_channel() {
		return is_static_channel;
	}
	public void setIs_static_channel(String is_static_channel) {
		this.is_static_channel = is_static_channel;
	}
	public String getIs_static_content() {
		return is_static_content;
	}
	public void setIs_static_content(String is_static_content) {
		this.is_static_content = is_static_content;
	}
	public String getIs_access_by_dir() {
		return is_access_by_dir;
	}
	public void setIs_access_by_dir(String is_access_by_dir) {
		this.is_access_by_dir = is_access_by_dir;
	}
	public String getIs_list_child() {
		return is_list_child;
	}
	public void setIs_list_child(String is_list_child) {
		this.is_list_child = is_list_child;
	}
	public int getPage_size() {
		return page_size;
	}
	public void setPage_size(int page_size) {
		this.page_size = page_size;
	}
	public String getChannel_rule() {
		return channel_rule;
	}
	public void setChannel_rule(String channel_rule) {
		this.channel_rule = channel_rule;
	}
	public String getContent_rule() {
		return content_rule;
	}
	public void setContent_rule(String content_rule) {
		this.content_rule = content_rule;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getTpl_channel() {
		return tpl_channel;
	}
	public void setTpl_channel(String tpl_channel) {
		this.tpl_channel = tpl_channel;
	}
	public String getTpl_content() {
		return tpl_content;
	}
	public void setTpl_content(String tpl_content) {
		this.tpl_content = tpl_content;
	}
	public String getTitle_img() {
		return title_img;
	}
	public void setTitle_img(String title_img) {
		this.title_img = title_img;
	}
	public String getContent_img() {
		return content_img;
	}
	public void setContent_img(String content_img) {
		this.content_img = content_img;
	}
	public int getHas_title_img() {
		return has_title_img;
	}
	public void setHas_title_img(int has_title_img) {
		this.has_title_img = has_title_img;
	}
	public int getHas_content_img() {
		return has_content_img;
	}
	public void setHas_content_img(int has_content_img) {
		this.has_content_img = has_content_img;
	}
	public int getTitle_img_width() {
		return title_img_width;
	}
	public void setTitle_img_width(int title_img_width) {
		this.title_img_width = title_img_width;
	}
	public int getTitle_img_height() {
		return title_img_height;
	}
	public void setTitle_img_height(int title_img_height) {
		this.title_img_height = title_img_height;
	}
	public int getContent_img_width() {
		return content_img_width;
	}
	public void setContent_img_width(int content_img_width) {
		this.content_img_width = content_img_width;
	}
	public int getContent_img_height() {
		return content_img_height;
	}
	public void setContent_img_height(int content_img_height) {
		this.content_img_height = content_img_height;
	}
	public int getComment_control() {
		return comment_control;
	}
	public void setComment_control(int comment_control) {
		this.comment_control = comment_control;
	}
	public int getAllow_updown() {
		return allow_updown;
	}
	public void setAllow_updown(int allow_updown) {
		this.allow_updown = allow_updown;
	}
	public int getIs_blank() {
		return is_blank;
	}
	public void setIs_blank(int is_blank) {
		this.is_blank = is_blank;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getAllow_share() {
		return allow_share;
	}
	public void setAllow_share(int allow_share) {
		this.allow_share = allow_share;
	}
	public int getAllow_scope() {
		return allow_scope;
	}
	public void setAllow_scope(int allow_scope) {
		this.allow_scope = allow_scope;
	}
	
	
	
	
}
