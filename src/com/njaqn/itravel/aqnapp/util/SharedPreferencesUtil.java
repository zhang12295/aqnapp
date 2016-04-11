package com.njaqn.itravel.aqnapp.util;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesUtil {

	private String fileName;
	private int mode;
	private Context context;

	public SharedPreferencesUtil(Context context, String fileName, int mode) {
		this.context = context;
		this.fileName = fileName;
		this.mode = mode;
	}

	public void saveData(List<String> keys, List<String> lst) {
		SharedPreferences sp = context.getSharedPreferences(fileName, mode);
		SharedPreferences.Editor editor = sp.edit();
		editor.putInt("count", lst.size());
		for (int i = 0; i < lst.size(); i++) {
			editor.putString(keys.get(i), lst.get(i));
		}
		editor.commit();
	}

	public int getDataCount() {
		SharedPreferences sp = context.getSharedPreferences(fileName, mode);
		int count = sp.getInt("count", 0);
		return count;
	}

	public List<String> getData(List<String> keys) {
		List<String> lst = new ArrayList<String>();
		SharedPreferences sp = context.getSharedPreferences(fileName, mode);
		for (int i = 0; i < keys.size(); i++) {
			String data = sp.getString(keys.get(i), "");
			lst.add(data);
		}
		return lst;
	}
}
