package com.messi.learnenglish.dao;

import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;
import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;


// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table MEANS.
*/
public class MeansDao extends AbstractDao<Means, Long> {

    public static final String TABLENAME = "MEANS";

    /**
     * Properties of entity Means.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Mean = new Property(1, String.class, "mean", false, "MEAN");
        public final static Property ResultVoiceId = new Property(2, String.class, "resultVoiceId", false, "RESULT_VOICE_ID");
        public final static Property ResultAudioPath = new Property(3, String.class, "resultAudioPath", false, "RESULT_AUDIO_PATH");
        public final static Property PartsId = new Property(4, Long.class, "partsId", false, "PARTS_ID");
    };

    private Query<Means> parts_MeanListQuery;

    public MeansDao(DaoConfig config) {
        super(config);
    }
    
    public MeansDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'MEANS' (" + //
                "'_id' INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "'MEAN' TEXT," + // 1: mean
                "'RESULT_VOICE_ID' TEXT," + // 2: resultVoiceId
                "'RESULT_AUDIO_PATH' TEXT," + // 3: resultAudioPath
                "'PARTS_ID' INTEGER);"); // 4: partsId
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'MEANS'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Means entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String mean = entity.getMean();
        if (mean != null) {
            stmt.bindString(2, mean);
        }
 
        String resultVoiceId = entity.getResultVoiceId();
        if (resultVoiceId != null) {
            stmt.bindString(3, resultVoiceId);
        }
 
        String resultAudioPath = entity.getResultAudioPath();
        if (resultAudioPath != null) {
            stmt.bindString(4, resultAudioPath);
        }
 
        Long partsId = entity.getPartsId();
        if (partsId != null) {
            stmt.bindLong(5, partsId);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Means readEntity(Cursor cursor, int offset) {
        Means entity = new Means( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // mean
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // resultVoiceId
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // resultAudioPath
            cursor.isNull(offset + 4) ? null : cursor.getLong(offset + 4) // partsId
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Means entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setMean(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setResultVoiceId(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setResultAudioPath(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setPartsId(cursor.isNull(offset + 4) ? null : cursor.getLong(offset + 4));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Means entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Means entity) {
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
    
    /** Internal query to resolve the "meanList" to-many relationship of Parts. */
    public List<Means> _queryParts_MeanList(Long partsId) {
        synchronized (this) {
            if (parts_MeanListQuery == null) {
                QueryBuilder<Means> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.PartsId.eq(null));
                parts_MeanListQuery = queryBuilder.build();
            }
        }
        Query<Means> query = parts_MeanListQuery.forCurrentThread();
        query.setParameter(0, partsId);
        return query.list();
    }

}
