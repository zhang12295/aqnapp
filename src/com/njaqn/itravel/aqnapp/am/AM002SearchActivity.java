package com.njaqn.itravel.aqnapp.am;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.njaqn.itravel.aqnapp.AppInfo;
import com.njaqn.itravel.aqnapp.R;
import com.njaqn.itravel.aqnapp.listener.SearchViewListener;
import com.njaqn.itravel.aqnapp.service.AmService;
import com.njaqn.itravel.aqnapp.service.AmServiceImpl;
import com.njaqn.itravel.aqnapp.service.SearchService;
import com.njaqn.itravel.aqnapp.service.SearchServiceImpl;
import com.njaqn.itravel.aqnapp.service.adapter.SearchAutoCompleteAdapter;
import com.njaqn.itravel.aqnapp.service.adapter.SearchHistoryAdapter;
import com.njaqn.itravel.aqnapp.service.adapter.SearchResultAdatper;
import com.njaqn.itravel.aqnapp.util.SharedPreferencesUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class AM002SearchActivity extends Activity implements OnClickListener,
		SearchViewListener {

	private AppInfo app;
	private Handler handler;

	private ListView lvResults;
	private ListView lvTips;
	private View historyView;
	private View initView;
	private View childView;
	private Button btnClear;
	private EditText etInput;
	private ImageView ivDelete;
	private ImageButton btnSearch;
	private LinearLayout changedLayout;
	private LinearLayout.LayoutParams mLayoutParams;

	private SearchAutoCompleteAdapter autoCompleteAdapter;
	private SearchHistoryAdapter historyAdapter;
	private SearchResultAdatper resultAdapter;

	private String lastSearch = "";
	private List<HashMap<String, Object>> resultData;
	private List<HashMap<String, Object>> hotData;
	private List<HashMap<String, Object>> nearbyData;
	private List<String> historyData;
	private List<String> keys;
	private int historyDataCount = 0;
	private List<String> autoCompleteData;
	private SharedPreferencesUtil sp;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.am002_search);
		handler = new Handler();
		initViews();
		initData();

	}

	private void initData() {
		handler.post(new Runnable() {

			@Override
			public void run() {
				getHistoryData();
				getHotData();
				getNearbyData();
				for (int i = 0; i < hotData.size(); i++) {
					TextView textView = (TextView) findViewById(R.id.hot_spot1
							+ i);
					textView.setText(hotData.get(i).get("name").toString());
					textView.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							int position = v.getId() - R.id.hot_spot1;
							int id = (Integer) hotData.get(position).get("id");
							String nameString = hotData.get(position)
									.get("name").toString();
							Toast.makeText(AM002SearchActivity.this,
									nameString + "  ID:" + id,
									Toast.LENGTH_SHORT).show();
							String text = id+"";
														
							Intent i = new Intent(AM002SearchActivity.this,AM006SpotActivity.class);
							i.putExtra("id", text);
							i.putExtra("name", nameString);
							startActivity(i);
							
						}
					});
				}
				for (int i = 0; i < nearbyData.size(); i++) {
					TextView textView = (TextView) findViewById(R.id.nearby_spot1
							+ i);
					textView.setText(nearbyData.get(i).get("name").toString());
					textView.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							int position = v.getId() - R.id.nearby_spot1;
							int id = (Integer) nearbyData.get(position).get(
									"id");
							String nameString = nearbyData.get(position)
									.get("name").toString();
							Toast.makeText(AM002SearchActivity.this,
									nameString + "  ID:" + id,
									Toast.LENGTH_SHORT).show();
							String text = id+"";
							
							Intent i = new Intent(AM002SearchActivity.this,AM006SpotActivity.class);
							i.putExtra("id", text);
							i.putExtra("name", nameString);
							startActivity(i);
						}
					});
				}
			}
		});

	}

	private void getNearbyData() {
		app = (AppInfo) getApplication();
		double longitude = app.getLongitude();
		double latitude = app.getLatitude();
		SearchService ss = new SearchServiceImpl();
		nearbyData = ss.getNearbySpot(longitude, latitude, 9);
	}

	private void getHotData() {
		AmService as = new AmServiceImpl();
		hotData = as.getHotSpot(9);
	}

	private void getResultData(String search) {

		if (search != null) {
			if (!lastSearch.equals(search) && !search.equals("")) {
				if (resultData != null) {
					resultData.clear();
				}
				SearchService ss = new SearchServiceImpl();
				resultData = ss.searchInSpotsForResults(search);
				lastSearch = search;
				lvResults.setAdapter(null);
				resultAdapter = new SearchResultAdatper(this, resultData,
						lvResults);
				lvResults.setAdapter(resultAdapter);
			}
		}

	}

	private void getAutoCompleteData(String search) {
		if (autoCompleteData == null) {
			autoCompleteData = new ArrayList<String>();
		} else {
			// 根据text 获取auto data
			autoCompleteData.clear();
			// service....
			List<String> dbData = new ArrayList<String>();
			SearchService ss = new SearchServiceImpl();
			dbData = ss.searchInSpotsForComplete(search);
			if (dbData != null) {
				for (int i = 0; i < dbData.size(); i++) {
					autoCompleteData.add(dbData.get(i));
				}
			}
		}
		if (autoCompleteAdapter == null) {
			autoCompleteAdapter = new SearchAutoCompleteAdapter(this,
					autoCompleteData);
		} else {
			autoCompleteAdapter.notifyDataSetChanged();
		}

	}

	private void getHistoryData() {
		sp = new SharedPreferencesUtil(this, "historySearchData",
				Activity.MODE_PRIVATE);
		historyDataCount = sp.getDataCount();
		keys = new ArrayList<String>();
		for (int i = 0; i < historyDataCount; i++) {
			keys.add("historySearchData" + i);
		}
		historyData = sp.getData(keys);
		for (int i = historyData.size() - 1; i > -1; i--) {
			if (historyData.get(i).equals("")) {
				historyData.remove(i);
			}
		}
		historyAdapter = new SearchHistoryAdapter(this, historyData);
	}

	private void initViews() {
		mLayoutParams = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		initView = findViewById(R.id.init_view);

		historyView = LayoutInflater.from(this).inflate(
				R.layout.search_history_view, null);
		btnClear = (Button) historyView
				.findViewById(R.id.search_btn_recent_clear);
		btnClear.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				historyDataCount = 0;
				historyData.clear();
				keys.clear();
				historyAdapter.notifyDataSetChanged();
			}
		});
		childView = initView;
		changedLayout = (LinearLayout) findViewById(R.id.change_layout);
		etInput = (EditText) findViewById(R.id.search_et_input);
		ivDelete = (ImageView) findViewById(R.id.search_iv_delete);
		btnSearch = (ImageButton) findViewById(R.id.search_btn_search);

		lvTips = (ListView) historyView.findViewById(R.id.search_lv_recent);
		lvTips.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View view,
					int i, long l) {
				String text = lvTips.getAdapter().getItem(i).toString();
				etInput.setText(text);
				etInput.setSelection(text.length());
				notifyStartSearching(text);
			}
		});
		lvResults = new ListView(this);
		lvResults.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View view,
					int position, long l) {
//				Toast.makeText(AM002SearchActivity.this, position + "",
//						Toast.LENGTH_SHORT).show();
				HashMap<String, Object> data = (HashMap<String, Object>) lvResults
						.getItemAtPosition(position);
				String text = data.get("id").toString();
				String spotName = data.get("name").toString();
				
				Intent i = new Intent(AM002SearchActivity.this,AM006SpotActivity.class);
				i.putExtra("id", text);
				i.putExtra("name", spotName);
				startActivity(i);

			}
		});

		ivDelete.setOnClickListener(this);
		btnSearch.setOnClickListener(this);

		etInput.addTextChangedListener(new EditChangedListener());
		etInput.setOnClickListener(this);
		etInput.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_SEARCH) {
					notifyStartSearching(etInput.getText().toString());
				}
				return true;
			}
		});
	}

	private class EditChangedListener implements TextWatcher {

		@Override
		public void afterTextChanged(Editable s) {

		}

		@Override
		public void beforeTextChanged(CharSequence charSequence, int start,
				int count, int after) {

		}

		@Override
		public void onTextChanged(CharSequence charSequence, int start,
				int before, int count) {
			if (!"".equals(charSequence.toString())) {
				ivDelete.setVisibility(View.VISIBLE);
				if (autoCompleteAdapter != null
						&& lvTips.getAdapter() != autoCompleteAdapter) {
					lvTips.setAdapter(autoCompleteAdapter);
					btnClear.setVisibility(View.GONE);
				}
				// 更新autoComplete数据
				onRefreshAutoComplete(charSequence + "");
			} else {
				ivDelete.setVisibility(View.GONE);
				if (historyAdapter != null) {
					lvTips.setAdapter(historyAdapter);
					btnClear.setVisibility(View.VISIBLE);
				}
			}

		}

	}

	/**
	 * 通知监听者 进行搜索操作
	 * 
	 * @param text
	 */
	private void notifyStartSearching(String text) {
		if (!text.equals("")) {
			if (childView != lvResults) {
				changedLayout.removeView(childView);
				changedLayout.addView(lvResults, mLayoutParams);
				childView = lvResults;
				Log.i("childView", "lvResults" + childView.hashCode());
			}
			onSearch(text);
		} else {
			if (childView != initView) {
				changedLayout.removeView(childView);
				changedLayout.addView(initView, mLayoutParams);
				childView = initView;
				Log.i("childView", "initView" + childView.hashCode());
			}
		}
		// 隐藏软键盘
		InputMethodManager imm = (InputMethodManager) this
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.search_et_input:
			if (childView != historyView) {
				changedLayout.removeView(childView);
				changedLayout.addView(historyView, mLayoutParams);
				childView = historyView;
				Log.i("childView", "historyView" + childView.hashCode());
				if (((EditText) v).getText().toString().equals("")) {
					lvTips.setAdapter(historyAdapter);
					btnClear.setVisibility(View.VISIBLE);
				} else {
					lvTips.setAdapter(autoCompleteAdapter);
					btnClear.setVisibility(View.GONE);
				}
			}
			break;
		case R.id.search_iv_delete:
			etInput.setText("");
			if (childView != historyView) {
				changedLayout.removeView(childView);
				changedLayout.addView(historyView, mLayoutParams);
				childView = historyView;
				Log.i("childView", "historyView" + childView.hashCode());

				lvTips.setAdapter(historyAdapter);
				btnClear.setVisibility(View.VISIBLE);

			}

			ivDelete.setVisibility(View.GONE);
			InputMethodManager imm = (InputMethodManager) this
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
			break;
		case R.id.search_btn_search:
			notifyStartSearching(etInput.getText().toString());
			break;
		}
	}

	@Override
	public void onRefreshAutoComplete(String text) {
		getAutoCompleteData(text);
	}

	@Override
	public void onSearch(String text) {
		if (childView != lvResults) {
			changedLayout.removeView(childView);
			changedLayout.addView(lvResults, mLayoutParams);
			childView = lvResults;
			Log.i("childView", "lvResults" + childView.hashCode());
		}
		getResultData(text);

		// 第一次获取结果 还未配置适配器
		if (lvResults.getAdapter() == null) {
			// 获取搜索数据 设置适配器
			lvResults.setAdapter(resultAdapter);
		} else {
			// 更新搜索数据
			resultAdapter.notifyDataSetChanged();
		}
		if (resultData.size() > 0) {
			Toast.makeText(this, "完成搜索", Toast.LENGTH_SHORT).show();
		}else {
			Toast.makeText(this, "没有找到相关景区", Toast.LENGTH_SHORT).show();
		}
		

		handler.post(new Runnable() {

			@Override
			public void run() {
				for (int i = 0; i < historyDataCount; i++) {
					if (lastSearch.equals(historyData.get(i))) {
						historyData.remove(i);
						historyDataCount--;
						keys.remove(historyDataCount);
					}
				}
				historyData.add(0, lastSearch);
				keys.add("historySearchData" + historyDataCount);
				historyDataCount++;
				historyAdapter.notifyDataSetChanged();
				sp.saveData(keys, historyData);
			}
		});

	}

	public void returnOnClick(View v) {
		this.finish();
	}

}
