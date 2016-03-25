package com.njaqn.itravel.aqnapp.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.baidu.location.LocationClient;
import com.baidu.mapapi.map.BaiduMap;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.SynthesizerListener;
import com.njaqn.itravel.aqnapp.service.bean.PlayWordBean;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.DropBoxManager.Entry;
import android.util.Log;

public class VoiceUtil extends Thread
{
    private Context ctx;
    private SpeechSynthesizer syn;
    private String talker = TALKER_XIAOYAN_WOMEN_ZH_EN;

    private int playMode = 0;
    
    final static String TALKER_XIAOYAN_WOMEN_ZH_EN = "xiaoyan";
    final static String TALKER_XIAOYU_MEM_ZH_EN = "xiaoyu";
    final static String TALKER_XIAOQIAN_WOMEM_ZH_EN = "xiaoqian";
    final static String TALKER_XIAOMEI_WOMEM_ZH_EN_GD = "xiaomei";
    final static String TALKER_XIAOLIN_WOMEM_TW = "xiaolin";
    final static String TALKER_MARRY_WOMEM_EN = "marry";
    final static String TALKER_HENRY_MEN_EN = "Henry";

    private PlayAuditData data;
    private boolean isWait = false;

    public VoiceUtil(Context ctx)
    {
	this.ctx = ctx;
	initVoice();
    }

    public VoiceUtil(Context ctx, PlayAuditData data)
    {
	this.ctx = ctx;
	this.data = data;
	initVoice();
    }

    public boolean isSpeaking()
    {
	return syn.isSpeaking();
    }

    public int getPlayMode()
    {
	return playMode;
    }
    
    public void setPlayMode(int playMode)
    {
	this.playMode = playMode;
    }

    public VoiceUtil(Context ctx, String talker)
    {
	this.ctx = ctx;
	this.talker = talker;
	initVoice();
    }

    public void setTalker(String talker)
    {
	this.talker = talker;
    }

    private boolean initVoice()
    {
	SpeechUtility.createUtility(ctx, "appid=55379711");
	syn = SpeechSynthesizer.createSynthesizer(ctx, initListener);

	syn.setParameter(SpeechConstant.VOICE_NAME, talker);
	syn.setParameter(SpeechConstant.PITCH, "50"); // 设置音调
	syn.setParameter(SpeechConstant.VOLUME, "50"); // 设置音量

	return true;
    }

    private InitListener initListener = new InitListener()
    {
	public void onInit(int code)
	{
	    Log.d("mySynthesizer:", "InitListener init() code=" + code);

	}
    };

    private SynthesizerListener mTtsListener = new SynthesizerListener()
    {
	public void onSpeakBegin()
	{
	}

	public void onSpeakPaused()
	{
	}

	public void onSpeakResumed()
	{
	}

	public void onBufferProgress(int percent, int beginPos, int endPos,
		String info)
	{
	}

	public void onSpeakProgress(int percent, int beginPos, int endPos)
	{
	}

	public void onCompleted(SpeechError error)
	{
	    synchronized (mTtsListener)
	    {
		if (isWait)
		{
		    mTtsListener.notify();
		    isWait = false;
		}
	    }
	}

	public void onEvent(int arg0, int arg1, int arg2, Bundle arg3)
	{
	}
    };

    public void playAudio(String word)
    {
	if (word == null)
	    return;

	syn.startSpeaking(word, mTtsListener);
	// 表示当前正在播放
	playMode = 1;
    }

    public void playPause()
    {
	if (playMode == 1)
	{
	    syn.pauseSpeaking();
	    // 表示暂停状态
	    playMode = 2;
	}
    }

    public void playResume()
    {
	if (playMode == 2)
	{
	    syn.resumeSpeaking();
	    playMode = 1;
	}
    }

    public void playStop()
    {
	if (syn.isSpeaking())
	    syn.stopSpeaking();
	//表示开始停止播放
	playMode = 0;
    }

    public void setAudioVolume(int volume)
    {
	syn.setParameter(SpeechConstant.VOLUME, "" + volume); // 设置音调
    }

    /***
     * 该线程循环播放 Map中的 播放内容
     */
    public void run()
    {
	while (true)
	{
	    try
	    {
		Iterator<Map.Entry<String, PlayWordBean>> it = data
			.getPlayWordMap().entrySet().iterator();
		while (it.hasNext())
		{
		    Map.Entry<String, PlayWordBean> entry = it.next();
		    String key = entry.getKey();
		    PlayWordBean o = entry.getValue();

		    if (isWait)
			continue;

		    synchronized (mTtsListener)
		    {
			playAudio(o.getWord());
			o.setPlayCount(o.getPlayCount() - 1);
			isWait = true;
			mTtsListener.wait();

			if (o.getPlayCount() == 0)
			    it.remove();
		    }
		}

		Thread.sleep(1000);
	    }
	    catch (Exception ex)
	    {
		Log.e("Err", ex.getMessage());
	    }
	}
    }
}
