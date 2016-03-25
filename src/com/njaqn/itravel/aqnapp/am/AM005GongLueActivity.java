package com.njaqn.itravel.aqnapp.am;

import com.njaqn.itravel.aqnapp.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AM005GongLueActivity extends Activity {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_am_shouye_gonglue);
	}
	
	
	public void showcity1(View v)
	{
    	Intent city = new Intent(this,AM003CityChangeActivity.class);
    	startActivityForResult(city, 0);
	}
	
	//取得城市切换actuvity传来的数据
		protected void onActivityResult(int requestCode, int resultCode, Intent data) {
			String result = data.getStringExtra("result");
//			Button cityName= (Button)this.findViewById(R.id.txt_cityName);
//			cityName.setText(result);
			super.onActivityResult(requestCode, resultCode, data);
		}
		
		
}
