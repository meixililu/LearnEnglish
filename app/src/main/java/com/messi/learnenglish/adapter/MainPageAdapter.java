package com.messi.learnenglish.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.messi.learnenglish.DictionaryFragment;
import com.messi.learnenglish.LeisureFragment;
import com.messi.learnenglish.MainFragment;
import com.messi.learnenglish.R;
import com.messi.learnenglish.StudyFragment;

public class MainPageAdapter extends FragmentPagerAdapter {

	public static String[] CONTENT;
	private Bundle bundle;
	
    public MainPageAdapter(FragmentManager fm,Bundle bundle,Context mContext) {
        super(fm);
        this.bundle = bundle;
        CONTENT = new String[] { 
        		mContext.getResources().getString(R.string.title_translate),
        		mContext.getResources().getString(R.string.title_dictionary),
        		mContext.getResources().getString(R.string.title_study),
        		mContext.getResources().getString(R.string.title_leisure)
        };
    }

    @Override
    public Fragment getItem(int position) {
        if( position == 0 ){
        	return MainFragment.getInstance();
        }else if( position == 1 ){
        	return DictionaryFragment.getInstance();
        }else if( position == 2 ){
        	return StudyFragment.getInstance();
        }else if( position == 3 ){
        	return LeisureFragment.getInstance();
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