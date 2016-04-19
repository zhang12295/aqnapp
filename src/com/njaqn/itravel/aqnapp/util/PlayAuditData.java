package com.njaqn.itravel.aqnapp.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.baidu.mapapi.model.LatLng;
import com.njaqn.itravel.aqnapp.service.AmService;
import com.njaqn.itravel.aqnapp.service.AmServiceImpl;
import com.njaqn.itravel.aqnapp.service.bean.JSpotBean;
import com.njaqn.itravel.aqnapp.service.bean.PlayWordBean;

public class PlayAuditData {
	private Map<String,PlayWordBean> playWordMap = null;
	private String locationAddress = null;
	private LatLng locationLatLng = null;
	private String city;
	private String province;
	
	public PlayAuditData()
	{
		playWordMap =  new TreeMap<String,PlayWordBean>(); //�����ϳ��б�
	}
	
	public PlayAuditData(Map<String,PlayWordBean> playWordMap){
		this.playWordMap = playWordMap;
	}

	public String getLocationAddress() {
		return locationAddress;
	}

	public void setLocationInfo(String province,String city, String locationAddress,LatLng locationLatLng) 
	{	
		this.province = province;
		this.locationLatLng = locationLatLng;
		this.locationAddress = locationAddress;
        this.city = city;
		//locationAddress�� key
		if(playWordMap.containsKey(locationAddress)) return;
		
		String word = "���������ڵ�λ���ǣ�"+locationAddress.replace(province, "").replace(city, "");
		
		//���ŵ�ǰλ��
		PlayWordBean pwb = new PlayWordBean();
		pwb.setWord(word);
		pwb.setPlayCount(2);
		pwb.setKey("s1");
		
		playWordMap.put(pwb.getKey(),pwb);
		
		//������Χ����
		AmService am = new AmServiceImpl();
		List<JSpotBean> lst = am.getAroundSpotByCurrLocation(locationLatLng.longitude, locationLatLng.latitude, 5);
		if(lst == null) return;
		
		for(JSpotBean o : lst)
		{
			pwb = new PlayWordBean();
			
			pwb.setWord(o.getIntro());
			pwb.setPlayCount(1);
			pwb.setKey(o.getName());
		
			playWordMap.put("s2"+o.getName(),pwb);
		}
	}

	public LatLng getLocationLatLng() {
		return locationLatLng;
	}

	public Map<String,PlayWordBean> getPlayWordMap() {
		return playWordMap;
	}
}
