package com.njaqn.itravel.aqnapp.am;

import java.util.HashMap;
import java.util.List;

import com.njaqn.itravel.aqnapp.AppInfo;
import com.njaqn.itravel.aqnapp.R;
import com.njaqn.itravel.aqnapp.service.BaseService;
import com.njaqn.itravel.aqnapp.service.BaseServiceImpl;
import com.njaqn.itravel.aqnapp.service.adapter.AM003CityAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class AM003CityChangeActivity extends Activity
{

    private ListView lvProvinceAm003;
    private ListView lvCityAm003;
    private BaseService service;
    private AppInfo app;

    protected void onCreate(Bundle savedInstanceState)
    {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.am003_am_change_city);
	app = (AppInfo) getApplication(); // 获取应用程序

	lvProvinceAm003 = (ListView) this.findViewById(R.id.lvProvinceAm003);
	lvCityAm003 = (ListView) this.findViewById(R.id.lvCityAm003);
	lvCityAm003.setDividerHeight(0);
	laodProvinceData();

	lvProvinceAm003.setOnItemClickListener(new ProvinceItemClickListener());
	lvCityAm003.setOnItemClickListener(new CityItemClickListener());

    }

    private void laodProvinceData()
    {
	service = new BaseServiceImpl();
	List<HashMap<String, Object>> data = service
		.getAllProvinceByCountryId(2);
	SimpleAdapter satProvince = new SimpleAdapter(this, data,
		R.drawable.am003_item_province, new String[]
		{ "name" }, new int[]
		{ R.id.provinceName });
	lvProvinceAm003.setAdapter(satProvince);
    }

    private final class ProvinceItemClickListener implements
	    OnItemClickListener
    {
	Intent data = new Intent();

	public void onItemClick(AdapterView<?> parent, View view, int position,
		long id)
	{
	    ListView lv_city = (ListView) parent;
	    HashMap<String, Object> data = (HashMap<String, Object>) lv_city
		    .getItemAtPosition(position);
	    showCity(Integer.parseInt(data.get("id").toString()));
	}
    }

    private void showCity(int provinceId)
    {
	List<HashMap<String, Object>> data = service
		.getAllCityByProvinceId(provinceId);
	if (data != null)
	{
	    AM003CityAdapter adapter = new AM003CityAdapter(
		    this.getApplicationContext(), data);
	    lvCityAm003.setAdapter(adapter);
	}
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

}
