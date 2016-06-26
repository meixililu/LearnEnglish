package com.messi.learnenglish;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

import com.avos.avoscloud.AVAnalytics;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.gc.materialdesign.views.ProgressBarCircularIndeterminate;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.messi.learnenglish.adapter.ReadingJuheListAdapter;
import com.messi.learnenglish.db.DataBaseUtil;
import com.messi.learnenglish.impl.FragmentProgressbarListener;
import com.messi.learnenglish.util.AVOUtil;
import com.messi.learnenglish.util.KeyUtil;
import com.messi.learnenglish.util.LogUtil;
import com.messi.learnenglish.util.SaveData;
import com.messi.learnenglish.util.Settings;
import com.messi.learnenglish.util.ToastUtil;
import com.messi.learnenglish.util.TranslateUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReadingJuheActivity extends BaseActivity implements FragmentProgressbarListener {

    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.menu_btn)
    FloatingActionButton menu_btn;
    @BindString(R.string.title_tool)
    String title;

    private ReadingJuheListAdapter pageAdapter;
    private List<AVObject> avObjects;
    private SharedPreferences spf;
    private boolean isNeedSaveData;

    private long exitTime = 0;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reading_juhe_activity);
        ButterKnife.bind(this);
        initViews();
        initData();
        initDatas();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void initDatas() {

    }

    private void initViews() {
        if (toolbar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
        spf = Settings.getSharedPreferences(this);
        avObjects = new ArrayList<AVObject>();
        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=" + getString(R.string.app_id));
    }

    private void initData() {
        try {
            long lastTimeSave = spf.getLong(KeyUtil.SaveLastTime_ReadingJuheActivity, 0);
            if (System.currentTimeMillis() - lastTimeSave > 1000 * 60 * 60 * 24 * 10) {
                SaveData.deleteObject(this, "ReadingJuheActivity");
                LogUtil.DefalutLog("deleteObject   ReadingJuheActivity");
                new QueryTask().execute();
            } else {
                List<String> listStr = (ArrayList<String>) SaveData.getObject(this, "ReadingJuheActivity");
                if (listStr == null || listStr.size() == 0) {
                    LogUtil.DefalutLog("avObjects is null");
                    new QueryTask().execute();
                } else {
                    LogUtil.DefalutLog("avObjects is not null");
                    for (String str : listStr) {
                        avObjects.add(AVObject.parseAVObject(str));
                    }
                    initTabTitle();
                }
            }
        } catch (Exception e) {
            new QueryTask().execute();
            e.printStackTrace();
        }
    }

    @OnClick(R.id.menu_btn)
    public void onClick() {
        Intent intent = new Intent(this, WXEntryActivity.class);
        intent.putExtra(KeyUtil.ActionbarTitle,title);
        startActivity(intent);
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "ReadingJuhe Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.messi.learnenglish/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "ReadingJuhe Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.messi.learnenglish/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    private class QueryTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressbar();
        }

        @Override
        protected Void doInBackground(Void... params) {
            AVQuery<AVObject> query = new AVQuery<AVObject>(AVOUtil.Category.Category);
            query.whereEqualTo(AVOUtil.Category.isvalid, "1");
            query.orderByAscending(AVOUtil.Category.order);
            try {
                List<AVObject> avObject = query.find();
                if (avObject != null) {
                    avObjects.clear();
                    avObjects.addAll(avObject);
                    isNeedSaveData = true;
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
            initTabTitle();
        }
    }

    private void initTabTitle() {
        pageAdapter = new ReadingJuheListAdapter(getSupportFragmentManager(), this, avObjects);
        viewpager.setAdapter(pageAdapter);
        viewpager.setOffscreenPageLimit(7);
        tablayout.setTabsFromPagerAdapter(pageAdapter);
        tablayout.setupWithViewPager(viewpager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_more:
                toMoreActivity();
                break;
            case R.id.action_collected:
                toCollectedActivity();
                break;
        }
        return true;
    }

    private void toCollectedActivity() {
        Intent intent = new Intent(this, CollectedActivity.class);
        startActivity(intent);
        AVAnalytics.onEvent(this, "index_pg_to_collectedpg");
    }

    private void toMoreActivity() {
        Intent intent = new Intent(this, MoreActivity.class);
        startActivity(intent);
        AVAnalytics.onEvent(this, "index_pg_to_morepg");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_MENU:
                toMoreActivity();
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            ToastUtil.diaplayMesShort(getBaseContext(), this.getResources().getString(R.string.exit_program));
            exitTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (avObjects != null && isNeedSaveData) {
            List<String> listStr = new ArrayList<String>();
            for (AVObject item : avObjects) {
                listStr.add(item.toString());
            }
            SaveData.saveObject(this, "ReadingJuheActivity", listStr);
            Settings.saveSharedPreferences(spf, KeyUtil.SaveLastTime_ReadingJuheActivity,
                    System.currentTimeMillis());
            LogUtil.DefalutLog("saveObject   ReadingJuheActivity");
        }

        WebViewFragment.mMainFragment = null;
        boolean AutoClear = spf.getBoolean(KeyUtil.AutoClear, false);
        TranslateUtil.saveTranslateApiOrder(spf);
        if (AutoClear) {
            DataBaseUtil.getInstance().clearExceptFavorite();
        }
    }


}
