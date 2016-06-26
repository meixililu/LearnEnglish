package com.messi.learnenglish;


import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.messi.learnenglish.adapter.MainPageAdapter;
import com.messi.learnenglish.impl.FragmentProgressbarListener;
import com.messi.learnenglish.util.KeyUtil;
import com.messi.learnenglish.util.LogUtil;
import com.messi.learnenglish.util.Settings;
import com.messi.learnenglish.views.PagerSlidingTabStrip;

public class WXEntryActivity extends BaseActivity implements FragmentProgressbarListener {

    public static int currentIndex = 0;
    private ViewPager viewPager;
    private PagerSlidingTabStrip indicator;
    private MainPageAdapter mAdapter;
    private SharedPreferences mSharedPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.content_frame);
            initViews();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initViews() {
        mSharedPreferences = getSharedPreferences(this.getPackageName(), Activity.MODE_PRIVATE);
        viewPager = (ViewPager) findViewById(R.id.pager);
        indicator = (PagerSlidingTabStrip) findViewById(R.id.indicator);
        mAdapter = new MainPageAdapter(this.getSupportFragmentManager(), null, this);
        viewPager.setAdapter(mAdapter);
        viewPager.setOffscreenPageLimit(4);
        indicator.setViewPager(viewPager);

        setLastTimeSelectTab();
    }

    private void setLastTimeSelectTab() {
        int index = mSharedPreferences.getInt(KeyUtil.LastTimeSelectTab, 0);
        viewPager.setCurrentItem(index);
    }

    private void saveSelectTab() {
        int index = viewPager.getCurrentItem();
        LogUtil.DefalutLog("WXEntryActivity---onDestroy---saveSelectTab---index:" + index);
        Settings.saveSharedPreferences(mSharedPreferences, KeyUtil.LastTimeSelectTab, index);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveSelectTab();
    }

    //	private void checkUpdate(){
//		BDAutoUpdateSDK.cpUpdateCheck(this, new MyCPCheckUpdateCallback());
//	}

//	private class MyCPCheckUpdateCallback implements CPCheckUpdateCallback {
//
//		@Override
//		public void onCheckUpdateCallback(AppUpdateInfo info, AppUpdateInfoForInstall infoForInstall) {
//			if(info != null || infoForInstall != null){
//				showUpdateDialog(info, infoForInstall);
//			}
//		}
//	}

//	private void showUpdateDialog(final AppUpdateInfo info, final AppUpdateInfoForInstall infoForInstall){
//		String updateInfo = "有更丰富的内容，更快的速度，更好的体验，快快更新吧！";
//		if(info != null){
//			updateInfo = info.getAppChangeLog();
//		}else if(infoForInstall != null){
//			updateInfo = infoForInstall.getAppChangeLog();
//		}
//		if(updateInfo.contains("<br>")){
//			updateInfo = updateInfo.replace("<br>", "\n");
//		}
//		Dialog dialog = new Dialog(WXEntryActivity.this, "更新啦,更新啦!", updateInfo);
//		dialog.addAcceptButton("好的");
//		dialog.addCancelButton("稍后");
//		dialog.setOnAcceptButtonClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				if(infoForInstall != null && !TextUtils.isEmpty(infoForInstall.getInstallPath())) {
//					BDAutoUpdateSDK.cpUpdateInstall(getApplicationContext(), infoForInstall.getInstallPath());
//				}else if(info != null) {
//					BDAutoUpdateSDK.cpUpdateDownload(WXEntryActivity.this, info, new UpdateDownloadCallback());
//				}
//			}
//		});
//		dialog.show();
//	}

//	private class UpdateDownloadCallback implements CPUpdateDownloadCallback {
//
//		@Override
//		public void onDownloadComplete(String apkPath) {
//			BDAutoUpdateSDK.cpUpdateInstall(getApplicationContext(), apkPath);
//		}
//		@Override
//		public void onStart() {
//		}
//
//		@Override
//		public void onPercent(int percent, long rcvLen, long fileSize) {
//		}
//
//		@Override
//		public void onFail(Throwable error, String content) {
//		}
//
//		@Override
//		public void onStop() {
//		}
//
//	}


}
