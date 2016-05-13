package com.njaqn.itravel.aqnapp.bm;

import com.njaqn.itravel.aqnapp.R;
import com.njaqn.itravel.aqnapp.R.array;
import com.njaqn.itravel.aqnapp.R.id;
import com.njaqn.itravel.aqnapp.R.layout;
import com.njaqn.itravel.aqnapp.util.FileUtil;
import com.njaqn.itravel.aqnapp.util.ImageFileUtil;

import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class BM007SettingAcitivity extends Activity implements OnClickListener {

	private String[] mCloudVoicersEntries;
	private String[] mCloudVoicersValue ;
	
	private RelativeLayout mRelSpeeker;
	private RelativeLayout mRelSoftFeedback;
	private RelativeLayout mRelUpdate;
	private RelativeLayout mRelAboutUs;
	private RelativeLayout mRelClearCache;
	
	private Switch mSwitch;
	private TextView mClearCache;
	private final static String FOLDER_NAME = "/AQN";  
	
	private ImageFileUtil imageFileUtil;
	private FileUtil fileUtil;
	private String speaker;
	private int selectedNum;
	SharedPreferences settingPreferences;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bm007_setting);
		initVeiw();
		String i = fileUtil.getAutoFileOrFilesSize(imageFileUtil.getStorageDirectory());
		mClearCache.setText(i);
	}

	
	private void initVeiw() {
		mRelAboutUs = (RelativeLayout) findViewById(R.id.rel_aboutus);
		mRelSoftFeedback = (RelativeLayout) findViewById(R.id.rel_softfeedback);
		mRelSpeeker = (RelativeLayout) findViewById(R.id.rel_speekersetting);
		mRelUpdate = (RelativeLayout) findViewById(R.id.rel_versionupdate);
		mRelClearCache = (RelativeLayout) findViewById(R.id.rel_clearcache);
		mSwitch = (Switch) findViewById(R.id.switch1);
		mClearCache = (TextView) findViewById(R.id.tv_cachesize);
		findViewById(R.id.im_setting_back).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				returnOnClick();
			}
		});
		
		imageFileUtil = new ImageFileUtil(getApplicationContext());
		
		mRelAboutUs.setOnClickListener(this);
		mRelSoftFeedback.setOnClickListener(this);
		mRelSpeeker.setOnClickListener(this);
		mRelUpdate.setOnClickListener(this);
		mRelClearCache.setOnClickListener(this);
		
		mCloudVoicersEntries = getResources().getStringArray(R.array.voicer_cloud_entries);
		mCloudVoicersValue = getResources().getStringArray(R.array.voicer_cloud_values);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rel_aboutus:
			
			break;
		case R.id.rel_softfeedback:
			sendFeedback();
			break;
		case R.id.rel_speekersetting:
			selectSpeaker();
			break;
		case R.id.rel_versionupdate:
			
			break;
		case R.id.rel_clearcache:
			clearCache();
			break;
		default:
			break;
		}
		
	}

	private void clearCache() {
		 Dialog dialog = new AlertDialog.Builder(this).setTitle("确定删除？")
				 			.setPositiveButton("确定",new DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
							
									imageFileUtil.deleteFile();
									Toast.makeText(BM007SettingAcitivity.this, "缓存已清除", Toast.LENGTH_SHORT).show();
									mClearCache.setText(fileUtil.getAutoFileOrFilesSize(imageFileUtil.getStorageDirectory()));
								}
							}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
									// TODO Auto-generated method stub
									
								}
							}).create();
		 dialog.show();
	}

	private void sendFeedback() {
		LayoutInflater layoutInflater = LayoutInflater.from(this);
		final View feView = layoutInflater.inflate(R.layout.bm007_setting_feedback, null);
		Dialog dialog = new AlertDialog.Builder(this).setTitle("反馈").setView(feView).setPositiveButton("发送", new DialogInterface.OnClickListener() {
			EditText feTextView = (EditText) feView.findViewById(R.id.et_feedback);
			@Override
			public void onClick(DialogInterface dialog, int which) {
				String feedbackString = feTextView.getText().toString();
				if(feedbackString.equals(null)){
					dialog.dismiss();
				}else{
					//发送反馈至服务器
				}
				
			}
		}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		}).create();
		dialog.show();
	}

	private void selectSpeaker() {
		new AlertDialog.Builder(this).setTitle("选择发音人")
		.setSingleChoiceItems(mCloudVoicersEntries, // 单选框有几项,各是什么名字
				selectedNum, // 默认的选项
				new DialogInterface.OnClickListener() { // 点击单选框后的处理
			public void onClick(DialogInterface dialog,
					int which) { // 点击了哪一项
				speaker = mCloudVoicersValue[which];
				settingPreferences = getSharedPreferences("appInfo",Activity.MODE_PRIVATE);
				SharedPreferences.Editor editor = settingPreferences.edit();
				editor.putString("speaker", speaker);
				editor.commit();
				Toast.makeText(BM007SettingAcitivity.this, "设置成功", Toast.LENGTH_SHORT).show();
				selectedNum = which;
				dialog.dismiss();
			}
		}).show();
		
	}

	public void returnOnClick() {
		this.finish();
	}
}
