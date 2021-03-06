package com.messi.learnenglish;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.gc.materialdesign.views.ButtonFloatSmall;
import com.gc.materialdesign.views.ButtonRectangle;
import com.iflytek.cloud.SpeechSynthesizer;
import com.messi.learnenglish.bean.UserSpeakBean;
import com.messi.learnenglish.impl.PracticeProgressListener;
import com.messi.learnenglish.util.LogUtil;
import com.messi.learnenglish.util.NumberUtil;
import com.messi.learnenglish.util.SDCardUtil;
import com.messi.learnenglish.util.ScoreUtil;
import com.messi.learnenglish.util.XFUtil;

public class PracticeWriteFragment extends BaseFragment implements OnClickListener{

	private View view;
	private String content;
	private TextView questionTv,translate_result;
	private ImageView translate_result_img;
	private ButtonRectangle check_btn;
	private ButtonFloatSmall mButtonFloatSmall;
	private PracticeProgressListener mPracticeProgress;
	private EditText translate_input;
	private int resultPosition;
	private int index;
	private int[] orderList;
	private String[] cn,en;
	private boolean isCheck;
	private String videoPath;
	private SpeechSynthesizer mSpeechSynthesizer;
	private SharedPreferences mSharedPreferences;
	
	public PracticeWriteFragment(String content, PracticeProgressListener mPracticeProgress, String videoPath, 
			SharedPreferences mSharedPreferences,SpeechSynthesizer mSpeechSynthesizer){
		this.content = content;
		this.mPracticeProgress = mPracticeProgress;
		resultPosition = NumberUtil.getRandomNumber(4);
		getContent();
		this.videoPath = SDCardUtil.getDownloadPath(videoPath);
		this.mSpeechSynthesizer = mSpeechSynthesizer;
		this.mSharedPreferences = mSharedPreferences;
		orderList = NumberUtil.getNumberOrderWithoutRepeat(4, 0, 4, false);
	}
	
	private void getContent(){
		String temp[] = content.split("#");
		cn = temp[0].split(",");
		en = temp[1].split(",");
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.practice_write_fragment, container, false);
		initViews();
		return view;
	}
	
	private void initViews(){
		questionTv = (TextView)view.findViewById(R.id.questiontv);
		translate_result = (TextView)view.findViewById(R.id.translate_result);
		translate_result_img = (ImageView)view.findViewById(R.id.translate_result_img);
		translate_input = (EditText)view.findViewById(R.id.translate_input);
		check_btn = (ButtonRectangle)view.findViewById(R.id.check_btn);
		mButtonFloatSmall = (ButtonFloatSmall)view.findViewById(R.id.buttonFloat);
		check_btn.setEnabled(false);
		setIndex();
		setContent();
		check_btn.setOnClickListener(this);
		translate_result_img.setOnClickListener(this);
		mButtonFloatSmall.setOnClickListener(this);
		
		translate_input.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}
			@Override
			public void afterTextChanged(Editable s) {
				check_btn.setEnabled(true);
				check_btn.setText("检查");
				isCheck = true;
			}
		});
	}
	
	private void setIndex(){
		resultPosition = orderList[index];
	}
	
	private void setContent(){
		questionTv.setText("\"" + cn[resultPosition] +"\"");
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()){
		case R.id.check_btn:
			submit();
			break;
		case R.id.select_answer1:
			playVideo(resultPosition);
			break;
		case R.id.translate_result_img:
			setResult();
			break;
		case R.id.buttonFloat:
			playVideo(resultPosition);
			break;
		}
	}
	
	private void submit(){
		if(isCheck){
			checkResult();
		}else{
			if(index < orderList.length-1){
				index++;
				setIndex();
				setContent();
				check_btn.setEnabled(false);
				translate_input.setText("");
				translate_result.setText("");
			}else{
				toNextPage();
			}
		}
	}
	
	private void checkResult(){
		hideIME();
		String userInput = translate_input.getText().toString().toLowerCase();
		UserSpeakBean bean = ScoreUtil.score(getActivity(), userInput, en[resultPosition].toLowerCase());
		translate_input.setText(bean.getContent());
		if(bean.getContent().length() > 0){
			translate_input.setSelection(bean.getContent().length()-1);
		}
		setScore(bean);
		isCheck = false;
	}
	
	private void setResult(){
		String txt = translate_result.getText().toString();
		if(TextUtils.isEmpty(txt)){
			translate_result.setText("\"" + en[resultPosition] +"\"");
		}else{
			translate_result.setText("");
		}
	}
	
	private void setScore(UserSpeakBean bean){
		String word = "Nice";
		if(bean.getScoreInt() > 90){
			word = "Perfect";
		}else if(bean.getScoreInt() > 70){
			word = "Great";
		}else if(bean.getScoreInt() > 59){
			word = "Not bad";
		}else {
			word = "Try harder";
		}
		if(index < orderList.length-1){
			check_btn.setText(word+",下一个");
		}else{
			check_btn.setText(word+",下一关");
		}
	}
	/**
	 * 点击翻译之后隐藏输入法
	 */
	private void hideIME(){
		final InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);       
		imm.hideSoftInputFromWindow(translate_input.getWindowToken(), 0); 
	}
	
	private void toNextPage(){
		if(mPracticeProgress != null){
			mPracticeProgress.toNextPage();
		}
	}
	
	private void playVideo(int position){
		String filepath = videoPath + position + ".pcm";
		XFUtil.playVideoInBackground(getActivity(), mSpeechSynthesizer, mSharedPreferences, filepath, en[position]);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		LogUtil.DefalutLog("PracticeOneFragment---onDestroy");
	}
	
}
