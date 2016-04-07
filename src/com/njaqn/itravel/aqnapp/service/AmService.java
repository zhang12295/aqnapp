/**
 *    
 */
package com.njaqn.itravel.aqnapp.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.njaqn.itravel.aqnapp.service.bean.JSpotBean;
import com.njaqn.itravel.aqnapp.service.bean.JingDianBean;
import com.njaqn.itravel.aqnapp.service.bean.PlayFileBean;

public interface AmService
{

    public Map getAppConfig();

    public List<JSpotBean> getAroundSpotByCurrLocation(double Longitude,
	    double Latitude, int maxSpotNum);

    public List<JSpotBean> getAllSpotByCityId(int cityId);

    public List<JSpotBean> getCityHotSpotByCityId(int cityId, int maxSpotNum);

    public List<HashMap<String, Object>> getHotSpot(int maxSpotNum);

    public List<JSpotBean> getHotSpotByProviceId(int proviceId, int maxSpotNum);

    public JSpotBean getSpotById(int spotId);

    public JingDianBean getJingDianById(int jingdianId);

    public List<JingDianBean> getAllJingDianBySpotId(int spotId);

    public List<PlayFileBean> getAllPlayFile();

    public boolean delPlayFile(String fileName);

    public List<JSpotBean> getSpotLocationByCityId(int cityId);

    public String getSpotIntroById(int id);

    public JSONObject judgeLocation(double d, double e);
}
