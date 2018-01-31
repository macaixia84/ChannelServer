package cn.gov.msa.urcms.ws;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONBuilder;

public class XmlHelper {
	private static final String TITLE = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>";

	public static List<Map<String, Object>> getData(String json) {

		List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
		String key = null;

		JSONArray valueArray = null;

		try {
			System.out.println("aaa");
			JSONObject jsonObject = JSONObject.fromObject(json);

			Iterator iterator = jsonObject.keys();
			key = (String) iterator.next();
			valueArray = jsonObject.getJSONArray(key);
			System.out.println("key" + key + "value" + valueArray);
			String key1 = null;
			String value1 = null;
			for (int i = 0; i < valueArray.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				JSONObject jo = valueArray.getJSONObject(i);
				String title = jo.getString("title");
				String priority = jo.getString("priority");
				String state = jo.getString("state");
				String assignee = jo.getString("assignee");
				String taskNumber = jo.getString("taskNumber");
				String createdDate = jo.getString("createdDate");
				String updateDate = jo.getString("updateDate");
				map.put("title", title);
				map.put("priority", priority);
				map.put("state", state);
				map.put("assignee", assignee);
				map.put("taskNumber", taskNumber);
				map.put("createdDate", createdDate);
				map.put("updateDate", updateDate);
				maps.add(map);
			}
			return maps;

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}

		return maps;
	}

	public String list2json(List<Map<String, Object>> list) {
		if (list.isEmpty()) {
			StringBuilder jsonBuilder = new StringBuilder();
			jsonBuilder.append("{" + "\"" + "datas" + "\"" + ":["
					+ "{\"channelid\":\"0\",\"attrname\":\"数据查询失败!\"}]}\"");
			return jsonBuilder.toString();
		} else {
			try {
				StringBuilder json = new StringBuilder();
				json.append("{" + "\"" + "datas" + "\"" + ":");
				json.append("[");
				if (list != null && list.size() > 0) {

					// for(int i = 0;i<list.size();i++){
					// Map<String, Object> obj = list.get(i);
					// System.out.println(obj);
					// json.append(getMapToJson(obj));
					// json.append(",");
					// }
					for (Map<String, Object> obj : list) {
						json.append(getMapToJson(obj));
						json.append(",");
					}
					json.setCharAt(json.length() - 1, ']');
				} else {
					json.append("]");
				}
				//////////
				json.append("}");
				return json.toString();
			} catch (Exception e) {
				StringBuilder jsonBuilder = new StringBuilder();
				jsonBuilder.append("{" + "\"" + "datas" + "\"" + ":["
						+ "{\"channelid\":\"00\",\"attrname\":\"数据查询异常!\"}]}\"");
				return jsonBuilder.toString();
			}
		}
	}
	
	public String listAssortjson(List<Map<String, Object>> list) {
		if (list.isEmpty()) {
			StringBuilder jsonBuilder = new StringBuilder();
			jsonBuilder.append("{" + "\"" + "datas" + "\"" + ":["
					+ "{\"assortmentid\":\"0\",\"assortmentname\":\"数据查询失败!\"}]}\"");
			return jsonBuilder.toString();
		} else {
			try {
				StringBuilder json = new StringBuilder();
				json.append("{" + "\"" + "datas" + "\"" + ":");
				json.append("[");
				if (list != null && list.size() > 0) {

					// for(int i = 0;i<list.size();i++){
					// Map<String, Object> obj = list.get(i);
					// System.out.println(obj);
					// json.append(getMapToJson(obj));
					// json.append(",");
					// }
					for (Map<String, Object> obj : list) {
						json.append(getMapToJson(obj));
						json.append(",");
					}
					json.setCharAt(json.length() - 1, ']');
				} else {
					json.append("]");
				}
				//////////
				json.append("}");
				return json.toString();
			} catch (Exception e) {
				StringBuilder jsonBuilder = new StringBuilder();
				jsonBuilder.append("{" + "\"" + "datas" + "\"" + ":["
						+ "{\"assortmentid\":\"00\",\"assortmentname\":\"数据查询异常!\"}]}\"");
				return jsonBuilder.toString();
			}
		}
	}

