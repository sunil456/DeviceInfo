package com.sunil.deviceinfo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class SensorsActivity extends AppCompatActivity {


    //listview
    ListView mListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensors);

        //Actionbar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Sensors");

            // set back button in actionbar
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        //ListView
        mListView = findViewById(R.id.sensor_lv);

        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);

        //set Adapter to listview
        mListView.setAdapter(new MySensorAdapter(this , R.layout.sensor_row , sensors));

        //Total numbers of available sensors in the device(i.e List Size)
        String total = mListView.getCount() + "";
        TextView totalSensors = findViewById(R.id.countSens);
        totalSensors.setText("Available Sensors: " + total);

        Toast.makeText(this , total + " Sensors Detected..." , Toast.LENGTH_LONG).show();
    }

    //creating custom adapter class for our listView
    public static class MySensorAdapter extends ArrayAdapter<Sensor>
    {
        private int textViewResourceId;


        private static class ViewHolder
        {
            TextView itemView;
        }

        //constructor
        MySensorAdapter(Context context, int textViewResourceId , List<Sensor> items)
        {
            super(context , textViewResourceId , items);
            this.textViewResourceId = textViewResourceId;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            ViewHolder viewHolder = null;

            if (convertView == null)
            {
                convertView = LayoutInflater.from(this.getContext())
                        .inflate(textViewResourceId , parent , false);

                viewHolder = new ViewHolder();
                viewHolder.itemView = convertView.findViewById(R.id.content);
                convertView.setTag(viewHolder);
            }
            else
            {
                viewHolder = (ViewHolder)convertView.getTag();
            }

            Sensor items = getItem(position);

            //Get All Sensors list
            if (items != null)
            {
                viewHolder.itemView.setText("Name: " + items.getName()
                + "\nInt Type: " + items.getType()
                + "\nString Type: " + sensorTypeToString(items.getType())
                + "\nVendor: " + items.getVendor()
                + "\nVersion: " + items.getVersion()
                + "\nResolution: " + items.getResolution()
                + "\nPower: " + items.getPower() + "mAh"
                + "\nMaximum Range: " + items.getMaximumRange());
            }

            return convertView;
        }
    }

    public static String sensorTypeToString(int sensorType)
    {
        switch (sensorType)
        {
            case Sensor.TYPE_ACCELEROMETER:
                return "Accelerometer";

            case Sensor.TYPE_TEMPERATURE:
                return "Temperature";

            case Sensor.TYPE_AMBIENT_TEMPERATURE:
                return "Ambient Temperature";

            case Sensor.TYPE_LINEAR_ACCELERATION:
                return "Linear Acceleration";

            case Sensor.TYPE_GAME_ROTATION_VECTOR:
                return "Game Rotation Vector";

            case Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR:
                return "Geomagnetic Rotation Vector";

            case Sensor.TYPE_GRAVITY:
                return "Gravity";

            case Sensor.TYPE_GYROSCOPE:
                return "Gyroscope";

            case Sensor.TYPE_GYROSCOPE_UNCALIBRATED:
                return "Gyroscope Uncalibrated";

            case Sensor.TYPE_HEART_RATE:
                return "Heart Monitor";

            case Sensor.TYPE_LIGHT:
                return "Light";

            case Sensor.TYPE_MAGNETIC_FIELD:
                return "Magnetic Field";

            case Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED:
                return "Magnetic Field Uncalibrated";

            case Sensor.TYPE_ORIENTATION:
                return "Orientation";

            case Sensor.TYPE_PRESSURE:
                return "Pressure";

            case Sensor.TYPE_PROXIMITY:
                return "Proximity";

            case Sensor.TYPE_RELATIVE_HUMIDITY:
                return "Relative Humidity";

            case Sensor.TYPE_ROTATION_VECTOR:
                return "Rotation Vector";

            case Sensor.TYPE_SIGNIFICANT_MOTION:
                return "Signification Motion";

            case Sensor.TYPE_STEP_COUNTER:
                return "Step Counter";

            case Sensor.TYPE_STEP_DETECTOR:
                return "Step Director";

            default:
                return "Unknown";

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
