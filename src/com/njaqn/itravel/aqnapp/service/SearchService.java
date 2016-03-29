package com.njaqn.itravel.aqnapp.service;

import java.util.HashMap;
import java.util.List;

public interface SearchService {
	public int searchInProvinces(String search);
	public HashMap<String, Object> searchInCities(String search);
}
