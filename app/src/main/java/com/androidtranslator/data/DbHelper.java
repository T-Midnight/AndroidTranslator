package com.androidtranslator.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import static com.androidtranslator.data.HistoryContract.*;

public class DbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "translator.db"; //имя файла базы данных
    private static final int DATABASE_VERSION = 1; //версия базы данных


    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //создание таблицы
        String SQL_CREATE_HISTORY_TABLE = "CREATE TABLE " + HistoryEntry.TABLE_NAME + " ("
                + HistoryContract.HistoryEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + HistoryEntry.COLUMN_WORD + " TEXT NOT NULL, "
                + HistoryEntry.COLUMN_TRANSLATE + " TEXT NOT NULL, "
                + HistoryEntry.COLUMN_LANG_FROM + " TEXT NOT NULL, "
                + HistoryEntry.COLUMN_LANG_TO + " TEXT NOT NULL);";
        db.execSQL(SQL_CREATE_HISTORY_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + HistoryEntry.TABLE_NAME);
        onCreate(db);
    }
}
