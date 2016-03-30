package com.njaqn.itravel.aqnapp.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class HttpDb
{
    private String url = AQNAppConst.URL_DB;
    String r = "";

    public HttpDb()
    {
    }

    public HttpDb(String url)
    {
	this.url = url;
    }

    private String select(int op, String sql) throws Exception
    {
	r = "";
	final HttpPost mp = new HttpPost(url);
	final DefaultHttpClient client = new DefaultHttpClient();
	StringEntity param = new StringEntity("op=" + op + "&sql=" + sql,
		HTTP.UTF_8);
	param.setChunked(false);
	param.setContentType("application/x-www-form-urlencoded");
	mp.setEntity(param);

	Thread thread = new Thread(new Runnable()
	{
	    public void run()
	    {
		try
		{
		    HttpResponse res = client.execute(mp);
		    int status = res.getStatusLine().getStatusCode();
		    if (status != HttpStatus.SC_OK)
		    {
			throw new Exception("网络发生错误!请耐心等待");
		    }

		    String msg = EntityUtils.toString(res.getEntity(), "UTF-8");
		    Bundle bd = new Bundle();
		    bd.putString("msg", msg);

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

	return r;
    }

    Handler hd = new Handler()
    {
	public void handleMessage(android.os.Message msg)
	{
	    switch (msg.what)
	    {
	    case 0:
		Bundle b = msg.getData();
		String result = b.getString("msg");
		r = result;
		break;

	    default:
		break;
	    }

	};
    };

    /**
     * 查询单个字符串
     * 
     * @param sql
     * @return
     * @throws Exception
     */
    public String selectStr(String sql) throws Exception
    {
	return select(1, sql).replace("\r\n", "");
    }

    /**
     * 查询单个数值
     * 
     * @param sql
     * @return
     * @throws Exception
     */
    public int selectInt(String sql) throws Exception
    {
	return Integer.parseInt(selectStr(sql));
    }

    /**
     * 查询字符串数组
     * 
     * @param sql
     * @return
     * @throws Exception
     */
    public String[] selectRowStr(String sql) throws Exception
    {
	String r = select(2, sql).replace("\r\n", "");
	return r.split("\r");
    }

    public List<String[]> selectList(String sql) throws Exception
    {
	String r = select(3, sql).replace("\r\n", "");
	String rs[] = r.split("\t");
	List<String[]> lst = new ArrayList<String[]>();

	for (int i = 0; i < rs.length; i++)
	{
	    String rss[] = rs[i].split("\r");
	    lst.add(rss);
	}
	return lst;
    }

    /**
     * 增、删、改
     * 
     * @param sql
     * @return
     * @throws Exception
     */
    public boolean update(String sql) throws Exception
    {
	String r = select(0, sql).replace("\r\n", "");
	if (r.equals("OK"))
	    return true;
	else
	    return false;
    }
}