package com.njaqn.itravel.aqnapp.am;


import com.baidu.mapapi.map.MapView;
import com.njaqn.itravel.aqnapp.util.MapUtil;
import com.njaqn.itravel.aqnapp.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class ShouYeActivity extends Activity 
{
	private MapView mMapView = null;  
	private MapUtil map = null;
	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		map = new MapUtil(this.getApplicationContext());
		map.initMap();
		setContentView(R.layout.activity_am_shouye);
		
		mMapView = (MapView) findViewById(R.id.bmapView);
		
		map.setCurrLocation(mMapView);
	}
	
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		getMenuInflater().inflate(R.menu.shouye, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item)
	{
	
		int id = item.getItemId();
		if (id == R.id.action_settings) 
		{
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override  
    protected void onDestroy() 
	{ 
		map.destroy();
        super.onDestroy();  
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy(); 
        mMapView=null;
    }  
	
    @Override  
    protected void onResume()
    {  
        super.onResume();  
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理  
        mMapView.onResume();  
        }  
    @Override  
    protected void onPause()
    {  
        super.onPause();  
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理  
        mMapView.onPause();  
      }  
  }

