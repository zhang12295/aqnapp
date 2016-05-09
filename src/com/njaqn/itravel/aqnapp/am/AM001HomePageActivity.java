package com.njaqn.itravel.aqnapp.am;

import com.njaqn.itravel.aqnapp.bm.BM005LoginActivity;

import com.njaqn.itravel.aqnapp.service.fragment.CommunityFragment;
import com.njaqn.itravel.aqnapp.service.fragment.FacilityFragment;
import com.njaqn.itravel.aqnapp.service.fragment.MapFragment;
import com.njaqn.itravel.aqnapp.service.fragment.SelfFragment;
import com.njaqn.itravel.aqnapp.util.MapUtil;
import com.njaqn.itravel.aqnapp.util.VoiceUtil;
import com.njaqn.itravel.aqnapp.AppInfo;
import com.njaqn.itravel.aqnapp.R;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class AM001HomePageActivity extends Activity {
//	private MapUtil map = null;
	private VoiceUtil vutil;

	public VoiceUtil getVutil() {
		return vutil;
	}

	private ImageView imgPlayView;
	private ImageView imgCommunityView;
	private ImageView imgFacilityView;
	private ImageView imgSelfView;
	private ImageView imgMapView;
	private View layoutMapView;

	public View getLayoutMapView() {
		return layoutMapView;
	}

	private Button btnLocation;
	public Button getBtnLocation() {
		return btnLocation;
	}

	private AppInfo app;

	public AppInfo getApp() {
		return app;
	}

	private MapFragment mapFrg;

	public MapFragment getMapFrg() {
		return mapFrg;
	}

	private CommunityFragment commFrg;
	private FacilityFragment facFrg;
	private SelfFragment selfFrg;

	private Animation animation;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		app = (AppInfo) getApplication(); // 获取Application
		// 初始化语音播放配置
		vutil = new VoiceUtil(this);

//		map = new MapUtil(this.getApplicationContext(), app, vutil);
		setContentView(R.layout.am001_home_page);

		//
		animation = AnimationUtils.loadAnimation(this, R.anim.rotate);
		animation.setFillAfter(true);

		// 初始化控件
		initView();
		vutil.changImageMode(imgPlayView, animation);
		setEvent(0);

	}

	public void setPlayViewAnimation() {
		imgPlayView.setAnimation(animation);
		imgPlayView.startAnimation(animation);
	}

	// 切换播放模式
	public void switchPlayMode(View v) {
		if (vutil != null) {
			if (vutil.getPlayMode() == 1) {
				vutil.playPause();
			} else if (vutil.getPlayMode() == 2) {
				vutil.playResume();
			}
		}
	}

	public void switchMainContent(View v) {
		setDefaultImage();
		switch (v.getId()) {
		case R.id.layoutMapView:
			imgMapView.setBackgroundResource(R.drawable.am001_menu_a1);
			setEvent(0);
			break;

		case R.id.layoutCommunityView:
			imgCommunityView.setBackgroundResource(R.drawable.am001_menu_b1);
			setEvent(1);
			break;
		case R.id.layoutFacilityView:
			setEvent(2);
			imgFacilityView.setBackgroundResource(R.drawable.am001_menu_d1);
			break;
		case R.id.layoutSelfView:
			setEvent(3);
			imgSelfView.setBackgroundResource(R.drawable.am001_menu_e1);
			break;
		}
	}

	private void setEvent(int i) {
		FragmentManager fragManager = getFragmentManager();
		FragmentTransaction fragTransaction = fragManager.beginTransaction();
		hideFragment(fragTransaction);
		switch (i) {
		case 0:
			if (mapFrg == null) {
				mapFrg = new MapFragment();
				fragTransaction.add(R.id.homeView, mapFrg);
			} else {
				fragTransaction.show(mapFrg);
			}
			break;

		case 1:
			if (commFrg == null) {
				commFrg = new CommunityFragment();
				fragTransaction.add(R.id.homeView, commFrg);
			} else {
				fragTransaction.show(commFrg);
			}
			break;
		case 2:
			if (facFrg == null) {
				facFrg = new FacilityFragment();
				fragTransaction.add(R.id.homeView, facFrg);
			} else {
				fragTransaction.show(facFrg);
			}
			break;
		case 3:
			if (selfFrg == null) {
				selfFrg = new SelfFragment();

				fragTransaction.add(R.id.homeView, selfFrg);
			} else {
				fragTransaction.show(selfFrg);
			}
			break;
		}
		fragTransaction.commit();

	}

	private void hideFragment(FragmentTransaction ft) {
		if (mapFrg != null) {
			ft.hide(mapFrg);
		}
		if (commFrg != null) {
			ft.hide(commFrg);
		}
		if (facFrg != null) {
			ft.hide(facFrg);
		}
		if (selfFrg != null) {
			ft.hide(selfFrg);
		}
	}

	public void downloadOnClick(View v) {
		Intent intent = new Intent(this, AM004DownloadActivity.class);
		startActivity(intent);
	}

	public void btnLocationOnClick(View v) {
		Intent city = new Intent(this, AM003CityChangeActivity.class);
		startActivityForResult(city, 0);
	}

	public void btnMyOnClick(View v) {
		Intent login = new Intent(this, BM005LoginActivity.class);
		startActivity(login);
	}

	// 进行城市切换
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		this.btnLocation.setText(app.getCity());
		switchMainContent(layoutMapView);
		mapFrg.setCityRange(app.getProvinceId(),app.getCity());
		mapFrg.addView(3);
	}

	public void imgSearchOnClick(View v) {
		Intent it = new Intent(this, AM002SearchActivity.class);
		startActivity(it);
	}

	private void setDefaultImage() {
		imgMapView.setBackgroundResource(R.drawable.am001_menu_a0);
		imgCommunityView.setBackgroundResource(R.drawable.am001_menu_b0);
		imgFacilityView.setBackgroundResource(R.drawable.am001_menu_d0);
		imgSelfView.setBackgroundResource(R.drawable.am001_menu_e0);
	}

	private void initView() {
		imgPlayView = (ImageView) findViewById(R.id.imgPlayView);
		imgCommunityView = (ImageView) findViewById(R.id.imgCommunityView);
		imgFacilityView = (ImageView) findViewById(R.id.imgFacilityView);
		imgSelfView = (ImageView) findViewById(R.id.imgSelfView);
		imgMapView = (ImageView) findViewById(R.id.imgMapView);
		layoutMapView = findViewById(R.id.layoutMapView);
		btnLocation = (Button) this.findViewById(R.id.btnLocation);

	}

	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		super.onNewIntent(intent);
		setIntent(intent);

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Intent intent = getIntent();
		String type = intent.getStringExtra("type");
		if (type != null) {
			switch (intent.getStringExtra("type")) {
			case "spot":
//				map.mapClear();
//				map.setSpotArea(Integer.parseInt(intent.getStringExtra("id")));
//				map.setJingDianPointer(Integer.parseInt(intent
//						.getStringExtra("id")));
//				map.setMapCenter(Integer.parseInt(intent.getStringExtra("id")),
//						16);
				mapFrg.setJingDianPointer(Integer.parseInt(intent.getStringExtra("id")));
				break;

			default:
				break;
			}
		}

	}

}
