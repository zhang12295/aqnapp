package com.njaqn.itravel.aqnapp.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.util.Log;

import com.njaqn.itravel.aqnapp.service.bean.AQNPointer;
import com.njaqn.itravel.aqnapp.service.bean.BCityBean;
import com.njaqn.itravel.aqnapp.service.bean.BCountryBean;
import com.njaqn.itravel.aqnapp.service.bean.BProvinceBean;
import com.njaqn.itravel.aqnapp.service.bean.JSpotBean;
import com.njaqn.itravel.aqnapp.service.bean.JingDianBean;
import com.njaqn.itravel.aqnapp.service.bean.PlayFileBean;
import com.njaqn.itravel.aqnapp.util.AQNAppConst;
import com.njaqn.itravel.aqnapp.util.AppConf;
import com.njaqn.itravel.aqnapp.util.HttpDb;
import com.njaqn.itravel.aqnapp.util.ImageUtil;
import com.njaqn.itravel.aqnapp.util.UrlHttp;

public class BaseServiceImpl implements BaseService
{

    @Override
    public BCountryBean getCountryByProvinceId(int provinceId)
    {
	BCountryBean cb = null;
	try
	{
	    UrlHttp http = new UrlHttp();
	    String r = http.postRequest("province.id", "" + provinceId,
		    AQNAppConst.PAGEID_AM_BASE, AQNAppConst.OP_AM_BASE_8);
	    if (r.equals("null") || r.equals("Err"))
		return null;

	    JSONObject json = new JSONObject(r);
	    cb = new BCountryBean();
	    cb.setId(json.getInt("id"));
	    cb.setName(json.getString("name"));
	    cb.setPinYin(json.getString("pinyin"));

	    return cb;
	}
	catch (Exception ex)
	{
	    System.out.println(ex.getMessage());
	}

	return null;
    }

    @Override
    public BProvinceBean getProvinceByCityId(int cityId)
    {
	try
	{
	    UrlHttp http = new UrlHttp();
	    String r = http.postRequest("city.id", "" + cityId,
		    AQNAppConst.PAGEID_AM_BASE, AQNAppConst.OP_AM_BASE_6);
	    if (r.equals("null") || r.equals("Err"))
		return null;

	    JSONObject json = new JSONObject(r);
	    BProvinceBean pb = new BProvinceBean();
	    pb.setId(json.getInt("id"));
	    pb.setName(json.getString("name"));
	    pb.setPinYin(json.getString("pinyin"));

	    return pb;
	}
	catch (Exception ex)
	{
	    System.out.println(ex.getMessage());
	}

	return null;
    }

    @Override
    public BCityBean getCityByName(String cityName)
    {
	try
	{
	    UrlHttp http = new UrlHttp();
	    String r = http.postRequest("city.name", "" + cityName,
		    AQNAppConst.PAGEID_AM_BASE, AQNAppConst.OP_AM_BASE_7);
	    if (r.equals("null") || r.equals("Err"))
		return null;

	    BCityBean cb = new BCityBean();
	    cb.setId(Integer.parseInt(r));
	    cb.setName(cityName);

	    return cb;
	}
	catch (Exception ex)
	{
	    System.out.println(ex.getMessage());
	}

	return null;
    }

    public List<HashMap<String, Object>> getAllProvinceByCountryId(int CountryId)
    {
	try
	{
	    UrlHttp http = new UrlHttp();
	    String r = http.postRequest("province.countryId", "" + CountryId,
		    AQNAppConst.PAGEID_AM_BASE, AQNAppConst.OP_AM_BASE_4);
	    if (r.equals("Err"))
		return null;

	    JSONArray json = new JSONArray(r);

	    List<HashMap<String, Object>> lst = new ArrayList<HashMap<String, Object>>();
	    for (int i = 0; i < json.length(); i++)
	    {
		JSONObject obj = json.getJSONObject(i);
		HashMap<String, Object> provinceData = new HashMap<String, Object>();
		provinceData.put("id", obj.getInt("id"));
		provinceData.put("name", obj.getString("name"));
		lst.add(provinceData);
	    }

	    return lst;
	}
	catch (Exception ex)
	{
	    System.out.println(ex.getMessage());
	}

	return null;
    }

    public List<HashMap<String, Object>> getAllCityByProvinceId(int provinceId)
    {
	try
	{
	    UrlHttp http = new UrlHttp();
	    String r = http.postRequestForSql("select id,name,cityImage,keywords,flag from B_City where ProvinceID =" +
	    		provinceId, AQNAppConst.DB_MANY_MANY);
	    if (r.equals("Err"))
		return null;

	    JSONArray json = new JSONArray(r);

	    List<HashMap<String, Object>> lst = new ArrayList<HashMap<String, Object>>();
	    for (int i = 0; i < json.length(); i++)
	    {
		JSONObject obj = json.getJSONObject(i);
		HashMap<String, Object> cityData = new HashMap<String, Object>();
		cityData.put("id", obj.getInt("id"));
		cityData.put("name", obj.getString("name"));
		cityData.put("cityImage", obj.getString("cityImage"));
		cityData.put("keywords", obj.getString("keywords"));
		cityData.put("flag", obj.getInt("flag"));
		lst.add(cityData);
	    }

	    return lst;
	}
	catch (Exception ex)
	{
	    System.out.println(ex.getMessage());
	}

	return null;
    }

    @Override
    public Bitmap getImageByUrl(String url)
    {
	return new ImageUtil().getBitMap(url);
    }
}
