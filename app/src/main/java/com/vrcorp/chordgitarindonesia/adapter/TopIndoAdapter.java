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

public class TopIndoAdapter extends RecyclerView.Adapter<TopIndoAdapter.MyViewHolder> {
    private ArrayList<String> penyanyiList = new ArrayList<>();
    private ArrayList<String> judulList = new ArrayList<>();
    private ArrayList<String> bgList = new ArrayList<>();
    private ArrayList<String> idPenyanyiList = new ArrayList<>();
    private ArrayList<String> idChord = new ArrayList<>();
    private Context context;


    public TopIndoAdapter(Context context, ArrayList<String> penyanyiList,
                          ArrayList<String> judulList,
                          ArrayList<String> bgList,
                          ArrayList<String> idPenyanyiList, ArrayList<String> idChord) {
        this.context = context;
        this.penyanyiList = penyanyiList;
        this.judulList = judulList;
        this.bgList = bgList;
        this.idPenyanyiList = idPenyanyiList;
        this.idChord = idChord;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtKategori, txtJudul,txtPenyanyi, txtChart;
        ImageView bgPhoto;
        CardView cardBaru;
        public MyViewHolder(View view) {
            super(view);
            //this.bg = view.findViewById(R.id.bg_img);
            //txtKategori = view.findViewById(R.id.ketgori_home);
            txtChart = view.findViewById(R.id.chart);
            txtJudul = view.findViewById(R.id.judul_lagu);
            txtPenyanyi =view.findViewById(R.id.penyanyi);
            cardBaru = view.findViewById(R.id.card_home);
            //bgPhoto= view.findViewById(R.id.img_home);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.top_indo_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        //holder.txtKategori.setText("Terakhir Dimainkan");
        holder.txtChart.setText(idPenyanyiList.get(position));
        holder.txtJudul.setText(judulList.get(position));
        holder.txtPenyanyi.setText(penyanyiList.get(position));
        /*
        Glide.with(holder.photoResep.getContext())
                .load(Uri.parse(photoList.get(position)))
                .apply(RequestOptions.centerCropTransform())
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        holder.photoResep.setImageDrawable(resource);
                    }
                });*/
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
        return judulList.size();
    }
}
