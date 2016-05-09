package com.njaqn.itravel.aqnapp.service;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.baidu.mapapi.model.LatLng;
import com.njaqn.itravel.aqnapp.service.bean.JingDianBean;
import com.njaqn.itravel.aqnapp.util.AQNAppConst;
import com.njaqn.itravel.aqnapp.util.UrlHttp;

public class MapServiceImpl implements MapService {

	@Override
	public int getSpotCountInCity(int cityId) {
		try {
			UrlHttp http = new UrlHttp();

			String r = http
					.postRequestForSql(
							"select COUNT(*) from J_Spot where CountyID in (select ID from B_County where CityID = "
									+ cityId + ")", AQNAppConst.DB_ONE_ONE);
			if (!r.equals("Err"))
				return Integer.parseInt(r);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public List<String> getSpotInCity(int cityId, int pageNo) {
		try {
			UrlHttp http = new UrlHttp();

			String r = http
					.postRequestForSql(
							"select name from J_Spot where CountyID in (select ID from B_County where CityID = "
									+ cityId + ") order by Hot desc",
							AQNAppConst.DB_MANY_MANY);
			if (!r.equals("Err")) {
				JSONArray json = new JSONArray(r);
				List<String> lst = new ArrayList<String>();
				for (int i = 0; i < json.length(); i++) {
					JSONObject obj = json.getJSONObject(i);
					String name = obj.getString("name");
					lst.add(name);
				}
				return lst;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int getSpotIdByName(String name) {
		try {
			UrlHttp http = new UrlHttp();

			String r = http.postRequestForSql(
					"select id from J_Spot where name = '" + name + "'",
					AQNAppConst.DB_ONE_ONE);
			if (!r.equals("Err"))
				return Integer.parseInt(r);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public JingDianBean getNearestJingDian(LatLng latLng) {
		
		try {
			UrlHttp http = new UrlHttp();
			String r = http
					.postRequestForSql(
							"select top 1 name, ID, longitude, latitude, dbo.fnGetDistance("
									+ latLng.longitude
									+ ","
									+ latLng.latitude
									+ ",longitude,latitude) distance from J_JingDian where Longitude is not null order by distance",
							AQNAppConst.DB_ONE_MANY);
			if (r.equals("Err")) {
				return null;
			}
			JSONObject jsonObject = new JSONObject(r);
			JingDianBean jBean = new JingDianBean();
			jBean.setId(jsonObject.getInt("ID"));
			jBean.setName(jsonObject.getString("name"));
			jBean.setLongitude(jsonObject.getDouble("longitude"));
			jBean.setLatitude(jsonObject.getDouble("latitude"));
			return jBean;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
