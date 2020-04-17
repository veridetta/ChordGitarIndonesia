package com.vrcorp.chordgitarindonesia;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialog;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.vrcorp.chordgitarindonesia.adapter.KategoriAdapter;
import com.vrcorp.chordgitarindonesia.db.DBHelper;
import com.vrcorp.chordgitarindonesia.db.DBModel;

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
    private ActionBar toolbar;
    private FloatingActionButton floatingActionButton;
    private SearchView searchView;
    boolean fabKlik=false;
    private KategoriAdapter mDataAdapter;
    private CardView textInputLayout;
    LinearLayout ly_loading;
    ImageView img_load;
    TextView katJudul;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kategori);
        toolbar = getSupportActionBar();
        toolbar.hide();
        img_load = findViewById(R.id.dialog_img);
        katJudul = findViewById(R.id.kategori_jdul);
        Glide.with(this).asGif().load(R.drawable.load).into(img_load);
        rc_kategori = findViewById(R.id.rc_kategori);
        ly_loading = findViewById(R.id.ly_loading);
        String[] sAbjad={"0-9","A","B","C","D","E","F","G","H","I","J","K","L","M","N",
                "O","P","Q","R","S","T","U","V","W","X","Y","Z"};
        Intent intent = getIntent();
        id_abjad=intent.getStringExtra("id_abjad");
        katJudul.setText(sAbjad[Integer.parseInt(id_abjad)]);
        floatingActionButton = findViewById(R.id.fab_filter);
        searchView = findViewById(R.id.cari_input);
        textInputLayout = findViewById(R.id.cari_layout);
        //Toast.makeText(this,"Isinya "+id_abjad,Toast.LENGTH_LONG).show();
        helper = new DBHelper(KategoriActivity.this);
        new GetData().execute();
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fabKlik){
                    textInputLayout.setVisibility(View.GONE);
                    fabKlik=false;
                }else{
                    textInputLayout.setVisibility(View.VISIBLE);
                    fabKlik=true;
                }
            }
        });
        searchView.setQueryHint("Filter Band");
        searchView.onActionViewExpanded();
        searchView.setIconified(true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                searchView.clearFocus();
            }
        }, 90);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                System.out.println("CARI hasil "+query);
                if(query.length()>2){
                    mDataAdapter.getFilter().filter(query);
                }else{
                    Toast.makeText(KategoriActivity.this, "Minimal 3 huruf",Toast.LENGTH_SHORT).show();
                    if(query.length()<1){
                        mDataAdapter.getFilter().filter(query);
                    }
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.length()<1){
                    mDataAdapter.getFilter().filter(newText);
                }
                return false;
            }
        });

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
                    judulList.add(dbList.get(i).getNama_band());
                    idList.add(dbList.get(i).getId_band());
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
                mDataAdapter = new KategoriAdapter( KategoriActivity.this, dbList);
                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(),
                        2, LinearLayoutManager.VERTICAL, false);
                rc_kategori.setLayoutManager(mLayoutManager);
                rc_kategori.setAdapter(mDataAdapter);
                System.out.println("Mentok"+judulList);
                ly_loading.setVisibility(View.GONE);
            }
            //Intent intent = new Intent(LaunchActivity.this,MainActivity.class);
            //startActivity(intent);
        }
    }

}
