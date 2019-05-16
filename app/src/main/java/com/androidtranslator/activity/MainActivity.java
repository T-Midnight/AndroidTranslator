package com.androidtranslator.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.util.Linkify;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.androidtranslator.R;
import com.androidtranslator.data.DbHelper;
import com.androidtranslator.data.History;
import com.androidtranslator.web.Client;
import com.androidtranslator.web.Lang;
import com.androidtranslator.web.Translation;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.androidtranslator.data.TableContract.HistoryEntry;
import static com.androidtranslator.data.TableContract.LanguageEntry;

public class MainActivity extends AppCompatActivity {
    Button button;
    EditText etFrom;
    EditText etTo;
    ImageButton imageButton;
    TextView tVLink;
    MaterialSpinner spFrom;
    MaterialSpinner spTo;

    private DbHelper mDbHelper;

    private String translateKey = "";
    private String currentText = "";
    private String code = "";
    private int indexTo = 0, indexFrom = 0; // Индексы состояния спиннеров
    private Call<Translation> currentCall;  // Текущий вызов API
    private Map<String, String> languages;  // Языки из базы
    private List<String> spinnerItems;      // Список языков для спиннера
    private History historyItem;            // Текущее переведенное слово
    private String codeFrom = "";
    private String codeTo= "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.btnTranslate);
        etFrom = (EditText) findViewById(R.id.etFrom);
        etTo = (EditText) findViewById(R.id.etTo);
        imageButton = (ImageButton) findViewById(R.id.imageButton);
        tVLink = (TextView) findViewById(R.id.tVLink);
        translateKey = getResources().getString(R.string.API_KEY);
        spFrom = (MaterialSpinner) findViewById(R.id.spFrom);
        spTo = (MaterialSpinner) findViewById(R.id.spTo);

        mDbHelper = new DbHelper(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //HistoryEntry.insertTestData(mDbHelper);
                setTextTranslated(null);
                if (!etFrom.getText().toString().isEmpty()) {
                    if (currentCall != null)
                        currentCall.cancel();
                    currentText = etFrom.getText().toString();
                    getTranslate(currentText);
                }
            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(intent);
            }
        });

        getLanguages();
        settingSpinner();
    }

    // Получение списка языков при помощи API
    private void getLanguages() {
        languages = LanguageEntry.getMapLanguages(mDbHelper);
        if (languages.isEmpty()) {
            Client.getApi().getLangs(translateKey, "ru").enqueue(new Callback<Lang>() {
                @Override
                public void onResponse(Call<Lang> call, Response<Lang> response) {
                    if (response.isSuccessful()) {
                        Map<String, String> langs = new TreeMap<>(response.body().getLangs());
                        for (Map.Entry<String, String> entry : langs.entrySet()) {
                            languages.put(entry.getValue(), entry.getKey());
                        }
                        LanguageEntry.setListLanguages(languages, mDbHelper);
                        setSpinners();
                    } else {
                        errorMessage(response.code());
                    }
                }

                @Override
                public void onFailure(Call<Lang> call, Throwable t) {
                    errorMessage(0);
                }
            });
        } else {
            setSpinners();
        }
    }

    // Настройка отображения и поведения выбора языков
    private void settingSpinner() {
        spFrom.setPadding(30, spFrom.getPaddingTop(),
                30, spFrom.getPaddingBottom());

        spTo.setPadding(30, spTo.getPaddingTop(),
                30, spTo.getPaddingBottom());

        Linkify.addLinks(tVLink, Linkify.WEB_URLS);

        View.OnClickListener oclSpinner = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLanguages();
            }
        };

        MaterialSpinner.OnItemSelectedListener islSpinner = new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                setTextTranslated(null);
            }
        };

        spFrom.setOnClickListener(oclSpinner);
        spTo.setOnClickListener(oclSpinner);
        spFrom.setOnItemSelectedListener(islSpinner);
        spTo.setOnItemSelectedListener(islSpinner);
    }

    // Установка списка языков
    private void setSpinners() {
        spinnerItems = new ArrayList<>(languages.keySet());

        Collections.sort(spinnerItems, String.CASE_INSENSITIVE_ORDER);

        spFrom.setItems(spinnerItems);
        spTo.setItems(spinnerItems);

        spTo.setSelectedIndex(indexTo);
        spFrom.setSelectedIndex(indexFrom);
    }

    // Проверяем загружены ли языки
    private boolean checkLanguages() {
        if (languages == null || languages.isEmpty() ||
                spinnerItems == null || spinnerItems.isEmpty()) {
            getLanguages();
            return false;
        } else {
            return true;
        }
    }

    // Вывод перевода и сохранение в историю
    private void setTextTranslated(History hItem) {
        if (hItem != null) {
            historyItem = hItem;
            HistoryEntry.insert(hItem, mDbHelper);
            etTo.setText(historyItem.translate);
        } else {
            etTo.setText("");
        }
    }

    // Получение перевода при помощи API
    private void getTranslate(String text) {
        if (!checkLanguages()) {
            return;
        }

        codeFrom = languages.get(spinnerItems.get(spFrom.getSelectedIndex()));
        codeTo = languages.get(spinnerItems.get(spTo.getSelectedIndex()));
        code = codeFrom + "-" + codeTo;
        currentCall = Client.getApi().getTranslate(translateKey, text, code);
        currentCall.enqueue(new Callback<Translation>() {
            @Override
            public void onResponse(Call<Translation> call, Response<Translation> response) {
                if (response.isSuccessful()) {
                    History historyItem = new History(currentText, response.body().getText()[0], codeFrom, codeTo);
                    setTextTranslated(historyItem);
                } else {
                    errorMessage(response.code());
                }
            }

            @Override
            public void onFailure(Call<Translation> call, Throwable t) {
                if (!call.isCanceled()) {
                    errorMessage(0);
                }
            }
        });
    }

    // Вывод сообщений при ошибках
    private void errorMessage(int code) {
        switch (code) {
            case 400:
                Toast.makeText(getApplicationContext(), "Неверный запрос",
                        Toast.LENGTH_SHORT).show();
                break;
            case 401:
                Toast.makeText(getApplicationContext(),  "Неправильный API-ключ",
                        Toast.LENGTH_SHORT).show();
                break;
            case 402:
                Toast.makeText(getApplicationContext(), "API-ключ заблокирован",
                        Toast.LENGTH_SHORT).show();
                break;
            case 404:
                Toast.makeText(getApplicationContext(), "Превышено суточное ограничение на объем переведенного текста",
                        Toast.LENGTH_SHORT).show();
                break;
            case 413:
                Toast.makeText(getApplicationContext(), "Превышен максимально допустимый размер текста",
                        Toast.LENGTH_SHORT).show();
                break;
            case 422:
                Toast.makeText(getApplicationContext(), "Текст не может быть переведен",
                        Toast.LENGTH_SHORT).show();
                break;
            case 501:
                Toast.makeText(getApplicationContext(), "Заданное направление перевода не поддерживается",
                        Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(getApplicationContext(),"Отсутствует интернет соединение",
                        Toast.LENGTH_SHORT).show();
        }
    }
}
