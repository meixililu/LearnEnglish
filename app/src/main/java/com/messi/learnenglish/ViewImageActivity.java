package com.messi.learnenglish;

import java.io.File;

import com.bumptech.glide.Glide;
import com.gc.materialdesign.views.ButtonFloat;
import com.messi.learnenglish.util.KeyUtil;
import com.messi.learnenglish.util.LogUtil;
import com.messi.learnenglish.util.SDCardUtil;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class ViewImageActivity extends BaseActivity implements OnClickListener {

	private ImageView imageview;
	private ButtonFloat mButtonFloat;
	private String imgUrl;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_image_activity);
		try {
			init();
			loadData();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void init() {
		imgUrl = getIntent().getStringExtra(KeyUtil.DailySentenceBigImgUrl);
		imageview = (ImageView) findViewById(R.id.imageview);
		mButtonFloat = (ButtonFloat) findViewById(R.id.buttonfloat);
		imageview.setOnClickListener(this);
		mButtonFloat.setOnClickListener(this);
	}
	
	private void loadData() throws Exception{
		Glide.with(this).load(imgUrl).into(imageview);
	}
	
	private void shareImg() throws Exception{
		Drawable mDrawable = imageview.getDrawable();
		BitmapDrawable bd = (BitmapDrawable)mDrawable;
		Bitmap bitmap = bd.getBitmap();
		if(bitmap !=  null){
			String imgPath = SDCardUtil.saveBitmap(this, bitmap);
			File file = new File(imgPath);    
			if (file != null && file.exists() && file.isFile()) {    
				Uri uri = Uri.fromFile(file);    
				Intent intent = new Intent(Intent.ACTION_SEND);
				intent.setType("image/png");    
				intent.putExtra(Intent.EXTRA_STREAM, uri); 
				intent.putExtra(Intent.EXTRA_SUBJECT, this.getResources().getString(R.string.share));  
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				this.startActivity(Intent.createChooser(intent, this.getResources().getString(R.string.share))); 
			}    
		}else{
			LogUtil.DefalutLog("bitmap == null");
		}
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.buttonfloat){
			try {
				shareImg();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(v.getId() == R.id.imageview){
			ViewImageActivity.this.finish();
		}
	}
}
