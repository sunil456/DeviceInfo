package com.sunil.deviceinfo;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.NumberFormat;

public class DisplayActivity extends AppCompatActivity {


    private String titles[];
    private String descriptions[];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        //Actionbar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Device ID");

            // set back button in actionbar
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);

        }

        // Screensize in pixels , Width X Height
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
        String resolution = width + "x" + height + " pixel";

        //Physical size in inch
        double x = Math.pow(width/displayMetrics.xdpi ,2);
        double y = Math.pow(height/displayMetrics.ydpi ,2);
        double screenInches =Math.sqrt(x+y);

        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMaximumFractionDigits(2);
        numberFormat.setMaximumFractionDigits(2);
        String screenInchesOutput = numberFormat.format(screenInches);

        //Refresh Rate
        Display display = ((WindowManager)getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        float refreshRating = display.getRefreshRate();
        NumberFormat numberFormat1 = NumberFormat.getNumberInstance();
        numberFormat1.setMaximumFractionDigits(1);
        numberFormat1.setMaximumFractionDigits(1);
        String ouputRefreshRating = numberFormat1.format(refreshRating);

        titles = new String[]{"Resolution" , "Density" , "Physical Size","Refresh Rate" , "Orientation"};
        descriptions = new String[]{resolution , DisplayMetrics.DENSITY_XHIGH + " dpi (xhdpi)" ,
            screenInchesOutput + " inch " , ouputRefreshRating + " Hz " , this.getResources().getConfiguration().orientation + ""};

        ListView listView = findViewById(R.id.displayLists);
        myAdapter adapter = new myAdapter(this , titles , descriptions);
        listView.setAdapter(adapter);

    }

    // Creating custom adapter class
    private class myAdapter extends ArrayAdapter<String>
    {
        Context context;
        String myTitles[];
        String myDescription[];

        myAdapter(Context context , String myTitles[] , String myDescription[])
        {
            super(context , R.layout.tworow , R.id.title , myTitles);
            this.context = context;
            this.myTitles = myTitles;
            this.myDescription = myDescription;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
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
