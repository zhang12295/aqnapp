package com.njaqn.itravel.aqnapp.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.njaqn.itravel.aqnapp.util.AQNAppConst;
import com.njaqn.itravel.aqnapp.util.UrlHttp;

public class SearchServiceImpl implements SearchService {

	@Override
	public int searchInProvinces(String search) {
		try {
			UrlHttp http = new UrlHttp();
			String r = http.postRequestForSql(
					"select top 1 id from B_Province where Name = '"
							+ search + "'", AQNAppConst.DB_ONE_ONE);
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

}
