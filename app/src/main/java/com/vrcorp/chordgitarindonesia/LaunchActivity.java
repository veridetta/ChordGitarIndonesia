package com.vrcorp.chordgitarindonesia;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hanks.htextview.base.HTextView;
import com.hhl.gridpagersnaphelper.GridPagerSnapHelper;
import com.vrcorp.chordgitarindonesia.adapter.AbjadAdapter;
import com.vrcorp.chordgitarindonesia.adapter.LastAdapter;
import com.vrcorp.chordgitarindonesia.adapter.TopDuniaAdapter;
import com.vrcorp.chordgitarindonesia.adapter.TopIndoAdapter;
import com.vrcorp.chordgitarindonesia.db.DBHelper;

import java.io.IOException;
import java.util.ArrayList;

public class LaunchActivity extends AppCompatActivity {
    private DBHelper dbHelper;
    private SQLiteDatabase database;
    private ImageView bgLaunch;
    private HTextView txtWelcome;
    int delay = 2000; //milliseconds
    Handler handler;
    ArrayList<String> arrMessages = new ArrayList<>();
    int position=0;
    private ActionBar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        bgLaunch = findViewById(R.id.bg_launch);
        toolbar = getSupportActionBar();
        toolbar.hide();
        txtWelcome = findViewById(R.id.welcome);
        Glide.with(this).asGif().load(R.drawable.load).into(bgLaunch);
        String[] pesan = {"Menyiapkan","Memuat chord terbaik","Mencari lagu pilihan", "Hampir selesai",
        "Siapkan dirimu","Akan segera siap"};
        for(int i=0;i<pesan.length;i++){
            arrMessages.add(pesan[i]);
        }
        txtWelcome.animateText(arrMessages.get(position));
        position++;
        /* Change Messages every 2 Seconds */
        handler = new Handler();
        handler.postDelayed(new Runnable(){
            public void run(){
                handler.postDelayed(this, delay);
                if(position>=arrMessages.size())
                    position=0;
                txtWelcome.animateText(arrMessages.get(position));
                position++;
            }
        }, delay);
        new CopyDB().execute();
    }
    private class CopyDB extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            dbHelper = new DBHelper(LaunchActivity.this);
            try {
                dbHelper.updateDB();
            }catch (IOException mIOException){
                throw new Error("UnableTOUpdateDB");
            }
            try {
                database=dbHelper.getWritableDatabase();
            }catch (SQLException mSQLException){
                throw mSQLException;
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            Intent intent = new Intent(LaunchActivity.this,MainActivity.class);
            startActivity(intent);
        }
        }
}
