package com.njaqn.itravel.aqnapp.util;

import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.SynthesizerListener;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class VoiceUtil extends Activity
{

	public boolean initVoice()
	{
		SpeechUtility.createUtility(getApplicationContext(),  "appid=55379711");
		return true;
	}
	

}
