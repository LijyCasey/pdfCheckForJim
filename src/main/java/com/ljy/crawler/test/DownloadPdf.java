package com.ljy.crawler.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class DownloadPdf {
	
	private class result{
		private boolean result;
//		private String 
	}
	private static final String[] dictionary = "高血压、减盐、糖尿病、减糖、超重、肥胖、血脂异常、高血脂、烟草、禁烟、控烟、戒烟、大气污染、空气污染、水污染、土壤污染、环境保护、全民健身、健康素养、健康管理、心血管病、心血管疾病、心脑血管疾病、慢性病、慢病、门诊统筹"
			.split("、");


	public static void main(String[] args) {
		// new DownloadPdf().get();
		CloseableHttpClient client = HttpClients.createDefault();
		String url = "http://govfile.beijing.gov.cn/Govfile/DownLoadNewPageServlet?id=6159&file_name=null";
		String filePath = "d:/test2.txt";
		String charset = "UTF-8";
		boolean closeHttpClient = true;
		try {
			new DownloadPdf().executeDownloadFile(client,url,filePath,charset,closeHttpClient);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 执行文件下载
	 *
	 * @param httpClient
	 *            HttpClient客户端实例，传入null会自动创建一个
	 * @param remoteFileUrl
	 *            远程下载文件地址
	 * @param localFilePath
	 *            本地存储文件地址
	 * @param charset
	 *            请求编码，默认UTF-8
	 * @param closeHttpClient
	 *            执行请求结束后是否关闭HttpClient客户端实例
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public boolean executeDownloadFile(CloseableHttpClient httpClient, String remoteFileUrl, String localFilePath,
			String charset, boolean closeHttpClient) throws ClientProtocolException, IOException {
		CloseableHttpResponse response = null;
		InputStream in = null;
		StringBuffer sb = new StringBuffer();
		FileOutputStream fout = null;
		try {
			HttpGet httpget = new HttpGet(remoteFileUrl);
			// 模拟浏览器
			httpget.setHeader("User-Agent",
					"Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:46.0) Gecko/20100101 Firefox/46.0");
			response = httpClient.execute(httpget);
			HttpEntity entity = response.getEntity();
			if (entity == null) {
				return false;
			}
			in = entity.getContent();
			// 可以直接先解析，含有关键字再下载

			File file = new File(localFilePath);
			fout = new FileOutputStream(file);
			int l;
			byte[] tmp = new byte[1024];
			while ((l = in.read(tmp)) != -1) {
				sb.append(new String(tmp, 0, l));
//				fout.write(tmp, 0, l);
				
				// 注意这里如果用OutputStream.write(buff)的话，图片会失真
			}
//			if (check(sb)) {
//				fout.flush();
//			}
				 FileWriter fw = new FileWriter(file.getName());
		         BufferedWriter bw = new BufferedWriter(fw);
		         bw.write(sb.toString());
		         bw.flush();
		         bw.close();
			// 将文件输出到本地
			EntityUtils.consume(entity);
			return true;
		} finally {
			// 关闭低层流。
			if (fout != null) {
				try {
					fout.close();
				} catch (Exception e) {
				}
			}
			if (response != null) {
				try {
					response.close();
				} catch (Exception e) {
				}
			}
			if (closeHttpClient && httpClient != null) {
				try {
					httpClient.close();
				} catch (Exception e) {
				}
			}
		}
	}

	private boolean check(StringBuffer sb){
		for(String tmp:dictionary){
//			if(sb.in)
		}
		return false;
	}
}
