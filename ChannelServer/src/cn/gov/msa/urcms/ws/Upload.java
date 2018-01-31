package cn.gov.msa.urcms.ws;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletContext;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.RandomStringUtils;

public class Upload {

	public static final DateFormat MONTH_FORMAT = new SimpleDateFormat(
			"/yyyyMM/ddHHmmss");
	public static final char[] N36_CHARS = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
			'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
			'x', 'y', 'z' };
	private ServletContext ctx;

	public ServletContext getServletContext() {
		return ctx;
	}

	public void setServletContext(ServletContext ctx) {
		this.ctx = ctx;
	}

	public String generateFilename(String path, String ext) {

		return path + MONTH_FORMAT.format(new Date())
				+ RandomStringUtils.random(4, N36_CHARS) + "." + ext;
	}

	public static void main(String[] args) {
		String path = "D:/ceshi.txt";
		File file = new File(path);
		byte[] a = Upload.File2byte(path);

		File tmpFile = new File(file.getAbsolutePath());
		File parentDir = tmpFile.getParentFile();
		int count = 1;
		String extension = FilenameUtils.getExtension(tmpFile.getName());
		String baseName = FilenameUtils.getBaseName(tmpFile.getName());
		System.out.println(extension + "----扩展名");
		System.out.println(baseName + "----最短名字");
		// do {
		// tmpFile = new File(parentDir, baseName + "(" + count++ + ")." +
		// extension);
		// System.out.println(tmpFile);
		// } while (tmpFile.exists());

		// System.out.println(new Test().generateFilename(path, extension));

		// System.out.println(a);
		Upload.byte2File(a, "E:/", "22.txt");
		String short_path = path.substring(0, path.indexOf("/"));

		System.out.println(short_path + "----short_path");

		// System.out.println(new Test().generateFilename(short_path,
		// extension));
		String filenew = new Upload().generateFilename(short_path, extension);
		System.out.println(filenew + "---filenew");
		String name = FilenameUtils.getBaseName(new File(new File(filenew)
				.getAbsolutePath()).getName());
		String exten = FilenameUtils.getExtension(new File(new File(filenew)
				.getAbsolutePath()).getName());
		String fileName = name + "." + exten;
		String filePath = filenew.substring(0, filenew.indexOf(name));
		System.out.println(fileName + "---------------name");
		System.out.println(filePath + "---------------path");
		Upload.byte2File(a, filePath, fileName);
	}

	
	public static byte[] File2byte(String filePath) {
		byte[] buffer = null;
		try {
			File file = new File(filePath);
			// FileInputStream fis = new FileInputStream(file);
			BufferedInputStream fis = new BufferedInputStream(
					new FileInputStream(file));
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			byte[] b = new byte[1024];
			int n;
			while ((n = fis.read(b)) != -1) {
				bos.write(b, 0, n);
			}
			fis.close();
			bos.close();
			buffer = bos.toByteArray();
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buffer;
	}


	public static File byte2File(byte[] buf, String filePath, String fileName) {
		BufferedOutputStream bos = null;
		FileOutputStream fos = null;
		File file = null;
		try {
			File dir = new File(filePath);
			System.out.println(dir + "  ff ");
			if (!dir.exists() || dir.isDirectory()) {
				dir.mkdirs();
				System.out.println("create");
			}
			file = new File(filePath + File.separator + fileName);
			fos = new FileOutputStream(file);
			bos = new BufferedOutputStream(fos);
			bos.write(buf);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return file;
	}
}
