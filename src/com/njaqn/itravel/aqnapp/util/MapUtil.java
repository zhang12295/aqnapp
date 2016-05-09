package com.njaqn.itravel.aqnapp.util;

import java.util.Timer;
import java.util.TimerTask;

import android.os.Handler;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.njaqn.itravel.aqnapp.service.bean.JingDianBean;

public class MapUtil {

	private JingDianBean nearestOne;

	public void setNearestOne(JingDianBean nearestOne) {
		this.nearestOne = nearestOne;
	}
	private LatLng lastLatLng;
	private LatLng latLng;
	private double moveDistence = 20;
	private Handler handler;

	public MapUtil(Handler handler) {
		this.handler = handler;
	}

	public void setLatLng(LatLng latLng) {
		this.latLng = latLng;
		System.out.println("设置latlng");
		if (nearestOne != null) {
			moveDistence = DistanceUtil.getDistance(latLng, lastLatLng);
			LatLng jingDianLatLng = new LatLng(nearestOne.getLatitude(),
					nearestOne.getLongitude());
			double distance = DistanceUtil.getDistance(latLng, jingDianLatLng);
			
			if (distance < 400) {
				jingDianListener.onApproachJingDian(nearestOne.getId(),
						nearestOne.getName(), distance);
			}
		}

	}

	public static interface JingDianListener {
		void onApproachJingDian(int id, String jingDianName, double distance);
	}

	private JingDianListener jingDianListener;
	public JingDianTask getjTask() {
		return jTask;
	}

	private JingDianTask jTask;
	public void setJingDianListener(JingDianListener jingDianListener) {
		System.out.println("设置listener");
		this.jingDianListener = jingDianListener;
		Timer timer = new Timer();
		jTask = new JingDianTask();
	}

	private class JingDianTask extends TimerTask {

		@Override
		public void run() {
			if (latLng != null&&moveDistence > 10 ) {
				lastLatLng = latLng;
				handler.sendEmptyMessage(2);
			}
		}

	}
	
	public void destory() {
		jTask.cancel();
	}

}
