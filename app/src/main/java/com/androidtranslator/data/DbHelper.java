package com.androidtranslator.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.androidtranslator.data.TableContract.LanguageEntry;
import static com.androidtranslator.data.TableContract.HistoryEntry;

public class DbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "translator.db"; //имя файла базы данных
    private static final int DATABASE_VERSION = 2; //версия базы данных


    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //создание таблиц
        String SQL_CREATE_HISTORY_TABLE = "CREATE TABLE " + HistoryEntry.TABLE_NAME + " ("
                + HistoryEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + HistoryEntry.COLUMN_WORD + " TEXT NOT NULL, "
                + HistoryEntry.COLUMN_TRANSLATE + " TEXT NOT NULL, "
                + HistoryEntry.COLUMN_LANG_FROM + " TEXT NOT NULL, "
                + HistoryEntry.COLUMN_LANG_TO + " TEXT NOT NULL);";
        db.execSQL(SQL_CREATE_HISTORY_TABLE);

        String SQL_CREATE_LANGUAGE_TABLE = "CREATE TABLE " + LanguageEntry.TABLE_NAME + " ("
                + LanguageEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + LanguageEntry.COLUMN_CODE + " TEXT NOT NULL, "
                + LanguageEntry.COLUMN_TRANSCRIPT + " TEXT NOT NULL);";
        db.execSQL(SQL_CREATE_LANGUAGE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + HistoryEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + LanguageEntry.TABLE_NAME);
        onCreate(db);
    }
}