	public String list3json(List<Map<String, Object>> list) {
		List<Map<String, Object>> articleList = new ArrayList<Map<String,Object>>();
		Map<String, Object> totalSize = new HashMap<String, Object>();
		
		for(int i = 0;i < list.size() - 1;i++){
			articleList.add(list.get(i));
		}	
		
		totalSize = list.get(list.size() - 1);	
		
		if (list.isEmpty()) {
			StringBuilder jsonBuilder = new StringBuilder();
			jsonBuilder.append("{" + "\"" + "datas" + "\"" + ":["
					+ "{\"result\":\"1\",\"info\":\"查询失败!\"}]}");
			return jsonBuilder.toString();
		} else if((list.size() == 1)&&(list.get(0).get("resu").equals("fail"))){
			StringBuilder jsonBuilder = new StringBuilder();
			jsonBuilder.append("{" + "\"" + "datas" + "\"" + ":["
					+ "{\"result\":\"3\",\"info\":\"页数超过最大页数!\"}]}");
			return jsonBuilder.toString();
		}else {
			try {
				StringBuilder json = new StringBuilder();
				json.append("{" + "\"" + "datas" + "\"" + ":");
				json.append("[" + "{\"result\":\"1\",\"info\":\"查询成功\",\"total\":"+"\""+totalSize.get("total")+"\",");
				json.append("\"" + "contentlist" + "\":[");
				if (list != null && list.size() > 0) {

					// for(int i = 0;i<list.size();i++){
					// Map<String, Object> obj = list.get(i);
					// System.out.println(obj);
					// json.append(getMapToJson(obj));
					// json.append(",");
					// }
					for (Map<String, Object> obj : articleList) {
						json.append(getMapToJson(obj));
						json.append(",");
					}
					json.setCharAt(json.length() - 1, ']');
				} else {
					json.append("}");
				}
				json.append("}]}");
				return json.toString();
			} catch (Exception e) {
				StringBuilder jsonBuilder = new StringBuilder();
				jsonBuilder.append("{" + "\"" + "datas" + "\"" + ":["
						+ "{\"result\":\"2\",\"info\":\"查询异常!\"}]}");
				return jsonBuilder.toString();
			}
		}
	}

	public String addContentResult2json(String result) {
		StringBuilder jsonBuilder = new StringBuilder();
		if (result.startsWith("success")) {
			jsonBuilder.append("{\"result\":\"0\",\"info\":\"添加成功!\",\"articleid\":\""+result.substring(7)+"\"}");
		} else if (result.startsWith("failed")) {
			String reason = result.substring(result.lastIndexOf("d") + 1);
			reason = reason.replace("\n","");
			jsonBuilder.append("{\"result\":\"1\",\"info\":\"" + reason	+ "!\"}");
		} else {
			jsonBuilder.append("{\"result\":\"2\",\"info\":\"接口異常!\"}");
		}
		return jsonBuilder.toString();
	}

	public String getMapToJson(Map<String, Object> map) {

		StringBuilder json = new StringBuilder();
		json.append("{");
		if (map != null && map.size() > 0) {
			Iterator it = map.entrySet().iterator();
			while (it.hasNext()) {
				Entry entry = (Map.Entry) it.next();
				Object key = entry.getKey();
				Object value = entry.getValue();
				json.append("\"" + key + "\"");
				json.append(":");
				json.append("\"" + value + "\"");
				json.append(",");
			}
			// for (String key : map.keySet()) {
			// json.append(key);
			// json.append(":");
			// json.append(map.get(key));
			// json.append(",");
			// }
			json.setCharAt(json.length() - 1, '}');
		} else {
			json.append("}");
		}
		return json.toString();

	}

