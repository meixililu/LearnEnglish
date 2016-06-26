package com.messi.learnenglish.views;

import com.messi.learnenglish.impl.DictionaryTranslateListener;
import com.messi.learnenglish.impl.FragmentProgressbarListener;
import com.messi.learnenglish.util.LogUtil;

import android.content.Context;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

public class TouchableSpanForDic extends ClickableSpan {// extend ClickableSpan

	private String word;
	private String sentence;
	private Context context;
	private FragmentProgressbarListener mProgressbarListener;
	private DictionaryTranslateListener mDictionaryTranslateListener;

	public TouchableSpanForDic(Context context, FragmentProgressbarListener mProgressbarListener,
			DictionaryTranslateListener mDictionaryTranslateListener, String sentence,  String word) {
		super();
		this.context = context;
		this.word = word;
		this.sentence = sentence;
		this.mProgressbarListener = mProgressbarListener;
		this.mDictionaryTranslateListener = mDictionaryTranslateListener;
	}

	public void onClick(View tv) {
		LogUtil.DefalutLog(word);
		if(mDictionaryTranslateListener != null){
			mDictionaryTranslateListener.translate(sentence, word);
		}
	}

	public void updateDrawState(TextPaint ds) {
		ds.setUnderlineText(false); // set to false to remove underline
	}
	
}
