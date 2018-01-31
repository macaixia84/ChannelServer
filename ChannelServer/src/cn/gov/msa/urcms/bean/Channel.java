package cn.gov.msa.urcms.bean;

//栏目表
public class Channel {
	
	//CHANNEL_ID
	private int channel_id;
	
	//模型ID
	private int model_id;
	
	//站点ID
	private int site_id;
	
	//父栏目ID
	private int parent_id;
	
	//访问路径
	private String channel_path;
	
	//树左边
	private int lef;
	
	//树右边
	private int rgt;
	
	//排列顺序
	private int priority;
	
	//是否有内容
	private int has_content;
	
	//是否显示
	private int is_display;
	
	//工作流id
	private int workflow_id;
	

	public int getChannel_id() {
		return channel_id;
	}

	public void setChannel_id(int channel_id) {
		this.channel_id = channel_id;
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

	public int getParent_id() {
		return parent_id;
	}

	public void setParent_id(int parent_id) {
		this.parent_id = parent_id;
	}

	public String getChannel_path() {
		return channel_path;
	}

	public void setChannel_path(String channel_path) {
		this.channel_path = channel_path;
	}

	public int getLef() {
		return lef;
	}

	public void setLef(int lef) {
		this.lef = lef;
	}

	public int getRgt() {
		return rgt;
	}

	public void setRgt(int rgt) {
		this.rgt = rgt;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public int getHas_content() {
		return has_content;
	}

	public void setHas_content(int has_content) {
		this.has_content = has_content;
	}

	public int getIs_display() {
		return is_display;
	}

	public void setIs_display(int is_display) {
		this.is_display = is_display;
	}

	public int getWorkflow_id() {
		return workflow_id;
	}

	public void setWorkflow_id(int workflow_id) {
		this.workflow_id = workflow_id;
	}
}
