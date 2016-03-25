/**
 * 作者：张范兴
 */
package com.njaqn.itravel.aqnapp.am;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.njaqn.itravel.aqnapp.AppInfo;
import com.njaqn.itravel.aqnapp.R;
import com.njaqn.itravel.aqnapp.service.AmService;
import com.njaqn.itravel.aqnapp.service.AmServiceImpl;
import com.njaqn.itravel.aqnapp.service.BaseService;
import com.njaqn.itravel.aqnapp.service.BaseServiceImpl;
import com.njaqn.itravel.aqnapp.service.adapter.AM002CityAdapter;
import com.njaqn.itravel.aqnapp.service.bean.JSpotBean;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;


public class AM002SearchActivity extends Activity 
{
	private ListView lvPrivince;
	private ListView lvCity;
	private TabHost tabHost;
	private BaseService service;
	private AppInfo app;
	
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.am002_search);
		
		lvPrivince = (ListView)this.findViewById(R.id.lvPrivince);
		lvCity = (ListView) this.findViewById(R.id.lvCity);
		lvPrivince.setOnItemClickListener(new ProvinceItemClickListener());
		lvCity.setOnItemClickListener(new CityItemClickListener());
		service = new BaseServiceImpl();
		app = (AppInfo)this.getApplication();
		
		writeProvinceListView();
		
		tabHost = (TabHost)this.findViewById(R.id.tabhost);
		tabHost.setup();
		TabSpec tabSpec = tabHost.newTabSpec("remen");
		tabSpec.setIndicator(createTabView("热门"));
		tabSpec.setContent(R.id.remen);
		tabHost.addTab(tabSpec);
		
		tabSpec = tabHost.newTabSpec("fujin");
		tabSpec.setIndicator(createTabView("附近"));
		tabSpec.setContent(R.id.fujin);
		tabHost.addTab(tabSpec);
		
		tabSpec = tabHost.newTabSpec("quanguo");
		tabSpec.setIndicator(createTabView("全国"));
		tabSpec.setContent(R.id.quanguo);
		tabHost.addTab(tabSpec);
		
		tabHost.setCurrentTab(0);
	}
	
	private final class ProvinceItemClickListener implements OnItemClickListener
	{
		@SuppressWarnings("unchecked")
		public void onItemClick(AdapterView<?> adapter, View view, int position,long id) 
		{
			HashMap<String,Object> data = (HashMap<String,Object>)lvPrivince.getItemAtPosition(position);
	
			List<HashMap<String, Object>> cityData = service.getAllCityByProvinceId(Integer.parseInt(data.get("id").toString()));
			AM002CityAdapter cityAdapter = new AM002CityAdapter(cityData, getApplicationContext());
			lvCity.setAdapter(cityAdapter);
		}
	}
	
	private final class CityItemClickListener implements OnItemClickListener
	{
		public void onItemClick(AdapterView<?> adapter, View view, int position,long id) 
		{
			int a =0;
			a=30;
		}
	}

	public void writeProvinceListView() 
	{
		List<HashMap<String, Object>> provinceData = service.getAllProvinceByCountryId(app.getCountryId());
		if(provinceData==null)
		{
			HashMap<String, Object> item = new HashMap<String, Object>();
			provinceData = new ArrayList<HashMap<String,Object>>();
			item.put("name", "暂无数据");
			provinceData.add(item);
		}
		
		SimpleAdapter satProvince= new SimpleAdapter(this, provinceData, R.drawable.am003_item_province, 
				new String[]{"name"}, new int[]{ R.id.provinceName} );
		lvPrivince.setAdapter(satProvince);
	}

	private View createTabView(String name) 
	{
		View tabView = getLayoutInflater().inflate(R.drawable.am002_tab, null);
		TextView textView = (TextView) tabView.findViewById(R.id.jingdian);
		textView.setText(name);
		return tabView;
	}
	
}
