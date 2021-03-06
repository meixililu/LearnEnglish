package com.messi.learnenglish;

import java.util.ArrayList;

import com.avos.avoscloud.AVAnalytics;
import com.gc.materialdesign.views.ButtonFloat;
import com.gc.materialdesign.views.ButtonRectangle;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;
import com.messi.learnenglish.adapter.PracticePageListItemAdapter;
import com.messi.learnenglish.bean.UserSpeakBean;
import com.messi.learnenglish.dao.record;
import com.messi.learnenglish.db.DataBaseUtil;
import com.messi.learnenglish.task.MyThread;
import com.messi.learnenglish.task.PublicTask;
import com.messi.learnenglish.task.PublicTask.PublicTaskListener;
import com.messi.learnenglish.util.AudioTrackUtil;
import com.messi.learnenglish.util.JsonParser;
import com.messi.learnenglish.util.KeyUtil;
import com.messi.learnenglish.util.LogUtil;
import com.messi.learnenglish.util.SDCardUtil;
import com.messi.learnenglish.util.ScoreUtil;
import com.messi.learnenglish.util.Settings;
import com.messi.learnenglish.util.StringUtils;
import com.messi.learnenglish.util.ToastUtil;
import com.messi.learnenglish.util.XFUtil;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.Animator.AnimatorListener;
import com.nineoldandroids.animation.ObjectAnimator;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class PracticeActivity extends BaseActivity implements OnClickListener {

	private record mBean;
	private FrameLayout record_question_cover,record_answer_cover,practice_page_exchange;
	private ImageButton voice_play_answer,voice_play_question;
	private TextView record_question,record_answer,practice_prompt,record_animation_text;
	private ListView recent_used_lv;
	private ImageView record_anim_img;
	private LinearLayout record_layout,record_animation_layout;
	private ButtonRectangle voice_btn;
	private ButtonFloat buttonFloat;
	
	private MyOnClickListener mAnswerOnClickListener,mQuestionOnClickListener;
	private SpeechSynthesizer mSpeechSynthesizer;
	private SpeechRecognizer recognizer;
	private SharedPreferences mSharedPreferences;
	private ArrayList<UserSpeakBean> mUserSpeakBeanList;
	private PracticePageListItemAdapter adapter;
	private boolean isEnglish;
	private boolean isExchange;
	private boolean isNewIn = true;
	private boolean isFollow;
	private StringBuilder sbResult = new StringBuilder();
	
	private MyThread mMyThread;
	private Thread mThread;
	private String userPcmPath;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.practice_activity);
		initData();
		initView();
	}
	
	private void initData(){
		mBean = (record)BaseApplication.dataMap.get(KeyUtil.DialogBeanKey);
		isEnglish = StringUtils.isEnglish(mBean.getEnglish());
		mUserSpeakBeanList = new ArrayList<UserSpeakBean>();
		adapter = new PracticePageListItemAdapter(this, mUserSpeakBeanList);
	}

	private void initView() {
		getSupportActionBar().setTitle(getResources().getString(R.string.title_Practice));
        mSharedPreferences = Settings.getSharedPreferences(this);
        mSpeechSynthesizer = SpeechSynthesizer.createSynthesizer(this,null);
        recognizer = SpeechRecognizer.createRecognizer(this,null);
		record_answer_cover = (FrameLayout) findViewById(R.id.record_answer_cover);
		record_question_cover = (FrameLayout) findViewById(R.id.record_question_cover);
		practice_page_exchange = (FrameLayout) findViewById(R.id.practice_page_exchange);
		practice_prompt = (TextView) findViewById(R.id.practice_prompt);
		record_answer = (TextView) findViewById(R.id.record_answer);
		record_question = (TextView) findViewById(R.id.record_question);
		voice_play_answer = (ImageButton) findViewById(R.id.voice_play_answer);
		voice_play_question = (ImageButton) findViewById(R.id.voice_play_question);
		voice_btn = (ButtonRectangle) findViewById(R.id.voice_btn);
		buttonFloat = (ButtonFloat) findViewById(R.id.buttonFloat);
		record_anim_img = (ImageView) findViewById(R.id.record_anim_img);
		record_layout = (LinearLayout) findViewById(R.id.record_layout);
		record_animation_layout = (LinearLayout) findViewById(R.id.record_animation_layout);
		record_animation_text = (TextView) findViewById(R.id.record_animation_text);
		recent_used_lv = (ListView) findViewById(R.id.recent_used_lv);
		recent_used_lv.setAdapter(adapter);
		
		record_question.setText(mBean.getChinese());
		record_answer.setText(mBean.getEnglish());
		
		initSpeakLanguage();
		mAnswerOnClickListener = new MyOnClickListener(mBean,voice_play_answer,true);
		mQuestionOnClickListener = new MyOnClickListener(mBean,voice_play_question,false);
		
		buttonFloat.setEnabled(false);
		record_question_cover.setOnClickListener(mQuestionOnClickListener);
		record_answer_cover.setOnClickListener(mAnswerOnClickListener);
		buttonFloat.setOnClickListener(this);
		voice_btn.setOnClickListener(this);
		practice_page_exchange.setOnClickListener(this);
		
	}
	
	private void initSpeakLanguage(){
		if(isEnglish){
			XFUtil.setSpeakLanguage(this,mSharedPreferences,XFUtil.VoiceEngineEN);
			practice_prompt.setText(this.getResources().getString(R.string.practice_prompt_english));
		}else{
			XFUtil.setSpeakLanguage(this,mSharedPreferences,XFUtil.VoiceEngineCH);
			practice_prompt.setText(this.getResources().getString(R.string.practice_prompt_chinese));
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) { 
		case R.id.voice_btn:
			showIatDialog();
			AVAnalytics.onEvent(PracticeActivity.this, "practice_pg_speak_btn");
			break;
		case R.id.buttonFloat:
			playUserPcm();
			break;
		case R.id.practice_page_exchange:
			exchangeContentAndResult();
			AVAnalytics.onEvent(PracticeActivity.this, "practice_pg_exchange_btn");
			break;
		default:
			break;
		}
	}
	
	private void playUserPcm(){
		if(!TextUtils.isEmpty(userPcmPath)){
			if(mMyThread != null){
				if(mMyThread.isPlaying){
					AudioTrackUtil.stopPlayPcm(mThread);
				}else{
					mThread = AudioTrackUtil.startMyThread(mMyThread);
				}
			}else{
				mMyThread = AudioTrackUtil.getMyThread(userPcmPath);
				mThread = AudioTrackUtil.startMyThread(mMyThread);
			}
		}
	}
	
	private void exchangeContentAndResult(){
		if(!isExchange){
			isExchange = true;
			mAnswerOnClickListener.setPlayResult(false);
			mQuestionOnClickListener.setPlayResult(true);
		}else{
			isExchange = false;
			mAnswerOnClickListener.setPlayResult(true);
			mQuestionOnClickListener.setPlayResult(false);
		}
		String tempAnswer = record_answer.getText().toString();
		String tempQuestion = record_question.getText().toString();
		record_answer.setText(tempQuestion);
		record_question.setText(tempAnswer);
		isEnglish = StringUtils.isEnglish(tempQuestion);
		initSpeakLanguage();
	}
	
	/**
	 * 显示转写对话框.
	 */
	public void showIatDialog() {
		buttonFloat.setEnabled(false);
		if(recognizer != null){
			if(!recognizer.isListening()){
				if(isNewIn){
					isNewIn = false;
					isFollow = true;
					showListen();
					practice_prompt.setVisibility(View.GONE);
					mAnswerOnClickListener.onClick(voice_play_answer);
				}else{
					record_layout.setVisibility(View.VISIBLE);
					voice_btn.setText(this.getResources().getString(R.string.finish));
					String path = SDCardUtil.getDownloadPath(SDCardUtil.UserPracticePath);
					userPcmPath = path + "/userpractice.pcm";
					recognizer.setParameter(SpeechConstant.ASR_AUDIO_PATH, userPcmPath);
					XFUtil.showSpeechRecognizer(this,mSharedPreferences,recognizer,recognizerListener);
				}
			}else{
				showProgressbar();
				finishRecord();
			}
		}
	}
	
	private void showListen(){
		record_animation_layout.setVisibility(View.VISIBLE);
		record_animation_text.setText("Listen");
		ObjectAnimator mObjectAnimator = ObjectAnimator.ofFloat(record_animation_layout, "scaleX", 1f, 1f);
		mObjectAnimator.setDuration(800).start();
		ObjectAnimator mObjectAnimator1 = ObjectAnimator.ofFloat(record_animation_layout, "scaleY", 1f, 1f);
		mObjectAnimator1.setDuration(800).start();
		ObjectAnimator mObjectAnimator2 = ObjectAnimator.ofFloat(record_animation_layout, "alpha", 1, 1);
		mObjectAnimator2.start();
	}
	
	/**
	 * finish record
	 */
	private void finishRecord(){
		recognizer.stopListening();
		isNewIn = true;
		record_layout.setVisibility(View.GONE);
		record_anim_img.setBackgroundResource(R.drawable.speak_voice_1);
		voice_btn.setText("Start");
	}
	
	private void onfinishPlay(){
		if(isFollow){
			isFollow = false;
			record_animation_layout.setVisibility(View.VISIBLE);
			record_animation_text.setText(this.getResources().getString(R.string.your_turn));
			animation();
		}
	}
	
	private void animationReward(int score){
		String word = "Nice";
		if(score > 90){
			word = "Perfect";
		}else if(score > 70){
			word = "Great";
		}else if(score > 59){
			word = "Not bad";
		}else {
			word = "Try harder";
		}
		record_animation_layout.setVisibility(View.VISIBLE);
		record_animation_text.setText(word);
		ObjectAnimator mObjectAnimator = ObjectAnimator.ofFloat(record_animation_layout, "alpha", 1, 0f);
		mObjectAnimator.addListener(mAnimatorListenerReward);
		mObjectAnimator.setStartDelay(300);
		mObjectAnimator.setDuration(1500).start();
	}
	
	private AnimatorListener mAnimatorListenerReward = new AnimatorListener() {
		@Override
		public void onAnimationStart(Animator animation) {
		}
		@Override
		public void onAnimationRepeat(Animator animation) {
		}
		@Override
		public void onAnimationEnd(Animator animation) {
			record_animation_layout.setVisibility(View.GONE);
		}
		@Override
		public void onAnimationCancel(Animator animation) {
		}
	};
	
	private void animation(){
		ObjectAnimator mObjectAnimator = ObjectAnimator.ofFloat(record_animation_layout, "scaleX", 1f, 1.3f);
		mObjectAnimator.addListener(mAnimatorListener);
		mObjectAnimator.setDuration(800).start();
		ObjectAnimator mObjectAnimator1 = ObjectAnimator.ofFloat(record_animation_layout, "scaleY", 1f, 1.3f);
		mObjectAnimator1.setDuration(800).start();
		ObjectAnimator mObjectAnimator2 = ObjectAnimator.ofFloat(record_animation_layout, "alpha", 1, 0);
		mObjectAnimator2.setDuration(800).start();
	}
	
	private AnimatorListener mAnimatorListener = new AnimatorListener() {
		@Override
		public void onAnimationStart(Animator animation) {
		}
		@Override
		public void onAnimationRepeat(Animator animation) {
		}
		@Override
		public void onAnimationEnd(Animator animation) {
			record_animation_layout.setVisibility(View.GONE);
			showIatDialog();
		}
		@Override
		public void onAnimationCancel(Animator animation) {
		}
	};
	
	RecognizerListener recognizerListener = new RecognizerListener() {

		@Override
		public void onBeginOfSpeech() {
			LogUtil.DefalutLog("onBeginOfSpeech");
		}

		@Override
		public void onError(SpeechError err) {
			LogUtil.DefalutLog("onError:"+err.getErrorDescription());
			finishRecord();
			hideProgressbar();
			ToastUtil.diaplayMesShort(PracticeActivity.this, err.getErrorDescription());
		}

		@Override
		public void onEndOfSpeech() {
			LogUtil.DefalutLog("onEndOfSpeech");
			finishRecord();
			showProgressbar();
		}

		@Override
		public void onResult(RecognizerResult results, boolean isLast) {
			LogUtil.DefalutLog("onResult---getResultString:"+results.getResultString());
			String text = JsonParser.parseIatResult(results.getResultString());
			sbResult.append(text);
			if(isLast) {
				LogUtil.DefalutLog("isLast-------onResult:"+sbResult.toString());
				hideProgressbar();
				finishRecord();
				UserSpeakBean bean = ScoreUtil.score(PracticeActivity.this, sbResult.toString(), record_answer.getText().toString());
				mUserSpeakBeanList.add(0,bean);
				adapter.notifyDataSetChanged();
				animationReward(bean.getScoreInt());
				sbResult.setLength(0);
				buttonFloat.setEnabled(true);
				mMyThread = AudioTrackUtil.getMyThread(userPcmPath);
			}
		}

		@Override
		public void onEvent(int arg0, int arg1, int arg2, Bundle arg3) {
			
		}

		@Override
		public void onVolumeChanged(int volume, byte[] arg1) {
			if(volume < 4){
				record_anim_img.setBackgroundResource(R.drawable.speak_voice_1);
			}else if(volume < 8){
				record_anim_img.setBackgroundResource(R.drawable.speak_voice_2);
			}else if(volume < 12){
				record_anim_img.setBackgroundResource(R.drawable.speak_voice_3);
			}else if(volume < 16){
				record_anim_img.setBackgroundResource(R.drawable.speak_voice_4);
			}else if(volume < 20){
				record_anim_img.setBackgroundResource(R.drawable.speak_voice_5);
			}else if(volume < 24){
				record_anim_img.setBackgroundResource(R.drawable.speak_voice_6);
			}else if(volume < 31){
				record_anim_img.setBackgroundResource(R.drawable.speak_voice_7);
			}
		}
	};
	
	public class MyOnClickListener implements OnClickListener {
		
		private record mBean;
		private ImageButton voice_play;
		private AnimationDrawable animationDrawable;
		private boolean isPlayResult;
		
		public void setPlayResult(boolean isPlayResult) {
			this.isPlayResult = isPlayResult;
		}

		private MyOnClickListener(record bean,ImageButton voice_play,boolean isPlayResult){
			this.mBean = bean;
			this.voice_play = voice_play;
			this.animationDrawable = (AnimationDrawable) voice_play.getBackground();
			this.isPlayResult = isPlayResult;
		}
		
		@Override
		public void onClick(final View v) {
			resetVoicePlayButton();
			String path = SDCardUtil.getDownloadPath(SDCardUtil.sdPath);
			if(TextUtils.isEmpty(mBean.getResultVoiceId()) || TextUtils.isEmpty(mBean.getQuestionVoiceId())){
				mBean.setQuestionVoiceId(System.currentTimeMillis() + "");
				mBean.setResultVoiceId(System.currentTimeMillis()-5 + "");
			}
			String filepath = "";
			String speakContent = "";
			if(isPlayResult){
				filepath = path + mBean.getResultVoiceId() + ".pcm";
				mBean.setResultAudioPath(filepath);
				speakContent = mBean.getEnglish();
			}else{
				filepath = path + mBean.getQuestionVoiceId() + ".pcm";
				mBean.setQuestionAudioPath(filepath);
				speakContent = mBean.getChinese();
			}
			if(mBean.getSpeak_speed() != MainFragment.speed){
				String filep1 = path + mBean.getResultVoiceId() + ".pcm";
				String filep2 = path + mBean.getQuestionVoiceId() + ".pcm";
				AudioTrackUtil.deleteFile(filep1);
				AudioTrackUtil.deleteFile(filep2);
				mBean.setSpeak_speed(MainFragment.speed);
			}
			mSpeechSynthesizer.setParameter(SpeechConstant.TTS_AUDIO_PATH, filepath);
			if(!AudioTrackUtil.isFileExists(filepath)){
				showProgressbar();
				XFUtil.showSpeechSynthesizer(PracticeActivity.this,mSharedPreferences,mSpeechSynthesizer,speakContent,
						new SynthesizerListener() {
					@Override
					public void onSpeakResumed() {
					}
					@Override
					public void onSpeakProgress(int arg0, int arg1, int arg2) {
					}
					@Override
					public void onSpeakPaused() {
					}
					@Override
					public void onSpeakBegin() {
						hideProgressbar();
						voice_play.setVisibility(View.VISIBLE);
						if(!animationDrawable.isRunning()){
							animationDrawable.setOneShot(false);
							animationDrawable.start();  
						}
					}
					@Override
					public void onCompleted(SpeechError arg0) {
						LogUtil.DefalutLog("---onCompleted");
						if(arg0 != null){
							ToastUtil.diaplayMesShort(PracticeActivity.this, arg0.getErrorDescription());
						}
						DataBaseUtil.getInstance().update(mBean);
						animationDrawable.setOneShot(true);
						animationDrawable.stop(); 
						animationDrawable.selectDrawable(0);
						hideProgressbar();
						onfinishPlay();
					}
					@Override
					public void onBufferProgress(int arg0, int arg1, int arg2, String arg3) {
					}
					@Override
					public void onEvent(int arg0, int arg1, int arg2,Bundle arg3) {
						
					}
				});
			}else{
				playLocalPcm(filepath,animationDrawable);
			}
			if(v.getId() == R.id.record_question_cover){
				AVAnalytics.onEvent(PracticeActivity.this, "practice_pg_play_question_btn", "口语练习页播放内容", 1);
			}else if(v.getId() == R.id.record_answer_cover){
				AVAnalytics.onEvent(PracticeActivity.this, "practice_pg_play_result_btn", "口语练习页播放结果", 1);
			}
		}
	}
	
	private void playLocalPcm(final String path,final AnimationDrawable animationDrawable){
		PublicTask mPublicTask = new PublicTask(PracticeActivity.this);
		mPublicTask.setmPublicTaskListener(new PublicTaskListener() {
			@Override
			public void onPreExecute() {
				if(!animationDrawable.isRunning()){
					animationDrawable.setOneShot(false);
					animationDrawable.start();  
				}
			}
			@Override
			public Object doInBackground() {
				AudioTrackUtil.createAudioTrack(path);
				return null;
			}
			@Override
			public void onFinish(Object resutl) {
				animationDrawable.setOneShot(true);
				animationDrawable.stop(); 
				animationDrawable.selectDrawable(0);
				onfinishPlay();
			}
		});
		mPublicTask.execute();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_settings:  
			toSettingActivity();
			AVAnalytics.onEvent(this, "practice_pg_to_setting_page");
			break;
		}
       return super.onOptionsItemSelected(item);
	}
	
	private void toSettingActivity(){
		Intent intent = new Intent(this, SettingActivity.class); 
		startActivity(intent); 
	}
	
	private void resetVoicePlayButton(){
		resetVoiceAnimation(voice_play_answer);
		resetVoiceAnimation(voice_play_question);
	}
	
	private void resetVoiceAnimation(View voice_play){
		AnimationDrawable animationDrawable = (AnimationDrawable) voice_play.getBackground();
		animationDrawable.setOneShot(true);
		animationDrawable.stop(); 
		animationDrawable.selectDrawable(0);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		if(mSpeechSynthesizer != null){
			mSpeechSynthesizer.destroy();
			mSpeechSynthesizer = null;
		}
		if(recognizer != null){
			recognizer.destroy();
			recognizer = null;
		}
	}
}
