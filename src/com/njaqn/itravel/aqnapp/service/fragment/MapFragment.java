package com.njaqn.itravel.aqnapp.service.fragment;

import com.baidu.mapapi.map.MapView;
import com.njaqn.itravel.aqnapp.R;
import com.njaqn.itravel.aqnapp.util.MapUtil;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MapFragment extends Fragment
{
    private MapUtil map;
    private Button button;
    private MapView mMapView;
    
    public MapFragment(MapUtil map,Button button)
    {
	this.map = map;
	this.button = button;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
	 View v = inflater.inflate(R.layout.am001_fragment_mapview, null);
	mMapView = (MapView) v.findViewById(R.id.bmapView);
	map.setBtnLocation(button);
	map.setCurrLocation(mMapView);

	return v;
    }
    

    @Override
    public void onDestroy()
    {
	map.destroy();
	super.onDestroy();
	// 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
	mMapView.onDestroy();
	mMapView = null;
    }

    @Override
    public void onResume()
    {
	super.onResume();
	// 在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
	mMapView.onResume();
    }

    @Override
    public void onPause()
    {
	super.onPause();
	// 在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
	mMapView.onPause();
    }
   
}
