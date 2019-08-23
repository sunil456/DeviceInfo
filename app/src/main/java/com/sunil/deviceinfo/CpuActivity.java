package com.sunil.deviceinfo;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.InputStream;
import java.util.Collections;

public class CpuActivity extends AppCompatActivity {

    ProcessBuilder mProcessBuilder;
    String holder = "";
    String[] DATA = {"/system/bin/cat", "/proc/cpuinfo"};
    InputStream mInputStream;
    Process mProcess;
    byte[] mByteArray;
    ListView mListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cpu);

        //Actionbar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("CPU");

            // set back button in actionbar
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        //ListView
        mListView = findViewById(R.id.cpuListView);

        //getting information of CPU
        mByteArray = new byte[1024];

        try
        {
            mProcessBuilder = new ProcessBuilder(DATA);
            mProcess = mProcessBuilder.start();
            mInputStream = mProcess.getInputStream();
            while (mInputStream.read(mByteArray) != -1)
            {
                holder = holder + new String(mByteArray);
            }
            //close input stream
            mInputStream.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        //adapter
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(this ,
                        android.R.layout.simple_list_item_1 ,
                        android.R.id.text1,
                        Collections.singletonList(holder));

        //set adapter to listview
        mListView.setAdapter(adapter);
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
