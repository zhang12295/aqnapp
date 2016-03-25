package com.njaqn.itravel.aqnapp;

import java.util.Timer;
import java.util.TimerTask;

import com.njaqn.itravel.aqnapp.am.ShouYeActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends Activity 
{
	private Intent it;
	private TimerTask task;
	
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		it = new Intent(this,ShouYeActivity.class);
		
		Timer timer=new Timer();
		task = new TimerTask()
		{
			public void run() 
			{
				finish();
				startActivity(it);
			}
		};
		
		timer.schedule(task, 5000);
	}
	
	public void openActivity(View v)
	{
		task.cancel();
		finish();
		it=new Intent(this,ShouYeActivity.class);
		startActivity(it);
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		
		int id = item.getItemId();
		if (id == R.id.action_settings) 
		{
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
