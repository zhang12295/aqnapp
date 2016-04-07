package com.njaqn.itravel.aqnapp.service;

import java.util.HashMap;
import java.util.List;
import android.R.string;

public interface SearchService {
	public int searchInProvinces(String search);

	public HashMap<String, Object> searchInCities(String search);

	public List<String> searchInSpotsForComplete(String search);

	public List<HashMap<String, Object>> searchInSpotsForResults(String search);

	public List<HashMap<String, Object>> getNearbySpot(double Longitude,
			double Latitude, int maxSpotNum);
}
