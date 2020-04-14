package com.vrcorp.chordgitarindonesia.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;


public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="chordDB";
    private static final String TB_LAGU="lagulist";
    private static final String TB_CHORD="chordlist";
    private static final int DATABASE_VERSION = 1;
    public static String DATABASE_PATH = "";

    private SQLiteDatabase mDB;
    private final Context context;
    private boolean mNeedUpdate = false;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        if(Build.VERSION.SDK_INT >=17){
            DATABASE_PATH = context.getApplicationInfo().dataDir+"/databases/";
        }else{
            DATABASE_PATH = "/data/data/"+context.getPackageName()+"/databases/";
        }
        this.context = context;
        copyDB();
        this.getReadableDatabase();
    }
    public void updateDB() throws IOException{
        if(mNeedUpdate){
            File dbFile = new File(DATABASE_PATH+DATABASE_NAME);
            if(dbFile.exists()){
                dbFile.delete();
                copyDB();
                mNeedUpdate=false;
            }
        }
    }
    private boolean checkDB(){
        File dbFile = new File(DATABASE_PATH+DATABASE_NAME);
        return dbFile.exists();
    }
    private void copyDB(){
        if(!checkDB()){
            this.getReadableDatabase();
            this.close();
            try{
                copyDBFile();
            }catch (IOException mIOExeption){
                throw new Error("ErrorCopyingDB"+mIOExeption);
            }
        }
    }
    private void copyDBFile() throws IOException{
        InputStream inputStream = context.getAssets().open(DATABASE_NAME);
        OutputStream outputStream = new FileOutputStream(DATABASE_PATH+DATABASE_NAME);
        byte[] bytes = new byte[1024];
        int mLength;
        while((mLength=inputStream.read(bytes))>0){
            outputStream.write(bytes,0,mLength);
        }
        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }
    public boolean openDB() throws SQLException{
        mDB = SQLiteDatabase.openDatabase(DATABASE_PATH+DATABASE_NAME,null,SQLiteDatabase.CREATE_IF_NECESSARY);
        return mDB != null;
    }
    @Override
    public synchronized void close(){
        if(mDB!=null){
            mDB.close();
        }
        super.close();
    }
    @Override
    public void onCreate(SQLiteDatabase db){

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        if(newVersion>oldVersion){
            mNeedUpdate=true;
        }
    }

    /* Retrive  data from database */
    public List<DBModel> getFromDB(){
        List<DBModel> modelList = new ArrayList<DBModel>();
        String query = "select * from "+TB_LAGU;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);

        if (cursor.moveToFirst()){
            do {
                DBModel model = new DBModel();
                model.setId(cursor.getString(0));
                model.setJudul(cursor.getString(1));
                model.setGambar(cursor.getString(2));
                model.setUrl(cursor.getString(3));
                model.setDes(cursor.getString(4));
                model.setKategori(cursor.getString(5));
                model.setFavorit(cursor.getString(6));
                model.setWaktu(cursor.getString(7));
                model.setPenerbit(cursor.getString(8));
                modelList.add(model);
            }while (cursor.moveToNext());
        }

        Log.d("student data", modelList.toString());
        return modelList;
    }
}