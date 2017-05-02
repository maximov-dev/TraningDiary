package com.exam.gym;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class addNewEx extends AppCompatActivity {

    protected int mYear, mMonth, mDay;
    TextView tvDate;

    DBHelper dbHelper;

    ListView listView;

    ArrayList<String> catex;

    ArrayAdapter<String> adapter;


    int count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_ex);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        listView = (ListView) findViewById(R.id.listView1);
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        tvDate = (TextView) findViewById(R.id.tv_date);
        tvDate.setText(mDay + "/"
                + (mMonth + 1) + "/" + mYear);

        getUserEx(mDay + "/"
                + (mMonth + 1) + "/" + mYear);




        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position,
                                    long id) {


                String ex = ((TextView) itemClicked).getText().toString();


                alertOrderInfo(ex);
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

            if (count < 8 ){
                Intent intent=new Intent(this,addExFull.class);
                intent.putExtra("data", tvDate.getText().toString());
                startActivityForResult(intent, 1);}

            else{
                Toast.makeText(this, "Превышено количество упражений " + count, Toast.LENGTH_SHORT).show();
            }



            return true;

        }


        return super.onOptionsItemSelected(item);
    }


    public void FindDate(View view) {

        DatePickerDialog dpd = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // Setear valor en editText
                        tvDate.setText(dayOfMonth + "/"
                                + (monthOfYear + 1) + "/" + year);
                        mYear = year;
                        mMonth  = monthOfYear;
                        mDay = dayOfMonth;
                        getUserEx(mDay + "/"+ (mMonth + 1) + "/" + mYear);
                    }
                }, mYear, mMonth, mDay);
        dpd.show();



    }


    private void getUserEx(String p){
        dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor c;


        String select= "data='"+p+"'";
        c = db.query("schedule",  null, select, null, null, null, null);

        count = c.getCount() ;


        // ставим позицию курсора на первую строку выборки
        // если в выборке нет строк, вернется false

        if(c.moveToFirst())

        {

            // определяем номера столбцов по имени в выборке
            int idColIndex = c.getColumnIndex("id");
            int nameColIndex = c.getColumnIndex("name");

            catex = new ArrayList<String>();

            do {

                String pr = c.getString(nameColIndex);

                catex.add( pr);

            } while (c.moveToNext());
        }


        else{
            catex = new ArrayList<String>();
            catex.add("Нет запланированных упражнений на этот день");

        }

        c.close();
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, catex);
        // Привяжем массив через адаптер к ListView
        listView.setAdapter(adapter);


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {


            if(requestCode==1)
            {

                getUserEx(mDay + "/"+ (mMonth + 1) + "/" + mYear);
            }

        }

        else {
            Toast.makeText(this, "Ничего не выбрано", Toast.LENGTH_SHORT).show();
        }
    }


    public void alertOrderInfo(final String name) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);


        builder.setTitle(name);
        /// .setMessage("Выберите:");




        builder.setPositiveButton("Удалить", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                dbHelper = new DBHelper(getApplicationContext());
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                int delCount = db.delete("schedule", "name='"+name+"'", null);
                Toast.makeText(getApplicationContext(), "Удалено", Toast.LENGTH_SHORT).show();
                getUserEx(mDay + "/"+ (mMonth + 1) + "/" + mYear);
                dialog.cancel();
            }
        });

        builder.setNeutralButton("Отмена", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {


                dialog.cancel();

            }
        });

        AlertDialog alert = builder.create();
        alert.show();



    }


}

