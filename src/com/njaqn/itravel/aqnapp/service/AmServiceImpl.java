package com.njaqn.itravel.aqnapp.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

import com.njaqn.itravel.aqnapp.service.bean.JSpotBean;
import com.njaqn.itravel.aqnapp.service.bean.JingDianBean;
import com.njaqn.itravel.aqnapp.service.bean.PlayFileBean;
import com.njaqn.itravel.aqnapp.util.AQNAppConst;
import com.njaqn.itravel.aqnapp.util.UrlHttp;

public class AmServiceImpl implements AmService
{

    /**
     * ��ݵ�ǰ��λ�û����Χ�ľ���
     */
    public List<JSpotBean> fillSpotList(String jsonData) throws Exception
    {
	if (jsonData.equals("Err"))
	    return null;

	JSONArray json = new JSONArray(jsonData);

	List<JSpotBean> lst = new ArrayList<JSpotBean>();
	for (int i = 0; i < json.length(); i++)
	{
	    JSONObject obj = json.getJSONObject(i);
	    JSpotBean spot = new JSpotBean();

	    spot.setName(obj.getString("Name"));
	    spot.setId(obj.getInt("ID"));
	    spot.setLongitude(obj.getString("Longitude"));
	    spot.setLatitude(obj.getString("latitude"));
	    spot.setIntro(obj.getString("intro"));
	    lst.add(spot);
	}

	return lst;
    }

    public List<JingDianBean> fillJingDianList(String jsonData)
	    throws Exception
    {
	if (jsonData.equals("Err"))
	    return null;

	JSONArray json = new JSONArray(jsonData);

	List<JingDianBean> lst = new ArrayList<JingDianBean>();
	for (int i = 0; i < json.length(); i++)
	{
	    JSONObject obj = json.getJSONObject(i);
	    JingDianBean jd = new JingDianBean();

	    jd.setName(obj.getString("name"));
	    jd.setId(obj.getInt("id"));
	    jd.setIntro(obj.getString("intro"));
	    jd.setLongitude(obj.getDouble("longitude"));
	    jd.setLatitude(obj.getDouble("latitude"));
	    lst.add(jd);
	}

	return lst;
    }

    public List<JSpotBean> getAroundSpotByCurrLocation(double Longitude,
	    double Latitude, int maxSpotNum)
    {
	try
	{
	    UrlHttp http = new UrlHttp();
	    return fillSpotList(http.postRequestForSql("select top "
		    + maxSpotNum + " name,intro from J_Spot", 3));
	}
	catch (Exception ex)
	{
	    Log.e("Err", ex.getMessage());
	}

	return null;
    }

    /**
     * �������������Ϣ
     */
    public Map getAppConfig()
    {
	try
	{
	    UrlHttp http = new UrlHttp();
	    String r = http.postRequestForSql(
		    "select paramClass, paramValue from b_appConf", 3);
	    // String r =
	    // http.postRequest(AQNAppConst.PAGEID_AM_APPCONF,AppConf.GET_ALL_OP);
	    if (r.equals("Err"))
		return null;

	    JSONArray json = new JSONArray(r);

	    Map<String, String> map = new java.util.HashMap<String, String>();
	    for (int i = 0; i < json.length(); i++)
	    {
		JSONObject obj = json.getJSONObject(i);
		map.put(obj.getString("paramClass"),
			obj.getString("paramValue"));
	    }
	    return map;
	}
	catch (Exception ex)
	{
	    System.out.println(ex.getMessage());
	}

	return null;
    }

    public List<JSpotBean> getAllSpotByCityId(int cityId)
    {
	try
	{
	    UrlHttp http = new UrlHttp();
	    String r = http.postRequest("city.id", "" + cityId,
		    AQNAppConst.PAGEID_AM_SPOT, AQNAppConst.OP_AM_SPOT_5);
	    if (r.equals("Err"))
		return null;

	    return fillSpotList(r);
	}
	catch (Exception ex)
	{
	    Log.e("Err", ex.getMessage());
	}
	return null;
    }

    public List<JSpotBean> getCityHotSpotByCityId(int cityId, int maxSpotNum)
    {
	try
	{
	    UrlHttp http = new UrlHttp();

	    Map<String, String> params = new HashMap<String, String>();
	    params.put("city.id", "" + cityId);
	    params.put("maxSpotNum", "" + maxSpotNum);

	    String r = http.postRequest(params, AQNAppConst.PAGEID_AM_SPOT,
		    AQNAppConst.OP_AM_SPOT_11);
	    if (r.equals("Err"))
		return null;

	    return fillSpotList(r);
	}
	catch (Exception ex)
	{
	    Log.e("Err", ex.getMessage());
	}
	return null;
    }

