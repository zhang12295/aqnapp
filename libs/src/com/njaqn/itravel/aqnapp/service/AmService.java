/**
 *    
 */
package com.njaqn.itravel.aqnapp.service;

import java.util.List;

import com.njaqn.itravel.aqnapp.service.bean.AQNPointer;

public interface AmService {
	/**
	 * 
	 * @param Longitude
	 * @param Latitude
	 * @param maxSpotNum
	 * @return
	 */
	public List getAppConfig();
	public List getAroundSpotByCurrLocation(AQNPointer pointer,int maxSpotNum);
	public List getAroundSpotByCurrLocation(double Longitude,double Latitude,int maxSpotNum);
}
