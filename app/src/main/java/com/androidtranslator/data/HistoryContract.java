package com.androidtranslator.data;

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
    }
}
