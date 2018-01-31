package cn.gov.msa.urcms.dao.impl;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.axis.encoding.Base64;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.Validate;
import org.springframework.jdbc.core.JdbcTemplate;

import cn.gov.msa.urcms.dao.ColumnDao;
import cn.gov.msa.urcms.ws.GetConfig;
import cn.gov.msa.urcms.ws.Upload;

public class ColumnDaoImpl extends JdbcTemplate implements ColumnDao {

	private GetConfig getConfig = new GetConfig();

	@Override
	public List<Map<String, Object>> columnQuery() {
		String sql = "select * from jc_channel_ext jce,jc_channel jc where jce.channel_id = jc.channel_id";
		List<Map<String, Object>> columnList = this.queryForList(sql);

		List<Map<String, Object>> columnResultList = new ArrayList<Map<String, Object>>();

		for (int i = 0; i < columnList.size(); i++) {
			Map<String, Object> column = columnList.get(i);
			Map<String, Object> columnResult = new LinkedHashMap<String, Object>();
			// 查询所有的栏目id及栏目名称
			columnResult.put("channelid", column.get("channel_id"));
			columnResult.put("attrname", column.get("channel_name"));

			columnResultList.add(columnResult);
		}

		return columnResultList;
	}

	@Override
	public List<Map<String, Object>> getArticleListByid(String columnId,
			String userId, int pageSize, int Pages,String publish) {// 52,1
		int start = 0, end = 0, pages = 0;
		List<Map<String, Object>> articleResultList = new ArrayList<Map<String, Object>>();
		Map<String, Object> articleMap = new HashMap<String, Object>();

		// 获取总查询条数
//		String total_sql = "select count(*) from jc_content jc where jc.channel_id = "
//				+ Integer.valueOf(columnId)
//				+ " and jc.user_id = "
//				+ Integer.valueOf(userId) + " and jc.status = " + 2;
//		System.out.println(total_sql);
		String total_sql = "select count(*) from jc_content jc,jc_content_attr attr where jc.channel_id = "
				+ Integer.valueOf(columnId)
				+ " and jc.status = " + 2+" and jc.content_id = attr.content_id and attr.attr_name='publish' and attr.attr_value like '%"+publish+"%'";
		System.out.println(total_sql);

		int total = this.queryForInt(total_sql);
		// System.out.println(total_sql);
		// System.out.println(total + "count");

		// 根据总条数和每页条数，计算出总页数
		if (total % pageSize == 0) {
			pages = total / pageSize;// 1
		} else {
			pages = total / pageSize + 1;
		}
		System.out.println(pages + "总页数");

		// 当前页数小于总页数
		if (Pages < pages) { // 10页 9页 10条 105总 1-10,11-20,21-30.。。
			start = (Pages - 1) * pageSize + 1; // 81 = 8*10+1
			end = Pages * pageSize; // 90

			// 当前页数为最后一页
		} else if (Pages == pages) { // 11页 101-105
			if (total == pageSize) {
				start = (Pages - 1) * pageSize + 1;
				end = total;
			} else {
				start = (Pages - 1) * pageSize + 1; //
				end = (Pages - 1) * pageSize + total % pageSize;// 11-1 * 10 +
																// 105 % // 10
			}

		} else {
			articleMap.put("resu", "fail");
			articleResultList.add(articleMap);
			return articleResultList;
		}
		System.out.println(start + "   " + end);

		// 查询出指定页的记录数
		String selectResult = "select t.channel_id,t.content_id,t.title,t.link,t.author,t.release_date,t.type_name from (select jc.channel_id,jc.content_id,jce.title,jce.link,jce.author,jce.release_date,jct.type_name"
				+ " from jc_content jc,jc_content_ext jce,jc_content_type jct,jc_content_attr attr  where jc.channel_id = "
				+ Integer.valueOf(columnId)
				+ " and jc.status=2 "
				+ " and jc.content_id = jce.content_id"
				+ " and jc.content_id=attr.content_id and attr.attr_name='publish' and attr.attr_value='" + publish + "'"
				+ " and jc.type_id = jct.type_id and jct.type_id in (select distinct type_id from jc_content where channel_id = "
				+ Integer.valueOf(columnId)
				+ " and status=2 "
				+ ")) t"
				+ " where rowid in (select rid  from (select rownum rn, rid  from (select rowid rid, content_id from (select jc.channel_id,jc.content_id,jce.title,jce.link,jce.author,"
				+ " jce.release_date,jct.type_name  from jc_content jc,jc_content_ext  jce,jc_content_type jct,jc_content_attr attr  where jc.channel_id = "
				+ Integer.valueOf(columnId)
				+ " and jc.content_id = jce.content_id "
				+" and jc.content_id=attr.content_id and attr.attr_name='publish' and attr.attr_value='" + publish + "'"
				+" and jc.type_id = jct.type_id and jc.status = 2 and jct.type_id in (select distinct type_id from jc_content where channel_id = "
				+ Integer.valueOf(columnId)
				+ " and status = 2 "
				+ ")) order by content_id desc) where rownum <= "
				+ end
				+ ") where rn >= " + start + ") order by content_id desc";
		System.out.println(selectResult);

		List<Map<String, Object>> articleList = this.queryForList(selectResult);
		System.out.println(articleList);
		// System.out.println("after------------");

		for (int i = 0; i <articleList.size() ; i++) {

			Map<String, Object> article = articleList.get(i);
			Map<String, Object> articleResult = new LinkedHashMap<String, Object>();
			articleResult.put("channelid", article.get("channel_id"));
			articleResult.put("articleid", article.get("content_id"));
			articleResult.put("title", article.get("title"));
			articleResult.put("url", article.get("link"));
			articleResult.put("datetime", article.get("release_date"));
			articleResult.put("business", article.get("type_name"));
			articleResult.put("author", article.get("author"));
			// articleResult.put("Total", total);
			articleResultList.add(articleResult);
		}
		Map<String, Object> totalSize = new HashMap<String, Object>();
		totalSize.put("total", total);
		articleResultList.add(totalSize);
		System.out.println(articleResultList);

		return articleResultList;
	}

