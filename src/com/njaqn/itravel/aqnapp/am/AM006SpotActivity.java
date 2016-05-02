package com.njaqn.itravel.aqnapp.am;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

import com.njaqn.itravel.aqnapp.R;
import com.njaqn.itravel.aqnapp.util.AQNAppConst;
import com.njaqn.itravel.aqnapp.util.ImageDownLoader;
import com.njaqn.itravel.aqnapp.util.UrlHttp;
import com.njaqn.itravel.aqnapp.util.VoiceUtil;
import com.njaqn.itravel.aqnapp.util.ImageDownLoader.onImageLoaderListener;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AM006SpotActivity extends Activity
{
    private TextView tv_spotIntro;
    private ImageButton ib_playIntro;
    private String id;
    private TextView mSpotName;
    private ImageView mImageView;
    private String playWorld;
    private VoiceUtil vUtil;
    private Bitmap mBitmap;
    private String imageUrl;
    private ImageView mAtlas;
    private RelativeLayout mRlAtlas;
    private ImageView iv_gotospot;

    ImageDownLoader mImageDownLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.am006_spot);
	init();
	initData();
    }

    private void initData()
    {
	UrlHttp url = new UrlHttp();
	String intro = null;
	String r = null;
	try
	{
	    r = url.postRequestForSql(
		    "select intro,TitleImage from J_Spot where ID = " + id,
		    AQNAppConst.DB_ONE_MANY);
	    if (r.equals("Err"))
	    {
		Toast.makeText(AM006SpotActivity.this, "暂无网络",
			Toast.LENGTH_SHORT).show();
	    }
	    else
	    {
		JSONObject json = new JSONObject(r);
		String imageurl = json.getString("TitleImage");
		intro = json.getString("intro");
		imageUrl = imageurl;
	    }
	    String t = url.postRequestForSql(
		    "select TitleImage from J_JingDian where SpotID=" + id,
		    AQNAppConst.DB_MANY_MANY);
	    if (t.equals("Err"))
	    {
		mRlAtlas.setVisibility(View.GONE);
		mAtlas.setVisibility(View.GONE);
	    }
	}
	catch (Exception e)
	{
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	tv_spotIntro.setText(intro);
	playWorld = intro;
	getImage();

    }

    private void getImage()
    {
	Bitmap bitmap = null;
	String mImageUrl = AQNAppConst.URL_IMG + imageUrl;
	bitmap = mImageDownLoader.downloadImage(mImageUrl,
		new onImageLoaderListener()
		{

		    @Override
		    public void onImageLoader(Bitmap bitmap, String url)
		    {
			if (mImageView != null && bitmap != null)
			{
			    mImageView.setImageBitmap(bitmap);
			}

		    }
		});
	if (bitmap != null)
	{
	    mImageView.setImageBitmap(bitmap);
	}
	else
	{
	    mImageView.setImageDrawable(this.getResources().getDrawable(
		    R.drawable.app_nopicture));
	}
    }

    private void init()
    {
	Intent i = this.getIntent();
	id = i.getStringExtra("id");
	String spotName = i.getStringExtra("name");
	tv_spotIntro = (TextView) findViewById(R.id.tv_spotintro);
	mSpotName = (TextView) findViewById(R.id.spot_name);
	ib_playIntro = (ImageButton) findViewById(R.id.ib_playintro);
	mImageView = (ImageView) findViewById(R.id.iv_spot);
	iv_gotospot = (ImageView) findViewById(R.id.iv_gotospot);
	mAtlas = (ImageView) findViewById(R.id.im_atlas);
	mRlAtlas = (RelativeLayout) findViewById(R.id.rl_atlas);

	vUtil = new VoiceUtil(this);
	mSpotName.setText(spotName);
	ib_playIntro.setOnClickListener(new imageButtonPlayListener());
	iv_gotospot.setOnClickListener(new imageButtonPlayListener());
	mImageView.setOnClickListener(new OnClickListener()
	{

	    @Override
	    public void onClick(View v)
	    {
		if (mAtlas.VISIBLE == View.GONE)
		{
		    Toast.makeText(AM006SpotActivity.this, "该景区暂无其他景点",
			    Toast.LENGTH_SHORT).show();
		}
		else
		{
		    Intent intent = new Intent(AM006SpotActivity.this,
			    AM006SpotIntroActivity.class);
		    intent.putExtra("id", id);
		    startActivity(intent);
		}

	    }
	});

	mImageDownLoader = new ImageDownLoader(this);
    }

    public void returnOnClick(View v)
    {
	this.finish();
    }

    @Override
    public void finish()
    {
	// TODO Auto-generated method stub
	vUtil.playStop();
	vUtil = null;
	super.finish();
    }

    @Override
    protected void onPause()
    {
	super.onPause();
    }

    class imageButtonPlayListener implements OnClickListener
    {
	public void onClick(View v)
	{
	    // TODO Auto-generated method stub
	    switch (v.getId())
	    {
	    case R.id.ib_playintro:
	    {
		if (vUtil.getPlayMode() == 0)
		{
		    vUtil.playAudio(playWorld);
		    ib_playIntro.setImageResource(R.drawable.am006_spot_pause);
		    vUtil.setPlayMode(1);
		}
		else if (vUtil.getPlayMode() == 1)
		{
		    vUtil.playPause();
		    ib_playIntro.setImageResource(R.drawable.am006_spot_play);
		    vUtil.setPlayMode(2);
		}
		else if (vUtil.getPlayMode() == 2)
		{
		    vUtil.playResume();
		    ib_playIntro.setImageResource(R.drawable.am006_spot_pause);
		    vUtil.setPlayMode(1);
		}

	    }
		break;
	    case R.id.iv_gotospot:
		Intent i = new Intent(AM006SpotActivity.this,
			AM001HomePageActivity.class);
		i.putExtra("id", id);
		i.putExtra("type", "spot");
		startActivityForResult(i, 10);
		finish();
		break;

	    }
	}
    }

}
