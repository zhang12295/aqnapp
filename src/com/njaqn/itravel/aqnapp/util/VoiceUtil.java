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
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.widget.ImageView;

public class VoiceUtil extends Thread {
	private Context ctx;
	private SpeechSynthesizer syn;
	private String lastWordString = "";

	public SpeechSynthesizer getSyn() {
		return syn;
	}

	private String talker = TALKER_XIAOYAN_WOMEN_ZH_EN;

	private int playMode = 0;

	private ImageView imagePlayView;
	private Animation animation;
	final static String TALKER_XIAOYAN_WOMEN_ZH_EN = "xiaoyan";
	final static String TALKER_XIAOYU_MEM_ZH_EN = "xiaoyu";
	final static String TALKER_XIAOQIAN_WOMEM_ZH_EN = "xiaoqian";
	final static String TALKER_XIAOMEI_WOMEM_ZH_EN_GD = "xiaomei";
	final static String TALKER_XIAOLIN_WOMEM_TW = "xiaolin";
	final static String TALKER_MARRY_WOMEM_EN = "marry";
	final static String TALKER_HENRY_MEN_EN = "Henry";

	private boolean isWait = false;

	public VoiceUtil(Context ctx) {
		this.ctx = ctx;
		initVoice();
	}

	public boolean isSpeaking() {
		return syn.isSpeaking();
	}

	public int getPlayMode() {
		return playMode;
	}

	public void setPlayMode(int playMode) {
		this.playMode = playMode;
	}

	public VoiceUtil(Context ctx, String talker) {
		this.ctx = ctx;
		this.talker = talker;
		initVoice();
	}

	public void setTalker(String talker) {
		this.talker = talker;
	}

	private boolean initVoice() {

		syn = SpeechSynthesizer.createSynthesizer(ctx, initListener);

		syn.setParameter(SpeechConstant.VOICE_NAME, talker);
		syn.setParameter(SpeechConstant.PITCH, "50");
		syn.setParameter(SpeechConstant.VOLUME, "50");

		return true;
	}

	private InitListener initListener = new InitListener() {
		public void onInit(int code) {
			Log.d("mySynthesizer:", "InitListener init() code=" + code);

		}
	};

	private SynthesizerListener mTtsListener = new SynthesizerListener() {
		public void onSpeakBegin() {
		}

		public void onSpeakPaused() {
		}

		public void onSpeakResumed() {
		}

		public void onBufferProgress(int percent, int beginPos, int endPos,
				String info) {
		}

		public void onSpeakProgress(int percent, int beginPos, int endPos) {
		}

		public void onCompleted(SpeechError error) {
			synchronized (mTtsListener) {
				if (isWait) {
					mTtsListener.notify();
					isWait = false;
				}
			}
			playMode = 0;
			setPlayImage(playMode);
			
		}

		public void onEvent(int arg0, int arg1, int arg2, Bundle arg3) {
		}
	};

	public void playAudio(String word) {
		if (word == null)
			return;
		lastWordString = word;
		int code = syn.startSpeaking(word, mTtsListener);
		// 更改播放模式0：取消播放/完成播放 1：正在播放 2：暂停播放
		playMode = 1;
		setPlayImage(playMode);

	}

	public void setPlayImage(int mode) {
		if (imagePlayView != null && animation != null) {
			switch (mode) {
			case 1:
				imagePlayView.setBackgroundResource(R.drawable.am001_menu_c0);
				imagePlayView.setAnimation(animation);
				imagePlayView.startAnimation(animation);
				break;
			case 0:
			case 2:
				imagePlayView.clearAnimation();
				imagePlayView.setBackgroundResource(R.drawable.am001_menu_c1);
			default:
				break;
			}

		}
	}

	public void playPause() {
		if (playMode == 1) {
			syn.pauseSpeaking();
			playMode = 2;
			setPlayImage(playMode);
		}
	}

	public void playResume() {
		if (playMode == 2) {
			syn.resumeSpeaking();
			playMode = 1;
			setPlayImage(playMode);

		}
	}

	public void playStop() {
		if (syn.isSpeaking())
			syn.stopSpeaking();
		playMode = 0;
	}

	public void setAudioVolume(int volume) {
		syn.setParameter(SpeechConstant.VOLUME, "" + volume);
	}

	public void changImageMode(ImageView imgPlayView, Animation animation) {
		this.imagePlayView = imgPlayView;
		this.imagePlayView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				switch (playMode) {
				case 0:
					playAudio(lastWordString);
					break;
				case 1:
					playPause();
				case 2:
					playResume();
				default:
					break;
				}
				
			}
		});
		this.animation = animation;

	}
}
