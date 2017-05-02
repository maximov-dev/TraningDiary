package com.exam.gym;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class overviewEx extends AppCompatActivity {

    String ex, status;

    TextView name, desc;
    DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview_ex);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        name = (TextView) findViewById(R.id.tv_name);
        desc = (TextView) findViewById(R.id.tv_desc);

        ex= getIntent().getExtras().getString("Ex");

        name.setText(ex);

        getInfo();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_over, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==android.R.id.home) {
            onBackPressed();
            return true;
        }
        if (id == R.id.del_ex) {

            if (status.equals("old")){

                Toast.makeText(getApplicationContext(), "Удалять можно только ваши упражнения", Toast.LENGTH_SHORT).show();
            }

            else{
                dbHelper = new DBHelper(this);
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                int delCount = db.delete("category", "name='"+ex+"'", null);
                Toast.makeText(getApplicationContext(), "Удалено", Toast.LENGTH_SHORT).show();

                finish();

            }


            return true;
        }


        return super.onOptionsItemSelected(item);
    }




    private void getInfo(){
        dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor c  = db.query("category",  null, "name='"+ex+"'", null, null, null, null);




        // ставим позицию курсора на первую строку выборки
        // если в выборке нет строк, вернется false

        if(c.moveToFirst())

        {

            // определяем номера столбцов по имени в выборке

            int descColIndex = c.getColumnIndex("desc");
            int statusColIndex = c.getColumnIndex("status");
            desc.setText(c.getString(descColIndex));
            status = c.getString(statusColIndex);

        }



        c.close();



    }

}
