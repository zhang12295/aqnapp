/**
 *    
 */
package com.njaqn.itravel.aqnapp.service;

import java.util.HashMap;
import java.util.List;
import android.graphics.Bitmap;

import com.njaqn.itravel.aqnapp.service.bean.BCityBean;
import com.njaqn.itravel.aqnapp.service.bean.BCountryBean;
import com.njaqn.itravel.aqnapp.service.bean.BProvinceBean;

public interface BaseService
{

    public BCountryBean getCountryByProvinceId(int ProvinceId);

    public BProvinceBean getProvinceByCityId(int cityId);

    public BCityBean getCityByName(String cityName);

    public List<HashMap<String, Object>> getAllProvinceByCountryId(int CountryId);

    public List<HashMap<String, Object>> getAllCityByProvinceId(int provinceId);

    public Bitmap getImageByUrl(String url);
}
