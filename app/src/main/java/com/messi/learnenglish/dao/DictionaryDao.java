package com.messi.learnenglish.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table DICTIONARY.
*/
public class DictionaryDao extends AbstractDao<Dictionary, Long> {

    public static final String TABLENAME = "DICTIONARY";

    /**
     * Properties of entity Dictionary.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Word_name = new Property(1, String.class, "word_name", false, "WORD_NAME");
        public final static Property Result = new Property(2, String.class, "result", false, "RESULT");
        public final static Property To = new Property(3, String.class, "to", false, "TO");
        public final static Property From = new Property(4, String.class, "from", false, "FROM");
        public final static Property Ph_am = new Property(5, String.class, "ph_am", false, "PH_AM");
        public final static Property Ph_en = new Property(6, String.class, "ph_en", false, "PH_EN");
        public final static Property Ph_zh = new Property(7, String.class, "ph_zh", false, "PH_ZH");
        public final static Property Type = new Property(8, String.class, "type", false, "TYPE");
        public final static Property QuestionVoiceId = new Property(9, String.class, "questionVoiceId", false, "QUESTION_VOICE_ID");
        public final static Property QuestionAudioPath = new Property(10, String.class, "questionAudioPath", false, "QUESTION_AUDIO_PATH");
        public final static Property ResultVoiceId = new Property(11, String.class, "resultVoiceId", false, "RESULT_VOICE_ID");
        public final static Property ResultAudioPath = new Property(12, String.class, "resultAudioPath", false, "RESULT_AUDIO_PATH");
        public final static Property Iscollected = new Property(13, String.class, "iscollected", false, "ISCOLLECTED");
        public final static Property Visit_times = new Property(14, Integer.class, "visit_times", false, "VISIT_TIMES");
        public final static Property Speak_speed = new Property(15, Integer.class, "speak_speed", false, "SPEAK_SPEED");
        public final static Property Backup1 = new Property(16, String.class, "backup1", false, "BACKUP1");
        public final static Property Backup2 = new Property(17, String.class, "backup2", false, "BACKUP2");
        public final static Property Backup3 = new Property(18, String.class, "backup3", false, "BACKUP3");
        public final static Property Backup4 = new Property(19, String.class, "backup4", false, "BACKUP4");
        public final static Property Backup5 = new Property(20, String.class, "backup5", false, "BACKUP5");
    };

    private DaoSession daoSession;


    public DictionaryDao(DaoConfig config) {
        super(config);
    }
    
    public DictionaryDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'DICTIONARY' (" + //
                "'_id' INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "'WORD_NAME' TEXT," + // 1: word_name
                "'RESULT' TEXT," + // 2: result
                "'TO' TEXT," + // 3: to
                "'FROM' TEXT," + // 4: from
                "'PH_AM' TEXT," + // 5: ph_am
                "'PH_EN' TEXT," + // 6: ph_en
                "'PH_ZH' TEXT," + // 7: ph_zh
                "'TYPE' TEXT," + // 8: type
                "'QUESTION_VOICE_ID' TEXT," + // 9: questionVoiceId
                "'QUESTION_AUDIO_PATH' TEXT," + // 10: questionAudioPath
                "'RESULT_VOICE_ID' TEXT," + // 11: resultVoiceId
                "'RESULT_AUDIO_PATH' TEXT," + // 12: resultAudioPath
                "'ISCOLLECTED' TEXT," + // 13: iscollected
                "'VISIT_TIMES' INTEGER," + // 14: visit_times
                "'SPEAK_SPEED' INTEGER," + // 15: speak_speed
                "'BACKUP1' TEXT," + // 16: backup1
                "'BACKUP2' TEXT," + // 17: backup2
                "'BACKUP3' TEXT," + // 18: backup3
                "'BACKUP4' TEXT," + // 19: backup4
                "'BACKUP5' TEXT);"); // 20: backup5
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'DICTIONARY'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Dictionary entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String word_name = entity.getWord_name();
        if (word_name != null) {
            stmt.bindString(2, word_name);
        }
 
        String result = entity.getResult();
        if (result != null) {
            stmt.bindString(3, result);
        }
 
        String to = entity.getTo();
        if (to != null) {
            stmt.bindString(4, to);
        }
 
        String from = entity.getFrom();
        if (from != null) {
            stmt.bindString(5, from);
        }
 
        String ph_am = entity.getPh_am();
        if (ph_am != null) {
            stmt.bindString(6, ph_am);
        }
 
        String ph_en = entity.getPh_en();
        if (ph_en != null) {
            stmt.bindString(7, ph_en);
        }
 
        String ph_zh = entity.getPh_zh();
        if (ph_zh != null) {
            stmt.bindString(8, ph_zh);
        }
 
        String type = entity.getType();
        if (type != null) {
            stmt.bindString(9, type);
        }
 
        String questionVoiceId = entity.getQuestionVoiceId();
        if (questionVoiceId != null) {
            stmt.bindString(10, questionVoiceId);
        }
 
        String questionAudioPath = entity.getQuestionAudioPath();
        if (questionAudioPath != null) {
            stmt.bindString(11, questionAudioPath);
        }
 
        String resultVoiceId = entity.getResultVoiceId();
        if (resultVoiceId != null) {
            stmt.bindString(12, resultVoiceId);
        }
 
        String resultAudioPath = entity.getResultAudioPath();
        if (resultAudioPath != null) {
            stmt.bindString(13, resultAudioPath);
        }
 
        String iscollected = entity.getIscollected();
        if (iscollected != null) {
            stmt.bindString(14, iscollected);
        }
 
        Integer visit_times = entity.getVisit_times();
        if (visit_times != null) {
            stmt.bindLong(15, visit_times);
        }
 
        Integer speak_speed = entity.getSpeak_speed();
        if (speak_speed != null) {
            stmt.bindLong(16, speak_speed);
        }
 
        String backup1 = entity.getBackup1();
        if (backup1 != null) {
            stmt.bindString(17, backup1);
        }
 
        String backup2 = entity.getBackup2();
        if (backup2 != null) {
            stmt.bindString(18, backup2);
        }
 
        String backup3 = entity.getBackup3();
        if (backup3 != null) {
            stmt.bindString(19, backup3);
        }
 
        String backup4 = entity.getBackup4();
        if (backup4 != null) {
            stmt.bindString(20, backup4);
        }
 
        String backup5 = entity.getBackup5();
        if (backup5 != null) {
            stmt.bindString(21, backup5);
        }
    }

    @Override
    protected void attachEntity(Dictionary entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Dictionary readEntity(Cursor cursor, int offset) {
        Dictionary entity = new Dictionary( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // word_name
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // result
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // to
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // from
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // ph_am
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // ph_en
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // ph_zh
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // type
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // questionVoiceId
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // questionAudioPath
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // resultVoiceId
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // resultAudioPath
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13), // iscollected
            cursor.isNull(offset + 14) ? null : cursor.getInt(offset + 14), // visit_times
            cursor.isNull(offset + 15) ? null : cursor.getInt(offset + 15), // speak_speed
            cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16), // backup1
            cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17), // backup2
            cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18), // backup3
            cursor.isNull(offset + 19) ? null : cursor.getString(offset + 19), // backup4
            cursor.isNull(offset + 20) ? null : cursor.getString(offset + 20) // backup5
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Dictionary entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setWord_name(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setResult(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setTo(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setFrom(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setPh_am(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setPh_en(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setPh_zh(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setType(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setQuestionVoiceId(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setQuestionAudioPath(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setResultVoiceId(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setResultAudioPath(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setIscollected(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
        entity.setVisit_times(cursor.isNull(offset + 14) ? null : cursor.getInt(offset + 14));
        entity.setSpeak_speed(cursor.isNull(offset + 15) ? null : cursor.getInt(offset + 15));
        entity.setBackup1(cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16));
        entity.setBackup2(cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17));
        entity.setBackup3(cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18));
        entity.setBackup4(cursor.isNull(offset + 19) ? null : cursor.getString(offset + 19));
        entity.setBackup5(cursor.isNull(offset + 20) ? null : cursor.getString(offset + 20));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Dictionary entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Dictionary entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
