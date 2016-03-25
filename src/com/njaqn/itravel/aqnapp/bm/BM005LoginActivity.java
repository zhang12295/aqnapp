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
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Toast;

public class BM005LoginActivity extends Activity 
{
	private Intent it;
	private EditText txtUser;
	private EditText txtPasswd;
    private CheckBox chkIsShow;
    private Button btnLogin;

	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bm005_login);
		
		txtPasswd = (EditText)findViewById(R.id.txtPasswd);
		txtUser = (EditText)findViewById(R.id.txtUser);
		chkIsShow = (CheckBox)findViewById(R.id.chkIsShow);
		btnLogin = (Button)findViewById(R.id.btnLogin);

		txtPasswd.setTransformationMethod(PasswordTransformationMethod.getInstance());
		chkIsShow.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
	         public void onCheckedChanged(CompoundButton arg0,boolean arg1) 
	         {
	            if(chkIsShow.isChecked())
	            	txtPasswd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());//设置EditText的密码为可见的
	            else 
	            	txtPasswd.setTransformationMethod(PasswordTransformationMethod.getInstance());//设置密码为隐藏的
	         }
		});
	}

	public boolean onCreateOptionsMenu(Menu menu) 
	{
		getMenuInflater().inflate(R.menu.deng_lu, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) 
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void backLoginOnClick(View v)
	{
		finish();
	    it = new Intent(this,AM001HomePageActivity.class);
	    startActivity(it);
	}
	
	public void btnloginOnClick(View v)
	{
		BmService bm = new BmServiceImpl();
		String user = txtUser.getText().toString();
		String password = txtPasswd.getText().toString();
		
		if(user.trim().equals(""))
		{
			Toast.makeText(getApplicationContext(), "用户名不能为空，请输入用户名!", Toast.LENGTH_LONG).show();
			txtUser.setFocusable(true);
			return;
		}
		
		if(password.trim().equals(""))
		{
			Toast.makeText(getApplicationContext(), "登陆密码不能为空，请输入用户名!", Toast.LENGTH_LONG).show();
			txtPasswd.setFocusable(true);
			return;
		}
			
		if(bm.login(user, password))
		{
			finish();
		//	it = new Intent(this,PersonalCenterActivity.class);
		//	startActivity(it);
		}
		else
		{
			Toast.makeText(getApplicationContext(), "用户名或密码错误", Toast.LENGTH_LONG).show();
			txtPasswd.setText("");
			txtPasswd.setFocusable(true);
		}
	}
	
	public void btnChangePasswdOnClick(View v)
	{
		it = new Intent(this,BM003ResetPasswdActivity.class);
		startActivity(it);
	}
	
	public void btnBmRegisterActivityOnClick(View v)
	{
		it = new Intent(this,BM001RegisterActivity.class);
		startActivity(it);
	}
}
