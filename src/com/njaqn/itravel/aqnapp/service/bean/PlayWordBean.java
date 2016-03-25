package com.njaqn.itravel.aqnapp.service.bean;

public class PlayWordBean {

	private String word;       //播放词
	private int playCount;  //播放次数
	private int wordType;      //类型
	private String key;     //唯一标示符号
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public int getWordType() {
		return wordType;
	}
	public void setWordType(int wordType) {
		this.wordType = wordType;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public int getPlayCount() {
		return playCount;
	}
	public void setPlayCount(int playCount) {
		this.playCount = playCount;
	}
}
