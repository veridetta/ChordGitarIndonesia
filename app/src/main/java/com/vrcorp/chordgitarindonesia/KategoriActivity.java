package com.vrcorp.chordgitarindonesia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.SQLException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.vrcorp.chordgitarindonesia.adapter.KategoriAdapter;
import com.vrcorp.chordgitarindonesia.db.DBHelper;
import com.vrcorp.chordgitarindonesia.db.DBModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class KategoriActivity extends AppCompatActivity {
    String id_abjad="1";
    RecyclerView rc_kategori;
    Integer totalData=0,mentok=0;
    DBHelper helper;
    //---------_ABJAD------------------
    private ArrayList<String> judulList = new ArrayList<>();
    private ArrayList<String> idList = new ArrayList<String>();
    List<DBModel> dbList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kategori);
        rc_kategori = findViewById(R.id.rc_kategori);
        Intent intent = getIntent();
        id_abjad=intent.getStringExtra("id_abjad");
        //Toast.makeText(this,"Isinya "+id_abjad,Toast.LENGTH_LONG).show();
        helper = new DBHelper(KategoriActivity.this);
        new GetData().execute();
    }
    private class GetData extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            totalData = helper.getTotalKategori(id_abjad);
            dbList= new ArrayList<DBModel>();
            dbList = helper.getLagu(id_abjad);
            System.out.println("List"+totalData);
            if(totalData<1){

            }else{
                for (int i = 0; i < totalData; i++) {
                    judulList.add(dbList.get(i).getJudul());
                    idList.add(dbList.get(i).getId());
                    if(totalData - i == 1){
                        mentok=1;
                    }
                }


            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            if(mentok>0){
                KategoriAdapter mDataAdapter = new KategoriAdapter( KategoriActivity.this, judulList, idList);
                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(),
                        2, LinearLayoutManager.VERTICAL, false);
                rc_kategori.setLayoutManager(mLayoutManager);
                rc_kategori.setAdapter(mDataAdapter);
                System.out.println("Mentok"+judulList);

            }
            //Intent intent = new Intent(LaunchActivity.this,MainActivity.class);
            //startActivity(intent);
        }
    }
}
