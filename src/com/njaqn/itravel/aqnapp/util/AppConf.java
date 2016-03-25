package com.njaqn.itravel.aqnapp.util;

import java.util.Map;

import android.graphics.Bitmap;

import com.njaqn.itravel.aqnapp.service.AmService;
import com.njaqn.itravel.aqnapp.service.AmServiceImpl;

public class AppConf
{

    public static int GET_ALL_OP = 1;
    public static String VERSION = "1";
    public static String WELCOME = "2";
    public static String WELCOME_WAIT_TIMES = "3";
    public static String WELCOME_IMG = "4";
    public static String WELCOME_BG_COLOR = "5";

    private Map mapConf = null;

    public AppConf()
    {
	AmService am = new AmServiceImpl();
	mapConf = am.getAppConfig();
    }

    public String getAppConfig(String key)
    {
	if (mapConf != null)
	{
	    if (mapConf.containsKey(key))
		return mapConf.get(key).toString();
	}

	return null;
    }

    public Bitmap getWelcomeImage()
    {
	return new ImageUtil().getBitMap(getAppConfig(WELCOME_IMG));
    }
}
