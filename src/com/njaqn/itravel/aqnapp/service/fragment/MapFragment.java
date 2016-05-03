package com.njaqn.itravel.aqnapp.service.fragment;

import java.util.List;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolygonOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.overlayutil.PoiOverlay;
import com.baidu.mapapi.search.core.CityInfo;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.district.DistrictResult;
import com.baidu.mapapi.search.district.DistrictSearch;
import com.baidu.mapapi.search.district.DistrictSearchOption;
import com.baidu.mapapi.search.district.OnGetDistricSearchResultListener;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.poi.PoiSortType;
import com.njaqn.itravel.aqnapp.R;
import com.njaqn.itravel.aqnapp.util.MapUtil;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class MapFragment extends Fragment implements
		OnGetDistricSearchResultListener, OnGetPoiSearchResultListener {
	private MapUtil map;
	private Button button;
	private MapView mMapView;
	private final int color = 0xAA878887;
	private BaiduMap mBaiduMap;
	private DistrictSearch mDistrictSearch;
	private PoiSearch mPoiSearch;
	private int facilityId;
	
	private static final int TOILET = 1;
	private static final int PARKING = 2;
	

	public MapFragment(MapUtil map, Button button) {
		this.map = map;
		this.button = button;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.am001_fragment_mapview, null);
		mMapView = (MapView) v.findViewById(R.id.bmapView);
		map.setBtnLocation(button);
		map.setCurrLocation(mMapView);
		mBaiduMap = mMapView.getMap();
		return v;
	}

	@Override
	public void onDestroy() {
		map.destroy();

		mMapView.onDestroy();
		mMapView = null;
		if (mPoiSearch != null) {
			mPoiSearch.destroy();
		}
		if (mDistrictSearch != null) {
			mDistrictSearch.destroy();
		}
		super.onDestroy();
	}

	public void setCityRange(String cityName) {
		mDistrictSearch = DistrictSearch.newInstance();
		mDistrictSearch.setOnDistrictSearchListener(this);
		if (cityName != null) {
			mDistrictSearch.searchDistrict(new DistrictSearchOption()
					.cityName(cityName));
		}
	}

	@Override
	public void onGetDistrictResult(DistrictResult districtResult) {
		mBaiduMap.clear();
		if (districtResult == null) {
			return;
		}
		if (districtResult.error == SearchResult.ERRORNO.NO_ERROR) {
			List<List<LatLng>> polyLines = districtResult.getPolylines();
			if (polyLines == null) {
				return;
			}
			LatLngBounds.Builder builder = new LatLngBounds.Builder();
			for (List<LatLng> polyline : polyLines) {
				OverlayOptions ooPolylinell = new PolylineOptions().width(5)
						.points(polyline).dottedLine(true).color(color);
				mBaiduMap.addOverlay(ooPolylinell);
				OverlayOptions ooPolygon = new PolygonOptions()
						.points(polyline).stroke(new Stroke(1, color))
						.fillColor(0xAA24b69f);
				mBaiduMap.addOverlay(ooPolygon);
				for (LatLng latLng : polyline) {
					builder.include(latLng);
				}
			}
			mBaiduMap.setMapStatus(MapStatusUpdateFactory
					.newLatLngBounds(builder.build()));
		}

	}

	public void setToiletSign(LatLng latLng) {
		// mBaiduMap.clear();
		mPoiSearch = PoiSearch.newInstance();
		mPoiSearch.setOnGetPoiSearchResultListener(this);
		facilityId = TOILET;
		mPoiSearch.searchNearby((new PoiNearbySearchOption()).keyword("厕所")
				.location(latLng).pageNum(0).radius(5000)
				.sortType(PoiSortType.distance_from_near_to_far));
		
	}

	public void setParkingSign(LatLng latLng) {
		// mBaiduMap.clear();
		mPoiSearch = PoiSearch.newInstance();
		mPoiSearch.setOnGetPoiSearchResultListener(this);
		facilityId = PARKING;
		mPoiSearch.searchNearby((new PoiNearbySearchOption()).keyword("停车场")
				.location(latLng).pageNum(0).radius(5000)
				.sortType(PoiSortType.distance_from_near_to_far));
	}

	@Override
	public void onGetPoiDetailResult(PoiDetailResult result) {
		if (result.error != SearchResult.ERRORNO.NO_ERROR) {
			Toast.makeText(getActivity(), "抱歉，未找到结果", Toast.LENGTH_SHORT)
					.show();
		} else {
			Toast.makeText(getActivity(),
					result.getName() + ": " + result.getAddress(),
					Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onGetPoiResult(PoiResult result) {
		if (result == null
				|| result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
			Toast.makeText(getActivity(), "未找到结果", Toast.LENGTH_LONG).show();
			return;
		}
		if (result.error == SearchResult.ERRORNO.NO_ERROR) {
			mBaiduMap.clear();
			PoiOverlay overlay = new MyPoiOverlay(mBaiduMap);
			mBaiduMap.setOnMarkerClickListener(overlay);
			overlay.setData(result,facilityId);
			overlay.addToMap();
			overlay.zoomToSpan();
			return;
		}
		if (result.error == SearchResult.ERRORNO.AMBIGUOUS_KEYWORD) {

			// 当输入关键字在本市没有找到，但在其他城市找到时，返回包含该关键字信息的城市列表
			String strInfo = "在";
			for (CityInfo cityInfo : result.getSuggestCityList()) {
				strInfo += cityInfo.city;
				strInfo += ",";
			}
			strInfo += "找到结果";
			Toast.makeText(getActivity(), strInfo, Toast.LENGTH_LONG).show();
		}

	}

	private class MyPoiOverlay extends PoiOverlay {

		public MyPoiOverlay(BaiduMap baiduMap) {
			super(baiduMap);
		}

		@Override
		public boolean onPoiClick(int index) {
			super.onPoiClick(index);
			PoiInfo poi = getPoiResult().getAllPoi().get(index);
			// if (poi.hasCaterDetails) {
			mPoiSearch.searchPoiDetail((new PoiDetailSearchOption())
					.poiUid(poi.uid));
			// }
			return true;
		}
	}

}
