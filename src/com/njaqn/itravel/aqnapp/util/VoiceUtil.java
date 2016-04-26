package com.njaqn.itravel.aqnapp.util;


import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.SynthesizerListener;
import com.njaqn.itravel.aqnapp.R;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.widget.ImageView;

public class VoiceUtil extends Thread
{
    private Context ctx;
    private SpeechSynthesizer syn;
    private String talker = TALKER_XIAOYAN_WOMEN_ZH_EN;

    private int playMode = 0;

    private ImageView imagePalyView;
    private Animation animation;
    final static String TALKER_XIAOYAN_WOMEN_ZH_EN = "xiaoyan";
    final static String TALKER_XIAOYU_MEM_ZH_EN = "xiaoyu";
    final static String TALKER_XIAOQIAN_WOMEM_ZH_EN = "xiaoqian";
    final static String TALKER_XIAOMEI_WOMEM_ZH_EN_GD = "xiaomei";
    final static String TALKER_XIAOLIN_WOMEM_TW = "xiaolin";
    final static String TALKER_MARRY_WOMEM_EN = "marry";
    final static String TALKER_HENRY_MEN_EN = "Henry";
    
    private boolean isWait = false;

    public VoiceUtil(Context ctx)
    {
	this.ctx = ctx;
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
	syn.setParameter(SpeechConstant.PITCH, "50"); 
	syn.setParameter(SpeechConstant.VOLUME, "50"); 

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
	// 更改播放模式
	playMode = 1;
	if (imagePalyView != null && animation != null)
	{
	    imagePalyView.setBackgroundResource(R.drawable.am001_menu_c0);
	    imagePalyView.setAnimation(animation);
	    imagePalyView.startAnimation(animation);
	}

    }

    public void playPause()
    {
	if (playMode == 1)
	{
	    syn.pauseSpeaking();
	    if (imagePalyView != null && animation != null)
	    {
		imagePalyView.clearAnimation();
		imagePalyView.setBackgroundResource(R.drawable.am001_menu_c1);
	    }
	    playMode = 2;
	}
    }

    public void playResume()
    {
	if (playMode == 2)
	{
	    syn.resumeSpeaking();
	    if (imagePalyView != null && animation != null)
	    {
		imagePalyView.setBackgroundResource(R.drawable.am001_menu_c0);
		imagePalyView.setAnimation(animation);
		imagePalyView.startAnimation(animation);
	    }
	   playMode = 1;
	}
    }

    public void playStop()
    {
	if (syn.isSpeaking())
	    syn.stopSpeaking();
	playMode = 0;
    }

    public void setAudioVolume(int volume)
    {
	syn.setParameter(SpeechConstant.VOLUME, "" + volume); 
    }
//
//    public void run()
//    {
//	while (true)
//	{
//	    try
//	    {
//		Iterator<Map.Entry<String, PlayWordBean>> it = data
//			.getPlayWordMap().entrySet().iterator();
//		while (it.hasNext())
//		{
//		    Map.Entry<String, PlayWordBean> entry = it.next();
//		    String key = entry.getKey();
//		    PlayWordBean o = entry.getValue();
//
//		    if (isWait)
//			continue;
//
//		    synchronized (mTtsListener)
//		    {
//			playAudio(o.getWord());
//			o.setPlayCount(o.getPlayCount() - 1);
//			isWait = true;
//			mTtsListener.wait();
//
//			if (o.getPlayCount() == 0)
//			    it.remove();
//		    }
//		}
//
//		Thread.sleep(1000);
//	    }
//	    catch (Exception ex)
//	    {
//		Log.e("Err", ex.getMessage());
//	    }
//	}
//    }

    public void changImageMode(ImageView imgPlayView, Animation animation)
    {
	this.imagePalyView = imgPlayView;
	this.animation = animation;

    }
}
