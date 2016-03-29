package com.njaqn.itravel.aqnapp.service.adapter;

import java.util.HashMap;
import java.util.List;

import com.njaqn.itravel.aqnapp.AppInfo;
import com.njaqn.itravel.aqnapp.R;
import com.njaqn.itravel.aqnapp.service.BaseService;
import com.njaqn.itravel.aqnapp.service.BaseServiceImpl;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class AM003CityAdapter extends BaseAdapter {

	private List<HashMap<String,Object>> lstData;
	private LayoutInflater inflater;
	
	public AM003CityAdapter(Context context, List<HashMap<String,Object>> lstData)
	{
		this.lstData = lstData;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getCount() {
		return lstData.size();
	}

	@Override
	public Object getItem(int position) {
		return lstData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	public View getView(int position, View convertView, ViewGroup parent) 
	{	
		if(convertView == null)
		{
			convertView = inflater.inflate(R.drawable.am003_item_city, null); //生成条目界面对象
		}
		
		ImageView imgCityImage = (ImageView) convertView.findViewById(R.id.imgCityImage);
		TextView txtName = (TextView) convertView.findViewById(R.id.txtName);
		TextView txtKeywords = (TextView) convertView.findViewById(R.id.txtKeywords);
		//TextView txtFlag = (TextView) convertView.findViewById(R.id.txtFlag);
	
		HashMap<String, Object> data = (HashMap<String, Object>) lstData.get(position);
		String cityImage = data.get("cityImage").toString();
		
		int flag =  Integer.parseInt(data.get("flag").toString());
		
		BaseService bs = new BaseServiceImpl();
		
		if(!cityImage.equals(""))	
		{
			imgCityImage.setImageBitmap(bs.getImageByUrl(cityImage));
		}
		
		txtName.setText(data.get("name").toString());
		
//		if(flag==1) //已开通城市
//		{
//			txtFlag.setText("已开通");
//		}
//		else
//		{
//			txtFlag.setText("未开通");
//		}
		txtKeywords.setText(data.get("keywords").toString());
		return convertView;
	}

}