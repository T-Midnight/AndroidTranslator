package com.androidtranslator.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.androidtranslator.R;
import com.androidtranslator.data.DbHelper;

import java.util.ArrayList;
import java.util.List;

import static com.androidtranslator.data.TableContract.HistoryEntry;

public class HistoryActivity extends AppCompatActivity {

    private DbHelper mDbHelper;
    TextView tVCount;
    TextView tVItems;
    List<String> historyItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Activity activity = this;
        setContentView(R.layout.activity_history);
        tVCount = (TextView) findViewById(R.id.tVCount);
        tVItems = (TextView) findViewById(R.id.tVItems);
        mDbHelper = new DbHelper(this);

        showAll(mDbHelper);
    }

    private void showAll(DbHelper mDbHelper){
        historyItems = HistoryEntry.getStringHistory(mDbHelper);

        tVCount.setText(tVCount.getText() + "" + historyItems.size());
        tVItems.append(" № -- " + " Слово  -- " + " Перевод  -- " + " С  -- " + " На " + "\n");
        for (String item: historyItems) {
            tVItems.append(item);
        }
    }
}
