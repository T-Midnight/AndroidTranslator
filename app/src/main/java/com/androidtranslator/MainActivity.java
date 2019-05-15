package com.androidtranslator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.androidtranslator.data.DbHelper;
import com.androidtranslator.data.HistoryContract;

import static com.androidtranslator.data.HistoryContract.*;

public class MainActivity extends AppCompatActivity {
    private final String API_KEY = "trnsl.1.1.20190503T132704Z.4e36b17ed2836893.5b0339809720cab8b47a4f74fae8390374e0209d";
    Button button;
    EditText etFrom;
    EditText etTo;
    ImageButton imageButton;

    private DbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.btnTranslate);
        etFrom = (EditText) findViewById(R.id.etFrom);
        etTo = (EditText) findViewById(R.id.etTo);
        imageButton = (ImageButton) findViewById(R.id.imageButton);

        mDbHelper = new DbHelper(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HistoryEntry.insertTestData(mDbHelper);
            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(intent);
            }
        });
    }
}
