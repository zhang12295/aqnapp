package com.njaqn.itravel.aqnapp.util;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.model.LatLng;
import com.njaqn.itravel.aqnapp.service.AmService;
import com.njaqn.itravel.aqnapp.service.AmServiceImpl;
import com.njaqn.itravel.aqnapp.service.bean.AQNPointer;
import com.njaqn.itravel.aqnapp.service.bean.JSpotBean;
import com.njaqn.itravel.aqnapp.AppInfo;
import com.njaqn.itravel.aqnapp.R;

public class MapUtil extends Activity
{
    private Context ctx;
    private LocationClient cli;
    private BaiduMap map;
    private boolean isFristLoc = true;
    private String locationAddress = null;
    private LatLng locationLatLng = null;
    private PlayAuditData data;
    private AppInfo app;
    private Button btnLocation;
    private VoiceUtil vUtil;
    private Context activityContext;

    public MapUtil(Context ctx, PlayAuditData data, AppInfo app, VoiceUtil vUtil)
    {
	this.data = data;
	this.ctx = ctx;
	this.app = app;
	this.vUtil = vUtil;
	SDKInitializer.initialize(ctx);
    }

    public void setBtnLocation(Button btnLocation)
    {
	this.btnLocation = btnLocation;
    }

    public void setMapMarker(int iconResource, LatLng point, String id,
	    Bundle info)
    {
	BitmapDescriptor bitmap = BitmapDescriptorFactory
		.fromResource(iconResource);
	OverlayOptions option = new MarkerOptions().position(point)
		.icon(bitmap).zIndex(0).period(25).title(id).extraInfo(info);
	map.addOverlay(option);
    }

    public void setPopMarker(LatLng point, final String text)
    {
	// 创建InfoWindow展示的view
	LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	View v = inflater.inflate(R.layout.am001_map_popview, null);
	
	ImageButton button = (ImageButton) v.findViewById(R.id.btnPlayAudio);
	button.setOnClickListener(new OnClickListener()
	{
	    
	    
	     @Override
	    public void onClick(View v)
	    {
		 if(vUtil.getPlayMode() == 1 || vUtil.getPlayMode() == 2)
		 {
		     vUtil.playStop();
		     vUtil.setPlayMode(0);
		 }
		 vUtil.playAudio(text);
		
	    }
	});
	TextView textView = (TextView) v.findViewById(R.id.txtIntro);
	textView.setText(text);

	// 创建InfoWindow , 传入 view， 地理坐标， y 轴偏移量
	InfoWindow mInfoWindow = new InfoWindow(v, point, -27);
	map.showInfoWindow(mInfoWindow);
    }

    // 添加覆盖物的点
    public void setCurrLocationMarker()
    {
	BitmapDescriptor bd1 = BitmapDescriptorFactory
		.fromResource(R.drawable.m01_point_red);
	// BitmapDescriptor bd2 = BitmapDescriptorFactory
	// .fromResource(R.drawable.m01_point_white);
	// ArrayList<BitmapDescriptor> giflist = new
	// ArrayList<BitmapDescriptor>();
	// giflist.add(bd1);
	// giflist.add(bd2);
	OverlayOptions ol = new MarkerOptions().position(locationLatLng)
		.icon(bd1).zIndex(0).period(25);
	map.addOverlay(ol);
    }

    public void setMarkerText(LatLng pointer, String text)
    {
	// 构建文字Option对象，用于在地图上添加文字
	OverlayOptions textOption = new TextOptions().bgColor(0xAAFFFF00)
		.fontSize(24).fontColor(0xFFFF00FF).text(text)
		.position(pointer);
	map.addOverlay(textOption);
    }

    public boolean setCurrLocation(MapView view)
    {
	map = view.getMap();
	map.setMyLocationEnabled(true); // 开启定位图层
	cli = new LocationClient(ctx);// 实例化LocationClient类
	cli.registerLocationListener(new MyLocationListener());
	this.setLocationOption();
	map.setMapType(BaiduMap.MAP_TYPE_NORMAL); // 设置地图类型
	cli.start();
	return true;
    }

