package com.vrcorp.chordgitarindonesia.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.vrcorp.chordgitarindonesia.R;

import java.util.ArrayList;

public class KategoriAdapter extends RecyclerView.Adapter<KategoriAdapter.MyViewHolder> {
    private ArrayList<String> penyanyiList = new ArrayList<>();
    private ArrayList<String> idPenyanyiList = new ArrayList<>();
    private Context context;


    public KategoriAdapter(Context context, ArrayList<String> penyanyiList,
                           ArrayList<String> idPenyanyiList) {
        this.context = context;
        this.penyanyiList = penyanyiList;
        this.idPenyanyiList = idPenyanyiList;
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
        holder.txtPenyanyi.setText(penyanyiList.get(position));
        holder.cardBaru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(context, DetailActivity.class);
                //intent.putExtra("url",urlList.get(position));
                //context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return penyanyiList.size();
    }
}
