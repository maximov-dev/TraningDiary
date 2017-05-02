package com.exam.gym;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class addExFull extends AppCompatActivity {

    TextView tvEx;
    DBHelper dbHelper;
    EditText series,repetition;
    String data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ex_full);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        data = getIntent().getExtras().getString("data");
        tvEx = (TextView) findViewById(R.id.tv_ex);
        series = (EditText)  findViewById(R.id.input_series);
        repetition = (EditText)  findViewById(R.id.input_repetition);

        dbHelper = new DBHelper(this);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==android.R.id.home) {
            onBackPressed();
            return true;
        }



        return super.onOptionsItemSelected(item);
    }


    public void getExName(View view){

        Intent intent=new Intent(this,ViewExList.class);
        intent.putExtra("do", "getEx");
        startActivityForResult(intent, 1);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {



            if(requestCode==1)
            {


                tvEx.setText(data.getStringExtra("newEx"));
                tvEx.setTextColor(Color.parseColor("#4f9307"));
            }

        }

        else {
            Toast.makeText(this, "Вы не выбрали ни одного упражнения", Toast.LENGTH_SHORT).show();
        }
    }




    public void onClickDone(View v) {

        // создаем объект для данных
        ContentValues cv = new ContentValues();

        // получаем данные из полей ввода
        String name = tvEx.getText().toString() +
                " ( "+ series.getText().toString() + " X " +repetition.getText().toString()
                + " )";


        // подключаемся к Базе Данных
        SQLiteDatabase db = dbHelper.getWritableDatabase();


        switch (v.getId()) {
            case R.id.btnAdd:

                // подготовим данные для вставки в виде пар: наименование столбца - значение

                cv.put("name", name);
                cv.put("data", data);


                // вставляем запись и получаем ее ID
                long rowID = db.insert("schedule", null, cv);

                break;


        }
        // закрываем подключение к Базе Данных
        dbHelper.close();

        Intent intentMessage=new Intent();
        intentMessage.putExtra("ok", "ok");

        setResult(RESULT_OK, intentMessage);
        finish();
    }


}
