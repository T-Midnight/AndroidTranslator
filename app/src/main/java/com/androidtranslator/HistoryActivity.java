package com.androidtranslator;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.androidtranslator.data.DbHelper;
import com.androidtranslator.data.HistoryContract;

import static com.androidtranslator.data.HistoryContract.*;

public class HistoryActivity extends AppCompatActivity {

    private DbHelper mDbHelper;
    TextView tVCount;
    TextView tVItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Activity activity = this;
        setContentView(R.layout.activity_history);
        tVCount = (TextView) findViewById(R.id.tVCount);
        tVItems = (TextView) findViewById(R.id.tVItems);
        mDbHelper = new DbHelper(this);

        findAll(mDbHelper);
    }

    private void findAll(DbHelper mDbHelper){
        Cursor cursor = HistoryEntry.findAll(mDbHelper);

        try {
            tVCount.setText(tVCount.getText() + "" + cursor.getCount());
            tVItems.append(" № -- " +
                    " Слово  -- " +
                    " Перевод  -- " +
                    " С  -- " +
                    " На " + "\n");
            // Узнаем индекс каждого столбца
            int idColumnIndex = cursor.getColumnIndex(HistoryEntry._ID);
            int wordColumnIndex = cursor.getColumnIndex(HistoryEntry.COLUMN_WORD);
            int translateColumnIndex = cursor.getColumnIndex(HistoryEntry.COLUMN_TRANSLATE);
            int langFromColumnIndex = cursor.getColumnIndex(HistoryEntry.COLUMN_LANG_FROM);
            int langToColumnIndex = cursor.getColumnIndex(HistoryEntry.COLUMN_LANG_TO);

            // Проходим через все записи
            while (cursor.moveToNext()) {
                // Используем индекс для получения строки или числа
                int currentID = cursor.getInt(idColumnIndex);
                String currentWord = cursor.getString(wordColumnIndex);
                String currentTranslate = cursor.getString(translateColumnIndex);
                String currentLangFrom = cursor.getString(langFromColumnIndex);
                String currentLangTo = cursor.getString(langToColumnIndex);
                // Выводим значения каждого столбца
                tVItems.append(("\n" + currentID + " -- " +
                        currentWord  + " -- " +
                        currentTranslate + " -- " +
                        currentLangFrom + " -- " +
                        currentLangTo));
            }
        } finally {
            cursor.close();
        }
    }
}
