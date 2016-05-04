package com.njaqn.itravel.aqnapp.util;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
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
import com.baidu.mapapi.map.PolygonOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.model.LatLng;
import com.njaqn.itravel.aqnapp.service.AmService;
import com.njaqn.itravel.aqnapp.service.AmServiceImpl;
import com.njaqn.itravel.aqnapp.service.bean.JSpotBean;
import com.njaqn.itravel.aqnapp.service.bean.JingDianBean;
import com.njaqn.itravel.aqnapp.AppInfo;
import com.njaqn.itravel.aqnapp.R;

public class MapUtil
{
    private Context ctx;
    private LocationClient cli;
    private BaiduMap map;
    private boolean isFristLoc = true;
    private LatLng locationLatLng = null;
    private AppInfo app;
    private Button btnLocation;
    private VoiceUtil vUtil;
    // 表示显示那种颜色
    private int i = 0;
    private ButtonClickListener btnClick;
    private AmService aService = new AmServiceImpl();

    public MapUtil(Context ctx, AppInfo app, VoiceUtil vUtil)
    {
	this.ctx = ctx;
	this.app = app;
	this.vUtil = vUtil;
	SDKInitializer.initialize(ctx);
    }

    public void setBtnLocation(Button btnLocation)
    {
	this.btnLocation = btnLocation;
    }

    /**
     * 功能：在地图上添加一个覆盖物
     * 
     * @param iconResource
     *            显示的图标资源
     * @param point
     *            经纬度
     * @param info
     *            额外信息
     */
    public void setMapMarker(int iconResource, LatLng point, Bundle info)
    {
	BitmapDescriptor bitmap = BitmapDescriptorFactory
		.fromResource(iconResource);
	OverlayOptions option = new MarkerOptions().position(point)
		.icon(bitmap).zIndex(10).period(25).extraInfo(info);
	map.addOverlay(option);
    }

    /**
     * 在地图上添加标识信息
     * 
     * @param point
     *            经纬度
     * @param info
     *            显示在地图上的信息
     */
    public void setPopMarker(LatLng point, Bundle info)
    {
	// 初始化View
	LayoutInflater inflater = (LayoutInflater) ctx
		.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	View v = inflater.inflate(R.layout.am001_map_popview, null);

	ImageButton button = (ImageButton) v.findViewById(R.id.btnPlayAudio);
	ImageButton enter = (ImageButton) v.findViewById(R.id.btnEnter);
	if (btnClick != null)
	{
	    btnClick = null;
	}
	btnClick = new ButtonClickListener(info);
	enter.setOnClickListener(btnClick);
	button.setOnClickListener(btnClick);
	TextView textView = (TextView) v.findViewById(R.id.txtIntro);
	TextView txtName = (TextView) v.findViewById(R.id.txtName);
	txtName.setText(info.getString("name"));
	textView.setText(info.getString("intro"));

	// 添加信息窗口
	InfoWindow mInfoWindow = new InfoWindow(v, point, -27);
	map.showInfoWindow(mInfoWindow);
    }

    // 为相应的button添加监听事件
    private final class ButtonClickListener implements OnClickListener
    {

	private Bundle info;

	public ButtonClickListener(Bundle info)
	{
	    this.info = info;
	}

	@Override
	public void onClick(View v)
	{
	    switch (v.getId())
	    {
	    case R.id.btnPlayAudio:
		if (vUtil.getPlayMode() == 1 || vUtil.getPlayMode() == 2)
		{
		    vUtil.playStop();
		}
		vUtil.playAudio(info.getString("intro"));
		break;

	    case R.id.btnEnter:

		if (info.getString("type").equals("spot"))
		{
		    map.clear();
		    try
		    {
			setJingDianPointer(info.getInt("id"));
		    }
		    catch (Exception e)
		    {
			e.printStackTrace();
		    }

		}

		break;
	    }

	}
    }

    // 开启定位
    public boolean setCurrLocation(MapView view)
    {
	map = view.getMap();
	map.setMyLocationEnabled(true);
	cli = new LocationClient(ctx);
	cli.registerLocationListener(new MyLocationListener());
	this.setLocationOption();
	map.setMapType(BaiduMap.MAP_TYPE_NORMAL);
	cli.start();
	return true;
    }

    // 位置信息监听回掉接口
    public class MyLocationListener implements BDLocationListener
    {
	@Override
	public void onReceiveLocation(BDLocation location)
	{
	    if (location == null)
		return;
	    // 收集当前位置信息
	    MyLocationData locData = new MyLocationData.Builder()
		    .accuracy(location.getRadius()).direction(100)
		    .latitude(location.getLatitude())
		    .longitude(location.getLongitude()).build();
	    String city = location.getCity();
	    app.setCity(city.substring(0, city.length() - 1));
	    app.setLongitude(location.getLongitude());
	    app.setLatitude(location.getLatitude());
	    btnLocation.setText(app.getCity());

	    // 设置定位数据
	    map.setMyLocationData(locData);

	    if (isFristLoc)
	    {
		isFristLoc = false;
		locationLatLng = new LatLng(location.getLatitude(),
			location.getLongitude());
		// 设置地图中心点及缩放级别
		MapStatusUpdate su = MapStatusUpdateFactory.newLatLngZoom(
			locationLatLng, 16);
		map.animateMapStatus(su);

		// 设置地图覆盖点点击监听事件
		map.setOnMarkerClickListener(new MarkerListener());
		// 设置地图点击监听事件
		map.setOnMapClickListener(new MapClickListener());
		// 判断用户是否在景区
		
		setMark(location);

		
	    }
	}
    }

