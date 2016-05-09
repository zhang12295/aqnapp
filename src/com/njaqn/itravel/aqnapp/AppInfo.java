package com.njaqn.itravel.aqnapp;

import com.baidu.location.BDLocation;
import com.baidu.location.service.LocationService;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.MyLocationData;
import com.iflytek.cloud.SpeechUtility;
import com.njaqn.itravel.aqnapp.service.BaseService;
import com.njaqn.itravel.aqnapp.service.BaseServiceImpl;
import com.njaqn.itravel.aqnapp.service.bean.BCityBean;
import com.njaqn.itravel.aqnapp.service.bean.BCountryBean;
import com.njaqn.itravel.aqnapp.service.bean.BProvinceBean;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Vibrator;

public class AppInfo extends Application {
	private String city;
	private String localCity;
	private int localCityId;

	public int getLocalCityId() {
		return localCityId;
	}

	public void setLocalCityId(int localCityId) {
		this.localCityId = localCityId;
	}

	public String getLocalCity() {
		return localCity;
	}

	public void setLocalCity(String localCity) {
		this.localCity = localCity;
		BaseService bs = new BaseServiceImpl();
		BCityBean cb = bs.getCityByName(localCity);
		setLocalCityId(cb.getId());
	}

	private int cityId = 1;
	private String country;
	private int spotId;
	private int jingDianId;
	private int countryId;
	private int provinceId;

	public int getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(int provinceId) {
		this.provinceId = provinceId;
	}

	private SharedPreferences sp;
	private Editor editor;
	private double longitude;
	private double latitude;

	public BDLocation getLocation() {
		return location;
	}

	public void setLocation(BDLocation location) {
		this.location = location;
	}

	private BDLocation location;

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public int getJingDianId() {
		return jingDianId;
	}

	public void setJingDianId(int jingDianId) {
		this.jingDianId = jingDianId;
	}

	public int getSpotId() {
		return spotId;
	}

	public void setSpotId(int spotId) {
		this.spotId = spotId;
	}

	public String getCityImageUrl() {
		return cityImageUrl;
	}

	public void setCityImageUrl(String cityImageUrl) {
		this.cityImageUrl = cityImageUrl;
	}

	private boolean isLogin;
	private String cityImageUrl;

	public boolean isLogin() {
		return isLogin;
	}

	public void setLogin(boolean isLogin) {
		this.isLogin = isLogin;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		sp = getSharedPreferences("appInfo", Activity.MODE_PRIVATE);
		editor = sp.edit();
		BaseService bs = new BaseServiceImpl();
		BCityBean cb;
		if (city != null) {
			this.city = city;
			editor.putString("city", city);
			editor.commit();
			cb = bs.getCityByName(city);
		} else {
			this.city = sp.getString("city", "南京");
			cb = bs.getCityByName(this.city);
		}

		if (cb != null) {
			this.cityId = cb.getId();
			editor.putInt("cityId", cb.getId());

			BProvinceBean pb = bs.getProvinceByCityId(cityId);
			BCountryBean countryBean = bs.getCountryByProvinceId(pb.getId());
			this.countryId = countryBean.getId();
			editor.putInt("countryId", countryBean.getId());
			this.country = countryBean.getName();
			editor.putString("countryName", countryBean.getName());
			editor.commit();
		} else {
			this.cityId = sp.getInt("cityId", 1);
			BProvinceBean pb = bs.getProvinceByCityId(cityId);
			if (pb != null) {
				BCountryBean countryBean = bs
						.getCountryByProvinceId(pb.getId());
				this.countryId = countryBean.getId();
				this.country = countryBean.getName();
			} else {
				this.countryId = 1;
				this.country = "中国";
			}

		}
	}

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public int getCountryId() {
		return countryId;
	}

	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}

	public LocationService locationService;

	@Override
	public void onCreate() {
		super.onCreate();
		SpeechUtility.createUtility(this, "appid=55379711");
		locationService = new LocationService(getApplicationContext());
		SDKInitializer.initialize(getApplicationContext());

	}

}
