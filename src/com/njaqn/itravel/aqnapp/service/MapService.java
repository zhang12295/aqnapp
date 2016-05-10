package com.njaqn.itravel.aqnapp.service;

import java.util.List;

import com.baidu.mapapi.model.LatLng;
import com.njaqn.itravel.aqnapp.service.bean.JingDianBean;

public interface MapService {

	public int getSpotCountInCity(int cityId);

	public List<String> getSpotInCity(int cityId, int pageNo);

	public int getSpotIdByName(String name);

	public JingDianBean getNearestJingDian(LatLng latLng);
}
