/**
 * 作者：杨柳
 */

package com.njaqn.itravel.aqnapp.bm;

import com.njaqn.itravel.aqnapp.R;
import com.njaqn.itravel.aqnapp.R.id;
import com.njaqn.itravel.aqnapp.R.layout;
import com.njaqn.itravel.aqnapp.R.menu;
import com.njaqn.itravel.aqnapp.service.BmService;
import com.njaqn.itravel.aqnapp.service.BmServiceImpl;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;



public class BM001RegisterActivity extends Activity {
	private Intent it;
	private EditText txtRegisterUser;
	private EditText txtRegisterIdentifyingCode;
	private String phoneNumberTwo;

	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bm001_register);
		
		txtRegisterIdentifyingCode = (EditText)findViewById(R.id.txtRegisterIdentifyingCode);
		txtRegisterUser = (EditText)findViewById(R.id.txtRegisterUser);
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.bm_register, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) 
		{
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void BmRegisterTwoActivityOnClick(View v)
	{
		
		//BmServiceImpl bm = new BmServiceImpl();
		//String rt = bm.getValidateCodeFromSMS();
		
		if("1".equals("1"))
		{	
			Intent intent = new Intent(BM001RegisterActivity.this,BM002RegisterTwoActivity.class);
			Bundle data = new Bundle();
			phoneNumberTwo = txtRegisterUser.getText().toString();
			data.putString("userNoTwo",phoneNumberTwo);
			intent.putExtras(data);
			startActivity(intent);
			it = new Intent(this,BM002RegisterTwoActivity.class);
			startActivity(it);
		}
		else
		{
			Toast.makeText(getApplicationContext(), "验证码错误", Toast.LENGTH_LONG).show();
			txtRegisterIdentifyingCode.setText("");
			txtRegisterIdentifyingCode.setFocusable(true);
		}
		
	}
	
	public void sendRegisterIdentifyingCodeOnClick(View v)
	{
		BmServiceImpl bm = new BmServiceImpl();
		bm.getValidateCodeFromServer(txtRegisterUser.getText().toString());		
	}
	
	public void backLoginOnClick(View v)
	{
		it = new Intent(this,BM005LoginActivity.class);
		startActivity(it);
	}
}

