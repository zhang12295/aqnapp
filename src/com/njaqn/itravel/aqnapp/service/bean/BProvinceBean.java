package com.njaqn.itravel.aqnapp.service.bean;

public class BProvinceBean {

	private String name;   
	private String jc; //¼ò³Æ
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getJc() {
		return jc;
	}
	public void setJc(String jc) {
		this.jc = jc;
	}
	public int getCountryId() {
		return countryId;
	}
	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	private int countryId;
	private int id;
	private String pinYin;
	public String getPinYin() {
		return pinYin;
	}
	public void setPinYin(String pinYin) {
		this.pinYin = pinYin;
	}
}
