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
 * DAO for table TAG.
*/
public class TagDao extends AbstractDao<Tag, Long> {

    public static final String TABLENAME = "TAG";

    /**
     * Properties of entity Tag.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Name = new Property(1, String.class, "name", false, "NAME");
        public final static Property EveryDaySentenceId = new Property(2, Long.class, "everyDaySentenceId", false, "EVERY_DAY_SENTENCE_ID");
    };

    private Query<Tag> everyDaySentence_TaglistQuery;

    public TagDao(DaoConfig config) {
        super(config);
    }
    
    public TagDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'TAG' (" + //
                "'_id' INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "'NAME' TEXT," + // 1: name
                "'EVERY_DAY_SENTENCE_ID' INTEGER);"); // 2: everyDaySentenceId
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'TAG'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Tag entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(2, name);
        }
 
        Long everyDaySentenceId = entity.getEveryDaySentenceId();
        if (everyDaySentenceId != null) {
            stmt.bindLong(3, everyDaySentenceId);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Tag readEntity(Cursor cursor, int offset) {
        Tag entity = new Tag( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // name
            cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2) // everyDaySentenceId
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Tag entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setEveryDaySentenceId(cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Tag entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Tag entity) {
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
    
    /** Internal query to resolve the "taglist" to-many relationship of EveryDaySentence. */
    public List<Tag> _queryEveryDaySentence_Taglist(Long everyDaySentenceId) {
        synchronized (this) {
            if (everyDaySentence_TaglistQuery == null) {
                QueryBuilder<Tag> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.EveryDaySentenceId.eq(null));
                everyDaySentence_TaglistQuery = queryBuilder.build();
            }
        }
        Query<Tag> query = everyDaySentence_TaglistQuery.forCurrentThread();
        query.setParameter(0, everyDaySentenceId);
        return query.list();
    }

}