	@Override
	public String addContent(String Title, String shortName, String author,
			String target, String ContentDetail, String alter,
			String createTime, String updateTime, String reorderTime,
			String text, byte[][] files, String[] filename, String Appid,
			String Appname, String source, String assortment, String columnId,String publish) {

		String model_id = "";
		String site_id = "";
		int typeId = 0;
		int content_id = 0;

		try {
			// 通过栏目ID获取栏目下的model_id,site_id
			if (!columnId.isEmpty()) {
				String selectChannelParams = "select * from jc_channel where channel_id = "
						+ columnId;
				List<Map<String, Object>> channelResult = this
						.queryForList(selectChannelParams);

				model_id = channelResult.get(0).get("model_id").toString();
				site_id = channelResult.get(0).get("site_id").toString();
				// System.out.println(model_id +"helo"+ site_id);
			}
			// 外接系统指定user_id为定值48
			String selectUser = "select user_id from jc_user where username = '"
					+ "外接系统'";
			int userid = this.queryForInt(selectUser);

			String user_id = String.valueOf(userid);
			System.out.println(user_id);
			System.out.println("sdfsdf");
			if ((!assortment.isEmpty())) {
				// 通过类型名称获取类型ID
				String type_id = "select t.type_id from jc_content_type t where t.type_name = '"
						+ assortment + "'";
				// System.out.println(type_id);
				typeId = this.queryForInt(type_id);
				// System.out.println(typeId);
			}
			String sql="select S_JC_CONTENT.nextval id from dual";
			content_id = this.queryForInt(sql);

			if (!columnId.isEmpty()) {
				// 将外键ID插入到jc_content表中
				String content_insert_sql = "insert into jc_content(content_id,channel_id,user_id,type_id,model_id,site_id,sort_date,top_level,has_title_img,is_recommend,status,comments_day,downloads_day,ups_day,score,taskid,app_id)"
						+ " values("+content_id+","
						+ Integer.valueOf(columnId)
						+ ", "
						+ Integer.valueOf(user_id)
						+ ","
						+ typeId
						+ ","
						+ Integer.valueOf(model_id)
						+ ","
						+ Integer.valueOf(site_id)
						+ ",to_date('"
						+ createTime
						+ "','yyyy-MM-dd'),0,0,0,2,0,0,0,0,"
						+ "000000"
						+ ","
						+ Integer.valueOf(Appid) + ")";
				this.execute(content_insert_sql);
				// System.out.println("contentinset");
				

				// 获取最新插入的content_id
//				String content_id_new = "select content_id  from jc_content where channel_id = "
//						+ columnId
//						+ " and user_id = "
//						+ Integer.valueOf(user_id)
//						+ " and type_id = "
//						+ typeId
//						+ " and model_id = "
//						+ Integer.valueOf(model_id)
//						+ " and site_id = "
//						+ Integer.valueOf(site_id)
//						+ " order by content_id desc";
//				Map<String, Object> map = this.queryForList(content_id_new).get(0);
//				Iterator<Entry<String, Object>> iterator = map.entrySet().iterator();
//				
//				while (iterator.hasNext()) {
//		            Map.Entry<String, Object> entry = (Entry<String,Object>) iterator.next();
//		            String key = entry.getKey();//图片名称
//		            content_id =  Integer.valueOf(entry.getValue().toString());//带URL的图片名称
//		            System.out.println(key+"...."+content_id);
//				}
				// System.out.println(content_id + "contentid");
			}

			if (content_id != 0) {
				String insert_content_count = "insert into jc_content_count(content_id,views,views_month,views_week,views_day,comments,comments_month,comments_week,comments_day,downloads,downloads_month,downloads_week,downloads_day,ups,ups_month,ups_week,ups_day,downs) values("
						+ content_id + ",0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0)";
				this.execute(insert_content_count);
				// System.out.println("count");
			}

			if ((!columnId.isEmpty()) && (content_id != 0)) {
				String insert_content_channel = "insert into jc_content_channel(channel_id,content_id) values("
						+ Integer.valueOf(columnId) + "," + content_id + ")";
				// System.out.println(insert_content_channel);
				// System.out.println("insert content channel ok");
				this.execute(insert_content_channel);
			}

			// 向jc_content_ext表中插入数据
			String content_ext_sql = "insert into jc_content_ext(content_id,title,short_title,author,origin,description,release_date,is_bold,need_regenerate,createtime,updatetime,reordertime,amend) values("
					+ content_id
					+ ",'"
					+ Title
					+ "','"
					+ shortName
					+ "','"
					+ author
					+ "','"
					+ source
					+ "','"
					+ ContentDetail
					+ "',to_date('"
					+ createTime
					+ "','yyyy-MM-dd'),"
					+ 0
					+ ","
					+ 1
					+ ",to_date('"
					+ createTime
					+ "','yyyy-MM-dd'),to_date('"
					+ updateTime
					+ "','yyyy-MM-dd'),to_date('"
					+ reorderTime
					+ "','yyyy-MM-dd')," + "'" + alter + "')";
			this.execute(content_ext_sql);

			// System.out.println("22222222222");

			// 向jc_app表中插入数据
			String select_app = "select app_id from jc_app";
			List<Map<String, Object>> apps = this.queryForList(select_app);
			int status = 0;
			for (int i = 0; i < apps.size(); i++) {
				if (Appid.equals(apps.get(i).get("app_id").toString())) {

					status = 1;
					break;
				}
			}
			if ((status == 0) && (!Appid.isEmpty()) && (!Appname.isEmpty())) {
				String insert_app = "insert into jc_app(app_id,app_name) values("
						+ Integer.valueOf(Appid) + ",'" + Appname + "')";
				this.execute(insert_app);
			}

			// String app_insert = "insert into jc_app(app_id,app_name) values("
			// + Integer.valueOf(Appid) + ",'" + Appname + "')";
			// this.execute(app_insert);

			if ((!text.isEmpty()) && (content_id != 0)) {
				// 向jc_content_txt表中插入数据
				String content_txt_insert = "insert into jc_content_txt(content_id,txt1) values("
						+ content_id + ",'" + text + "')";
				this.execute(content_txt_insert);
			}
			String select_site;
			System.out.println(target +"  target");
            if(target.contains("交互界面")||target.contains("综合服务平台")){
    			 select_site = "select site_id from jc_site where site_name = '综合服务平台'";
            }else{
    			 select_site = "select site_id from jc_site where site_name = '协同管理平台'";
            }
			// System.out.println(select_site);
			int site_ids = this.queryForInt(select_site);
            System.out.println(site_ids);
			String select_columnId_sql = "select jce.channel_id from jc_channel_ext jce,jc_channel jc where jce.channel_name = '通知公告' and jce.channel_id = jc.channel_id and jc.site_id = "
					+ site_ids;
			// System.out.println(select_columnId_sql);
			// String select_flfg =
			// "select channel_id from jc_channel_ext where channel_name = '法律法规'";

			int tzgg = this.queryForInt(select_columnId_sql);
			// int flfg = this.queryForInt(select_flfg);

			if ((!columnId.isEmpty()) && (!target.isEmpty())
					&& (tzgg == Integer.valueOf(columnId))) {
				// System.out.println("execue");
				// 插入 展现系统字段
				String content_attr_sql = "insert into jc_content_attr(content_id,attr_name,attr_value) values("
						+ content_id + ",'showsys','" + target + "')";
				this.execute(content_attr_sql);
			}
			if ((!columnId.isEmpty()) && (!target.isEmpty())&& (tzgg == Integer.valueOf(columnId))) {
				String content_publish_sql = "insert into jc_content_attr(content_id,attr_name,attr_value) values("
						+ content_id + ",'publish','" + publish + "')";
				this.execute(content_publish_sql);
			}

			// String attachAddress = "/install/tomcat/webapps/ROOT/u/cms/msa/";

			// System.out.print("config"+getConfig.GetConfigs());
			// String attachAddress = "/u01/URCms/u/cms/msa/";
			String attachAddress = getConfig.GetConfigs();
			// System.out.println(attachAddress);
			File dir = new File(attachAddress);
			if (!dir.exists() || dir.isDirectory()) {
				dir.mkdirs();
				// System.out.println("create");
			}
			// System.out.println(attachAddress);
			// String filePath = attachAddress;
			if ((files != null) && (filename != null) && (filename.length != 0)
					&& (files.length != 0) && (content_id != 0)) {
				System.out.println("filename    " + filename);
				String[] attachment_name = generateAttachName(filename);// D:/ceshi.txt
				String[] attachment_path = generateAttachPath(files, filename,
						attachAddress);
				// System.out.println("111");
				for (int i = 0; i < attachment_name.length; i++) {
					String content_attachment_sql = "insert into jc_content_attachment(content_id,priority,attachment_path,attachment_name,filename,download_count) values("
							+ content_id
							+ ","
							+ i
							+ ",'"
							+ attachment_path[i]
							+ "','"
							+ attachment_name[i]
							+ "','"
							+ filename[i]
							+ "'," + 0 + ")";
					// System.out.println(content_attachment_sql);
					this.execute(content_attachment_sql);

					String upload_sql = "insert into jc_file(file_path,file_name,file_isvalid,content_id) values('"
							+ attachment_path[i]
							+ "','"
							+ attachment_name[i]
							+ "'," + 1 + "," + content_id + ")";
					// System.out.println(upload_sql);
					this.execute(upload_sql);
				}
			}
			return "success" + content_id;
		} catch (Exception e) {
			e.printStackTrace();
			return "failed" + e.toString();
		}
	}

