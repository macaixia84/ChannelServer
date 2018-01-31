package cn.gov.msa.urcms.ws;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GetConfig {

	public String GetConfigs() throws IOException{
		Properties prop = new Properties();
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("pathConfig.properties");
		prop.load(inputStream);
		return prop.getProperty("attachment_path");
	}
}
