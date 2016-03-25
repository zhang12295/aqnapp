package com.njaqn.itravel.aqnapp.service;

import java.util.List;

import com.njaqn.itravel.aqnapp.service.bean.JSpotBean;
import com.njaqn.itravel.aqnapp.service.bean.PlayFileBean;

public interface BmService {

	public boolean login(String userName,String passwd);
	public String getValidateCodeFromServer(String mobileNo);
	public String getValidateCodeFromSMS();
	public String postZhuceInfo(String mobileNo,String passwd);
}
