package com.sunil.deviceinfo;

import android.widget.Filter;

import java.util.ArrayList;

public class CustomFilter extends Filter {

    private MyAdapter adapter;
    private ArrayList<Model> filterList;

    public CustomFilter(ArrayList<Model> filterList , MyAdapter adapter) {
        this.adapter = adapter;
        this.filterList = filterList;
    }

    @Override
    protected FilterResults performFiltering(CharSequence charSequence) {

        FilterResults results = new FilterResults();

        //check charSequence validity
        if(charSequence != null && charSequence.length() > 0)
        {
            //change to upper case
            charSequence = charSequence.toString().toUpperCase();

            //store to our Filter MODEL
            ArrayList<Model> filteredModels = new ArrayList<>();

            for(int i = 0 ; i <filterList.size() ; i++)
            {
                //check
                if(filterList.get(i).getName().toUpperCase().contains(charSequence))
                {
                    //add model to filtered Models
                    filteredModels.add(filterList.get(i));
                }
            }

            results.count = filteredModels.size();
            results.values = filteredModels;
        }
        else
        {
            results.count = filterList.size();
            results.values = filterList;
        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence charSequence, FilterResults filterResults)
    {
        adapter.models = (ArrayList<Model>) filterResults.values;
        //refresh
        adapter.notifyDataSetChanged();
    }
}
