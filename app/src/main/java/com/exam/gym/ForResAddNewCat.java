package com.exam.gym;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


public class ForResAddNewCat extends AppCompatActivity implements View.OnClickListener {

    DBHelper dbHelper;

    String LOG_TAG="table category", cat;
    Button btnAdd;
    EditText etName, etDesc;

    String[] data = {"Базовые упражнения", "Для мышц по вектору",
            "Тренировкa мышц по мышечной группе"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_res_add_new_cat);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);



        etName = (EditText) findViewById(R.id.input_name);
        etDesc = (EditText) findViewById(R.id.input_desc);

        // создаем объект для создания и управления версиями БД
        dbHelper = new DBHelper(this);

        // адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(adapter);
        // заголовок
        spinner.setPrompt("Выбор группы упражнения");
        // выделяем элемент
        spinner.setSelection(0);
        // устанавливаем обработчик нажатия
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // показываем позиция нажатого элемента
                Spinner spinner = (Spinner) findViewById(R.id.spinner);
                assert spinner != null;
                cat = spinner.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

    }


    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==android.R.id.home) {
            onBackPressed();
            return true;
        }



        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onClick(View v) {

        // создаем объект для данных
        ContentValues cv = new ContentValues();

        // получаем данные из полей ввода
        String name = etName.getText().toString();
        String desc = etDesc.getText().toString();


            // подключаемся к БД
            SQLiteDatabase db = dbHelper.getWritableDatabase();


            switch (v.getId()) {
                case R.id.btnAdd:
                    Log.d(LOG_TAG, "--- Insert in mytable: ---");
                    // подготовим данные для вставки в виде пар: наименование столбца - значение

                    cv.put("name", name);
                    cv.put("cat", cat);
                    cv.put("desc", desc);
                    cv.put("status", "new");
                    // вставляем запись и получаем ее ID
                    long rowID = db.insert("category", null, cv);
                    Log.e(LOG_TAG, "row inserted, ID = " + rowID);
                    break;


            }
            // закрываем подключение к БД
            dbHelper.close();

            Intent intentMessage = new Intent();
            intentMessage.putExtra("ok", "ok");

            setResult(RESULT_OK, intentMessage);
            finish();
    }

}