	/*
	 * 参数 file,文件名称,上传地址 返回 附件上传路径
	 */
	public String[] generateAttachPath(byte[][] file, String[] filename,
			String filePath) {
		// attachment_path = /u01/appinstall/URCms/u/cms/msa
		Upload upload = new Upload();
		String[] attachPath = new String[filename.length];
		// System.out.println(filePath);
		for (int i = 0; i < filename.length; i++) {

			String ext = FilenameUtils.getExtension(new File(new File(
					filename[i]).getAbsolutePath()).getName());
			// /u/cms/msa/
			String filePathnew = filePath.substring(
					filePath.indexOf("/URCms/"), filePath.lastIndexOf("/"));

			// System.out.println(filePathnew);
			// /u/cms/msa/201501/17190531jlmw.txt
			String filenew = upload.generateFilename(filePathnew, ext);
			// System.out.println(filenew);

			// 17190531jlmw
			String name_new = FilenameUtils.getBaseName(new File(new File(
					filenew).getAbsolutePath()).getName());
			// txt
			String exten_new = FilenameUtils.getExtension(new File(new File(
					filenew).getAbsolutePath()).getName());
			// 17190531jlmw.txt
			String fileName_new = name_new + "." + exten_new;

			String filePaths = filePath
					+ filenew.substring(filenew.indexOf("msa") + 4,
							filenew.indexOf(name_new) - 1);
			// String filePaths = filePath.substring(0,
			// filenew.indexOf(name_new));
			Upload.byte2File(file[i], filePaths, fileName_new);
			// String filenew2 = filenew.substring(filenew.indexOf("/u/"));
			attachPath[i] = filenew;
		}
		return attachPath;
	}

