package com.messi.learnenglish;

import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.messi.learnenglish.adapter.EvaluationCategoryAdapter;
import com.messi.learnenglish.util.AVOUtil;

public class EvaluationCategoryActivity extends BaseActivity implements OnClickListener{

	private GridView category_lv;
	private EvaluationCategoryAdapter mAdapter;
	private List<AVObject> avObjects;
	private LinearLayout ad_view;
	private String code;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.evaluation_category_activity);
		initSwipeRefresh();
		initViews();
		new QueryTask().execute();
	}
	
	private void initViews(){
		code = getIntent().getStringExtra(AVOUtil.EvaluationType.ETCode);
		avObjects = new ArrayList<AVObject>();
		category_lv = (GridView) findViewById(R.id.studycategory_lv);
		ad_view = (LinearLayout) findViewById(R.id.ad_view);
		mAdapter = new EvaluationCategoryAdapter(this, avObjects);
		category_lv.setAdapter(mAdapter);
	}
	
	@Override
	public void onSwipeRefreshLayoutRefresh() {
		super.onSwipeRefreshLayoutRefresh();
		new QueryTask().execute();
	}
	
	private class QueryTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			showProgressbar();
		}
		
		@Override
		protected Void doInBackground(Void... params) {
			AVQuery<AVObject> query = new AVQuery<AVObject>(AVOUtil.EvaluationCategory.EvaluationCategory);
			query.whereEqualTo(AVOUtil.EvaluationCategory.ECIsValid, "1");
			query.whereEqualTo(AVOUtil.EvaluationCategory.ETCode, code);
			query.orderByDescending(AVOUtil.EvaluationCategory.ECOrder);
			try {
				List<AVObject> avObject  = query.find();
				if(avObject != null){
					avObjects.clear();
					avObjects.addAll(avObject);
				}
			} catch (AVException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			hideProgressbar();
			onSwipeRefreshLayoutFinish();
			mAdapter.notifyDataSetChanged();
		}
		
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		default:
			break;
		}
	}
	
}
