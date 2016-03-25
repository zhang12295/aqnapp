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



public class BM003ResetPasswdActivity extends Activity 
{
	private Intent it;
	private EditText txtResetPasswdUser;
	private EditText txtIdentifyingCode;
	private String phoneNumber;

	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bm003_reset_passwd);
		
		txtIdentifyingCode = (EditText)findViewById(R.id.txtIdentifyingCode);
		txtResetPasswdUser = (EditText)findViewById(R.id.txtResetPasswdUser);
	}

	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.bm_reset_passwd, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) 
	{
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
	
	public void BmResetPasswdTwoActivityOnClick(View v)
	{
		
		//BmServiceImpl bm = new BmServiceImpl();
		//String rt = bm.getValidateCodeFromSMS();
		
		if("1".equals("1"))
		{	
			Intent intent = new Intent(BM003ResetPasswdActivity.this,BM004ResetPasswdTwoActivity.class);
			Bundle data = new Bundle();
			phoneNumber = txtResetPasswdUser.getText().toString();
			data.putString("userNo",phoneNumber);
			intent.putExtras(data);
			startActivity(intent);
			it = new Intent(this,BM004ResetPasswdTwoActivity.class);
			startActivity(it);
		}
		else
		{
			Toast.makeText(getApplicationContext(), "验证码错误", Toast.LENGTH_LONG).show();
			txtIdentifyingCode.setText("");
			txtIdentifyingCode.setFocusable(true);
		}
		
	}
	
	public void sendIdentifyingCodeOnClick(View v)
	{
		BmServiceImpl bm = new BmServiceImpl();
		bm.getValidateCodeFromServer(txtResetPasswdUser.getText().toString());		
	}
	
	public void backLoginOnClick(View v)
	{
		it = new Intent(this,BM005LoginActivity.class);
		startActivity(it);
	}
}

