package com.androidtranslator.data;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import static com.androidtranslator.data.TableContract.HistoryEntry;

public class History {
    public int id;
    public String word;
    public String translate;
    public String langFrom;
    public String langTo;


    public History() {
        id = 0;
        word = "";
        translate = "";
        langFrom = "";
        langTo = "";
    }

    private void fromCursor(Cursor cursor){
        id = cursor.getInt(cursor.getColumnIndex(HistoryEntry._ID));
        word  = cursor.getString(cursor.getColumnIndex(HistoryEntry.COLUMN_WORD));
        translate = cursor.getString(cursor.getColumnIndex(HistoryEntry.COLUMN_TRANSLATE));
        langFrom = cursor.getString(cursor.getColumnIndex(HistoryEntry.COLUMN_LANG_FROM));
        langTo = cursor.getString(cursor.getColumnIndex(HistoryEntry.COLUMN_LANG_TO));
    }

    public static List<String> getLanguagesFromCursor(Cursor cursor){
        List<String> historyList = new ArrayList<>();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                History historyItem = new History();
                historyItem.fromCursor(cursor);
                historyList.add(historyItem.id + " -- " +
                        historyItem.word + " -- " +
                        historyItem.translate + " -- " +
                        historyItem.langFrom + " -- " +
                        historyItem.langTo);
            }
        }
        return historyList;
    }
}
