package com.njaqn.itravel.aqnapp.service.fragment;

import com.njaqn.itravel.aqnapp.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CommunityFragment extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	    Bundle savedInstanceState)
    {
	View v = inflater.inflate(R.layout.am001_fragment_communityview, null);
	return v;
    }
}
