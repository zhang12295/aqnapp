package com.njaqn.itravel.aqnapp.am;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

import com.njaqn.itravel.aqnapp.AM006SpotIntroActivity;
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
import android.widget.TextView;

public class AM006SpotActivity extends Activity implements OnPageChangeListener{
	private TextView tv_spotIntro;
	private ImageButton ib_playIntro;
	private ImageButton ib_playPause;
	private String id;
	private TextView tv_spotName;
	private ViewPager mViewPager;
	private String playWorld;
	private VoiceUtil vUtil;
	private List<ImageView> mImageViews;
	private List<Bitmap> mBitmap;
	private ViewGroup mViewGroup;
	
	private List<String> data;
	ImageDownLoader mImageDownLoader;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.am006_spot);
		init();
		initData();
	}

	private void initData() {
		UrlHttp url = new UrlHttp();
		String r = null;
		try {
			r = url.postRequestForSql("select intro from J_Spot where ID = "
					+ id, AQNAppConst.DB_ONE_ONE);
			String t = url.postRequestForSql("select TitleImage from J_Spot where ID" +
					"="+id, AQNAppConst.DB_MANY_MANY);
			JSONArray jsonArr = new JSONArray(t);
			for (int i = 0; i < jsonArr.length(); i++) {
				JSONObject obj = jsonArr.getJSONObject(i);
				String imgUrl = obj.getString("TitleImage");
				data.add(imgUrl);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tv_spotIntro.setText(r);
		playWorld = r;
		
		mBitmap = getImage();
		
		for(int i=0; i<mBitmap.size(); i++){
			ImageView imageView = new ImageView(this);
			imageView.setImageBitmap(mBitmap.get(i));
			mImageViews.add(imageView);
			
		}
	
	}

	private List<Bitmap> getImage(){
		Bitmap bitmap = null;
		
		for(int i=0; i<data.size(); i++){
			String mImageUrl = AQNAppConst.URL_IMG+data.get(i);
			bitmap =mImageDownLoader.downloadImage(mImageUrl, new onImageLoaderListener() {
				
				@Override
				public void onImageLoader(Bitmap bitmap, String url) {	
					if(bitmap!=null){
						mBitmap.add(bitmap);
					}
					
				}
			});
			if(bitmap!=null){
				mBitmap.add(bitmap);
			}
		}
		return mBitmap;
	}
	
	private void init() {
		Intent i = this.getIntent();
		id = i.getStringExtra("id");
		String spotName = i.getStringExtra("name");
		tv_spotIntro = (TextView) findViewById(R.id.tv_spotintro);
		tv_spotName = (TextView) findViewById(R.id.spot_name);
		ib_playIntro = (ImageButton) findViewById(R.id.ib_playintro);
		
		mViewPager = (ViewPager) findViewById(R.id.iv_spot);
		
		
		data = new ArrayList<String>();
		mBitmap = new ArrayList<Bitmap>();
		mImageViews = new ArrayList<ImageView>();
		
		mViewPager.setAdapter(new Myadapter());
		mViewPager.setOnPageChangeListener(this);
		mViewPager.setCurrentItem((mImageViews.size())*100);
		
		vUtil = new VoiceUtil(this);
		tv_spotName.setText(spotName);
		ib_playIntro.setOnClickListener(new imageButtonPlayListener());

		mImageDownLoader = new ImageDownLoader(this);
	}

	class Myadapter extends PagerAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mImageViews.size();
		}

		@Override
		public void destroyItem(View container, int position, Object object) {
			((ViewPager)container).removeView(mImageViews.get(position));
		}
		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0==arg1;
		}
		
		@Override
		public Object instantiateItem(View container, int position) {
			//return container;
			View viewClick=mImageViews.get(position);
			viewClick.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			 //跳转到详情界面
			 Intent i = new Intent(AM006SpotActivity.this,AM006SpotIntroActivity.class);
				i.putExtra("id", id);
				startActivity(i);
			}
			});
			((ViewPager)container).addView(mImageViews.get(position), 0);
            return mImageViews.get(position); 
		}
		
		
	}
	
	public void returnOnClick(View v) {
		this.finish();
	}

	
	class imageButtonPlayListener implements OnClickListener {
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.ib_playintro: {
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
				Intent i = new Intent();
				i.putExtra("id", id);
				startActivity(i);
				break;
			}
		}
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageSelected(int position) {
		// TODO Auto-generated method stub

	}

}
