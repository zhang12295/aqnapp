package com.njaqn.itravel.aqnapp.widget;


import com.njaqn.itravel.aqnapp.service.adapter.SearchAutoCompleteAdapter;
import com.njaqn.itravel.aqnapp.service.adapter.SearchHistoryAdapter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

public class MySearchView extends LinearLayout implements OnClickListener {

	private ListView lvResult;
	private View view;
	private EditText etInput;
	private ImageView ivDelete;
	private Button btnBack;
	private ListView lvTips;
	private LinearLayout layout;
	
	private Context mContext;

	private SearchHistoryAdapter mHistoryAdapter;
	private SearchAutoCompleteAdapter mAutoCompleteAdapter;

	private SearchViewListener mListener;
	
	public MySearchView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * search view回调方法
	 */
	public interface SearchViewListener {

		/**
		 * 更新自动补全内容 
		 * @param text
		 * 传入补全后的文本
		 */
		void onRefreshAutoComplete(String text);

		/**
		 * 开始搜索
		 * @param text
		 * 传入输入框的文本
		 */
		void onSearch(String text);
	}

}
