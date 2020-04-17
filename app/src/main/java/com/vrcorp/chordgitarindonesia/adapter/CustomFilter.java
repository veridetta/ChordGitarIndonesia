package com.vrcorp.chordgitarindonesia.adapter;

import android.widget.Filter;

import com.vrcorp.chordgitarindonesia.KategoriActivity;
import com.vrcorp.chordgitarindonesia.db.DBModel;

import java.util.ArrayList;

public class CustomFilter extends Filter {

    KategoriAdapter adapter;
    ArrayList<DBModel> filterList;

    public CustomFilter(ArrayList<DBModel> filterList, KategoriAdapter adapter)
    {
        this.adapter=adapter;
        this.filterList=filterList;

    }

    //FILTERING OCURS
    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results=new FilterResults();

        //CHECK CONSTRAINT VALIDITY
        if(constraint != null && constraint.length() > 0)
        {
            //CHANGE TO UPPER
            constraint=constraint.toString().toUpperCase();
            //STORE OUR FILTERED PLAYERS
            ArrayList<DBModel> filteredPlayers=new ArrayList<>();

            for (int i=0;i<filterList.size();i++)
            {
                //CHECK
                if(filterList.get(i).getNama_band().toUpperCase().contains(constraint))
                {
                    //ADD PLAYER TO FILTERED PLAYERS
                    filteredPlayers.add(filterList.get(i));
                }
            }

            results.count=filteredPlayers.size();
            results.values=filteredPlayers;
        }else
        {
            results.count=filterList.size();
            results.values=filterList;

        }

        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {

        adapter.LaguList= (ArrayList<DBModel>) results.values;

        //REFRESH
        adapter.notifyDataSetChanged();
    }
}