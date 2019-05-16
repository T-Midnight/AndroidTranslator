package com.androidtranslator.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import java.util.List;
import java.util.Map;

//контейнер для базы данных
public class TableContract {

    public TableContract(){
    }

    public static final class HistoryEntry implements BaseColumns{
        public final static String TABLE_NAME = "history";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_WORD = "word";
        public final static String COLUMN_TRANSLATE = "translate";
        public final static String COLUMN_LANG_FROM = "langFrom";
        public final static String COLUMN_LANG_TO = "langTo";

        private static List<String> historyItems;

        public static void insertTestData(DbHelper mDbHelper) {
            SQLiteDatabase db = mDbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COLUMN_WORD, "Привет");
            values.put(COLUMN_TRANSLATE, "Ni hao");
            values.put(COLUMN_LANG_FROM, "ru");
            values.put(COLUMN_LANG_TO, "cn");

            long newRowId = db.insert(TABLE_NAME, null, values);
        }

        public static List<String> getStringHistory(DbHelper mDbHelper) {
            if (historyItems != null) {
                return historyItems;
            }
            return getDBHistory(mDbHelper);
        }

        private static List<String> getDBHistory(DbHelper mDbHelper) {
            SQLiteDatabase db = mDbHelper.getReadableDatabase();
            String[] projection = {
                    _ID, COLUMN_WORD, COLUMN_TRANSLATE, COLUMN_LANG_FROM, COLUMN_LANG_TO
            };

            Cursor cursor = db.query(TABLE_NAME, projection, null, null,
                    null, null, "_ID ASC");
            historyItems = History.getLanguagesFromCursor(cursor);

            if (cursor != null) {
                cursor.close();
            }
            return historyItems;
        }
    }

    public static final class LanguageEntry implements BaseColumns {
        public final static String TABLE_NAME = "language";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_CODE = "code";
        public final static String COLUMN_TRANSCRIPT = "transcript";

        private static Map<String, String> languages;

        public static Map<String, String> getMapLanguages(DbHelper mDbHelper) {
            if (!languages.isEmpty()) {
                return languages;
            }
            return getDBLanguages(mDbHelper);
        }

        private static Map<String, String> getDBLanguages(DbHelper mDbHelper) {
            SQLiteDatabase db = mDbHelper.getReadableDatabase();
            String[] projection = {
                    _ID, COLUMN_CODE, COLUMN_TRANSCRIPT
            };

            Cursor cursor = db.query(TABLE_NAME, projection,
                    null, null, null, null,"transcript ASC");
            languages = Language.getLanguagesFromCursor(cursor);

            if (cursor != null) {
                cursor.close();
            }
            return languages;
        }

        public static void setListLanguages(Map<String, String> myLanguages, DbHelper mDbHelper) {
            languages = myLanguages;
            for (Map.Entry<String, String> entry : languages.entrySet()) {
                Language language = new Language();
                language.transcript = entry.getKey();
                language.code = entry.getValue();
                insert(language, mDbHelper);
            }
        }

        public static void insert(Language language, DbHelper mDbHelper) {
            SQLiteDatabase db = mDbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COLUMN_CODE, language.code);
            values.put(COLUMN_TRANSCRIPT, language.transcript);

            long newRowId = db.insert(TABLE_NAME, null, values);
        }
    }
}
