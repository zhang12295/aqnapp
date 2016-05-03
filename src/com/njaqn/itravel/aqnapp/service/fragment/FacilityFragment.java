package com.njaqn.itravel.aqnapp.service.fragment;

import com.baidu.mapapi.model.LatLng;
import com.njaqn.itravel.aqnapp.R;
import com.njaqn.itravel.aqnapp.am.AM001HomePageActivity;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

public class FacilityFragment extends Fragment implements OnClickListener {
	private View rootView;
	private View toilet;
	private View parking;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.am001_fragment_facilityview, null);
		initView();
		return rootView;
	}

	public void initView() {
		toilet = rootView.findViewById(R.id.facilityview_toilet);
		toilet.setOnClickListener(this);
		parking = rootView.findViewById(R.id.facilityview_parking);
		parking.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		AM001HomePageActivity activity = (AM001HomePageActivity) getActivity();
		double latitude = activity.getApp().getLatitude();
	    double longitude = activity.getApp().getLongitude();	    
		LatLng latLng = new LatLng(latitude, longitude);
		switch (id) {
		case R.id.facilityview_toilet:
			activity.getMapFrg().setToiletSign(latLng);

			break;

		case R.id.facilityview_parking:
			activity.getMapFrg().setParkingSign(latLng);
			break;
		default:
			break;
		}
		View view = activity.getLayoutMapView();
		activity.switchMainContent(view);

	}
}
