package com.njaqn.itravel.aqnapp.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class UrlHttp
{
	private String url;
	private String params;
	private static String retResult;
	
	public UrlHttp(){
		this.url = AQNAppConst.URL_AM;
	}
	
	public UrlHttp(String url,String params) {
		this.url = url;
		this.params = params;
	}
	
	private String post() throws Exception
	{
		retResult = "";
		final HttpPost post = new HttpPost(url);
		final DefaultHttpClient client = new DefaultHttpClient();
		StringEntity param = new StringEntity(params , HTTP.UTF_8);
		
		param.setChunked(false);
		param.setContentType("application/x-www-form-urlencoded");
		
		post.setEntity(param);
		
		Thread thread = new Thread(new Runnable()
		{
			public void run()
			{
				try
				{
					HttpResponse res = client.execute(post);
					int status = res.getStatusLine().getStatusCode();
					if (status != HttpStatus.SC_OK)
					{
						throw new Exception("网络发生错误!请耐心等待");
					}
					
					String msg = EntityUtils.toString(res.getEntity() , "UTF-8");
					Bundle bd = new Bundle();
					bd.putString("msg" , msg);
					
					Message tempMessage = new Message();
					tempMessage.setData(bd);
					tempMessage.what = 0;
					hd.handleMessage(tempMessage);
				} 
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
		
		thread.start();
		thread.join();
		
		return retResult;
	}
	
	
	Handler hd = new Handler()
	{
		public void handleMessage(android.os.Message msg) 
		{
			if (msg.what == 0)
			{
				Bundle b = msg.getData();
				String result = b.getString("msg");
				retResult = result.replace("\r\n", "");
			}
		};
	};
	
	public String postRequestForSql(String sql,int op) throws Exception
	{
		this.url = AQNAppConst.URL_DB;
		this.params = "op=" + op + "&sql=" +sql;
		return post();
	}

	/**
	 * Post Http 发送数据
	 * 
	 * @param msg
	 * @return
	 */
	public String postRequest( Map<String,String> params,int pageId,int op) throws Exception
	{
		this.setParams(params, pageId, op);
		return post();
	}

	public String postRequest(String key, String value,int pageId,int op) throws Exception
	{
		this.setParams(key,value, pageId, op);
		return post();
	}

	public String postRequest(int pageId,int op) throws Exception
	{
		this.params = "pageId=" + pageId + "&op=" +op;
		this.setUrl(pageId);
		return post();
	}

	private void setParams(Map<String,String> paramMap,int pageId,int op) throws Exception
	{
		StringBuilder data = new StringBuilder();
		
		if(paramMap!=null && !paramMap.isEmpty())
		{
			for(Map.Entry<String,String> entry:paramMap.entrySet())
			{	
				data.append(entry.getKey()).append("=");
				data.append(entry.getValue()).append("&");
			}
			
			data.append("pageId=").append(pageId).append("&op=").append(op);
			this.params = data.toString();
		}

		setUrl(pageId);
	}

	private void setParams(String key,String value,int pageId,int op) throws Exception
	{
		StringBuilder data = new StringBuilder();
		data.append(key).append("=");
		data.append(value).append("&");
		data.append("pageId=").append(pageId).append("&op=").append(op);
		this.params = data.toString();
		setUrl(pageId);
	}

	private void setUrl(int pageId)
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
}