	// 根据文件路径数组取到附件名称
	public String[] generateAttachName(String[] filename) {
		String[] attachName = new String[filename.length];
		for (int i = 0; i < filename.length; i++) {
			String ext = FilenameUtils.getExtension(new File(new File(
					filename[i]).getAbsolutePath()).getName());
			String baseName = FilenameUtils.getBaseName(new File(new File(
					filename[i]).getAbsolutePath()).getName());

			String attachNamee = baseName + "." + ext;
			attachName[i] = attachNamee;
		}
		return attachName;
	}

	@Override
	public Map<String, Object> queryContentById(String contentId,String siteName) {
		System.out.println("进入querycontent");
		Map<String, Object> queryContents = new LinkedHashMap<String, Object>();
		String type_id = "0";
		String author = "", origin = "", description = "", amend = "", createTime = "", updateTime = "", reorderTime = "", test = "", target = "", assortment = "";
		try {

			if ((!contentId.isEmpty()) && (contentId != null)) {
				// 根据contentId查询app_id,app_name
				String select_content = "select * from jc_content jc where jc.content_id = "
						+ Integer.valueOf(contentId) + " and status = " + 2;
				System.out.println(select_content);
//				System.out.println(this.queryForInt(select_content));
				List<Map<String, Object>> resulist = this
						.queryForList(select_content);
				System.out.println("sdsd");
				System.out.println(resulist.size());
				if (resulist.size() == 0) {
					queryContents.put("fail", "0");
				} else {
					System.out.println("else");
					Map<String, Object> content_map = this
							.queryForMap(select_content);
					System.out.println(content_map.get("app_id"));
					String app_id = null;
					String app_name = null;
					if (content_map.get("app_id") != null) {
						app_id = content_map.get("app_id").toString();

					}

					String channel_id = content_map.get("channel_id")
							.toString();
					type_id = content_map.get("type_id").toString();
					if (app_id != null) {
						System.out.println(app_id + channel_id + type_id);

						String select_app = "select app_name from jc_app ja where ja.app_id = "
								+ Integer.valueOf(app_id);

						Map<String, Object> app_name_map = this
								.queryForMap(select_app);
						System.out.println(app_name_map);

						app_name = app_name_map.get("app_name").toString();
						System.out.println(app_name);
					}

					String select_content_ext = "select * from jc_content_ext jce where jce.content_id = "
							+ Integer.valueOf(contentId);
					Map<String, Object> content_ext_map = this
							.queryForMap(select_content_ext);
					System.out.println(content_ext_map + "22211");
					String title = content_ext_map.get("title").toString();
					System.out.println(title + "title");
					String short_title = null;
					if (content_ext_map.get("short_title") != null) {
						short_title = content_ext_map.get("short_title")
								.toString();
						System.out.println(short_title + "short");
					}

					System.out.println(content_ext_map.get("author"));
					if (content_ext_map.get("author") != null) {
						author = content_ext_map.get("author").toString();
						System.out.println(author + "author");
					}
					if (content_ext_map.get("origin") != null) {
						origin = content_ext_map.get("origin").toString();
						System.out.println(origin + "origin");
					}
					if (content_ext_map.get("description") != null) {
						description = content_ext_map.get("description")
								.toString();
						System.out.println(description + "description");
					}
					if (content_ext_map.get("amend") != null) {
						amend = content_ext_map.get("amend").toString();
						System.out.println(amend + "amend");
					}
					if (content_ext_map.get("createtime") != null) {
						createTime = content_ext_map.get("createtime")
								.toString();
						System.out.println(createTime + "createtime");
					}
					if (content_ext_map.get("updatetime") != null) {
						updateTime = content_ext_map.get("updatetime")
								.toString();
						System.out.println(updateTime + "updatetime");
					}
					if (content_ext_map.get("reordertime") != null) {
						reorderTime = content_ext_map.get("reordertime")
								.toString();
						System.out.println(reorderTime + "reordertime");
					}

					String select_content_txt = "select txt1 from jc_content_txt jct where jct.content_id = "
							+ Integer.valueOf(contentId);
					System.out.println(this.queryForList(select_content_txt)
							+ "testma;p");
					if ((this.queryForList(select_content_txt).size() != 0)
							&& (this.queryForList(select_content_txt).get(0)
									.get("TXT1") != null)) {
						Map<String, Object> test_map = this
								.queryForMap(select_content_txt);
						System.out.println("11111111");
						test = test_map.get("txt1").toString();						
						
						Pattern pattern=Pattern.compile("(\r\n|\r|\n|\n\r)");
						//正则表达式的匹配一定要是这样，单个替换\r|\n的时候会错误
						  Matcher matcher=pattern.matcher(test);
						  test=matcher.replaceAll("<br/>");
						System.out.println("222222");
						System.out.println(test);
					}

					System.out.println("testtstfusdfsjfdshfdj");
//jc_content_channel 改为jc_content,正式环境下也需要改
					String content_channel = "select channel_id from jc_content where content_id = "
							+ Integer.valueOf(contentId);
					System.out.println(content_channel);
					int channel_ids = this.queryForInt(content_channel);
					System.out.println(channel_ids);

					String select_site = "select site_id from jc_site where site_name = '"+siteName+"'";
					int site_ids = this.queryForInt(select_site);
//					System.out.println(site_ids);
					String select_columnId_sql = "select jce.channel_id from jc_channel_ext jce,jc_channel jc where jce.channel_name = '通知公告' and jce.channel_id = jc.channel_id and jc.site_id = "
							+ site_ids;
					int tzgg = this.queryForInt(select_columnId_sql);

					System.out.println(channel_ids + "chan   " + tzgg);
					if (channel_ids == tzgg) {
						String select_content_attr = "select attr_value from jc_content_attr where content_id = "
								+ Integer.valueOf(contentId)
								+ " and attr_name = 'showsys'";
						System.out.println(select_content_attr);
						if (this.queryForList(select_content_attr).size() != 0) {
							Map<String, Object> target_map = this
									.queryForMap(select_content_attr);

							System.out.println(target_map);

							target = target_map.get("attr_value").toString();
							System.out.println(target + "target");
						}
						String select_content_type = "select type_name from jc_content_type where type_id = "
								+ Integer.valueOf(type_id);
						if (this.queryForList(select_content_type).size() != 0) {
							Map<String, Object> assortment_map = this
									.queryForMap(select_content_type);
							assortment = assortment_map.get("type_name").toString();
						}

						System.out.println(assortment + "assortment");

						String select_content_attachment = "select * from jc_content_attachment where content_id = "
								+ Integer.valueOf(contentId);
						System.out.println(target + " " + assortment);
						List<Map<String, Object>> attachment_path_map = this
								.queryForList(select_content_attachment);
						List<String> files = new ArrayList<String>();
						// List<String> paths = new ArrayList<String>();
						String pathss = "";
						byte[][] paths = new byte[attachment_path_map.size()][];
						String str = "";
						try {
							if (attachment_path_map.size() != 0) {
								System.out.println(files);
								// System.out.println(paths);

								// List<List<Byte>> attachPathList = new
								// ArrayList<List<Byte>>();
								// byte[][] attachContains = new
								// byte[100][attachment_path_map.size()];

								for (int i = 0; i < attachment_path_map.size(); i++) {
									String attachment_name = attachment_path_map
											.get(i).get("attachment_name")
											.toString();
									System.out.println(attachment_name);
									files.add(attachment_name);

									String attachment_path = attachment_path_map
											.get(i).get("attachment_path")
											.toString();
									System.out.println(attachment_path);
									attachment_path = "/u01/appinstall"
											+ attachment_path;
									byte[] attach_contains = Upload
											.File2byte(attachment_path);

									Upload.byte2File(
											attach_contains,
											"E://",
											"eeddddddssddddd"
													+ i
													+ "."
													+ attachment_name
															.substring(attachment_name
																	.indexOf(".") + 1));
									paths[i] = attach_contains;
									System.out.println(attach_contains);
									// String path = new String(attach_contains);
									// paths += path + ",";
									// attachPathList.add(attach_contains);
									// attachContains[i] = attach_contains;
									// paths.add(path);
								}
							}
							
							for (int i = 0; i < paths.length; i++) {
								str += Base64.encode(paths[i]);
								if (i != paths.length - 1) {
									str += ",";
								}
							}
						} catch (Exception e) {
							System.out.println("ex..");
	                          str="";
	                          files=null;
						}

						// System.out.println(attachContains);
						// System.out.println("dddddddddddddddddd");
						if (app_id != null) {
							queryContents.put("Appid", app_id);
							queryContents.put("Appname", app_name);
						}

						queryContents.put("columnId", channel_id);
						queryContents.put("Title", title);
						if (short_title != null) {
							queryContents.put("shortName", short_title);

						}
						queryContents.put("author", author);
						if (channel_ids == tzgg) {
							queryContents.put("target", target);
						}
						queryContents.put("ContentDetail", description);
						queryContents.put("alter", amend);
						queryContents.put("createTime", createTime);
						queryContents.put("updateTime", updateTime);
						queryContents.put("reorderTime", reorderTime);
						queryContents.put("text", test);
						queryContents.put("source", origin);
						queryContents.put("assortment", assortment);

						queryContents.put("files", str);
						queryContents.put("fileName", files);
					}else {						
						queryContents.put("success", "1");
					}
				}

					}

					
		} catch (Exception e) {
			queryContents.put("fail", "0");
		}
		return queryContents;
	}

