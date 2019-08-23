package com.sunil.deviceinfo;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyHolder> implements Filterable
{
    Context context;
    ArrayList<Model> models , filterList;
    CustomFilter filter;

    public MyAdapter(Context context, ArrayList<Model> models) {
        this.context = context;
        this.models = models;
        this.filterList = models;
    }
    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.model , null);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position)
    {
        holder.nameTxt.setText(models.get(position).getName());
        holder.img.setImageResource(models.get(position).getImg());

        //handle item clicks
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                //General
                if (models.get(position).getName().equals("General"))
                {
                    // start General Activity
                    Intent generalIntent = new Intent(context , GeneralActivity.class);
                    context.startActivity(generalIntent);

                }

               else if (models.get(position).getName().equals("Device Id"))
                {
                    Intent deviceIDIntent = new Intent(context , DeviceIDActivity.class);
                    context.startActivity(deviceIDIntent);
                }

               else if (models.get(position).getName().equals("Display"))
                {
                    Intent displayIntent = new Intent(context , DisplayActivity.class);
                    context.startActivity(displayIntent);
                }

               else if (models.get(position).getName().equals("Battery"))
                {
                    Intent batteryIntent = new Intent(context , BatteryActivity.class);
                    context.startActivity(batteryIntent);
                }

               else if (models.get(position).getName().equals("User Apps"))
                {
                    Intent userAppIntent = new Intent(context , UserAppsActivity.class);
                    context.startActivity(userAppIntent);
                }

               else if (models.get(position).getName().equals("System Apps"))
                {
                    Intent systemAppIntent = new Intent(context , SystemAppsActivity.class);
                    context.startActivity(systemAppIntent);
                }

               else if (models.get(position).getName().equals("Memory"))
                {
                    Intent memoryIntent = new Intent(context , MemoryActivity.class);
                    context.startActivity(memoryIntent);
                }

               else if (models.get(position).getName().equals("CPU"))
                {
                    Intent cpuIntent = new Intent(context , CpuActivity.class);
                    context.startActivity(cpuIntent);
                }

               else if (models.get(position).getName().equals("Sensors"))
                {
                    Intent sensorsIntent = new Intent(context , SensorsActivity.class);
                    context.startActivity(sensorsIntent);
                }

               else if (models.get(position).getName().equals("SIM"))
                {
                    Toast.makeText(context , "SIM" , Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return models.size();
    }

    //return filter obj
    @Override
    public Filter getFilter() {
        if (filter == null)
        {
            filter = new CustomFilter(filterList , this);
        }
        return filter;
    }
}
