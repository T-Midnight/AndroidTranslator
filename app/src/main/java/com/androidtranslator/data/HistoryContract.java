package com.androidtranslator.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

//контейнер для базы данных
//контейнер для базы данных
public class HistoryContract {

    private HistoryContract(){
    }

    public static final class HistoryEntry implements BaseColumns{
        public final static String TABLE_NAME = "history";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_WORD = "word";
        public final static String COLUMN_TRANSLATE = "translate";
        public final static String COLUMN_LANG_FROM = "langFrom";
        public final static String COLUMN_LANG_TO = "langTo";

        public static void insertTestData(DbHelper mDbHelper) {
            SQLiteDatabase db = mDbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COLUMN_WORD, "Привет");
            values.put(COLUMN_TRANSLATE, "Hello");
            values.put(COLUMN_LANG_FROM, "ru");
            values.put(COLUMN_LANG_TO, "en");

            long newRowId = db.insert(TABLE_NAME, null, values);
        }

        public static Cursor findAll(DbHelper mDbHelper) {
            SQLiteDatabase db = mDbHelper.getReadableDatabase();
            String[] projection = {
                    _ID, COLUMN_WORD, COLUMN_TRANSLATE, COLUMN_LANG_FROM, COLUMN_LANG_TO
            };

            Cursor cursor = db.query(TABLE_NAME, projection, null, null,
                    null, null, null);

            return cursor;
        }
    }
}
