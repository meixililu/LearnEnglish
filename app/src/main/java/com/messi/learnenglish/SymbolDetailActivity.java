package com.messi.learnenglish;

import com.avos.avoscloud.AVAnalytics;
import com.messi.learnenglish.dao.SymbolListDao;
import com.messi.learnenglish.util.DownLoadUtil;
import com.messi.learnenglish.util.KeyUtil;
import com.messi.learnenglish.util.SDCardUtil;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SymbolDetailActivity extends BaseActivity implements OnClickListener {

	private FrameLayout symbol_cover,teacher_cover;
	private TextView symbol_en,symbol_des,symbol_info,error_txt;
	private LinearLayout content;
	private ImageView play_img;
	private ImageButton symbol_play_img,teacher_play_img;
	private MediaPlayer mPlayer;
	private String audioPath;
	private SymbolListDao avObject;
	private String SDAudioMp3FullName,SDTeacherMp3FullName;
	private String SDAudioMp3Name,SDTeacherMp3Name;
	private String SDAudioMp3Url,SDTeacherMp3Url;
	private String currentFileFullName;
	private int loopTime;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.symbol_study_activity);
		initData();
		initViews();
		setData();
	}
	
	private void initData(){
		mPlayer = new MediaPlayer();
		avObject = (SymbolListDao) BaseApplication.dataMap.get(KeyUtil.DataMapKey);
		BaseApplication.dataMap.clear();
		if(avObject != null){
			audioPath = SDCardUtil.SymbolPath + avObject.getSDCode() + SDCardUtil.Delimiter;
		}else{
			finish();
		}
	}

	private void initViews() {
		content = (LinearLayout) findViewById(R.id.content);
		symbol_cover = (FrameLayout) findViewById(R.id.symbol_cover);
		teacher_cover = (FrameLayout) findViewById(R.id.teacher_cover);
		symbol_en = (TextView) findViewById(R.id.symbol_en);
		symbol_des = (TextView) findViewById(R.id.symbol_des);
		symbol_info = (TextView) findViewById(R.id.symbol_info);
		error_txt = (TextView) findViewById(R.id.error_txt);
		play_img = (ImageView) findViewById(R.id.play_img);
		symbol_play_img = (ImageButton) findViewById(R.id.symbol_play_img);
		teacher_play_img = (ImageButton) findViewById(R.id.teacher_play_img);
		
		symbol_cover.setOnClickListener(this);
		teacher_cover.setOnClickListener(this);
	}
	
	private void setData(){
		try {
			symbol_en.setText(avObject.getSDName());
			symbol_des.setText(avObject.getSDDes());
			symbol_info.setText(avObject.getSDInfo());
			
			SDAudioMp3Url = avObject.getSDAudioMp3Url();
			SDAudioMp3Name = SDAudioMp3Url.substring(SDAudioMp3Url.lastIndexOf("/")+1);
			SDAudioMp3FullName = SDCardUtil.getDownloadPath(audioPath) + SDAudioMp3Name;
			if(!SDCardUtil.isFileExist(SDAudioMp3FullName)){
				DownLoadUtil.downloadFile(this, SDAudioMp3Url, audioPath, SDAudioMp3Name, null);
			}
			
			SDTeacherMp3Url = avObject.getSDTeacherMp3Url();
			SDTeacherMp3Name = SDTeacherMp3Url.substring(SDTeacherMp3Url.lastIndexOf("/")+1);
			SDTeacherMp3FullName = SDCardUtil.getDownloadPath(audioPath) + SDTeacherMp3Name;
			if(!SDCardUtil.isFileExist(SDTeacherMp3FullName)){
				DownLoadUtil.downloadFile(this, SDTeacherMp3Url, audioPath, SDTeacherMp3Name, null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private Handler mHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			if(msg.what == 1){
				hideProgressbar();
				initUri(currentFileFullName);
			}
		}
	};
	
	private void playMp3(final String uriPath){
		try {
			if(mPlayer.isPlaying()){
				play_img.setBackgroundResource(R.drawable.ic_play_circle_outline_grey600_36dp);
				if(!uriPath.equals(currentFileFullName)){
					mPlayer.stop();
					initUri(uriPath);
				}else{
					mPlayer.pause();
				}
				currentFileFullName = uriPath;
			}else{
				if(TextUtils.isEmpty(currentFileFullName) || !uriPath.equals(currentFileFullName)){
					initUri(uriPath);
				}else{
					if(uriPath.equals(SDTeacherMp3FullName)){
						play_img.setBackgroundResource(R.drawable.ic_pause_circle_outline_grey600_36dp);
					}
					mPlayer.start();
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	private void initUri(final String uriPath){
		try {
			if(uriPath.equals(SDTeacherMp3FullName)){
				play_img.setBackgroundResource(R.drawable.ic_pause_circle_outline_grey600_36dp);
			}
			mPlayer.reset();
			Uri uri = Uri.parse(uriPath);
			mPlayer.setDataSource(this, uri);
			mPlayer.setOnCompletionListener(new OnCompletionListener() {
				@Override
				public void onCompletion(MediaPlayer mp) {
					if(uriPath.equals(SDTeacherMp3FullName)){
						play_img.setBackgroundResource(R.drawable.ic_play_circle_outline_grey600_36dp);
					}else{
						replay();
					}
				}
			});
			mPlayer.prepare();
			mPlayer.start();
			if(uriPath.equals(SDTeacherMp3FullName)){
				play_img.setBackgroundResource(R.drawable.ic_pause_circle_outline_grey600_36dp);
			}
			currentFileFullName = uriPath;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void replay(){
		loopTime ++;
		if(loopTime < 3){
			new Handler().postDelayed(new Runnable() {
				
				@Override
				public void run() {
					onClick(symbol_cover);
				}
			}, 1000);
		}else{
			loopTime = 0;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) { 
		case R.id.symbol_cover:
			if(SDCardUtil.isFileExist(SDTeacherMp3FullName)){
				playMp3(SDAudioMp3FullName);
			}else{
				currentFileFullName = SDAudioMp3FullName;
				DownLoadUtil.downloadFile(this, SDAudioMp3Url, audioPath, SDAudioMp3Name, mHandler);
			}
			AVAnalytics.onEvent(SymbolDetailActivity.this, "symbol_pg_play_mp3");
			break;
		case R.id.teacher_cover:
			if(SDCardUtil.isFileExist(SDTeacherMp3FullName)){
				playMp3(SDTeacherMp3FullName);
			}else{
				currentFileFullName = SDTeacherMp3FullName;
				DownLoadUtil.downloadFile(this, SDTeacherMp3Url, audioPath, SDTeacherMp3Name, mHandler);
			}
			AVAnalytics.onEvent(SymbolDetailActivity.this, "symbol_pg_play_teacher_mp3", "音标详情音标外教播放", 1);
			break;
		default:
			break;
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(mHandler != null){
			mHandler = null;
		}
		if (mPlayer != null) {   
			mPlayer.stop();  
			mPlayer.release();   
			mPlayer = null;   
        }  
	}
	
}
