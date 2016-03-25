package com.njaqn.itravel.aqnapp.util;

import java.net.URL;
import java.net.URLConnection;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class ImageUtil {
	
	private Bitmap map;
	private byte[] retResult;
	
	public ImageUtil()
	{
	}

	private byte[] getImage(String path) throws Exception
	{
		String url = AQNAppConst.URL_IMG+path;
		final HttpPost post = new HttpPost(url);
		final DefaultHttpClient client = new DefaultHttpClient();

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
						throw new Exception("ÍøÂç·¢Éú´íÎó!ÇëÄÍÐÄµÈ´ý");
					}
					
					byte[]  data = EntityUtils.toByteArray(res.getEntity());
					Bundle bd = new Bundle();
					bd.putByteArray("msg", data);
					
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
			if(msg.what == 0)
			{
				Bundle b = msg.getData();
				retResult = b.getByteArray("msg");
			}
		};
	};
	
	public Bitmap getBitMap(String path)
	{
		try
		{
			byte[] data = getImage(path);
			map = BitmapFactory.decodeByteArray(data,0, data.length);
			return map;
		}
		catch(Exception ex)
		{
			
		}
		
		return null;
	}

}