	@Override
	public String deleteContentById(String contentId, String userId,
			String appId) {
		String site_id = "";
		try {
			if ((!contentId.isEmpty()) && (contentId != null)) {
				String site_id_sql = "select site_id from jc_content where content_id = "
						+ Integer.valueOf(contentId);
				Map<String, Object> site_id_map = this.queryForMap(site_id_sql);
				site_id = site_id_map.get("site_id").toString();
				String select_recycle_on = "select is_recycle_on from jc_site where site_id = "
						+ Integer.valueOf(site_id);
				Map<String, Object> recyle_map = this
						.queryForMap(select_recycle_on);
				String is_recycle_on = recyle_map.get("is_recycle_on")
						.toString();
				if ("1".equals(is_recycle_on)) {
					String select_app_sql = "select app_id from jc_content where content_id = "
							+ Integer.valueOf(contentId);
					Map<String, Object> app_id_map = this
							.queryForMap(select_app_sql);
					String app_id = app_id_map.get("app_id").toString();
					 System.out.println(app_id);

					if ((!appId.isEmpty()) && (appId != null)
							&& (appId.equals(app_id))) {
						String update_content = "update jc_content set status = "
								+ 3
								+ " where user_id = "
								+ Integer.valueOf(userId)
								+ " and content_id = "
								+ Integer.valueOf(contentId)
								+ " and app_id = "
								+ Integer.valueOf(app_id);
						int result = this.update(update_content);
						// System.out.println(result);
						return "ok";
					} else {
						return "fail";
					}
				} else {
					return "fail";
				}

			}

		} catch (Exception e) {
			return "exception";
		}
		return "fail";
	}

