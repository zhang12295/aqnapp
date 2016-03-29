package com.njaqn.itravel.aqnapp;

import com.njaqn.itravel.aqnapp.service.BaseService;
import com.njaqn.itravel.aqnapp.service.BaseServiceImpl;
import com.njaqn.itravel.aqnapp.service.bean.BCityBean;
import com.njaqn.itravel.aqnapp.service.bean.BCountryBean;
import com.njaqn.itravel.aqnapp.service.bean.BProvinceBean;

import android.app.Application;

public class AppInfo extends Application
{
    private String city;
    private int cityId = 1;
    private String country;
    private int spotId;
    private int jingDianId;

	public int getJingDianId()
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
	this.city = city;
	BaseService bs = new BaseServiceImpl();
	BCityBean cb = bs.getCityByName(city);
	if (cb != null)
	{
	    this.cityId = cb.getId();
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

    private int countryId;
}
