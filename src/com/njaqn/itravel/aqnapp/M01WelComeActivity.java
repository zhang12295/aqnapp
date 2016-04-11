package com.njaqn.itravel.aqnapp;

import java.util.Timer;
import java.util.TimerTask;

import com.njaqn.itravel.aqnapp.am.AM001HomePageActivity;
import com.njaqn.itravel.aqnapp.util.AQNUtil;
import com.njaqn.itravel.aqnapp.util.AppConf;
import com.njaqn.itravel.aqnapp.util.VoiceUtil;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class M01WelComeActivity extends Activity
{
    private Intent it;
    private TimerTask task;
    private ImageView imgWelcomeBg;
    private ImageView imgWelcome;
    private AppConf conf;

    protected void onCreate(Bundle savedInstanceState)
    {
	super.onCreate(savedInstanceState);

	setContentView(R.layout.m01_welcome);
	imgWelcome = (ImageView) this.findViewById(R.id.imgWelcome);
	imgWelcome.setImageResource(R.drawable.m01_welcome);
	conf = new AppConf();

	playWelcomeWord();
	timerStart();
    }

    public void playWelcomeWord()
    {
	VoiceUtil vutil = new VoiceUtil(this.getApplicationContext());

	String welcomeWord = conf.getAppConfig(conf.WELCOME);
	if (welcomeWord != null)
	    welcomeWord = "欢迎使用爱去哪导游解说神器";

	vutil.playAudio(welcomeWord);
    }

    public void timerStart()
    {
	it = new Intent(this, AM001HomePageActivity.class);

	Timer timer = new Timer();
	task = new TimerTask()
	{
	    public void run()
	    {
		finish();
		startActivity(it);
	    }
	};

//	String waitTimes = conf.getAppConfig(conf.WELCOME_WAIT_TIMES);
//	if (waitTimes == null)
//	    waitTimes = "1";
	String waitTimes = "1";

	int times = new Integer(waitTimes).intValue();
	timer.schedule(task, times * 1000);
    }

    public void setWelcomeBackGroundColor()
    {
	String bgcolor = conf.getAppConfig(conf.WELCOME_BG_COLOR);
	if (bgcolor == null)
	{// 取背景图片的颜色
	    BitmapDrawable db = (BitmapDrawable) imgWelcome.getDrawable();
	    Bitmap bitmap = db.getBitmap();
	    int color = bitmap.getPixel(20, 20);
	    imgWelcomeBg.setBackgroundColor(color);
	}
    }

    public void imgJianTouOnClick(View v)
    {
	task.cancel();
	finish();

	it = new Intent(this, AM001HomePageActivity.class);
	startActivity(it);
    }

}
