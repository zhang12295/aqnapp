package com.njaqn.itravel.aqnapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.njaqn.itravel.aqnapp.util.AQNAppConst;
import com.njaqn.itravel.aqnapp.util.ImageDownLoader;
import com.njaqn.itravel.aqnapp.util.UrlHttp;
import com.njaqn.itravel.aqnapp.util.ImageDownLoader.onImageLoaderListener;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class AM006SpotIntroActivity extends Activity implements OnPageChangeListener {

	private ViewGroup mViewGroup;
	private ViewPager mPager;
	private TextView mTextView;
	private String id;
	private List<HashMap<String, Object>> data;
	private ImageDownLoader mImageDownLoader;
	private List<Bitmap> mBitmap;
	private List<ImageView> lstImage;
//	private int i;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.am006_spot_intro);
		Intent i = this.getIntent();
		id = i.getStringExtra("id");
		init();
		data = initDate();
		mBitmap = getImage();
		for(int j =0;j<mBitmap.size();j++){
			ImageView imageView = new ImageView(this);
			imageView.setImageBitmap(mBitmap.get(j));
			lstImage.add(imageView);
		}
		mTextView.setText(data.get(0).get("spotintro").toString());
	}

	private List<HashMap<String,Object>> initDate() {
		UrlHttp url = new UrlHttp();
		try {
			String r = url.postRequestForSql("select TitleImage,Intro from J_JingDian where SpotID" +
						"="+id, AQNAppConst.DB_MANY_MANY);
			JSONArray jsonArr = new JSONArray(r);
			for(int i = 0;i<jsonArr.length();i++){
				JSONObject obj = jsonArr.getJSONObject(i);
				HashMap<String, Object> data2 = new HashMap<String, Object>();
				data2.put("spotimg", obj.getString("TitleImage"));
				data2.put("spotintro", obj.getString("Intro"));	
				data.add(data2);
			}
			return data;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}

	private void init() {
		
		mPager = (ViewPager) findViewById(R.id.iv_spotimage);
		mTextView = (TextView) findViewById(R.id.tv_spot_int);
		mBitmap = new ArrayList<Bitmap>();
		data = new ArrayList<HashMap<String,Object>>();
		lstImage = new ArrayList<ImageView>();
		mImageDownLoader = new ImageDownLoader(this);
		mPager.setAdapter(new mPagerAdapter());
		mPager.setOnPageChangeListener(this);
	}

	class mPagerAdapter extends PagerAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return lstImage.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0==arg1;
		}
		
		@Override
		public void destroyItem(View container, int position, Object object) {
			((ViewPager)container).removeView(lstImage.get(position));
		}
		
		@Override
		public Object instantiateItem(View container, int position) {
			((ViewPager)container).addView(lstImage.get(position), 0);
            return lstImage.get(position); 
				}
	}
	
	private List<Bitmap> getImage(){
		Bitmap bitmap = null;
		
		for(int i=0; i<data.size(); i++){
			String mImageUrl = AQNAppConst.URL_IMG+data.get(i).get("spotimg");
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

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		
		
	}

	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub
		mTextView.setText(data.get(arg0).get("spotintro").toString());
	}
	
}
