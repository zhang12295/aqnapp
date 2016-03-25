package com.njaqn.itravel.aqnapp.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
public class UrlHttp
{
	private String url;
	
	public UrlHttp(){
		this.url = AQNAppConst.URL_AM;
	}
	
	public UrlHttp(String url) {
		this.url = url;
	}

	public String postRequestForSql(String sql,String op) throws Exception
	{
		this.url = AQNAppConst.URL_DB;
		HttpURLConnection cnn = getCnn();
		String params = "sql="+sql+"&op="+op;
		byte[] data = params.getBytes();
		cnn.setRequestProperty("Content-Length", String.valueOf(data.length));
		
		OutputStream out = cnn.getOutputStream();
		out.write(data);
		
		if(cnn.getResponseCode()==200)
		{
			InputStream in = cnn.getInputStream();
			return new String(StreamUtil.read(in));
		}
		return null;
	}

	/**
	 * Post Http ·¢ËÍÊý¾Ý
	 * 
	 * @param msg
	 * @return
	 */
	public String postRequest( Map<String,String> params,int pageId,int op) throws Exception
	{
		byte [] data = getParamsByte(params,pageId,op);
		HttpURLConnection cnn = getCnn();
		cnn.setRequestProperty("Content-Length", String.valueOf(data.length));
		
		OutputStream out = cnn.getOutputStream();
		out.write(data);
		
		if(cnn.getResponseCode()==200)
		{
			InputStream in = cnn.getInputStream();
			return new String(StreamUtil.read(in));
		}
		return null;
	}

	public String postRequest(Map<String,String> params,int pageId,int op,String url) throws Exception
	{
		this.url = url;
		return postRequest(params,pageId,op);
	}

	public String postRequest(String key, String value,int pageId,int op) throws Exception
	{
		byte[] data = (key+"="+value).getBytes();
		HttpURLConnection cnn = getCnn();
		cnn.setRequestProperty("Content-Length", String.valueOf(data.length));
		
		OutputStream out = cnn.getOutputStream();
		out.write(data);
		
		if(cnn.getResponseCode()==200)
		{
			InputStream in = cnn.getInputStream();
			return new String(StreamUtil.read(in));
		}
		return null;
	}

	public String postRequest(String key, String value,int pageId,int op,String url) throws Exception
	{
		this.url = url;
		return postRequest(key,value,pageId,op);
	}

	private HttpURLConnection getCnn() throws Exception
	{
		HttpURLConnection cnn = (HttpURLConnection)(new URL(url).openConnection());
		cnn.setConnectTimeout(5000);
		cnn.setRequestMethod("POST");
		cnn.setRequestProperty("Content-Type", "application/x-www-form-urlencode");
		return cnn;
	}

	private byte[] getParamsByte( Map<String,String> params,int pageId,int op) throws Exception
	{
		StringBuilder data = new StringBuilder();
		
		if(params!=null && !params.isEmpty())
		{
			for(Map.Entry<String,String> entry:params.entrySet())
			{	
				data.append(entry.getKey()).append("=");
				data.append(entry.getValue()).append("&");
			}
			
			data.append("pageId=").append(pageId).append("&op=").append(op);
			return data.toString().getBytes();
		}
		
		if(url==null)
		{
			if(pageId<AQNAppConst.PAGEID_BM)
				url = AQNAppConst.URL_AM;
			else if(pageId<AQNAppConst.PAGEID_CM)
				url = AQNAppConst.URL_BM;
			else if(pageId<AQNAppConst.PAGEID_DM)
				url = AQNAppConst.URL_CM;
			else
				url = AQNAppConst.URL_DM;
		}
		return null;
	}

}

