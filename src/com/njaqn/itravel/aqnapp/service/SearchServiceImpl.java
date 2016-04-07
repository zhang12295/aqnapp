package com.njaqn.itravel.aqnapp.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

import com.njaqn.itravel.aqnapp.util.AQNAppConst;
import com.njaqn.itravel.aqnapp.util.UrlHttp;

public class SearchServiceImpl implements SearchService {

	@Override
	public int searchInProvinces(String search) {
		try {
			UrlHttp http = new UrlHttp();
			String r = http.postRequestForSql(
					"select top 1 id from B_Province where Name = '" + search
							+ "'", AQNAppConst.DB_ONE_ONE);
			if (!r.equals("Err"))
				return Integer.parseInt(r);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public HashMap<String, Object> searchInCities(String search) {
		try {
			UrlHttp http = new UrlHttp();
			String r = http.postRequestForSql(
					"select top 1 id,ProvinceID from B_City where Name = '"
							+ search + "'", AQNAppConst.DB_MANY_MANY);
			if (r.equals("Err"))
				return null;
			JSONArray lst = new JSONArray(r);
			JSONObject obj = lst.getJSONObject(0);
			HashMap<String, Object> data = new HashMap<String, Object>();
			data.put("cityid", obj.getInt("id"));
			data.put("provinceid", obj.getInt("ProvinceID"));
			return data;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<String> searchInSpotsForComplete(String search) {
		try {
			UrlHttp http = new UrlHttp();
			String r = http.postRequestForSql(
					"select top 5 name from J_Spot where Name like '%25" + search
							+ "%25'", AQNAppConst.DB_MANY_MANY);
			if (r.equals("Err"))
				return null;
			JSONArray json = new JSONArray(r);
			List<String> lst = new ArrayList<String>();
			for (int i = 0; i < json.length(); i++) {
				JSONObject obj = json.getJSONObject(i);
				String name = obj.getString("name");
				lst.add(name);
			}
			return lst;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<HashMap<String, Object>> searchInSpotsForResults(String search) {
		try {
			UrlHttp http = new UrlHttp();
			String r = http.postRequestForSql(
					"select id,name,intro,titleImage from J_Spot where Name like '" + search
					+ "%25'", AQNAppConst.DB_MANY_MANY);
			if (r.equals("Err"))
				return null;

			JSONArray json = new JSONArray(r);
			List<HashMap<String, Object>> lst = new ArrayList<HashMap<String, Object>>();
			for (int i = 0; i < json.length(); i++) {
				JSONObject obj = json.getJSONObject(i);
				HashMap<String, Object> spotData = new HashMap<String, Object>();
				spotData.put("id", obj.getInt("id"));
				spotData.put("name", obj.getString("name"));
				spotData.put("intro", obj.get("intro"));
				spotData.put("titleImage", obj.get("titleImage"));
				lst.add(spotData);
			}
			return lst;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public List<HashMap<String, Object>> getNearbySpot(double Longitude,
			double Latitude, int maxSpotNum) {
		try
		{
		    UrlHttp http = new UrlHttp();
		    String r = http.postRequestForSql("select top "+maxSpotNum+" id,jc from J_Spot where Longitude is not null order by dbo.fnGetDistance("+Longitude
					+" , "+Latitude+",longitude,latitude)", AQNAppConst.DB_MANY_MANY);
		    if (r.equals("Err"))
			return null;

		    JSONArray json = new JSONArray(r);

		    List<HashMap<String, Object>> lst = new ArrayList<HashMap<String, Object>>();
		    for (int i = 0; i < json.length(); i++)
		    {
			JSONObject obj = json.getJSONObject(i);
			HashMap<String, Object> spotData = new HashMap<String, Object>();
			spotData.put("id", obj.getInt("id"));
			spotData.put("name", obj.getString("jc"));
			lst.add(spotData);
		    }

		    return lst;
		}
		catch (Exception ex)
		{
		    Log.e("Err", ex.getMessage());
		}

		return null;
	}

}
