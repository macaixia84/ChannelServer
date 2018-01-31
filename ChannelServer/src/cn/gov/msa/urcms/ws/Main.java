package cn.gov.msa.urcms.ws;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.aspectj.weaver.patterns.ThisOrTargetAnnotationPointcut;

import cn.gov.msa.urcms.service.ColumnServices;
import cn.gov.msa.urcms.service.impl.ColumnServicesImpl;


public class Main {	
	
	public static void main(String[] args) throws IOException {
//		ColumnServgetices columnServices = new ColumnServicesImpl();
//		columnServices.columnQuery();	   
	    GetConfig getConfig = new GetConfig();
	    getConfig.GetConfigs();
		
		
	}

}