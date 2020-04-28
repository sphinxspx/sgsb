package com.zjhc.sgsb.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.sevenstar.app.util.PropertiesHelper;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Map;

public class YunHelper {
	public  static String YUN_UPLOAD;
	public static String YUN_DOWNLOAD;
	public static String YUN_DELETE;
	public static String CLIENTID;
	public static String USERID;

	static {
		Map<String, String> propertiesMap = PropertiesHelper.getPropertiesMap("application.properties");
		YUN_UPLOAD=propertiesMap.get("YUN.UPLOAD");
		YUN_DOWNLOAD=propertiesMap.get("YUN.DOWNLOAD");
		YUN_DELETE=propertiesMap.get("YUN.DELETE");
		CLIENTID=propertiesMap.get("YUN.CLIENTID");
		USERID=propertiesMap.get("YUN.USERID");
	}
	
	public static JSONObject delete(JSONObject param) throws ClientProtocolException, IOException{
		JSONObject result=null;
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		CloseableHttpResponse httpResponse = null;
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(400000).setSocketTimeout(200000000).build();
		HttpPost httpPost = new HttpPost(YUN_DELETE);
		httpPost.setConfig(requestConfig);
		StringEntity entity = new StringEntity(param.toString(), Charset.forName("UTF-8"));
		httpPost.setEntity(entity);
		httpResponse = httpClient.execute(httpPost);
	    HttpEntity responseEntity = httpResponse.getEntity();
	    int statusCode= httpResponse.getStatusLine().getStatusCode();
	    if(statusCode == 200){
	        BufferedReader reader = new BufferedReader(new InputStreamReader(responseEntity.getContent()));
	        StringBuffer buffer = new StringBuffer();
	        String str = "";
	        while((str = reader.readLine())!=null) {
	            buffer.append(str);
	        }
	        result=JSONObject.parseObject(buffer.toString());     
	        //System.out.println(buffer.toString());
	    }
	         
	    httpClient.close();
	    if(httpResponse!=null){
	        httpResponse.close();
	    }
	    
		return result;
	}
	

	public static JSONObject upload(byte[] file,String fileName) throws ClientProtocolException, IOException{
/*		System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.SimpleLog");
		System.setProperty("org.apache.commons.logging.simplelog.showdatetime", "true");
		System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.commons.httpclient", "stdout");*/
		JSONObject result=null;
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		CloseableHttpResponse httpResponse = null;
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(400000).setSocketTimeout(200000000).build();
		HttpPost httpPost = new HttpPost(YUN_UPLOAD);
		httpPost.setConfig(requestConfig);
		MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
		//multipartEntityBuilder.addBinaryBody("file",file);
		multipartEntityBuilder.addBinaryBody("file", new ByteArrayInputStream(file),ContentType.APPLICATION_OCTET_STREAM, fileName);
		multipartEntityBuilder.addTextBody("clientId", CLIENTID);
		multipartEntityBuilder.addTextBody("userId", USERID);
		HttpEntity httpEntity = multipartEntityBuilder.build();
	    httpPost.setEntity(httpEntity);
	    httpResponse = httpClient.execute(httpPost);
	    HttpEntity responseEntity = httpResponse.getEntity();
	    int statusCode= httpResponse.getStatusLine().getStatusCode();
	    if(statusCode == 200){
	        BufferedReader reader = new BufferedReader(new InputStreamReader(responseEntity.getContent()));
	        StringBuffer buffer = new StringBuffer();
	        String str = "";
	        while((str = reader.readLine())!=null) {
	            buffer.append(str);
	        }
	        result=JSONObject.parseObject(buffer.toString());     
	        //System.out.println(buffer.toString());
	    }
	         
	    httpClient.close();
	    if(httpResponse!=null){
	        httpResponse.close();
	    }
	    
	    
		return result;
	}
	
	public static String getDownloadUrl(String infoid){
		StringBuffer url = new StringBuffer();
		url.append(YUN_DOWNLOAD).append("?infoid=").append(infoid).append("&type=F");
		return url.toString();
	}
	
}
