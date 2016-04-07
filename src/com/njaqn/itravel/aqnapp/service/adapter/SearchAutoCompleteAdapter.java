package com.njaqn.itravel.aqnapp.service.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.njaqn.itravel.aqnapp.R;

public class SearchAutoCompleteAdapter extends BaseAdapter {

	private Context mContext;
	private List<String> mData;
	private LayoutInflater mInflater;

	public SearchAutoCompleteAdapter(Context mContext, List<String> mData) {
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
		TextView txtComplete = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.search_item_autocomplete, null);
			txtComplete = (TextView) convertView
					.findViewById(R.id.autoCompleteName);
			convertView.setTag(txtComplete);
		} else {
			txtComplete = (TextView) convertView.getTag();
		}
		txtComplete.setText(mData.get(position));
		return convertView;
	}
}
