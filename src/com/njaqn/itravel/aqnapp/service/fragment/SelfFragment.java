package com.njaqn.itravel.aqnapp.service.fragment;

import com.njaqn.itravel.aqnapp.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SelfFragment extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	    Bundle savedInstanceState)
    {

	View view = inflater.inflate(R.layout.am001_fragment_selfview, null);
	return view;
    }
}
