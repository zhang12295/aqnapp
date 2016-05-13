package com.njaqn.itravel.aqnapp.service.fragment;

import com.baidu.platform.comapi.map.r;
import com.njaqn.itravel.aqnapp.R;
import com.njaqn.itravel.aqnapp.am.AM001HomePageActivity;
import com.njaqn.itravel.aqnapp.bm.BM007SettingAcitivity;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SelfFragment extends Fragment implements OnClickListener{
	private View root;
	private RelativeLayout ReltOrder;
	private RelativeLayout ReltSpot;
	private RelativeLayout ReltSetting;
	private RelativeLayout ReltFeedback;
	private TextView mlogin;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	    Bundle savedInstanceState)
    {

	root = inflater.inflate(R.layout.am001_fragment_selfview, null);
	initView();
	return root;
    }
    
	private void initView() {
		mlogin = (TextView) root.findViewById(R.id.tv_login);
		ReltFeedback= (RelativeLayout) root.findViewById(R.id.rl_feedback);
		ReltOrder = (RelativeLayout) root.findViewById(R.id.rl_order);
		ReltSetting = (RelativeLayout) root.findViewById(R.id.rl_setting);
		ReltSpot= (RelativeLayout) root.findViewById(R.id.rl_spot);
		
		ReltSetting.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.rl_setting:
			Intent intent = new Intent(this.getActivity(),BM007SettingAcitivity.class);
			startActivity(intent);
			break;

		default:
			break;
		}
	}
}
