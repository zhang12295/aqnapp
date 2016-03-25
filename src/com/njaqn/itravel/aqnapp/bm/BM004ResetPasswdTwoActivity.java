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

public class BM004ResetPasswdTwoActivity extends Activity 
{
	private Intent it;
	private EditText txtNewPasswd;
	private EditText txtConfirmPasswd;
	private String userNo;

	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bm004_reset_passwd_two);
		
		txtNewPasswd = (EditText)findViewById(R.id.txtNewPasswd);
		txtConfirmPasswd = (EditText)findViewById(R.id.txtConfirmPasswd);
	}

	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.bm_reset_passwd_two, menu);
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
		
		if((txtNewPasswd.getText().toString()).equals(txtConfirmPasswd.getText().toString()))
		{
			if(txtNewPasswd.getText().toString() == null  || txtNewPasswd.getText().toString().length() == 0 )
			{
				Toast.makeText(getApplicationContext(), "请重新输入", Toast.LENGTH_LONG).show();
				txtNewPasswd.setText("");
				txtNewPasswd.setFocusable(true);
			}
			else
			{
			    bm.postZhuceInfo(userNo,txtNewPasswd.getText().toString());
			    it = new Intent(this,AM001HomePageActivity.class);//转换到修改密码成功页面
			    startActivity(it);
			}
		}
		else
		{
			Toast.makeText(getApplicationContext(), "两次输入的密码不同", Toast.LENGTH_LONG).show();
			txtConfirmPasswd.setText("");
			txtConfirmPasswd.setFocusable(true);
		}			
	}
	
	public void backResetPassWdOnClick(View v)
	{
		it = new Intent(this,BM003ResetPasswdActivity.class);
        startActivity(it);
	}
}
