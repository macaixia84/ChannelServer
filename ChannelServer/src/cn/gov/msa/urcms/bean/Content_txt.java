package cn.gov.msa.urcms.bean;

import oracle.sql.CLOB;

public class Content_txt {

	private int content_id;
	
	//文章内容
	private CLOB txt;
	
	//扩展内容1
	private CLOB txt1;
	
	//扩展内容2
	private CLOB txt2;
	
	//扩展内容3
	private CLOB txt3;

	public int getContent_id() {
		return content_id;
	}

	public void setContent_id(int content_id) {
		this.content_id = content_id;
	}

	public CLOB getTxt() {
		return txt;
	}

	public void setTxt(CLOB txt) {
		this.txt = txt;
	}

	public CLOB getTxt1() {
		return txt1;
	}

	public void setTxt1(CLOB txt1) {
		this.txt1 = txt1;
	}

	public CLOB getTxt2() {
		return txt2;
	}

	public void setTxt2(CLOB txt2) {
		this.txt2 = txt2;
	}

	public CLOB getTxt3() {
		return txt3;
	}

	public void setTxt3(CLOB txt3) {
		this.txt3 = txt3;
	}
	
	
	
	

}
