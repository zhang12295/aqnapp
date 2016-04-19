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
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class SearchResultAdatper extends BaseAdapter implements OnScrollListener{

	private Context context;
	private List<HashMap<String, Object>> lstData;
	private LayoutInflater mInflater;
	private ListView mListView;
	private int mStart,mEnd;
	public static String[] URLS;
	private boolean mFirstIn;
	private ImageDownLoader mImageDownLoader;
	
	public SearchResultAdatper(Context context, List<HashMap<String,Object>> lstData,ListView listView)
	{
		this.lstData = lstData;
		this.context = context;
		mInflater = LayoutInflater.from(context);
		mImageDownLoader = new ImageDownLoader(context);
		mListView = listView;
		URLS = new String[lstData.size()];
		for (int i = 0; i < lstData.size(); i++) {
			URLS[i] = AQNAppConst.URL_IMG+lstData.get(i).get("titleImage").toString();
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
		public TextView txtName,txtIntro;
		public ImageView imgCityImage;
	}
	public View getView(int position, View convertView, ViewGroup parent) 
	{	
		ViewHolder viewHolder = null;
		if(convertView == null)
		{
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.search_item_result, null);
			viewHolder.imgCityImage = (ImageView) convertView.findViewById(R.id.searchSpotImage);
			viewHolder.txtName = (TextView) convertView.findViewById(R.id.searchResultName);
			viewHolder.txtIntro = (TextView) convertView.findViewById(R.id.searchResultIntro);
			convertView.setTag(viewHolder);
			
		}else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.imgCityImage.setImageResource(R.drawable.am006_spot_goto);
		String url = lstData.get(position).get("titleImage").toString();
		if (!url.equals("")) {
			url = AQNAppConst.URL_IMG + url;
			viewHolder.imgCityImage.setTag(url);
			Bitmap bitmap = mImageDownLoader.showCacheBitmap(url.replaceAll("[^\\w]", ""));
			if(bitmap != null){
				viewHolder.imgCityImage.setImageBitmap(bitmap);
			}		
		}
		viewHolder.txtIntro.setText(lstData.get(position).get("intro").toString());
		viewHolder.txtName.setText(lstData.get(position).get("name").toString());
		Log.i("viewHolder", lstData.get(position).get("name").toString()+":"+viewHolder.hashCode());
		
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
				mImageView.setImageDrawable(context.getResources().getDrawable(R.drawable.am006_spot_goto));
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
