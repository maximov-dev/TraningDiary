package com.exam.gym;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class DBHelper extends SQLiteOpenHelper {



    static String DB_PATH = "/data/data/com.exam.gym/databases/";
    static String DB_NAME = "DBCategoryEx.db";
    SQLiteDatabase db;
    private final Context mContext;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, 1);
        this.mContext = context;
    }

    public void createDB(){
        boolean dbExist = checkDB();
        if (dbExist) {

        } else {
            this.getReadableDatabase();

            try {
                copyDB();
            } catch (Exception e) {
                throw new Error("Error copying DB");

            }

        }
    }

    private void copyDB() throws IOException {
        InputStream dbInput = mContext.getAssets().open(DB_NAME);
        String outFile = DB_PATH + DB_NAME;
        OutputStream dbOutput = new FileOutputStream(outFile);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = dbInput.read(buffer))>0) {
            dbOutput.write(buffer,0,length);
        }

        dbOutput.flush();
        dbOutput.close();
        dbInput.close();

    }

    private boolean checkDB() {
        SQLiteDatabase check = null;
        try {
            String dbPath = DB_PATH+DB_NAME;
            check = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READONLY);
        } catch (Exception e) {
            // TODO: handle exception
        }
        if (check!=null) {
            check.close();
        }

        return check != null ? true : false;
    }

    public void openDB(){
        String dbPath = DB_PATH+DB_NAME;
        db = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READONLY);
    }

    public synchronized void close(){
        if(db != null)
            db.close();
        super.close();
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }





















}
