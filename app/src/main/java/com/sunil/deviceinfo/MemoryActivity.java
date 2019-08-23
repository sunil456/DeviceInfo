package com.sunil.deviceinfo;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.view.Menu;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;
import java.text.NumberFormat;

public class MemoryActivity extends AppCompatActivity {

    //declaring Views
    TextView mTvTotalRam , mTvFreeRam , mTvUsedRam;
    TextView mTvTotalRom , mTvFreeRom , mTvUsedRom;
    TextView mTvTotalHeap;
    TextView mTvPrecRam , mTvPercRom;
    ProgressBar mPBRam , mPBRom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory);

        //Actionbar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Memory");

            // set back button in actionbar
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        //initializing views
        //RAM
        mTvFreeRam = findViewById(R.id.freeRam);
        mTvTotalRam = findViewById(R.id.totalRam);
        mTvUsedRam = findViewById(R.id.usedRam);

        //ROM
        mTvTotalRom = findViewById(R.id.totalRom);
        mTvFreeRom = findViewById(R.id.freeRom);
        mTvUsedRom = findViewById(R.id.usedRom);

        //Heap
        mTvTotalHeap = findViewById(R.id.totalHeap);

        //ProgressBar of RAM & ROM
        mPBRam = findViewById(R.id.progressBarRam);
        mPBRom = findViewById(R.id.progressBarRom);
        mTvPrecRam = findViewById(R.id.tv_perc_ram);
        mTvPercRom = findViewById(R.id.tv_perc_rom);

        //RAM Getting Information
        ActivityManager activityManager = (ActivityManager)getSystemService(ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);

        //total ram
        float totalMem = memoryInfo.totalMem/(1024*1024);
        //free ram
        float freeMem = memoryInfo.availMem/(1024*1024);
        //used ram
        float usedMem = totalMem - freeMem;
        //Percentage of free Ram
        float freeMemPercentage = freeMem/totalMem*100;
        //Percentage of used Ram
        float usedMemPercentage = usedMem/totalMem*100;

        //Free RAM percentage decimal point conversion
        NumberFormat numberFormFreePerc = NumberFormat.getNumberInstance();
        numberFormFreePerc.setMinimumFractionDigits(1);
        numberFormFreePerc.setMaximumFractionDigits(1);
        String mFreeMemPerc = numberFormFreePerc.format(freeMemPercentage);

        //Used RAM Percentage decimal point conversion
        NumberFormat numberFormUedPerc = NumberFormat.getNumberInstance();
        numberFormUedPerc.setMinimumFractionDigits(1);
        numberFormUedPerc.setMaximumFractionDigits(1);
        String mUsedMemPerc = numberFormUedPerc.format(usedMemPercentage);

        //ROM: getting information
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        float blockSize = stat.getBlockSize();
        //for total ROM
        float totalBlocks = stat.getBlockCount();
        //free ROM
        float availableBlocks = stat.getAvailableBlocks();
        //value of total rom in mb
        float totalROM = (totalBlocks * blockSize)/(1024*1024);
        //value of free rom in mb
        float freeROM = (availableBlocks * blockSize)/(1024*1024);
        //value of used rom in mb
        float usedROM = totalROM - freeROM;

        //ROM percentage
        float freeRomPerc = (freeROM/totalROM)*100;
        float usedRomPerc = (usedROM/totalROM)*100;

        //Total ROM decimal point conversions
        NumberFormat numberFormatTotalRom = NumberFormat.getNumberInstance();
        numberFormatTotalRom.setMinimumFractionDigits(1);
        numberFormatTotalRom.setMaximumFractionDigits(1);
        String mTotalROM = numberFormatTotalRom.format(totalROM);

        //Free ROM decimal point conversion
        NumberFormat numberFormatFreeRom = NumberFormat.getNumberInstance();
        numberFormatFreeRom.setMinimumFractionDigits(1);
        numberFormatFreeRom.setMaximumFractionDigits(1);
        String mFreeROM = numberFormatFreeRom.format(freeROM);

        //USED ROM decimal point conversion
        NumberFormat numberFormatUsedRom = NumberFormat.getNumberInstance();
        numberFormatUsedRom.setMinimumFractionDigits(1);
        numberFormatUsedRom.setMaximumFractionDigits(1);
        String mUsedROM = numberFormatUsedRom.format(usedROM);

        //USED ROM Percentage point conversion
        NumberFormat numberFormatFreeRomPerc = NumberFormat.getNumberInstance();
        numberFormatFreeRomPerc.setMinimumFractionDigits(1);
        numberFormatFreeRomPerc.setMaximumFractionDigits(1);
        String mFreeRomPerc = numberFormatFreeRomPerc.format(freeRomPerc);

        //USED ROM Percentage point conversion
        NumberFormat numberFormatUsedRomPerc = NumberFormat.getNumberInstance();
        numberFormatUsedRomPerc.setMinimumFractionDigits(1);
        numberFormatUsedRomPerc.setMaximumFractionDigits(1);
        String mUsedRomPerc = numberFormatUsedRomPerc.format(usedRomPerc);

        //setting RAM info
        mTvTotalRam.setText(" " + totalMem + "MB");
        mTvFreeRam.setText(" " + freeMem + "MB" + "(" + mFreeMemPerc + "%)");
        mTvUsedRam.setText(" " + usedMem + "MB" + "(" + mUsedMemPerc + "%)");

        //setting ROM Info
        mTvTotalRom.setText(" " + mTotalROM + "MB");
        mTvFreeRom.setText(" " + mFreeROM + "MB" + "(" + mFreeRomPerc + "%)");
        mTvUsedRom.setText(" " + mUsedROM + "MB" + "(" + mUsedRomPerc + "%)");

        //getting Java Heap
        Runtime runtime = Runtime.getRuntime();
        long maxMemory = runtime.maxMemory();

        //setting Java Heap info
        mTvTotalHeap.setText(" " + maxMemory/(1024*1024) + "MB");

        //setting RAM info to ProgressBar and TextView on ProgressBar
        mTvPrecRam.setText(mUsedMemPerc + "% Used");
        mPBRam.setProgress((int)usedMemPercentage);

        //setting ROM information to ProgressBar and TextView on ProgressBar
        mTvPercRom.setText(mUsedRomPerc + "% Used");
        mPBRom.setProgress((int)((usedROM/totalROM) * 100));
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
