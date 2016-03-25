package com.njaqn.itravel.aqnapp.util;

public class AQNAppConst {

	private static final String DNS = "http://218.94.6.85:8091";
//	private static final String DNS = "http://192.168.1.103:8080";
	public static final String URL_AM = DNS+"/aqns/ma.do";  //
	public static final String URL_BM = DNS+"/aqns/mb.do";  //
	public static final String URL_CM = DNS+"/aqns/mc.do";  //
	public static final String URL_DM = DNS+"/aqns/md.do";  //
	public static final String URL_DB = DNS+"/aqns/db";
	public static final String URL_IMG = DNS+"/aqnimg";
	
	public static final int PAGEID_AM = 1;	
	public static final int PAGEID_AM_APPCONF = PAGEID_AM+1;
	public static final int PAGEID_AM_BASE = PAGEID_AM+2;
	public static final int PAGEID_AM_SPOT = PAGEID_AM+3;
	
	public static final int PAGEID_BM = 1000;
	public static final int PAGEID_BM_LOGIN = PAGEID_BM+1;
	
	public static final int PAGEID_CM = 2000;
	public static final int PAGEID_DM = 3000;

	public static final int OP_AM_BASE_1 = 1;
	public static final int OP_AM_BASE_2 = 2;
	public static final int OP_AM_BASE_4 = 4;
	public static final int OP_AM_BASE_5 = 5;
	public static final int OP_AM_BASE_6 = 6;
	public static final int OP_AM_BASE_7 = 7;
	public static final int OP_AM_BASE_8 = 8;
	
	public static final int OP_AM_SPOT_1 = 1;
	public static final int OP_AM_SPOT_2 = 2;
	public static final int OP_AM_SPOT_3 = 3;
	public static final int OP_AM_SPOT_4 = 4;
	public static final int OP_AM_SPOT_5 = 5;
	public static final int OP_AM_SPOT_6 = 6;
	public static final int OP_AM_SPOT_7 = 7;
	public static final int OP_AM_SPOT_8 = 8;
	public static final int OP_AM_SPOT_9 = 9;
	public static final int OP_AM_SPOT_10 = 10;
	public static final int OP_AM_SPOT_11 = 11;

	public static final int PAGEID_AM_1 = PAGEID_AM+1;
	
	//数据库操作
	public static final int DB_UPDATE = 0;     //修改，删除，插入
	public static final int DB_ONE_ONE = 1;    //返回单个列，单行
	public static final int DB_ONE_MANY = 2;   //返回单个列，多行
	public static final int DB_MANY_MANY = 3;  //返回多个列，多行
}
