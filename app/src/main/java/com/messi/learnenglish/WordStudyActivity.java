package com.messi.learnenglish;

import com.messi.learnenglish.adapter.WordStudyTabAdapter;
import com.messi.learnenglish.impl.FragmentProgressbarListener;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

public class WordStudyActivity extends BaseActivity implements FragmentProgressbarListener{

	private TabLayout tablayout;
	private ViewPager viewpager;
	private WordStudyTabAdapter pageAdapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.joke_activity);
		initViews();
	}
	
	private void initViews(){
		getSupportActionBar().setTitle(getResources().getString(R.string.title_word_study));
		tablayout = (TabLayout) findViewById(R.id.tablayout);
		viewpager = (ViewPager) findViewById(R.id.viewpager);
		
		pageAdapter = new WordStudyTabAdapter(getSupportFragmentManager(),this);
		viewpager.setAdapter(pageAdapter);
		viewpager.setOffscreenPageLimit(2);
		tablayout.setTabsFromPagerAdapter(pageAdapter);
		tablayout.setupWithViewPager(viewpager);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
}
