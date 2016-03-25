package com.njaqn.itravel.aqnapp.am;



import com.njaqn.itravel.aqnapp.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;

public class AM004DownloadActivity extends Activity {
	
	TabHost tabHost;
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.am004_download_list);
		
		tabHost = (TabHost)this.findViewById(R.id.tabhost);
		tabHost.setup();
		
		TabSpec ts = tabHost.newTabSpec("page1");
		//ts.setIndicator("首页",getResources().getDrawable(R.drawable.i1));
		ts.setIndicator(createview("第一页"));
		ts.setContent(R.id.page1);
		tabHost.addTab(ts);  
		
		ts = tabHost.newTabSpec("page2");
		//ts.setIndicator("首页2",getResources().getDrawable(R.drawable.i2));
		ts.setIndicator(createview("第二页"));
		ts.setContent(R.id.page2); 
		
	
		tabHost.addTab(ts);
		tabHost.setCurrentTab(0);
		
		
		
		//标签页改变的监听事件
		tabHost.setOnTabChangedListener(
		        new OnTabChangeListener(){
		          public void onTabChanged(String tabId) {
		            //do what you want to do
		            Toast.makeText(getApplicationContext(), "TabId=" + tabId, Toast.LENGTH_LONG).show();
		          }
		        }
		      );	          
	}
	
	private View createview(String string) {
		View tabview = getLayoutInflater().inflate(R.drawable.am004_download_tabtext_style,null);
		TextView textView = (TextView) tabview.findViewById(R.id.name);
		textView.setText(string);
		return tabview;
	}
}
