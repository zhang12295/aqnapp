package com.njaqn.itravel.aqnapp.service.adapter;

import java.util.HashMap;
import java.util.List;

import com.njaqn.itravel.aqnapp.AppInfo;
import com.njaqn.itravel.aqnapp.R;
import com.njaqn.itravel.aqnapp.util.AQNAppConst;
import com.njaqn.itravel.aqnapp.util.ImageDownLoader;
import com.njaqn.itravel.aqnapp.util.ImageDownLoader.onImageLoaderListener;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;


public class AM003CityAdapter extends BaseAdapter implements OnScrollListener {

	private Context context;
	private List<HashMap<String,Object>> lstData;
	private LayoutInflater mInflater;
	private ListView mListView;
	private int mStart,mEnd;
	public static String[] URLS;
	private boolean mFirstIn;
	private ImageDownLoader mImageDownLoader;
	
	public AM003CityAdapter(AppInfo app,Context context, List<HashMap<String,Object>> lstData,ListView listView)
	{
		this.lstData = lstData;
		this.context = context;
		mInflater = LayoutInflater.from(context);
		mImageDownLoader = new ImageDownLoader(context);
		mListView = listView;
		URLS = new String[lstData.size()];
		for (int i = 0; i < lstData.size(); i++) {
			URLS[i] = AQNAppConst.URL_IMG+lstData.get(i).get("cityImage").toString();
		}
		mFirstIn = true;
		listView.setOnScrollListener(this);
	}
	
	@Override
	public int getCount() {
		return lstData.size();
	}

	@Override
	public Object getItem(int position) {
		return lstData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	class ViewHolder{
		public TextView txtName,txtKeywords,txtFlag;
		public ImageView imgCityImage;
	}
	public View getView(int position, View convertView, ViewGroup parent) 
	{	
		ViewHolder viewHolder = null;
		if(convertView == null)
		{
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.am003_item_city, null);
			viewHolder.imgCityImage = (ImageView) convertView.findViewById(R.id.imgCityImage);
			viewHolder.txtName = (TextView) convertView.findViewById(R.id.txtName);
			viewHolder.txtKeywords = (TextView) convertView.findViewById(R.id.txtKeywords);
			//viewHolder.txtFlag = (TextView) convertView.findViewById(R.id.txtFlag);
			convertView.setTag(viewHolder);
			
		}else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.imgCityImage.setImageResource(R.drawable.ic_launcher);
		String url = lstData.get(position).get("cityImage").toString();
		if (!url.equals("")) {
			url = AQNAppConst.URL_IMG + url;
			viewHolder.imgCityImage.setTag(url);
			Bitmap bitmap = mImageDownLoader.showCacheBitmap(url.replaceAll("[^\\w]", ""));
			if(bitmap != null){
				viewHolder.imgCityImage.setImageBitmap(bitmap);
			}		
		}
		viewHolder.txtKeywords.setText(lstData.get(position).get("keywords").toString());
		viewHolder.txtName.setText(lstData.get(position).get("name").toString());
		Log.i("viewHolder", lstData.get(position).get("name").toString()+":"+viewHolder.hashCode());
		int flag =  Integer.parseInt(lstData.get(position).get("flag").toString());
		
//		if(flag==1) //开通服务
//		{
//			viewHolder.txtFlag.setText("已开通");
//		}
//		else
//		{
//			viewHolder.txtFlag.setText("没开通");
//		}
		return convertView;
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		mStart = firstVisibleItem;
		mEnd = visibleItemCount+firstVisibleItem;
		if(mFirstIn && visibleItemCount>0){
			showImage(mStart, mEnd);
			mFirstIn = false;
		}
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		if(scrollState == SCROLL_STATE_IDLE){
			showImage(mStart, mEnd);
		}else{
			cancelTask();
		}
		
	}
	
	private void showImage(int start, int end){
		Bitmap bitmap = null;
		for(int i=start; i<end; i++){
			String mImageUrl = URLS[i];
			final ImageView mImageView = (ImageView) mListView.findViewWithTag(mImageUrl);
			bitmap = mImageDownLoader.downloadImage(mImageUrl, new onImageLoaderListener() {
				
				@Override
				public void onImageLoader(Bitmap bitmap, String url) {
					if(mImageView != null && bitmap != null){
						mImageView.setImageBitmap(bitmap);
					}
					
				}
			});
			
			if(bitmap != null){
				mImageView.setImageBitmap(bitmap);
			}else{
				mImageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_launcher));
			}
		}
	}

	/**
	 * 取消任务
	 */
	public void cancelTask(){
		mImageDownLoader.cancelTask();
	}

}