	@Override
	public String updateContent(String contentId, String Title,
			String shortName, String author, String target,
			String ContentDetail, String alter, String createTime,
			String updateTime, String reorderTime, String text, byte[][] files,
			String[] filename, String Appid, String Appname, String source,
			String assortment, String columnId,String publish) {
		String judge = "select status from jc_content where content_id = "
				+ Integer.valueOf(contentId);
		if (this.queryForInt(judge) == 3) {
			return "fail";
		} else {
			// 查询栏目ID是否存在
			String select_channel = "select * from jc_channel where channel_id = "
					+ Integer.valueOf(columnId);
			if (!this.queryForList(select_channel).isEmpty()) {
				// 更改栏目ID
				String content_channel_sql = "update jc_content_channel set channel_id = "
						+ Integer.valueOf(columnId)
						+ " where content_id = "
						+ Integer.valueOf(contentId);
				int result = this.update(content_channel_sql);
				// System.out.println(result + "channel");

				// 获取类型ID，系统ID
				String content_sql = "select * from jc_content where content_id = "
						+ Integer.valueOf(contentId);
				Map<String, Object> content_map = this.queryForMap(content_sql);
				String type_id = content_map.get("type_id").toString();
				// System.out.println(type_id + "type");

				// 更新jc_content表
				String content_update = "update jc_content set app_id = "
						+ Integer.valueOf(Appid) + " where content_id = "
						+ Integer.valueOf(contentId);
				int result_content = this.update(content_update);
				// System.out.println(result_content + "content");

				// 更新jc_content_ext表
				String update_content = "update jc_content_ext set title = '"
						+ Title + "',short_title = '" + shortName
						+ "', author = '" + author + "', origin = '" + source
						+ "',description = '" + ContentDetail
						+ "', createtime = to_date('" + createTime
						+ "','yyyy-MM-dd'), updatetime =to_date('" + updateTime
						+ "','yyyy-MM-dd'), reordertime =to_date('"
						+ reorderTime + "','yyyy-MM-dd'), amend = '" + alter
						+ "' where content_id = " + Integer.valueOf(contentId);
				// System.out.println(update_content);
				int result_content_ext = this.update(update_content);
				// System.out.println(result_content_ext + "content_ext");

				// 更新jc_content_type表
				String select_content_type = "select type_id from jc_content_type where type_name = '"
						+ assortment + "'";
				List<Map<String, Object>> resuList = this
						.queryForList(select_content_type);

				if (resuList.size() != 0) {
					String content_type_update = "update jc_content set type_id = "
							+ Integer.valueOf(resuList.get(0).get("type_id")
									.toString())
							+ " where content_id = "
							+ Integer.valueOf(contentId);
					int result_content_type = this.update(content_update);
				}

				// if(assortment.isEmpty()){
				// String update_type =
				// "update jc_content_type set type_name = '无' where type_id = "
				// + Integer.valueOf(type_id);
				// int result_type = this.update(update_type);
				// }else{
				// String update_type =
				// "update jc_content_type set type_name = '" + assortment +
				// "' where type_id = "
				// + Integer.valueOf(type_id);
				// int result_type = this.update(update_type);
				// }

				// System.out.println(result_type + "type");

				// //更新jc_app表
				String select_app = "select app_id from jc_app";
				List<Map<String, Object>> apps = this.queryForList(select_app);
				int status = 0;
				for (int i = 0; i < apps.size(); i++) {
					if (Appid.equals(apps.get(i).get("app_id").toString())) {
						String update_apps = "update jc_app set app_name = '"
								+ Appname + "' where app_id = "
								+ Integer.valueOf(Appid);
						int resu = this.update(update_apps);
						status = 1;
						// System.out.println(resu);
						break;
					}
				}
				if (status == 0) {
					String insert_app = "insert into jc_app(app_id,app_name) values("
							+ Integer.valueOf(Appid) + ",'" + Appname + "')";
					this.execute(insert_app);
				}

				String select_site = "select site_id from jc_site where site_name = '协同管理平台'";
				int site_ids = this.queryForInt(select_site);

				String select_columnId_sql = "select jce.channel_id from jc_channel_ext jce,jc_channel jc where jce.channel_name = '通知公告' and jce.channel_id = jc.channel_id and jc.site_id = "
						+ site_ids;
				// String select_flfg =
				// "select channel_id from jc_channel_ext where channel_name = '法律法规'";
				// 由于有两个值，所以写死
				int tzgg = this.queryForInt(select_columnId_sql);
				// int flfg = this.queryForInt(select_flfg);

				if (tzgg == Integer.valueOf(columnId)) {
					String update_attr = "update jc_content_attr set attr_value = '"
							+ target
							+ "' where content_id = "
							+ Integer.valueOf(contentId)
							+ " and attr_name = 'showsys'";
					int result_attr = this.update(update_attr);
					//修改发布范围
					String update_pub_attr = "update jc_content_attr set attr_value = '"
							+ publish
							+ "' where content_id = "
							+ Integer.valueOf(contentId)
							+ " and attr_name = 'publish'";
					int publish_attr = this.update(update_pub_attr);
				}

				// System.out.println(result_attr + "attr");

				// 更新jc_content_txt表
				String update_txt = "update jc_content_txt set txt1 = '" + text
						+ "' where content_id = " + Integer.valueOf(contentId);
				int result_txt = this.update(update_txt);
				// System.out.println(result_txt + "txt");

				// 更新jc_content_attachment
				// System.out.println(this.getClass().getResource("/").getPath());
				// String serverAddress =
				// this.getClass().getResource("/").getPath();
				// String headerAddress = serverAddress.substring(0,
				// serverAddress.lastIndexOf("classes/"));
				// String attachAddress = headerAddress + "attach/";
				// 设定附件存放位置
				String attachAddress = "";
				try {
					attachAddress = getConfig.GetConfigs();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				File dir = new File(attachAddress);
				// 路径若不存在自动进行创建
				if (!dir.exists() || dir.isDirectory()) {
					dir.mkdirs();
					System.out.println("create");
				}

				System.out.println(attachAddress);
				String filePath = attachAddress;

				if (files == null || filename == null) {
					String del_attach2 = "delete from jc_content_attachment where content_id = "
							+ Integer.valueOf(contentId);
					this.execute(del_attach2);
				}
				if ((files != null) && (filename != null)
						&& (filename.length != 0) && (files.length != 0)) {
					String[] attachment_name = generateAttachName(filename);
					String[] attachment_path = generateAttachPath(files,
							filename, filePath);

					String del_attach = "delete from jc_content_attachment where content_id = "
							+ Integer.valueOf(contentId);
					this.execute(del_attach);

					for (int i = 0; i < attachment_name.length; i++) {
						String attach_update = "insert into jc_content_attachment(content_id,priority,attachment_path,attachment_name,filename,download_count) values("
								+ Integer.valueOf(contentId)
								+ ","
								+ i
								+ ",'"
								+ attachment_path[i]
								+ "','"
								+ attachment_name[i]
								+ "','"
								+ filename[i]
								+ "'," + 0 + ")";
						// System.out.println(attachment_path[i]);
						int result_update = this.update(attach_update);
						// System.out.println(result_update);
					}
				}
				return "ok";
			} else {
				return "fail";
			}
		}

	}

	@Override
	public List<Map<String, Object>> assortmentsQuery() {
		String selectContentType = "select * from jc_content_type where is_disabled = 0";
		List<Map<String, Object>> contentTypeList = this
				.queryForList(selectContentType);
		List<Map<String, Object>> contentTypeResultList = new ArrayList<Map<String, Object>>();

		for (int i = 0; i < contentTypeList.size(); i++) {
			Map<String, Object> conMap = contentTypeList.get(i);
			Map<String, Object> conResult = new LinkedHashMap<String, Object>();
			// 查询所有的业务分类ID及业务分类名称
			conResult.put("assortmentid", conMap.get("type_id"));
			conResult.put("assortmentname", conMap.get("type_name"));

			contentTypeResultList.add(conResult);
		}

		return contentTypeResultList;
	}

	@Override
	public List<Map<String, Object>> getArticleListByAppid(String appId,
			String userId, int pageSize, int Pages,String publish) {
		System.out.println("sjfhsdf");
		int start = 0, end = 0, pages = 0;
		List<Map<String, Object>> articleResultList = new ArrayList<Map<String, Object>>();
		Map<String, Object> articleMap = new HashMap<String, Object>();

		// 获取总查询条数
		System.out
				.println(appId + "  " + userId + " " + pageSize + " " + Pages);
		String total_sql = "select count(*) from jc_content jc,jc_content_attr attr where jc.app_id = "
				+ Integer.valueOf(appId)
				+ " and jc.user_id = "
				+ Integer.valueOf(userId) + " and jc.status = " + 2+" and jc.content_id=attr.content_id and attr.attr_name='publish' and attr.attr_value='"+publish+"'";
		int total = this.queryForInt(total_sql);

		// System.out.println(total_sql);
		System.out.println(total + "count");

		// 根据总条数和每页条数，计算出总页数
		if (total % pageSize == 0) {
			pages = total / pageSize;
		} else {
			pages = total / pageSize + 1;
		}
		System.out.println(pages + "总页数");
		System.out.println(Pages);

		// 当前页数小于总页数
		if (Pages < pages) { // 10页 9页 10条 105总 1-10,11-20,21-30.。。
			start = (Pages - 1) * pageSize + 1; // 81 = 8*10+1
			end = Pages * pageSize; // 90

			// 当前页数为最后一页
		} else if (Pages == pages) { // 11页 101-105
			start = (Pages - 1) * pageSize + 1; //
			int end2 = (total % pageSize == 0) ? pageSize : (total % pageSize);
			end = (Pages - 1) * pageSize + end2;// 11-1 * 10 + 105 %
												// 10
		} else {
			articleMap.put("resu", "fail");
			articleResultList.add(articleMap);
			return articleResultList;
		}
		System.out.println(start + "   " + end);

		// 查询出指定页的记录数
		String selectResult = "select t.channel_id,t.content_id,t.title,t.link,t.author,t.release_date,t.type_name from (select jc.channel_id,jc.content_id,jce.title,jce.link,jce.author,jce.release_date,jct.type_name"
				+ " from jc_content jc,jc_content_ext jce,jc_content_type jct,jc_content_attr attr  where jc.app_id = "
				+ Integer.valueOf(appId)
				+ " and jc.user_id = "
				+ Integer.valueOf(userId)
				+ " and jc.content_id = jce.content_id"
				+" and jc.content_id=attr.content_id and attr.attr_name='publish' and attr.attr_value='"+publish+"'"
				+ " and jc.type_id = jct.type_id and jct.type_id in (select distinct type_id from jc_content where app_id = "
				+ Integer.valueOf(appId)
				+ " and user_id = "
				+ Integer.valueOf(userId)
				+ ")) t"
				+ " where rowid in (select rid  from (select rownum rn, rid  from (select rowid rid, content_id from (select jc.channel_id,jc.content_id,jce.title,jce.link,jce.author,"
				+ " jce.release_date,jct.type_name  from jc_content jc,jc_content_ext  jce,jc_content_type jct,jc_content_attr attr where jc.app_id = "
				+ Integer.valueOf(appId)
				+ " and jc.user_id = "
				+ Integer.valueOf(userId)
				+ " and jc.content_id = jce.content_id "
				+ " and jc.content_id=attr.content_id and attr.attr_name='publish' and attr.attr_value='"+publish+"'"
				+" and jc.type_id = jct.type_id and jct.type_id in (select distinct type_id from jc_content where app_id = "
				+ Integer.valueOf(appId)
				+ " and user_id = "
				+ Integer.valueOf(userId)
				+ ")) order by content_id desc) where rownum <= "
				+ end
				+ ") where rn >= " + start + ") order by content_id desc";
		 System.out.println(selectResult);

		List<Map<String, Object>> articleList = this.queryForList(selectResult);
		// System.out.println(articleList);
		// System.out.println("after------------");
		int i_start = 0;
		int i_end = end - start;

		for (int i = i_start; i <= i_end; i++) {

			Map<String, Object> article = articleList.get(i);
			Map<String, Object> articleResult = new LinkedHashMap<String, Object>();
			articleResult.put("channelid", article.get("channel_id"));
			articleResult.put("articleid", article.get("content_id"));
			articleResult.put("title", article.get("title"));
			articleResult.put("url", article.get("link"));
			articleResult.put("datetime", article.get("release_date"));
			articleResult.put("business", article.get("type_name"));
			articleResult.put("author", article.get("author"));
			// articleResult.put("Total", total);
			articleResultList.add(articleResult);
		}
		Map<String, Object> totalSize = new HashMap<String, Object>();
		totalSize.put("total", total);
		articleResultList.add(totalSize);
		// System.out.println(articleResultList);

		return articleResultList;
	}

	@Override
	public List<Map<String, Object>> getArticleListByTypeIdAndChannelId(
			String typeId, String channelId,int pageSize, int Pages,String publish) {
		int start = 0, end = 0, pages = 0;
		List<Map<String, Object>> articleResultList = new ArrayList<Map<String, Object>>();
		Map<String, Object> articleMap = new HashMap<String, Object>();

		// 获取总查询条数
		String total_sql = "select count(*) from jc_content jc,jc_content_attr attr where jc.channel_id = '"
				+ Integer.valueOf(channelId)
				+ "' and jc.type_id = "
				+ Integer.valueOf(typeId) + " and jc.status = " + 2 + " and jc.content_id=attr.content_id and attr.attr_name='publish' and attr.attr_value='"+publish+"'";
		System.out.println(total_sql);

		int total = this.queryForInt(total_sql);
		// System.out.println(total_sql);
		// System.out.println(total + "count");

		// 根据总条数和每页条数，计算出总页数
		if (total % pageSize == 0) {
			pages = total / pageSize;// 1
		} else {
			pages = total / pageSize + 1;
		}
		System.out.println(pages + "总页数");

		// 当前页数小于总页数
		if (Pages < pages) { // 10页 9页 10条 105总 1-10,11-20,21-30.。。
			start = (Pages - 1) * pageSize + 1; // 81 = 8*10+1
			end = Pages * pageSize; // 90

			// 当前页数为最后一页
		} else if (Pages == pages) { // 11页 101-105
			if (total == pageSize) {
				start = (Pages - 1) * pageSize + 1;
				end = total;
			} else {
				start = (Pages - 1) * pageSize + 1; //
				end = (Pages - 1) * pageSize + total % pageSize;// 11-1 * 10 +
																// 105 % // 10
			}

		} else {
			articleMap.put("resu", "fail");
			articleResultList.add(articleMap);
			return articleResultList;
		}
		System.out.println(start + "   " + end);

		// 查询出指定页的记录数
		String selectResult = "select t.channel_id,t.content_id,t.title,t.link,t.author,t.release_date,t.type_name from (select jc.channel_id,jc.content_id,jce.title,jce.link,jce.author,jce.release_date,jct.type_name"
				+ " from jc_content jc,jc_content_ext jce,jc_content_type jct,jc_content_attr attr  where jc.channel_id = "
				+ Integer.valueOf(channelId)
				+ " and jc.type_id = "
				+ Integer.valueOf(typeId)
				+ " and jc.status=2 "
				+ " and jc.content_id = jce.content_id"
				+ " and jc.content_id=attr.content_id and attr.attr_name='publish' and attr.attr_value='"+publish+"'"
				+ " and jc.type_id = jct.type_id and jct.type_id in (select distinct type_id from jc_content where channel_id = "
				+ Integer.valueOf(channelId)
				+ " and status=2 and type_id = "
				+ Integer.valueOf(typeId)
				+ ")) t"
				+ " where rowid in (select rid  from (select rownum rn, rid  from (select rowid rid, content_id from (select jc.channel_id,jc.content_id,jce.title,jce.link,jce.author,"
				+ " jce.release_date,jct.type_name  from jc_content jc,jc_content_ext  jce,jc_content_type jct,jc_content_attr attr  where jc.channel_id = "
				+ Integer.valueOf(channelId)
				+ " and jc.type_id = "
				+ Integer.valueOf(typeId)
				+ " and jc.content_id = jce.content_id "
				+ " and jc.content_id=attr.content_id and attr.attr_name='publish' and attr.attr_value='"+publish+"'"
				+" and jc.type_id = jct.type_id and jc.status = 2 and jct.type_id in (select distinct type_id from jc_content where channel_id = "
				+ Integer.valueOf(channelId)
				+ " and type_id = "
				+ Integer.valueOf(typeId)
				+ " and status = 2 "
				+ ")) order by content_id desc) where rownum <= "
				+ end
				+ ") where rn >= " + start + ") order by content_id desc";
		System.out.println(selectResult);

		List<Map<String, Object>> articleList = this.queryForList(selectResult);
		System.out.println(articleList);
		// System.out.println("after------------");
		int i_start = 0;
		int i_end = end - start;

		for (int i = i_start; i <= i_end; i++) {

			Map<String, Object> article = articleList.get(i);
			Map<String, Object> articleResult = new LinkedHashMap<String, Object>();
			articleResult.put("channelid", article.get("channel_id"));
			articleResult.put("articleid", article.get("content_id"));
			articleResult.put("title", article.get("title"));
			articleResult.put("url", article.get("link"));
			articleResult.put("datetime", article.get("release_date"));
			articleResult.put("business", article.get("type_name"));
			articleResult.put("author", article.get("author"));
			// articleResult.put("Total", total);
			articleResultList.add(articleResult);
		}
		Map<String, Object> totalSize = new HashMap<String, Object>();
		totalSize.put("total", total);
		articleResultList.add(totalSize);
		// System.out.println(articleResultList);

		return articleResultList;
	}
}
