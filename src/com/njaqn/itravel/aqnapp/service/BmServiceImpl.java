package com.njaqn.itravel.aqnapp.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

import com.njaqn.itravel.aqnapp.service.bean.AQNPointer;
import com.njaqn.itravel.aqnapp.service.bean.BCityBean;
import com.njaqn.itravel.aqnapp.service.bean.BProvinceBean;
import com.njaqn.itravel.aqnapp.service.bean.JSpotBean;
import com.njaqn.itravel.aqnapp.service.bean.PlayFileBean;
import com.njaqn.itravel.aqnapp.util.AQNAppConst;
import com.njaqn.itravel.aqnapp.util.AppConf;
import com.njaqn.itravel.aqnapp.util.HttpDb;
import com.njaqn.itravel.aqnapp.util.UrlHttp;

public class BmServiceImpl implements BmService {

	// op = 1 add,  op=4 login, op=5获得验证码
	public boolean login(String user,String password)
	{
		try
		{
			UrlHttp http = new UrlHttp();
			Map<String,String> params = new HashMap<String,String>();
			params.put("acct.userName", user);
			params.put("acct.password", password);
			String r = http.postRequest(params, AQNAppConst.PAGEID_BM_LOGIN, 4);
			if(r.equals("Err"))
				return false;

			if(r.equals("1"))
				return true;
			else
				return false;
		}
		catch(Exception ex)
		{			
			System.out.println(ex.getMessage());
		}
		return false;
	}
	
	public String getValidateCodeFromServer(String mobileNo)
	{
		try
		{
			UrlHttp http = new UrlHttp();
			String r = http.postRequest("acct.userName",mobileNo,AQNAppConst.PAGEID_BM_LOGIN, 5);
			if(r.equals("Err"))
				return "";
			
			return r;
		}
		catch(Exception ex)
		{			
			System.out.println(ex.getMessage());
		}
		
		return "";
	}
	
	public String getValidateCodeFromSMS()
	{
		return "";
	}
	
	/*
	 * 成功返回OK字符串，失败返回失败Err
	 */
	public String postZhuceInfo(String mobileNo,String passwd)
	{
		try
		{
			UrlHttp http = new UrlHttp();
			Map<String,String> params = new HashMap<String,String>();
			params.put("acct.userName", mobileNo);
			params.put("acct.password", passwd);
			return http.postRequest(params, AQNAppConst.PAGEID_BM_LOGIN, 1);
		}
		catch(Exception ex)
		{			
			System.out.println(ex.getMessage());
			return ex.getMessage();
		}
	}
}
