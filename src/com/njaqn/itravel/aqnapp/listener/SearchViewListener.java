package com.njaqn.itravel.aqnapp.listener;
/**
 * search view回调方法
 */
public interface SearchViewListener {

	/**
	 * 更新自动补全内容
	 * 
	 * @param text
	 *            传入补全后的文本
	 */
	void onRefreshAutoComplete(String text);

	/**
	 * 开始搜索
	 * 
	 * @param text
	 *            传入输入框的文本
	 */
	void onSearch(String text);

	// /**
	// * 提示列表项点击时回调方法 (提示/自动补全)
	// */
	// void onTipsItemClick(String text);
}

