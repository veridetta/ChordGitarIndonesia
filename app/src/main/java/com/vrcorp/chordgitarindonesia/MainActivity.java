package com.vrcorp.chordgitarindonesia;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.SearchView;
import android.widget.Toast;

import com.hhl.gridpagersnaphelper.GridPagerSnapHelper;
import com.vrcorp.chordgitarindonesia.adapter.AbjadAdapter;
import com.vrcorp.chordgitarindonesia.adapter.LastAdapter;
import com.vrcorp.chordgitarindonesia.adapter.TopDuniaAdapter;
import com.vrcorp.chordgitarindonesia.adapter.TopIndoAdapter;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //------------- LastDimainkan --------------
    private ArrayList<String> last_photo = new ArrayList<>();
    private ArrayList<String> last_judul = new ArrayList<String>();
    private ArrayList<String> last_penyanyi = new ArrayList<>();
    private ArrayList<String> last_idChord = new ArrayList<>();
    private ArrayList<String> last_idPenyanyi = new ArrayList<>();
    //---top 10 indo ------------------
    private ArrayList<String> topindo_photo = new ArrayList<>();
    private ArrayList<String> topindo_judul = new ArrayList<String>();
    private ArrayList<String> topindo_penyanyi = new ArrayList<>();
    private ArrayList<String> topindo_idChord = new ArrayList<>();
    private ArrayList<String> topindo_idPenyanyi = new ArrayList<>();
    //-------top dunia ---------------
    private ArrayList<String> topdunia_photo = new ArrayList<>();
    private ArrayList<String> topdunia_judul = new ArrayList<String>();
    private ArrayList<String> topdunia_penyanyi = new ArrayList<>();
    private ArrayList<String> topdunia_idChord = new ArrayList<>();
    private ArrayList<String> topdunia_idPenyanyi = new ArrayList<>();
    //-----------------------------------------
    //---------_ABJAD------------------
    private ArrayList<String> abjad_id = new ArrayList<>();
    private ArrayList<String> abjad_judul = new ArrayList<String>();
    //-------------------
    //------------- RC Kumpulan ------------------
    RecyclerView rc_last, rc_topIndo, rcTopDunia,rcAbjad;
    //---------
    Integer chartIndo=0,chartDunia=0;
    private ActionBar toolbar;
    SharedPreferences sharedpreferences;
    SearchView cari;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = getSupportActionBar();
        toolbar.hide();
        sharedpreferences = getSharedPreferences("ChordGuitar", Context.MODE_PRIVATE);
        rc_last = findViewById(R.id.rc_terakhir_dimainkan);
        rc_topIndo= findViewById(R.id.rc_top_indo);
        rcTopDunia= findViewById(R.id.rc_top_dunia);
        rcAbjad = findViewById(R.id.rc_abjad);
        cari = findViewById(R.id.cari_input);
        new ContentGet().execute();
    }
    private class ContentGet extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            String jsonStr = null;
            JSONObject JSONObject = null;
            try {
                InputStream inputStream = getAssets().open("lastplay.json");
                int sizeOfJSONFile = inputStream.available();
                byte[] bytes = new byte[sizeOfJSONFile];
                inputStream.read(bytes);
                inputStream.close();
                jsonStr = new String(bytes, "UTF-8");

            } catch (IOException e) {
                e.printStackTrace();
            }
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    // last played -----------
                    JSONArray contacts = jsonObj.getJSONArray("lastplay");
                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);

                        String slast_idchord = c.getString("id");
                        String slast_judul = c.getString("judul");
                        String slast_penyanyi = c.getString("nama_band");
                        String slast_idpenyanyi = c.getString("id_band");
                        last_idChord.add(slast_idchord);
                        last_judul.add(slast_judul);
                        last_penyanyi.add(slast_penyanyi);
                        last_idPenyanyi.add(slast_idpenyanyi);
                    }
                    //-------------------------------
                    // TOP INDO -----------
                    JSONArray topindo = jsonObj.getJSONArray("top10indo");
                    // looping through All Contacts
                    for (int ind = 0; ind < topindo.length(); ind++) {
                        JSONObject ti = topindo.getJSONObject(ind);
                        String stopindo_idchord = ti.getString("id");
                        String stopindo_judul = ti.getString("judul");
                        String stopindo_penyanyi = ti.getString("nama_band");
                        String stopindo_idpenyanyi = ti.getString("id_band");
                        topindo_idChord.add(stopindo_idchord);
                        topindo_judul.add(stopindo_judul);
                        topindo_penyanyi.add(stopindo_penyanyi);
                        chartIndo++;
                        topindo_idPenyanyi.add(String.valueOf(chartIndo));
                    }
                    //-------------------------------
                    // TOP DUNIA -----------
                    JSONArray topdunia = jsonObj.getJSONArray("top10dunia");
                    // looping through All Contacts
                    for (int du = 0; du < topdunia.length(); du++) {
                        JSONObject dun = topdunia.getJSONObject(du);
                        String stopdunia_idchord = dun.getString("id");
                        String stopdunia_judul = dun.getString("judul");
                        String stopdunia_penyanyi = dun.getString("nama_band");
                        String stopdunia_idpenyanyi = dun.getString("id_band");
                        topdunia_idChord.add(stopdunia_idchord);
                        topdunia_judul.add(stopdunia_judul);
                        topdunia_penyanyi.add(stopdunia_penyanyi);
                        chartDunia++;
                        topdunia_idPenyanyi.add(String.valueOf(chartDunia));
                    }
                    String[] sAbjad={"0-9","A","B","C","D","E","F","G","H","I","J","K","L","M","N",
                    "O","P","Q","R","S","T","U","V","W","X","Y","Z"};
                    Integer nomor=1;
                    for (int ss=0;ss<sAbjad.length;ss++){
                        abjad_judul.add(sAbjad[ss]);
                        abjad_id.add(String.valueOf(nomor++));
                    }
                    //-------------------------------
                } catch (final JSONException e) {
                    Log.e("JSON", "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                            System.out.println("pesannya "+ e.getMessage());
                        }
                    });

                }
            } else {
                Log.e("Mainactvty", "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            //This is where we update the UI with the acquired data
            // Set description into TextView
            //Log.d(TAG, "onPostExecute: "+result);
            //--------------LAST -
            //----------------------
            LastAdapter mDataAdapter = new LastAdapter( MainActivity.this, last_penyanyi,
                    last_judul, last_photo, last_idPenyanyi,last_idChord);
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(MainActivity.this,
                    2, LinearLayoutManager.HORIZONTAL, false);
            //attachToRecyclerView
            GridPagerSnapHelper gridPagerSnapHelper = new GridPagerSnapHelper();
            gridPagerSnapHelper.setRow(1).setColumn(2);
            gridPagerSnapHelper.attachToRecyclerView(rc_last);
            rc_last.setLayoutManager(mLayoutManager);
            rc_last.setAdapter(mDataAdapter);
            //------------------------
            //-----top indo -----------
            //----------------------
            TopIndoAdapter indoAdapter = new TopIndoAdapter( MainActivity.this, topindo_penyanyi,
                    topindo_judul, topindo_photo, topindo_idPenyanyi,topindo_idChord);
            RecyclerView.LayoutManager indoLayout = new GridLayoutManager(MainActivity.this,
                    1, LinearLayoutManager.VERTICAL, false);
            //attachToRecyclerView
            rc_topIndo.setLayoutManager(indoLayout);
            rc_topIndo.setAdapter(indoAdapter);
            //--------------------
            //-----top dunia -----------
            //----------------------
            TopDuniaAdapter duniaAdapter = new TopDuniaAdapter( MainActivity.this, topindo_penyanyi,
                    topindo_judul, topindo_photo, topindo_idPenyanyi,topindo_idChord);
            RecyclerView.LayoutManager duniaLayout = new GridLayoutManager(MainActivity.this,
                    1, LinearLayoutManager.VERTICAL, false);
            //attachToRecyclerView
            rcTopDunia.setLayoutManager(duniaLayout);
            rcTopDunia.setAdapter(duniaAdapter);
            //--------------------
            //--------ABJAD-------------
            AbjadAdapter abjadAdapter = new AbjadAdapter( MainActivity.this,
                    abjad_judul,abjad_id);
            RecyclerView.LayoutManager abjadLayout = new GridLayoutManager(MainActivity.this,
                    4, LinearLayoutManager.VERTICAL, false);
            //attachToRecyclerView
            rcAbjad.setLayoutManager(abjadLayout);
            rcAbjad.setAdapter(abjadAdapter);
        }

    }
}
