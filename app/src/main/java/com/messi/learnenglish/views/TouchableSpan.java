package com.messi.learnenglish.views;

import com.gc.materialdesign.views.ProgressBarCircularIndeterminate;
import com.messi.learnenglish.BaseApplication;
import com.messi.learnenglish.DictionaryFragment;
import com.messi.learnenglish.R;
import com.messi.learnenglish.dao.Dictionary;
import com.messi.learnenglish.dialog.TranslateResultDialog;
import com.messi.learnenglish.impl.FragmentProgressbarListener;
import com.messi.learnenglish.util.KeyUtil;
import com.messi.learnenglish.util.LogUtil;
import com.messi.learnenglish.util.Settings;
import com.messi.learnenglish.util.ToastUtil;
import com.messi.learnenglish.util.TranslateUtil;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

public class TouchableSpan extends ClickableSpan {// extend ClickableSpan

	private String word;
	private Context context;
	private ProgressBarCircularIndeterminate mProgressbar;
	private FragmentProgressbarListener mProgressbarListener;

	public TouchableSpan(Context context, ProgressBarCircularIndeterminate mProgressbar, String string) {
		super();
		word = string;
		this.mProgressbar = mProgressbar;
		this.context = context;
	}
	
	public TouchableSpan(Context context, FragmentProgressbarListener mProgressbarListener, String string) {
		super();
		word = string;
		this.mProgressbarListener = mProgressbarListener;
		this.context = context;
	}

	public void onClick(View tv) {
		LogUtil.DefalutLog(word);
		RequestShowapiAsyncTask();
	}

	public void updateDrawState(TextPaint ds) {
		ds.setUnderlineText(false); // set to false to remove underline
	}
	
	private Handler mHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			if(mProgressbar != null){
				mProgressbar.setVisibility(View.GONE);
			}
			if(mProgressbarListener != null){
				mProgressbarListener.hideProgressbar();
			}
			if(msg.what == 1){
				setData();
			}else{
				showToast(context.getResources().getString(R.string.network_error));
			}
		}
	};
	
	private void RequestShowapiAsyncTask(){
		if(mProgressbar != null){
			mProgressbar.setVisibility(View.VISIBLE);
		}
		if(mProgressbarListener != null){
			mProgressbarListener.showProgressbar();
		}
		Settings.q = word;
		TranslateUtil.Translate_init(context, mHandler);
	}
	
	private void setData(){
		Dictionary bean = (Dictionary) BaseApplication.dataMap.get(KeyUtil.DataMapKey);
		BaseApplication.dataMap.clear();
		DictionaryFragment.isRefresh = true;
		TranslateResultDialog dialog = new TranslateResultDialog(context, bean);
		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(true);
		dialog.show();
	}
	
	private void showToast(String toastString) {
		ToastUtil.diaplayMesShort(context, toastString);
	}
	
}