    // 地图点击事件回掉接口
    private final class MapClickListener implements OnMapClickListener
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

    // 覆盖点点击回掉接口
    private final class MarkerListener implements OnMarkerClickListener
    {

	@Override
	public boolean onMarkerClick(Marker arg0)
	{
	    Bundle bundle = arg0.getExtraInfo();
	    String type = bundle.getString("type");
	    if (type != null) {
	        if (type.equals("spot"))
		    {
			setPopMarker(arg0.getPosition(), bundle);
		    }
		    else if (type.equals("jingDian"))
		    {
			setPopMarker(arg0.getPosition(), bundle);
		    }
		}
	

	    return true;
	}

    }

    // 设置定位参数
    private void setLocationOption()
    {
	LocationClientOption option = new LocationClientOption();
	option.setOpenGps(true);

	option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
	option.setCoorType("bd09ll");
	option.setAddrType("all");
	option.setScanSpan(5000);
	option.setIsNeedAddress(true);
	option.setNeedDeviceDirect(true);
	cli.setLocOption(option);
    }

    public void destroy()
    {
	cli.stop();
	map.setMyLocationEnabled(false);
    }

    // 判断用户进没进入景点
    private void setMark(BDLocation location)
    {
	List<JSONObject> locationSpot = aService.judgeLocation(location.getLongitude(), location.getLatitude());
	JSONObject currentSpot = locationSpot.get(0);

	try
	{

	    for (JSONObject j : locationSpot)
	    {
		try
		{
		    setJingDianPointer(j.getInt("ID"));
		    setSpotArea(j.getInt("ID"));
		}
		catch (Exception e)
		{
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
	    }

	    if (currentSpot.getInt("distance") < 1000)
	    {
		vUtil.playAudio("您当前所在的景区是" + currentSpot.getString("name"));
	    }
	   else
	    {
		vUtil.playAudio("您当前不在任何景区");
	    }
	}
	catch (Exception e)
	{
	    e.printStackTrace();
	}
    }

    // 表示景区
    public void setSpotArea(int spotId)
    {
	List<JSONObject> spotAroundPoints = aService
		.getSpotAroundPointsBysoptId(spotId);
	List<LatLng> pts = null;
	// 填充颜色数组
	int colors[] =
	{ 0xAA51c6f4, 0xAA0ad15a, 0xAAc0d108, 0xAAef7ada };
	if (spotAroundPoints.size() >= 5)
	{
	    pts = new ArrayList<LatLng>();
	    for (JSONObject j : spotAroundPoints)
	    {
		LatLng pt = null;
		try
		{
		    pt = new LatLng(j.getDouble("latitude"),
			    j.getDouble("longitude"));
		}
		catch (JSONException e)
		{
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
		pts.add(pt);

	    }
	    i = i % 4;
	    i++;
	    OverlayOptions polygonOption = new PolygonOptions().points(pts)
		    .stroke(new Stroke(5, colors[i])).fillColor(colors[i]);
	    // 在地图上添加多边形Option，用于标识景区位置
	    map.addOverlay(polygonOption);
	}

    }

    // 标识景点所在区域
    public void setJingDianPointer(int ID)
    {
	List<JingDianBean> jingDians = aService.getAllJingDianBySpotId(ID);
	if (jingDians != null)
	{
	    for (JingDianBean i : jingDians)
	    {
		LatLng latlng = new LatLng(i.getLatitude(), i.getLongitude());
		Bundle bundle = new Bundle();
		bundle.putString("type", "jingDian");
		bundle.putString("intro", i.getIntro());
		bundle.putString("name", i.getName());
		bundle.putInt("id", i.getId());
		setMapMarker(R.drawable.am001_map_spot, latlng, bundle);
	    }
	}
    }

    // 设置地图中心点级缩放级别
    public void setMapCenter(int spotId, int zoom)
    {
	List<JSONObject> spotAroundPoints = aService
		.getSpotAroundPointsBysoptId(spotId);
	if (spotAroundPoints.size() != 0)
	{
	    JSONObject center = spotAroundPoints.get(0);
	    MapStatusUpdate su = null;
	    try
	    {
		su = MapStatusUpdateFactory.newLatLngZoom(
			new LatLng(center.getDouble("latitude"), center
				.getDouble("longitude")), 16);
	    }
	    catch (JSONException e)
	    {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	    map.animateMapStatus(su);
	}

    }

}
