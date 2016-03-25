package com.njaqn.itravel.aqnapp.am;

import com.njaqn.itravel.aqnapp.R;
import com.njaqn.itravel.aqnapp.R.id;
import com.njaqn.itravel.aqnapp.R.layout;
import com.njaqn.itravel.aqnapp.R.menu;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.ViewFlipper;
import android.widget.TabHost.TabSpec;

public class AM006SpotActivity extends Activity
{
    private TabHost tabHost;
    private ViewFlipper viewFlipper;
    private float startX;
    private Animation in_lefttoright;
    private Animation out_lefttoright;
    private Animation in_righttoleft;
    private Animation out_righttoleft;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.am006_spot);

	// 文本框的垂直滚动
	textView = (TextView) this.findViewById(R.id.textSpotSummaryContent);
	textView.setMovementMethod(ScrollingMovementMethod.getInstance());

	// TabHost代码
	tabHost = (TabHost) this.findViewById(R.id.tabhost);
	tabHost.setup();

	TabSpec tabSpec = tabHost.newTabSpec("page1");
	tabSpec.setIndicator(createTabView("查看评论"));
	tabSpec.setContent(R.id.page1);
	tabHost.addTab(tabSpec);

	tabSpec = tabHost.newTabSpec("page2");
	tabSpec.setIndicator(createTabView("添加评论"));
	tabSpec.setContent(R.id.page2);
	tabHost.addTab(tabSpec);

	tabSpec = tabHost.newTabSpec("page3");
	tabSpec.setIndicator(createTabView("查看设施"));
	tabSpec.setContent(R.id.page3);
	tabHost.addTab(tabSpec);

	tabHost.setCurrentTab(0);

	// 界面切换
	in_lefttoright = AnimationUtils.loadAnimation(this,
		R.anim.enter_lefttoright);
	out_lefttoright = AnimationUtils.loadAnimation(this,
		R.anim.out_lefttoright);

	in_righttoleft = AnimationUtils.loadAnimation(this,
		R.anim.enter_righttoleft);
	out_righttoleft = AnimationUtils.loadAnimation(this,
		R.anim.out_righttoleft);
	viewFlipper = (ViewFlipper) this.findViewById(R.id.viewFlipper);
    }

    private View createTabView(String name)
    {
	View tabView = getLayoutInflater().inflate(R.drawable.am006_tab, null);
	TextView textView = (TextView) tabView.findViewById(R.id.name);
	textView.setText(name);
	return tabView;
    }

    public boolean onTouchEvent(MotionEvent event)
    {
	if (event.getAction() == MotionEvent.ACTION_DOWN)
	{
	    startX = event.getX();
	}
	else if (event.getAction() == MotionEvent.ACTION_UP)
	{
	    float endX = event.getX();
	    if (endX > startX)
	    {
		viewFlipper.setInAnimation(in_lefttoright);
		viewFlipper.setOutAnimation(out_lefttoright);
		viewFlipper.showNext();// 显示下一页

	    }
	    else if (endX < startX)
	    {
		viewFlipper.setInAnimation(in_righttoleft);
		viewFlipper.setOutAnimation(out_righttoleft);
		viewFlipper.showPrevious();// 显示前一页
	    }
	    return true;
	}
	return super.onTouchEvent(event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(R.menu.am006_spot, menu);
	return true;
    }

    @Override
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
}