    public class MyLocationListener implements BDLocationListener
    {
	@Override
	public void onReceiveLocation(BDLocation location)
	{
	    if (location == null)
		return;

	    MyLocationData locData = new MyLocationData.Builder()
		    .accuracy(location.getRadius()).direction(100)
		    .latitude(location.getLatitude())
		    .longitude(location.getLongitude()).build();
	    String city = location.getCity();
	    String province = location.getProvince();
	    app.setCity(city.substring(0, city.length() - 1));
	    btnLocation.setText(app.getCity());

	    // 设置定位数据
	    map.setMyLocationData(locData);
	    // 开启默认的定位图标显示
	    map.setMyLocationEnabled(true);

	    if (location.getLocType() == BDLocation.TypeNetWorkLocation)
		locationAddress = location.getAddrStr();

	    if (isFristLoc)
	    {
		isFristLoc = false;
		locationLatLng = new LatLng(location.getLatitude(),
			location.getLongitude());
		data.setLocationInfo(province, city, locationAddress,
			locationLatLng);
		// setCurrLocationMarker(); // 设置闪烁的图标
		//setPopMarker(locationLatLng, locationAddress);
		// LatLng l1 = new
		// LatLng(location.getLatitude()+0.01,location.getLongitude()+0.01);
		// setMapMarker(R.drawable.ic_launcher,l1);
		MapStatusUpdate su = MapStatusUpdateFactory.newLatLngZoom(
			locationLatLng, 16);
		// 设置地图中心点及缩放级别
		map.animateMapStatus(su);

		// 设置地图标志点击监听事件
		map.setOnMarkerClickListener(new MarkerListener());

		//
		map.setOnMapClickListener(new ClickListener());
		// 标志景区位置
		setSpotMark();
	    }
	}
    }

    private final class ClickListener implements OnMapClickListener
    {

	@Override
	public void onMapClick(LatLng arg0)
	{
	    map.hideInfoWindow();
	    
	}

	@Override
	public boolean onMapPoiClick(MapPoi arg0)
	{
	    // TODO Auto-generated method stub
	    return false;
	}
	
    }
    private final class MarkerListener implements OnMarkerClickListener
    {

	@Override
	public boolean onMarkerClick(Marker arg0)
	{
	    AmService aService = new AmServiceImpl();
	    Bundle bundle = arg0.getExtraInfo();
	    String type = bundle.getString("type");
	    int id = Integer.parseInt(arg0.getTitle());
	    if (type.equals("spot"))
	    {
		setPopMarker(arg0.getPosition(),aService.getSpotIntroById(id));
		
	    }

	    return false;
	}

    }

    // 设置定位参数
    private void setLocationOption()
    {
	LocationClientOption option = new LocationClientOption();
	option.setOpenGps(true);

	// 设置定位模式
	option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
	option.setCoorType("bd09ll");
	option.setAddrType("all");
	option.setScanSpan(5000);
	option.setIsNeedAddress(true);
	option.setNeedDeviceDirect(true);
	cli.setLocOption(option);
    }

    public static AQNPointer getCurrGPSPointer()
    {
	return null;
    }

    public void destroy()
    {
	cli.stop();
	map.setMyLocationEnabled(false);
    }

    private void setSpotMark()
    {
	int currentCityId = app.getCityId();
	AmService aService = new AmServiceImpl();
	List<JSpotBean> spots = new ArrayList<JSpotBean>();
	spots = aService.getSpotLocationByCityId(currentCityId);
	if (spots != null)
	{
	    for (JSpotBean i : spots)
	    {
		LatLng latlng = new LatLng(Double.parseDouble(i.getLatitude()),
			Double.parseDouble(i.getLongitude()));
		Bundle bundle = new Bundle();
		bundle.putString("type", "spot");
		setMapMarker(R.drawable.am001_map_spot, latlng, i.getId() + "",
			bundle);
	    }
	}
    }
}
