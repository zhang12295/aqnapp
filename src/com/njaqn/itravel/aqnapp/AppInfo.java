package com.njaqn.itravel.aqnapp;

import com.njaqn.itravel.aqnapp.service.BaseService;
import com.njaqn.itravel.aqnapp.service.BaseServiceImpl;
import com.njaqn.itravel.aqnapp.service.bean.BCityBean;
import com.njaqn.itravel.aqnapp.service.bean.BCountryBean;
import com.njaqn.itravel.aqnapp.service.bean.BProvinceBean;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class AppInfo extends Application
{
    private String city;
    private int cityId = 1;
    private String country;
    private int spotId;
    private int jingDianId;
<<<<<<< HEAD
    private int countryId;
    private SharedPreferences sp ;
    private Editor editor ;
    public int getJingDianId()
=======

	public int getJingDianId()
>>>>>>> e89d573c2809a3eb48f56b2d06ffd24c53e158cf
    {
	return jingDianId;
    }

    public void setJingDianId(int jingDianId)
    {
	this.jingDianId = jingDianId;
    }

    public int getSpotId()
    {
	return spotId;
    }

    public void setSpotId(int spotId)
    {
	this.spotId = spotId;
    }

    public String getCityImageUrl()
    {
	return cityImageUrl;
    }

    public void setCityImageUrl(String cityImageUrl)
    {
	this.cityImageUrl = cityImageUrl;
    }

    private boolean isLogin;
    private String cityImageUrl;

    public boolean isLogin()
    {
	return isLogin;
    }

    public void setLogin(boolean isLogin)
    {
	this.isLogin = isLogin;
    }

    public String getCity()
    {
	return city;
    }

    public void setCity(String city)
    {
	sp = getSharedPreferences("appInfo", Activity.MODE_PRIVATE);
	editor = sp.edit();
	BaseService bs = new BaseServiceImpl();
	BCityBean cb;
	if(city != null)
	{
	    this.city = city;
	    editor.putString("city", city);
	    editor.commit();
	    cb = bs.getCityByName(city);
	}
	else
	{
	    this.city = sp.getString("city", "ÄÏ¾©");
	    cb = bs.getCityByName(this.city);
	}
	
	if (cb != null)
	{
	    this.cityId = cb.getId();
	    editor.putInt("cityId", cb.getId());
	   
	    BProvinceBean pb = bs.getProvinceByCityId(cityId);
	    BCountryBean countryBean = bs.getCountryByProvinceId(pb.getId());
	    this.countryId = countryBean.getId();
	    editor.putInt("countryId", countryBean.getId());
	    this.country = countryBean.getName();
	    editor.putString("countryName", countryBean.getName());
	    editor.commit();
	}
	else
	{
	    this.cityId = sp.getInt("cityId", 1);
	    BProvinceBean pb = bs.getProvinceByCityId(cityId);
	    BCountryBean countryBean = bs.getCountryByProvinceId(pb.getId());
	    this.countryId = countryBean.getId();
	    this.country = countryBean.getName();
	    
	}
    }

    public int getCityId()
    {
	return cityId;
    }

    public void setCityId(int cityId)
    {
	this.cityId = cityId;
    }

    public String getCountry()
    {
	return country;
    }

    public void setCountry(String country)
    {
	this.country = country;
    }

    public int getCountryId()
    {
	return countryId;
    }

    public void setCountryId(int countryId)
    {
	this.countryId = countryId;
    }

}
