package com.sunil.deviceinfo;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ProgressBar;
import android.widget.TextView;

public class BatteryActivity extends AppCompatActivity {

    //Views
    TextView textView1,textView2 , batteryPercentage , mTextViewPercentage;

    private double batteryCapacity;
    private ProgressBar mProgressBar;
    private int mProgressStatus = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battery);

        //Actionbar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Battery");

            // set back button in actionbar
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);

        }

        //Get application context
        android.content.Context context = getApplicationContext();

        //initialize a new IntentFilter
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);

        //Register the broadcast receiver
        context.registerReceiver(mBroadCastReceiver , intentFilter);

        //Get the widgets reference from xml
        batteryPercentage = findViewById(R.id.battery_percentage);
        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);

        mTextViewPercentage = findViewById(R.id.tv_percentage);
        mProgressBar = findViewById(R.id.progressBar);

        Object mPowerProfile = null;
        String POWER_PROFILE_CLASS = "com.android.internal.os.PowerProfile";
        try
        {
            mPowerProfile = Class.forName(POWER_PROFILE_CLASS)
                    .getConstructor(Context.class)
                    .newInstance(this);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        try
        {
            batteryCapacity = (Double) Class.forName(POWER_PROFILE_CLASS)
                    .getMethod("getAveragePower" , java.lang.String.class)
                    .invoke(mPowerProfile , "battery.capacity");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private BroadcastReceiver mBroadCastReceiver = new BroadcastReceiver() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onReceive(Context context, Intent intent) {
            String charging_status = "",battery_condition="",power_source = "Unplugged";

            //Get battery percentage
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL , 0);

            //Get battery Condition
            int health = intent.getIntExtra(BatteryManager.EXTRA_HEALTH , 0);

            if (health == BatteryManager.BATTERY_HEALTH_COLD)
            {
                battery_condition = "Cold";
            }
            if (health == BatteryManager.BATTERY_HEALTH_COLD)
            {
                battery_condition = "Cold";
            }
            if (health == BatteryManager.BATTERY_HEALTH_DEAD)
            {
                battery_condition = "Dead";
            }
            if (health == BatteryManager.BATTERY_HEALTH_OVERHEAT)
            {
                battery_condition = "Over Heat";
            }
            if (health == BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE)
            {
                battery_condition = "Over Voltage";
            }
            if (health == BatteryManager.BATTERY_HEALTH_UNKNOWN)
            {
                battery_condition = "Unknown";
            }
            if (health == BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE)
            {
                battery_condition = "Unspecified Failure";
            }

            //Get Battery Temperature in celcius
            int temperature_c = (intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE,0))/10;
            //Celcius to Fahrenheit battery temperature conversion
            int temperature_f = (int)(temperature_c * 1.8 + 32);

            //get the battery power source
            int chargePlug = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED , -1);

            if (chargePlug == BatteryManager.BATTERY_PLUGGED_USB)
                power_source = "USB";

            if (chargePlug == BatteryManager.BATTERY_PLUGGED_AC)
                power_source = "AC Adapter";

            if (chargePlug == BatteryManager.BATTERY_PLUGGED_WIRELESS)
                power_source = "Wireless";

            //get the status of the battery i.e Charging/Discharging
            int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS , -1);
            if (status == BatteryManager.BATTERY_STATUS_CHARGING)
                charging_status = "Charging";

            if (status == BatteryManager.BATTERY_STATUS_DISCHARGING)
                charging_status = "Discharging";

            if (status == BatteryManager.BATTERY_STATUS_FULL)
                charging_status = "Battery Full";

            if (status == BatteryManager.BATTERY_STATUS_UNKNOWN)
                charging_status = "Unknown";

            if (status == BatteryManager.BATTERY_STATUS_NOT_CHARGING)
                charging_status = "Not Charging";


            //get battery technology
            String technology = intent.getExtras().getString(BatteryManager.EXTRA_TECHNOLOGY);

            //get battery voltage
            int voltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE , 0);

            //Display the output of battery status
            batteryPercentage.setText("Battery Percentage: " + level + "%");
            textView1.setText("Condition:\n" +
                    "Temperature:\n" +
                    "Power Source:\n" +
                    "Charging Status:\n" +
                    "Type:\n" +
                    "Voltage:\n" +
                    "Capacity:");

            textView2.setText(battery_condition + "\n" +
                    "" + temperature_c + "" +
                    (char)0x00B0 + "C/" +
                    temperature_f + "" +
                    (char)0x00B0 + "F\n" +
                    "" + power_source + "\n" +
                    "" + charging_status + "\n" +
                    "" + technology + "\n" +
                    "" + voltage + "mV\n" +
                    "" + batteryCapacity + "mAh");

            int levels = intent.getIntExtra(BatteryManager.EXTRA_LEVEL , -1);
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE , -1);
            float percentage = levels/(float)scale;

            //Update the progress bar to display current battery charged percentage
            mProgressStatus = (int)((percentage) * 100);

            mTextViewPercentage.setText("" + mProgressStatus + "%");

            //display the battery charged percentage in progress bar
            mProgressBar.setProgress(mProgressStatus);

        }
    };

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