	public static String getObjectToJson(List<Map<String, Object>> list,
			String obj) {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("{" + "\"" + obj + "\"" + ":[");
		for (int i = 0; i < list.size(); i++) {
			Object o = list.get(i);
			stringBuffer.append("{");
			Field[] fields = o.getClass().getDeclaredFields();
			System.out.println(fields);
			for (int j = 0; j < fields.length; j++) {
				String name = fields[j].getName();
				try {
					Method method = o.getClass().getMethod(
							"get" + name.substring(0, 1).toUpperCase()
									+ name.substring(1), new Class[] {});
					Object result = method.invoke(o, new Object[] {});
					stringBuffer.append("\"" + name + "\"" + ":" + "\""
							+ result + "\"");
					if (j != fields.length - 1) {
						stringBuffer.append(",");
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			stringBuffer.append("}");
			if (i != list.size() - 1) {
				stringBuffer.append(",");
			}
		}
		stringBuffer.append("]}");
		return stringBuffer.toString();
	}
	
	public String queryContents2json(Map<String, Object> map){
		StringBuilder jsonBuilder = new StringBuilder();
		 
		if(map.isEmpty()){
			jsonBuilder.append("{\"result\":\"2\",\"info\":\"查询异常!\"}");
			return jsonBuilder.toString();
		}else if((map.size() == 1)&&("0".equals(map.get("fail")))){
			jsonBuilder.append("{\"result\":\"1\",\"info\":\"查询失败!\"}");
			return jsonBuilder.toString();
		}else if(map.size() == 1&&"1".equals(map.get("success"))){
			jsonBuilder.append("{\"result\":\"0\",\"info\":\"没有相关数据\"}");
		}else if(map != null && map.size() > 0){
			jsonBuilder.append("{\"result\":\"0\",\"info\":\"查询成功\"");
			jsonBuilder.append(",\"datas\":{");
			Iterator it = map.entrySet().iterator();
			while (it.hasNext()) {
				Entry entry = (Map.Entry) it.next();
				Object key = entry.getKey();
				Object value = entry.getValue();
				jsonBuilder.append("\"" + key + "\"");
				jsonBuilder.append(":");
				jsonBuilder.append("\"" + value + "\"");
				jsonBuilder.append(",");
			}
			jsonBuilder.setCharAt(jsonBuilder.length() - 1, '}');
			jsonBuilder.append("}");
		}
		return jsonBuilder.toString();
		
	}
	public String deleteArticle2json(String result){
		StringBuilder jsonBuilder = new StringBuilder();
		if("ok".equals(result)){
			jsonBuilder.append("{\"result\":\"0\",\"info\":\"删除成功!\"}");
		}else if("fail".equals(result)){
			jsonBuilder.append("{\"result\":\"1\",\"info\":\"删除失败!\"}");
		}else if("exception".equals(result)){
			jsonBuilder.append("{\"result\":\"2\",\"info\":\"接口异常!\"}");
		}
		return jsonBuilder.toString();
	}

	public String updateArticle2json(String result) {
		StringBuilder jsonBuilder = new StringBuilder();
		if("ok".equals(result)){
			jsonBuilder.append("{\"result\":\"0\",\"info\":\"修改成功!\"}");
		}else if("fail".equals(result)){
			jsonBuilder.append("{\"result\":\"1\",\"info\":\"修改失败!\"}");
		}else if("exception".equals(result)){
			jsonBuilder.append("{\"result\":\"2\",\"info\":\"接口异常!\"}");
		}
		return jsonBuilder.toString();
	}

	public String listByAppidjson(List<Map<String, Object>> list) {
		System.out.println("app xmlhelper");
		System.out.println(list.size());
		List<Map<String, Object>> articleList = new ArrayList<Map<String,Object>>();
		Map<String, Object> totalSize = new HashMap<String, Object>();
		
		for(int i = 0;i < list.size() - 1;i++){
			articleList.add(list.get(i));
		}	
		
		totalSize = list.get(list.size() - 1);	
		
		if (list.isEmpty()) {
			StringBuilder jsonBuilder = new StringBuilder();
			jsonBuilder.append("{" + "\"" + "datas" + "\"" + ":["
					+ "{\"result\":\"1\",\"info\":\"查询失败!\"}]}");
			return jsonBuilder.toString();
		} else if((list.size() == 1)&&(list.get(0).get("resu").equals("fail"))){
			StringBuilder jsonBuilder = new StringBuilder();
			jsonBuilder.append("{" + "\"" + "datas" + "\"" + ":["
					+ "{\"result\":\"3\",\"info\":\"页数超过最大页数!\"}]}");
			return jsonBuilder.toString();
		}else {
			try {
				StringBuilder json = new StringBuilder();
				json.append("{" + "\"" + "datas" + "\"" + ":");
				json.append("[" + "{\"result\":\"1\",\"info\":\"查询成功\",\"total\":"+"\""+totalSize.get("total")+"\",");
				json.append("\"" + "contentlist" + "\":[");
				if (list != null && list.size() > 0) {

					// for(int i = 0;i<list.size();i++){
					// Map<String, Object> obj = list.get(i);
					// System.out.println(obj);
					// json.append(getMapToJson(obj));
					// json.append(",");
					// }
					for (Map<String, Object> obj : articleList) {
						json.append(getMapToJson(obj));
						json.append(",");
					}
					json.setCharAt(json.length() - 1, ']');
				} else {
					json.append("}");
				}
				json.append("}]}");
				return json.toString();
			} catch (Exception e) {
				StringBuilder jsonBuilder = new StringBuilder();
				jsonBuilder.append("{" + "\"" + "datas" + "\"" + ":["
						+ "{\"result\":\"2\",\"info\":\"查询异常!\"}]}");
				return jsonBuilder.toString();
			}
		}
	}

	public String listByTypeIdAndChannelIdJson(List<Map<String, Object>> list) {
		System.out.println("app xmlhelper");
		System.out.println(list.size());
		List<Map<String, Object>> articleList = new ArrayList<Map<String,Object>>();
		Map<String, Object> totalSize = new HashMap<String, Object>();
		
		for(int i = 0;i < list.size() - 1;i++){
			articleList.add(list.get(i));
		}	
		
		totalSize = list.get(list.size() - 1);	
		
		if (list.isEmpty()) {
			StringBuilder jsonBuilder = new StringBuilder();
			jsonBuilder.append("{" + "\"" + "datas" + "\"" + ":["
					+ "{\"result\":\"1\",\"info\":\"查询失败!\"}]}");
			return jsonBuilder.toString();
		} else if((list.size() == 1)&&(list.get(0).get("resu").equals("fail"))){
			StringBuilder jsonBuilder = new StringBuilder();
			jsonBuilder.append("{" + "\"" + "datas" + "\"" + ":["
					+ "{\"result\":\"3\",\"info\":\"页数超过最大页数!\"}]}");
			return jsonBuilder.toString();
		}else {
			try {
				StringBuilder json = new StringBuilder();
				json.append("{" + "\"" + "datas" + "\"" + ":");
				json.append("[" + "{\"result\":\"1\",\"info\":\"查询成功\",\"total\":"+"\""+totalSize.get("total")+"\",");
				json.append("\"" + "contentlist" + "\":[");
				if (list != null && list.size() > 0) {

					// for(int i = 0;i<list.size();i++){
					// Map<String, Object> obj = list.get(i);
					// System.out.println(obj);
					// json.append(getMapToJson(obj));
					// json.append(",");
					// }
					for (Map<String, Object> obj : articleList) {
						json.append(getMapToJson(obj));
						json.append(",");
					}
					json.setCharAt(json.length() - 1, ']');
				} else {
					json.append("}");
				}
				json.append("}]}");
				return json.toString();
			} catch (Exception e) {
				StringBuilder jsonBuilder = new StringBuilder();
				jsonBuilder.append("{" + "\"" + "datas" + "\"" + ":["
						+ "{\"result\":\"2\",\"info\":\"查询异常!\"}]}");
				return jsonBuilder.toString();
			}
		}
	}

}