package com.njaqn.itravel.aqnapp.service.adapter;

import java.util.List;

import com.njaqn.itravel.aqnapp.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SearchHistoryAdapter extends BaseAdapter {

	private Context mContext;
	private List<String> mData;
	private LayoutInflater mInflater;

	public SearchHistoryAdapter(Context mContext, List<String> mData) {
		this.mContext = mContext;
		this.mData = mData;
		mInflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public Object getItem(int position) {
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView txtHistory = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.search_item_history, null);
			txtHistory = (TextView) convertView
					.findViewById(R.id.recentSearchName);
			convertView.setTag(txtHistory);
		} else {
			txtHistory = (TextView) convertView.getTag();
		}
		txtHistory.setText(mData.get(position));

		return convertView;
	}

}
