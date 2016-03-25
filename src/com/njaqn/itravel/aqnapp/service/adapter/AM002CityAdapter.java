package com.njaqn.itravel.aqnapp.service.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.njaqn.itravel.aqnapp.AppInfo;
import com.njaqn.itravel.aqnapp.R;
import com.njaqn.itravel.aqnapp.am.AM006SpotActivity;
import com.njaqn.itravel.aqnapp.service.AmServiceImpl;
import com.njaqn.itravel.aqnapp.service.bean.JSpotBean;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class AM002CityAdapter extends BaseAdapter
{
	private List<HashMap<String,Object>> data;
	private LayoutInflater infCity;
	private AmServiceImpl amService = new AmServiceImpl();
	private AppInfo app;
	private Context ctx;
	public AM002CityAdapter(List<HashMap<String, Object>> data,Context ctx)
	{
		this.data = data;
		infCity = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.ctx = ctx;
	}

	public int getCount() 
	{
		return data.size();
	}

	public Object getItem(int position)
	{
			return data.get(position);
	}
	
	public long getItemId(int position) 
	{
		return position;
	}

	private class ViewHolder
	{
		private ListView lvSpot;
		private TextView  txtCityName;
	}
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		List<HashMap<String, Object>> spotData = new ArrayList<HashMap<String,Object>>() ;
		ViewHolder vh = null;
		if(convertView == null)
		{
			convertView = infCity.inflate(R.drawable.am002_item, null);
			
			vh = new ViewHolder();
			vh.lvSpot = (ListView)convertView.findViewById(R.id.lvSpot);
			vh.txtCityName = (TextView)convertView.findViewById(R.id.txtCityName);
			
			convertView.setTag(vh);
		}
		else 
		{
			vh = (ViewHolder) convertView.getTag();
		}
	
		while(position<data.size())
		{
			HashMap<String, Object> item = (HashMap<String, Object>) data.get(position);
			
			int cityId = Integer.parseInt(item.get("id").toString());
			vh.txtCityName.setText(item.get("name").toString());
			
			List<JSpotBean> lstSpot = amService.getCityHotSpotByCityId(cityId, 5);
			ViewGroup.LayoutParams params =  vh.lvSpot.getLayoutParams();
			params.height =  60*lstSpot.size();
			
			HashMap<String, Object> map = null;
			if(lstSpot.size() == 0)
			{
				map = new HashMap<String, Object>();
				map.put("name","ÔÝÎÞÊý¾Ý");
				spotData.add(map);
			}
			else
			{
				for(int j=0;j<lstSpot.size();j++)
				{
					 map = new HashMap<String, Object>();
					JSpotBean spot = lstSpot.get(j);
					map.put("id", spot.getId());
					map.put("name", spot.getName());
					spotData.add(map);
				}
			}

			if(vh.lvSpot.getCount() ==0)
			{
				SimpleAdapter adapter = new SimpleAdapter(ctx, spotData, R.drawable.am002_item_spot, 	
						new String[]{"name"}, new int[]{ R.id.txtSpotName1} );
				vh.lvSpot.setAdapter(adapter);
			}
			break;
		}
		return convertView;
	}
}
