package cn.gov.msa.urcms.bean;

public class Content_type {

	private int type_id;
	
	//名称
	private String type_name;
	
	//图片宽
	private int img_width;
	
	//图片高
	private int img_height;
	
	//是否有图片
	private int has_image;
	
	//是否禁用
	private int is_disabled;
	
	
	public int getType_id() {
		return type_id;
	}
	public void setType_id(int type_id) {
		this.type_id = type_id;
	}
	public String getType_name() {
		return type_name;
	}
	public void setType_name(String type_name) {
		this.type_name = type_name;
	}
	public int getImg_width() {
		return img_width;
	}
	public void setImg_width(int img_width) {
		this.img_width = img_width;
	}
	public int getImg_height() {
		return img_height;
	}
	public void setImg_height(int img_height) {
		this.img_height = img_height;
	}
	public int getHas_image() {
		return has_image;
	}
	public void setHas_image(int has_image) {
		this.has_image = has_image;
	}
	public int getIs_disabled() {
		return is_disabled;
	}
	public void setIs_disabled(int is_disabled) {
		this.is_disabled = is_disabled;
	}
	
	

}
