package com.vrcorp.chordgitarindonesia.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.vrcorp.chordgitarindonesia.R;
import com.vrcorp.chordgitarindonesia.db.DBModel;

import java.util.ArrayList;
import java.util.List;

public class KategoriAdapter extends RecyclerView.Adapter<KategoriAdapter.MyViewHolder> {
    private ArrayList<String> penyanyiList = new ArrayList<>();
    private ArrayList<String> idPenyanyiList = new ArrayList<>();
    private Context context;
    CustomFilter filter;
    List<DBModel> LaguList;

    public KategoriAdapter(Context context, List<DBModel> LaguList) {
        this.context = context;
        this.LaguList = LaguList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtPenyanyi;
        CardView cardBaru;
        public MyViewHolder(View view) {
            super(view);
            txtPenyanyi =view.findViewById(R.id.penyanyi);
            cardBaru = view.findViewById(R.id.card_home);
            //bgPhoto= view.findViewById(R.id.img_home);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.kategori_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        //holder.txtKategori.setText("Terakhir Dimainkan");
        final DBModel lagu = LaguList.get(position);
        holder.txtPenyanyi.setText(lagu.getNama_band());
        holder.cardBaru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(context, DetailActivity.class);
                //intent.putExtra("url",urlList.get(position));
                //context.startActivity(intent);
                Toast.makeText(context, lagu.getId_band(), Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return LaguList.size();
    }
    public Filter getFilter() {
        if(filter==null)
        {
            filter=new CustomFilter((ArrayList<DBModel>) LaguList,this);
        }

        return filter;
    }
}
