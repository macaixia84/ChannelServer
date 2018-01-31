package cn.gov.msa.urcms.ws;

import javax.servlet.ServletContext;

import org.springframework.web.context.ServletContextAware;


public class FileReposity implements ServletContextAware{

	private ServletContext servletContext;
	
	public String getR(){
//		return servletContext.getRealPath("/");
		return this.getServletContext().getRealPath("/3");
	}

	public ServletContext getServletContext() {
		return servletContext;
	}


	public  void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}


	public static void main(String[] args){
		System.out.println(new FileReposity().getR());
	}

	
}
