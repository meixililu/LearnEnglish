package com.messi.learnenglish.adapter;

import com.messi.learnenglish.R;
import com.messi.learnenglish.ReadingFragment;
import com.messi.learnenglish.SpokenEnglishPractiseFragment;
import com.messi.learnenglish.util.AVOUtil;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class SpokenEnglishTabAdapter extends FragmentPagerAdapter {

	public static String[] CONTENT;
	
    public SpokenEnglishTabAdapter(FragmentManager fm,Context mContext) {
        super(fm);
        CONTENT = new String[] { 
        		mContext.getResources().getString(R.string.spoken_english_practice),
        		mContext.getResources().getString(R.string.title_spoken_english_study)
        };
    }

    @Override
    public Fragment getItem(int position) {
        if( position == 0 ){
        	return new SpokenEnglishPractiseFragment();
        }else if( position == 1 ){
        	return new ReadingFragment(AVOUtil.Category.spoken_english);
        }
        return null;
    }

    @Override
    public int getCount() {
        return CONTENT.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return CONTENT[position].toUpperCase();
    }
}