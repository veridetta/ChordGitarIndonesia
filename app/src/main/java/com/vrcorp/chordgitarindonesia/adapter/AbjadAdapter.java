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

public class AbjadAdapter extends RecyclerView.Adapter<AbjadAdapter.MyViewHolder> {
    private ArrayList<String> judulList = new ArrayList<>();
    private ArrayList<String> idChord = new ArrayList<>();
    private Context context;


    public AbjadAdapter(Context context,
                        ArrayList<String> judulList,
                        ArrayList<String> idChord) {
        this.context = context;
        this.judulList = judulList;
        this.idChord = idChord;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView abjad;
        CardView cardBaru;
        public MyViewHolder(View view) {
            super(view);
            abjad=view.findViewById(R.id.abjad);
            cardBaru = view.findViewById(R.id.card_home);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.abjad_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.abjad.setText(judulList.get(position));
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
