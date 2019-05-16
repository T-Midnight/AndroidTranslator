package com.androidtranslator.data;

import android.database.Cursor;

import java.util.Map;
import java.util.TreeMap;

import static com.androidtranslator.data.TableContract.*;

public class Language {
    public int id;
    public String code;
    public String transcript;


    public Language() {
        id = 0;
        code = "";
        transcript = "";
    }

    private void fromCursor(Cursor cursor){
        id = cursor.getInt(cursor.getColumnIndex(LanguageEntry._ID));
        code = cursor.getString(cursor.getColumnIndex(LanguageEntry.COLUMN_CODE));
        transcript = cursor.getString(cursor.getColumnIndex(LanguageEntry.COLUMN_TRANSCRIPT));
    }

    public static Map<String, String> getLanguagesFromCursor(Cursor cursor){
        Map<String, String> languages = new TreeMap<>();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Language language = new Language();
                language.fromCursor(cursor);
                languages.put(language.transcript, language.code);
            }
        }
        return languages;
    }
}
