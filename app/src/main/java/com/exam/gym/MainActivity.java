package com.exam.gym;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // до метода onCreate()
        RelativeLayout mRelativeLayout;


// в методе onCreate()
        mRelativeLayout = (RelativeLayout)findViewById(R.id.RelativeLayout);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbHelper = new DBHelper(getApplicationContext());
        // создаем базу данных
        dbHelper.createDB();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void goEdit(View v) {
        Intent intent=new Intent(this,addNewEx.class);
        startActivity(intent);
    }
    public void goViewEx(View v) {
        Intent intent=new Intent(this,ViewExList.class);
        intent.putExtra("do", "getInfo");
        startActivity(intent);
    }

    public void goday(View v) {
        Intent intent=new Intent(this,addNewEx.class);
        startActivity(intent);
    }

    public void onRedButtonClick(View view)
    {
        //setContentView(R.layout.content_main2);

       // RelativeLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.yellowColor));

        //Toast.makeText(this, "eq", Toast.LENGTH_SHORT).show();
    }

    public void onClickAuthor(MenuItem item)
    {
        Toast.makeText(this, "Дневник тренировок \nВерсия: 1.1 \nАвтор: Максимов", Toast.LENGTH_SHORT).show();
    }

    public void onSettings(MenuItem item)
    {
      //  Intent intent=new Intent(this,TDSettings.class);
        //startActivity(intent);
        Toast.makeText(this, "Дневник тренировок \nВерсия: 1.1 \nАвтор: Максимов", Toast.LENGTH_SHORT).show();
    }

}
