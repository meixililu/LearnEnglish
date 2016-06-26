package com.messi.learnenglish.db;

import java.util.List;


import android.content.Context;

import com.messi.learnenglish.BaseApplication;
import com.messi.learnenglish.dao.DaoSession;
import com.messi.learnenglish.dao.Dictionary;
import com.messi.learnenglish.dao.DictionaryDao;
import com.messi.learnenglish.dao.EveryDaySentence;
import com.messi.learnenglish.dao.EveryDaySentenceDao;
import com.messi.learnenglish.dao.Means;
import com.messi.learnenglish.dao.MeansDao;
import com.messi.learnenglish.dao.Parts;
import com.messi.learnenglish.dao.PartsDao;
import com.messi.learnenglish.dao.SymbolListDao;
import com.messi.learnenglish.dao.SymbolListDaoDao;
import com.messi.learnenglish.dao.Tag;
import com.messi.learnenglish.dao.TagDao;
import com.messi.learnenglish.dao.record;
import com.messi.learnenglish.dao.recordDao;
import com.messi.learnenglish.util.LogUtil;

import de.greenrobot.dao.query.DeleteQuery;
import de.greenrobot.dao.query.QueryBuilder;

public class DataBaseUtil {

	private static DataBaseUtil instance;  
    private static Context appContext;  
    private DaoSession mDaoSession;
    private recordDao mrecordDao;
    private EveryDaySentenceDao mEveryDaySentenceDao;
    private DictionaryDao mDictionaryDao;
    private MeansDao MmeansDao;
    private PartsDao mPartsDao;
    private TagDao mTagDao;
    private SymbolListDaoDao mSymbolListDaoDao;

	public DataBaseUtil() {
	}
	
	public static DataBaseUtil getInstance() {  
        if (instance == null) {  
            instance = new DataBaseUtil();  
            if (appContext == null){  
                appContext = BaseApplication.mInstance;  
            }  
            instance.mDaoSession = BaseApplication.getDaoSession(appContext);
            instance.mrecordDao = instance.mDaoSession.getRecordDao();
            instance.mDictionaryDao = instance.mDaoSession.getDictionaryDao();  
            instance.mEveryDaySentenceDao = instance.mDaoSession.getEveryDaySentenceDao();  
            instance.mPartsDao = instance.mDaoSession.getPartsDao();  
            instance.MmeansDao = instance.mDaoSession.getMeansDao();  
            instance.mTagDao = instance.mDaoSession.getTagDao();  
            instance.mSymbolListDaoDao = instance.mDaoSession.getSymbolListDaoDao();  
        }  
        return instance;  
    }  
	
	public long insert(Dictionary bean){
		bean.setIscollected("0");
		bean.setVisit_times(0);
//		bean.setSpeak_speed(MainFragment.speed);
		bean.setQuestionVoiceId(System.currentTimeMillis() + "");
		return mDictionaryDao.insert(bean);
	}
	
	public long insert(Parts bean){
		return mPartsDao.insert(bean);
	}
	
	public long insert(Tag bean){
		return mTagDao.insert(bean);
	}
	
	public long insert(Means bean){
		return MmeansDao.insert(bean);
	}
	
	public void insert(List<SymbolListDao> beans){
		for (SymbolListDao bean : beans) {
			mSymbolListDaoDao.insert(bean);
		}
	}
	
	public long getSymbolListSize(){
		return mSymbolListDaoDao.count();
	}
	
	public List<SymbolListDao> getSymbolList(){
		return mSymbolListDaoDao.loadAll();
	}
	
	public void update(SymbolListDao bean){
		mSymbolListDaoDao.update(bean);
	}

	public long insert(record bean) {
		bean.setIscollected("0");
		bean.setVisit_times(0);
//		bean.setSpeak_speed(MainFragment.speed);
		bean.setQuestionVoiceId(System.currentTimeMillis() + "");
		bean.setResultVoiceId(System.currentTimeMillis()-5 + "");
		return mrecordDao.insert(bean);
	}
	
	
	public void update(record bean){
		mrecordDao.update(bean);
	}
	
	public void update(Dictionary bean){
		mDictionaryDao.update(bean);
	}

	public List<record> getDataListRecord(int offset, int maxResult) {
		QueryBuilder<record> qb = mrecordDao.queryBuilder();
		qb.orderDesc(recordDao.Properties.Id);
		qb.limit(maxResult);
		return qb.list();
	}
	
	public List<Dictionary> getDataListDictionary(int offset, int maxResult) {
		QueryBuilder<Dictionary> qb = mDictionaryDao.queryBuilder();
		qb.orderDesc(DictionaryDao.Properties.Id);
		qb.limit(maxResult);
		return qb.list();
	}
	
	public List<record> getDataListCollected(int offset, int maxResult) {
		QueryBuilder<record> qb = mrecordDao.queryBuilder();
		qb.where(recordDao.Properties.Iscollected.eq("1"));
		qb.orderDesc(recordDao.Properties.Id);
		qb.limit(maxResult);
		return qb.list();
	}
	
	public List<Dictionary> getDataListDictionaryCollected(int offset, int maxResult) {
		QueryBuilder<Dictionary> qb = mDictionaryDao.queryBuilder();
		qb.where(DictionaryDao.Properties.Iscollected.eq("1"));
		qb.orderDesc(DictionaryDao.Properties.Id);
		qb.limit(maxResult);
		return qb.list();
	}

	public void dele(record bean) {
		mrecordDao.delete(bean);
	}
	
	public void dele(Dictionary bean) {
		mDictionaryDao.delete(bean);
	}
	
	public void clearExceptFavorite() {
		clearTranslateExceptFavorite();
		clearDictionaryExceptFavorite();
	}
	
	public void clearTranslateExceptFavorite() {
		QueryBuilder<record> qb = mrecordDao.queryBuilder();
		DeleteQuery<record> bd = qb.where(recordDao.Properties.Iscollected.eq("0")).buildDelete();
		bd.executeDeleteWithoutDetachingEntities();
	}
	
	public void clearDictionaryExceptFavorite() {
		QueryBuilder<Dictionary> qb = mDictionaryDao.queryBuilder();
		DeleteQuery<Dictionary> bd = qb.where(DictionaryDao.Properties.Iscollected.eq("0")).buildDelete();
		bd.executeDeleteWithoutDetachingEntities();
	}
	
	public void clearAll() {
		clearAllTranslate();
		clearAllDictionary();
	}
	
	public void clearAllTranslate(){
		mrecordDao.deleteAll();
	}
	
	public void clearAllDictionary() {
		mDictionaryDao.deleteAll();
	}

	public long getRecordCount() {
		return mrecordDao.count();
	}
	
	public long getDictionaryCount(){
		return mDictionaryDao.count();
	}
	
	/**Daily Sentence CURD**/
	public long insert(EveryDaySentence bean){
		return mEveryDaySentenceDao.insert(bean);
	}
	
	public boolean isExist(long cid){
		QueryBuilder<EveryDaySentence> qb = mEveryDaySentenceDao.queryBuilder();
		qb.where(EveryDaySentenceDao.Properties.Cid.eq(cid));
		int size = qb.list().size();
		LogUtil.DefalutLog("isExist---size:"+size);
		return size > 0;
	}
	
	public List<EveryDaySentence> getDailySentenceList(int limit){
		QueryBuilder<EveryDaySentence> qb = mEveryDaySentenceDao.queryBuilder();
		qb.orderDesc(EveryDaySentenceDao.Properties.Id);
		qb.limit(limit);
		return qb.list();
	}
	/**Daily Sentence CURD**/
	
}
