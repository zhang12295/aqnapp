package com.njaqn.itravel.aqnapp.bm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.njaqn.itravel.aqnapp.R;
import com.njaqn.itravel.aqnapp.R.id;
import com.njaqn.itravel.aqnapp.R.layout;
import com.njaqn.itravel.aqnapp.R.menu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class BM006HomePageActivity extends Activity {
	private ListView listView;
	private ListView listViewTwo;
	private Intent it;
	private LinearLayout currMemu;
	private LinearLayout[] menu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bm006_home_page);
				
		listView = (ListView) this.findViewById(R.id.lvMenu);
		listView.setOnItemClickListener(new ItemClickListener());

		menu = new LinearLayout[10];
		menu[0] = (LinearLayout) this.findViewById(R.id.menu0);
		menu[1] = (LinearLayout) this.findViewById(R.id.menu1);
		menu[2] = (LinearLayout) this.findViewById(R.id.menu2);
		menu[3] = (LinearLayout) this.findViewById(R.id.menu3);
		menu[4] = (LinearLayout) this.findViewById(R.id.menu4);
		menu[5] = (LinearLayout) this.findViewById(R.id.menu5);
		menu[6] = (LinearLayout) this.findViewById(R.id.menu6);
		menu[7] = (LinearLayout) this.findViewById(R.id.menu7);
		
		currMemu = menu[0];
		currMemu.setVisibility(0);
		menu[1].setVisibility(8);
		menu[2].setVisibility(8);
		menu[3].setVisibility(8);
		menu[4].setVisibility(8);
		menu[5].setVisibility(8);
		menu[6].setVisibility(8);
    	menu[7].setVisibility(8);
		
        show();
	}

	private final class ItemClickListener implements OnItemClickListener
	{
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) 
		{
			
			currMemu.setVisibility(8);
			
			switch(position)
			{
			   case 0:
					menu[0].setVisibility(0);
					currMemu = menu[0];
	                break;
	                
			   case 1:
					menu[1].setVisibility(0);
					currMemu = menu[1];
	                break;
	                
			   case 2:
					menu[2].setVisibility(0);
					currMemu = menu[2];
	                break;
	                
			   case 3:
					menu[3].setVisibility(0);
					currMemu = menu[3];
	                break;
	                
			   case 4:
					menu[4].setVisibility(0);
					currMemu = menu[4];
	                break;
	                
			   case 5:
					menu[5].setVisibility(0);
					currMemu = menu[5];
	                break;
	            
			   case 6:
					menu[6].setVisibility(0);
					currMemu = menu[6];
	                break;
	                
     		   case 7:
					menu[7].setVisibility(0);
					currMemu = menu[7];
	                break;
	 
	           default:
	                break;
			}
			
		}
			
			/*
			List<HashMap<String, Object>> data1 = new ArrayList<HashMap<String,Object>>();{
			for(int i=0;i<element.length;i++)
			{
				HashMap<String, Object> item1 = new HashMap<String, Object>();
				item1.put("element",element[i]);
				data1.add(item1);
			}
			SimpleAdapter right = new SimpleAdapter(getApplicationContext(), data1, R.layout.bm006_item,
					//哪个数据绑定到哪个控件上
					new String[]{"element"}, new int[]{R.id.bm_item});
			
			listViewTwo.setAdapter(right);
			
		}
		*/
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.bm006_home_page, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	String element[] = new String[]{"旅游规划","积分","购买景点","收藏","分享","订单","我的社区","设置"};
	private void show()
	{
		List<HashMap<String, Object>> data = new ArrayList<HashMap<String,Object>>();
		for(int i=0;i<element.length;i++)
		{
			HashMap<String, Object> item = new HashMap<String, Object>();
			item.put("element",element[i]);
			data.add(item);
		}
		SimpleAdapter adapter = new SimpleAdapter(this, data, R.layout.bm006_item,
				//哪个数据绑定到哪个控件上
				new String[]{"element"}, new int[]{R.id.bm_item});
		
		listView.setAdapter(adapter);	
	}
	
	public void btnCreditShopOnClick(View v)
	{
		Intent it = new Intent(this,BM005LoginActivity.class);
		startActivity(it);
	}
}

