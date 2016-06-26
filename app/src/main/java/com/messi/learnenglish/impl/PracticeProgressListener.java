package com.messi.learnenglish.impl;

public interface PracticeProgressListener {

	public void toNextPage();
	public void onLoading();
	public void onCompleteLoading();
	public void finishActivity();
	
}
