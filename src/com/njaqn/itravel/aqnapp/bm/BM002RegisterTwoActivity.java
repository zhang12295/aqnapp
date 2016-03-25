/**
 * 作者：杨柳
 */
package com.njaqn.itravel.aqnapp.bm;

import com.njaqn.itravel.aqnapp.R;
import com.njaqn.itravel.aqnapp.R.id;
import com.njaqn.itravel.aqnapp.R.layout;
import com.njaqn.itravel.aqnapp.R.menu;
import com.njaqn.itravel.aqnapp.am.AM001HomePageActivity;
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

public class BM002RegisterTwoActivity extends Activity 
{
	private Intent it;
	private EditText txtRegisterNewPasswd;
	private EditText txtRegisterConfirmPasswd;
	private String userNoTwo;

	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bm002_register_two);
		
		txtRegisterNewPasswd = (EditText)findViewById(R.id.txtRegisterNewPasswd);
		txtRegisterConfirmPasswd = (EditText)findViewById(R.id.txtRegisterConfirmPasswd);
	}

	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.bm_register_two, menu);
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
	
	public void postZhuceInfoOnClick(View v)
	{
		BmService bm = new BmServiceImpl();
		
		if((txtRegisterNewPasswd.getText().toString()).equals(txtRegisterConfirmPasswd.getText().toString()))
		{
			if(txtRegisterNewPasswd.getText().toString() == null  || txtRegisterNewPasswd.getText().toString().length() == 0 )
			{
				Toast.makeText(getApplicationContext(), "请重新输入", Toast.LENGTH_LONG).show();
				txtRegisterConfirmPasswd.setText("");
				txtRegisterConfirmPasswd.setFocusable(true);
			}
			else
			{
		        bm.postZhuceInfo(userNoTwo,txtRegisterNewPasswd.getText().toString());
		        it = new Intent(this,AM001HomePageActivity.class);//转换到修改密码成功页面
		        startActivity(it);
			}
		}
		else
		{
			Toast.makeText(getApplicationContext(),"两次输入的密码不同", Toast.LENGTH_LONG).show();
			txtRegisterConfirmPasswd.setText("");
			txtRegisterConfirmPasswd.setFocusable(true);
		}
			
	}
	
	public void backregisterOnClick(View v)
	{
		it = new Intent(this,BM001RegisterActivity.class);
        startActivity(it);
	}
}
