package com.exam.gym;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class ViewExList extends AppCompatActivity {



    String[] data = {"Все упражнения","Базовые упражнения", "Для мышц по вектору",
            "Тренировкa мышц по мышечной группе"};
    DBHelper dbHelper;

    ListView listView;

    ArrayList<String> catex;

    ArrayAdapter<String> adapter;

    Boolean show = false;

    String cat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_ex_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

         listView = (ListView) findViewById(R.id.listView1);

        if (getIntent().getExtras().getString("do").equals("getEx")){ show = true;}

        // адаптер
        ArrayAdapter<String> adapterSpin = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data);
        adapterSpin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(adapterSpin);
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
                getUserEx(cat);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });


        getUserEx("Все упражнения");


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position,
                                    long id) {


                String ex = ((TextView) itemClicked).getText().toString();

                if(show){
                    Intent intentMessage=new Intent();
                    intentMessage.putExtra("newEx", ((TextView) itemClicked).getText());
                    setResult(RESULT_OK, intentMessage);
                    finish();
                }
                else {



                    overviewEx(ex);


                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_ex, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
           if(id==android.R.id.home) {
               onBackPressed();
               return true;
           }
            if (id == R.id.add_ex) {
                Intent intent=new Intent(this,ForResAddNewCat.class);
                startActivityForResult(intent, 1);
                return true;
            }


                return super.onOptionsItemSelected(item);
        }





    private void getUserEx(String p){
        dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor c;

        if(p.equals("Все упражнения")){
            c = db.query("category",  null, null, null, null, null, null);
            }
        else {
            String select= "cat='"+p+"'";
            c = db.query("category",  null, select, null, null, null, null);

        }

        // ставим позицию курсора на первую строку выборки
        // если в выборке нет строк, вернется false
        if(c.moveToFirst())

        {

            // определяем номера столбцов по имени в выборке
            int idColIndex = c.getColumnIndex("id");
            int nameColIndex = c.getColumnIndex("name");
            int catColIndex = c.getColumnIndex("cat");

            catex = new ArrayList<String>();

            do {

                String pr = c.getString(nameColIndex);

                catex.add( pr);

            } while (c.moveToNext());
        }


        else{
            catex = new ArrayList<String>();
            catex.add("Нет упражнений на эту группу мышц");

        }

        c.close();
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, catex);
        // Привяжем массив через адаптер к ListView
        listView.setAdapter(adapter);


    }


    private void overviewEx(String ex){

        Intent in;
        in = new Intent(this,overviewEx.class);
        in.putExtra("Ex", ex);
        startActivity(in);
    }


    @Override
    public void onResume() {
        super.onResume();

        getUserEx("Все упражнения");
    }


}