    @Override
    public List<HashMap<String, Object>> getHotSpot(int maxSpotNum)
    {
	try
	{
	    UrlHttp http = new UrlHttp();
	    String r = http.postRequestForSql("select top "+maxSpotNum+" id,jc from J_Spot order by Hot desc", AQNAppConst.DB_MANY_MANY);
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

    public List<JSpotBean> getHotSpotByProviceId(int provinceId, int maxSpotNum)
    {
	try
	{
	    UrlHttp http = new UrlHttp();
	    Map<String, String> params = new HashMap<String, String>();
	    params.put("province.id", "" + provinceId);
	    params.put("maxSpotNum", "" + maxSpotNum);

	    return fillSpotList(http.postRequest(params,
		    AQNAppConst.PAGEID_AM_SPOT, AQNAppConst.OP_AM_SPOT_10));
	}
	catch (Exception ex)
	{
	    Log.e("Err", ex.getMessage());
	}
	return null;
    }

    public List<PlayFileBean> getAllPlayFile()
    {
	return null;
    }

    public boolean delPlayFile(String fileName)
    {
	return false;
    }

    @Override
    public JSpotBean getSpotById(int spotId)
    {

	try
	{
	    UrlHttp http = new UrlHttp();
	    String r = http.postRequest("spot.id", "" + spotId,
		    AQNAppConst.PAGEID_AM_SPOT, AQNAppConst.OP_AM_SPOT_6);
	    if (r.equals("Err"))
		return null;

	    JSONObject obj = new JSONObject(r);
	    JSpotBean spot = new JSpotBean();

	    spot.setId(obj.getInt("id"));
	    spot.setName(obj.getString("name"));

	    return spot;
	}
	catch (Exception ex)
	{
	    Log.e("Err", ex.getMessage());
	}

	return null;
    }

    @Override
    public JingDianBean getJingDianById(int jingdianId)
    {

	try
	{
	    UrlHttp http = new UrlHttp();
	    String r = http.postRequest("jingDian.id", "" + jingdianId,
		    AQNAppConst.PAGEID_AM_SPOT, AQNAppConst.OP_AM_SPOT_8);
	    if (r.equals("Err"))
		return null;

	    JSONObject obj = new JSONObject(r);
	    JingDianBean jd = new JingDianBean();

	    jd.setId(obj.getInt("id"));
	    jd.setName(obj.getString("name"));
	    jd.setSpotId(obj.getInt("spotId"));
	    jd.setIntro("intro");
	    return jd;
	}
	catch (Exception ex)
	{
	    Log.e("Err", ex.getMessage());
	}

	return null;

    }

    @Override
    public List<JingDianBean> getAllJingDianBySpotId(int spotId)
    {

	try
	{
	    UrlHttp http = new UrlHttp();
	    String r = http.postRequest("spot.id", "" + spotId,
		    AQNAppConst.PAGEID_AM_SPOT, AQNAppConst.OP_AM_SPOT_9);
	    if (r.equals("Err"))
		return null;

	    return fillJingDianList(r);
	}
	catch (Exception ex)
	{
	    Log.e("Err", ex.getMessage());
	}

	return null;
    }

    @Override
    public List<JSpotBean> getSpotLocationByCityId(int cityId)
    {
	try
	{
	    UrlHttp http = new UrlHttp();
	    String r = http.postRequestForSql("select ID , Name, Longitude, latitude, intro from J_Spot where countyID in (select ID from B_County where CityID = "+cityId+")",3);
	    if (r.equals("Err"))
		return null;

	    return fillSpotList(r);
	}
	catch (Exception ex)
	{
	    Log.e("Err", ex.getMessage());
	}

	return null;
    }

    @Override
    public String getSpotIntroById(int id)
    {
	try
	{
	    UrlHttp http = new UrlHttp();
	    String r = http.postRequestForSql("select Intro from J_Spot where ID ="+id+"",1);
	    if (r.equals("Err"))
		return null;

	    return r;
	}
	catch (Exception ex)
	{
	    Log.e("Err", ex.getMessage());
	}

	return null;
    }

    @Override
    public JSONObject judgeLocation(double longitude, double latitude)
    {
	try
	{
	    UrlHttp http = new UrlHttp();
	    String r = http.postRequestForSql("select top 1 name, ID, dbo.fnGetDistance("+longitude+","+latitude+",longitude,latitude) distance from J_Spot where Longitude is not null order by distance",3);
	    if (r.equals("Err"))
	    {
		return null;
	    }
	    else
	    {
		JSONArray array = new JSONArray(r);
		JSONObject object = array.getJSONObject(0);
		return object;
	    }
	    
	}
	catch (Exception ex)
	{
	    Log.e("Err", ex.getMessage());
	}

	return null;
    }


}
