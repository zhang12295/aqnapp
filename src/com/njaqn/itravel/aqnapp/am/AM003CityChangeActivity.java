package com.njaqn.itravel.aqnapp.am;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.iflytek.cloud.InitListener;
import com.njaqn.itravel.aqnapp.AppInfo;
import com.njaqn.itravel.aqnapp.R;
import com.njaqn.itravel.aqnapp.service.BaseService;
import com.njaqn.itravel.aqnapp.service.BaseServiceImpl;
import com.njaqn.itravel.aqnapp.service.SearchService;
import com.njaqn.itravel.aqnapp.service.SearchServiceImpl;
import com.njaqn.itravel.aqnapp.service.adapter.AM003CityAdapter;
import com.njaqn.itravel.aqnapp.service.bean.BCityBean;
import com.njaqn.itravel.aqnapp.service.bean.BProvinceBean;

import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.util.LruCache;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class AM003CityChangeActivity extends Activity
{

    private ListView lvProvinceAm003;
    private ListView lvCityAm003;
    private BaseService service;
    private AppInfo app;
    private int initCityid;
    private int initProvinceId;
    private ImageButton searchButton;
    private EditText searchEditText;
    private List<HashMap<String, Object>> provinceData;
    private LruCache<Integer, List<HashMap<String, Object>>> cityData;
    private SimpleAdapter satProvince;
    private AM003CityAdapter adapter;

    private void initCityData()
    {
	int maxMemory = (int) Runtime.getRuntime().maxMemory();
	int cachesize = maxMemory / 16;
	cityData = new LruCache<Integer, List<HashMap<String, Object>>>(
		cachesize)
	{
	    @Override
	    protected int sizeOf(Integer key,
		    List<HashMap<String, Object>> value)
	    {
		// 杩斿洖缂撳瓨澶у皬
		return value.size();
	    }
	};
    }

    protected void onCreate(Bundle savedInstanceState)
    {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.am003_am_change_city);
	app = (AppInfo) getApplication(); // 鑾峰彇Application
	initCityid = app.getCityId();
	service = new BaseServiceImpl();
	BProvinceBean bean = service.getProvinceByCityId(initCityid);
	if (isNotNullorError(bean))
	{
	    initProvinceId = bean.getId();
	}
	else
	{
	    initProvinceId = 14;
	}

	lvProvinceAm003 = (ListView) this.findViewById(R.id.lvProvinceAm003);
	lvCityAm003 = (ListView) this.findViewById(R.id.lvCityAm003);
	searchButton = (ImageButton) this.findViewById(R.id.citychange_btn_search);
	searchEditText = (EditText) this.findViewById(R.id.search);
	lvCityAm003.setDividerHeight(0);
	initCityData();
	laodProvinceData();


		lvProvinceAm003 = (ListView) this.findViewById(R.id.lvProvinceAm003);
		lvCityAm003 = (ListView) this.findViewById(R.id.lvCityAm003);
		searchButton = (ImageButton) this.findViewById(R.id.citychange_btn_search);
		searchEditText = (EditText) this.findViewById(R.id.search);
		lvCityAm003.setDividerHeight(0);
		initCityData();
		laodProvinceData();

    }

    private boolean isNotNullorError(Object object)
    {
	if (object == null)
	{
	    return false;
	}
	else if (object instanceof String)
	{
	    String string = (String) object;
	    if (string.equals("") || string.equals("Err"))
	    {
		return false;
	    }
	}
	else
	{
	    return true;
	}
	return false;
    }

    private void laodProvinceData()
    {
	provinceData = service.getAllProvinceByCountryId(2);
	satProvince = new SimpleAdapter(this, provinceData,
		R.drawable.am003_item_province, new String[]
		{ "name" }, new int[]
		{ R.id.provinceName });
	provinceSetSelection(initProvinceId, initCityid);
    }

    public void provinceSetSelection(final int provinceId, int cityid)
    {

	lvProvinceAm003.post(new Runnable()
	{

	    @Override
	    public void run()
	    {
		int position = 0;
		for (int i = 0; i < provinceData.size(); i++)
		{
		    if (Integer.parseInt(provinceData.get(i).get("id")
			    .toString()) == provinceId)
		    {
			position = i;
			break;
		    }
		}
		lvProvinceAm003.requestFocus();
		lvProvinceAm003.setAdapter(satProvince);
		lvProvinceAm003.setSelection(position);
		lvProvinceAm003.setItemChecked(position, true);
	    }
	});
	if (provinceId != -1)
	{
	    showCity(provinceId, cityid);
	}
    }

    private final class ProvinceItemClickListener implements
	    OnItemClickListener
    {
	Intent data = new Intent();

	public void onItemClick(AdapterView<?> parent, View view, int position,
		long id)
	{
	    ListView lv_city = (ListView) parent;
	    Log.i("lv_city", lv_city.hashCode() + "");
	    HashMap<String, Object> data = (HashMap<String, Object>) lv_city
		    .getItemAtPosition(position);
	    showCity(Integer.parseInt(data.get("id").toString()), 0);
	}
    }

    private void showCity(int provinceId, int cityId)
    {
	List<HashMap<String, Object>> data = cityData.get(provinceId);
	if (data == null)
	{
	    data = service.getAllCityByProvinceId(provinceId);
	    cityData.put(provinceId, data);
	}
	if (data != null)
	{
	    Log.i("lvCityAm003", lvCityAm003.hashCode() + "");
	    lvCityAm003.setAdapter(null);
	    Log.i("setAdapter", "succcess");
	    adapter = new AM003CityAdapter(app, AM003CityChangeActivity.this,
		    data, lvCityAm003);
	    if (cityId != 0)
	    {
		setCitySelection(data, cityId);
	    }
	    else
	    {
		lvCityAm003.setAdapter(adapter);
	    }
	}
    }

    private void setCitySelection(final List<HashMap<String, Object>> data,
	    final int cityId)
    {
	lvCityAm003.postDelayed(new Runnable()
	{

	    @Override
	    public void run()
	    {
		int position = 0;
		for (int i = 0; i < data.size(); i++)
		{
		    if (Integer.parseInt(data.get(i).get("id").toString()) == cityId)
		    {
			position = i;
			break;
		    }
		}
		lvCityAm003.requestFocus();
		lvCityAm003.setAdapter(adapter);
		lvCityAm003.setSelection(position);
		lvCityAm003.setItemChecked(position, true);
	    }
	}, 10);

    }

    private final class CityItemClickListener implements OnItemClickListener
    {
	public void onItemClick(AdapterView<?> parent, View view, int position,
		long id)
	{
	    HashMap<String, Object> data = (HashMap<String, Object>) lvCityAm003
		    .getItemAtPosition(position);
	    String name = (String) data.get("name");
	    app.setCity(name);
	    returnOnClick(null);
	}
    }

    public void returnOnClick(View v)
    {
	this.finish();
    }

    private final class SearchClickListener implements OnClickListener
    {

	@Override
	public void onClick(View v)
	{
	    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
	    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
	    String search = searchEditText.getText().toString();
	    if (!search.equals(""))
	    {
		SearchService ss = new SearchServiceImpl();
		HashMap<String, Object> result = ss.searchInCities(search);
		if (result != null)
		{
		    int cityId = (Integer) result.get("cityid");
		    int provinceId = (Integer) result.get("provinceid");
		    provinceSetSelection(provinceId, cityId);
		}
		else
		{
		    int provinceId = ss.searchInProvinces(search);
		    if (provinceId != 0)
		    {
			provinceSetSelection(provinceId, 0);
		    }
		    else
		    {
			Toast.makeText(getApplicationContext(), "很抱歉，无该城市信息",
				Toast.LENGTH_SHORT).show();
		    }
		}
	    }
	}

    }

}
