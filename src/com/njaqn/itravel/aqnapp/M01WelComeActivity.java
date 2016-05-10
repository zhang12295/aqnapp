package com.njaqn.itravel.aqnapp;

import java.util.Timer;
import java.util.TimerTask;

import com.njaqn.itravel.aqnapp.am.AM001HomePageActivity;
import com.njaqn.itravel.aqnapp.util.AppConf;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class M01WelComeActivity extends Activity {
//	private final int SDK_PERMISSION_REQUEST = 127;
	private Intent it;
	private TimerTask task;
	private ImageView imgWelcomeBg;
	private ImageView imgWelcome;
	private AppConf conf;
//	private VoiceUtil vutil;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.m01_welcome);
		imgWelcome = (ImageView) this.findViewById(R.id.imgWelcome);
		imgWelcome.setImageResource(R.drawable.m01_welcome);
		conf = new AppConf();

		playWelcomeWord();
//		getPersimmions();
		timerStart();
	}
//	@TargetApi(23)
//	private void getPersimmions() {
//		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//			ArrayList<String> permissions = new ArrayList<String>();
//			/***
//			 * 定位权限为必须权限，用户如果禁止，则每次进入都会申请
//			 */
//			// 定位精确位置
//			if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
//				permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
//			}
//			if(checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
//				permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
//			}
//			
//			if (permissions.size() > 0) {
//				requestPermissions(permissions.toArray(new String[permissions.size()]), SDK_PERMISSION_REQUEST);
//			}
//		}
//	}

	public void playWelcomeWord() {
//		vutil = new VoiceUtil(this);

		String welcomeWord = conf.getAppConfig(conf.WELCOME);
		if (welcomeWord != null)
			welcomeWord = "welcome";
		

	}

	public void timerStart() {
		it = new Intent(this, AM001HomePageActivity.class);

		Timer timer = new Timer();
		task = new TimerTask() {
			public void run() {
//				vutil.playAudio("welcome");
				finish();
				startActivity(it);
			}
		};

		// String waitTimes = conf.getAppConfig(conf.WELCOME_WAIT_TIMES);
		// if (waitTimes == null)
		// waitTimes = "1";
		String waitTimes = "3";

		int times = new Integer(waitTimes).intValue();
		timer.schedule(task, times * 1000);
	}

	public void setWelcomeBackGroundColor() {
		String bgcolor = conf.getAppConfig(conf.WELCOME_BG_COLOR);
		if (bgcolor == null) {
			BitmapDrawable db = (BitmapDrawable) imgWelcome.getDrawable();
			Bitmap bitmap = db.getBitmap();
			int color = bitmap.getPixel(20, 20);
			imgWelcomeBg.setBackgroundColor(color);
		}
	}

	public void imgJianTouOnClick(View v) {
		task.cancel();
		finish();

		it = new Intent(this, AM001HomePageActivity.class);
		startActivity(it);
	}

}
