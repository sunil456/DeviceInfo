package com.sunil.deviceinfo;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import static android.os.SystemClock.uptimeMillis;
import static java.lang.System.getProperty;

public class GeneralActivity extends AppCompatActivity {

    private String titles[];
    private String descriptions[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general);

        //Actionbar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("General Info");

            // set back button in actionbar
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);

        }

        //Calculating device up time
        long miliSec = uptimeMillis();
        String upTime = String.format("%02d : %02d : %02d",
                TimeUnit.MILLISECONDS.toHours(miliSec),
                TimeUnit.MILLISECONDS.toMinutes(miliSec) -
                TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(miliSec)),
                TimeUnit.MILLISECONDS.toSeconds(miliSec) -
                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(miliSec)));

        // arrays of containing information
        titles = new String[]{"Model","Manufacturer","Device","Board","Hardware",
                "Brand","Android Version","OS Name","API Level",
                "Bootloader","Build Number","Radio Version",
                "Kernal" , "Android Runtime" , "Up Time"};

        descriptions = new String[]{Build.MODEL, Build.MANUFACTURER , Build.DEVICE , Build.BOARD , Build.HARDWARE,
            Build.BRAND , Build.VERSION.RELEASE , Build.VERSION_CODES.class.getFields()[Build.VERSION.SDK_INT].getName() ,
             Build.VERSION.SDK_INT+"" , Build.BOOTLOADER , Build.FINGERPRINT , Build.getRadioVersion() ,
            getProperty("os.arch") , "ART"+getProperty("java.vm.version" ), upTime};

        ListView listView = findViewById(R.id.generalList);

        //set adapter to our listview
        MyAdapter adapter = new MyAdapter(this , titles , descriptions);
        listView.setAdapter(adapter);

    }

    public class MyAdapter extends ArrayAdapter<String>
    {
        Context context;
        String myTitles[];
        String myDescription[];

        MyAdapter(Context context , String[] myTitles , String[] myDescription)
        {
            super(context , R.layout.tworow , R.id.title , myTitles);
            this.context = context;
            this.myTitles = myTitles;
            this.myDescription = myDescription;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            LayoutInflater layoutInflater =
                    (LayoutInflater)getApplicationContext().getSystemService(context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.tworow , parent , false);

            //views in tworow.xml
            TextView myTitle = row.findViewById(R.id.titleTv);
            TextView myDescription = row.findViewById(R.id.descTv);

            //set Text to views
            myTitle.setText(titles[position]);
            myDescription.setText(descriptions[position]);

            return row;
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        //when backbutton in actionbar is pressed go to previous activity
        onBackPressed();
        return true;
    }

    // hide searchView from this activity

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu , menu);
        menu.findItem(R.id.action_search).setVisible(false);
        return true;
    }
}
