package com.sunil.deviceinfo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telecom.TelecomManager;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class DeviceIDActivity extends AppCompatActivity {

    //Phone state permission
    private static final int READ_PHONE_STATE_PERMISSION = 1;
    private String titles[];
    private String descriptions[];

    private TelephonyManager telecomManager;
    private String imei , simCardSerial , simSubscriberID;

    @SuppressLint("ServiceCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_id);

        //Actionbar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Device ID");

            // set back button in actionbar
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);

        }
        //Android Device ID
        String deviceID = Settings.Secure.getString(this.getContentResolver() , Settings.Secure.ANDROID_ID);
        //IMEI number
        telecomManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) ==
                    PackageManager.PERMISSION_DENIED)
            {
                String[] permissions = { Manifest.permission.READ_PHONE_STATE };

                requestPermissions(permissions , READ_PHONE_STATE_PERMISSION);
            }
            else
            {
                //permission was granted
                imei = telecomManager.getDeviceId();
                simCardSerial = telecomManager.getSimSerialNumber();
                simSubscriberID = telecomManager.getSubscriberId();
            }
        }
        else
        {
            // System OS is < marshmallow , no need runtime permission
            imei = telecomManager.getDeviceId();
            simCardSerial = telecomManager.getSimSerialNumber();
            simSubscriberID = telecomManager.getSubscriberId();
        }
        //IP Address
        String ipAddress = "No Internet Connection";
        ConnectivityManager connectivityManager = (ConnectivityManager)this.getSystemService(CONNECTIVITY_SERVICE);
        boolean is3GEnabled = false;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            Network[] networks = connectivityManager.getAllNetworks();

            for (Network network : networks)
            {
                NetworkInfo info = connectivityManager.getNetworkInfo(network);
                if (info != null)
                {
                    if (info.getType() == ConnectivityManager.TYPE_MOBILE)
                    {
                        ipAddress = getMobileIPAddress();
                    }
                }
            }
        }
        else
        {
            NetworkInfo mMobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (mMobile != null)
            {
                ipAddress = is3GEnabled + "";
            }
        }

        //WiFi Mac Address
        String wifiAddress = "No WiFi Connection";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            Network[] networks = connectivityManager.getAllNetworks();

            for (Network network : networks)
            {
                NetworkInfo info = connectivityManager.getNetworkInfo(network);
                if (info != null)
                {
                    if (info.getType() == ConnectivityManager.TYPE_WIFI)
                    {
                        wifiAddress = getWifiIPAddress();
                    }
                }
            }
        }
        else
        {
            NetworkInfo mMobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (mMobile != null)
            {
                ipAddress = is3GEnabled + "";
            }
        }

        //Bluetooth Mac Address
        String bt = android.provider.Settings.Secure.getString(this.getContentResolver() , "bluetooth_address");

        // Array Container
        titles = new String[]{"Android Device ID" , "IMEI , MEID or ESN" , "Hardware Serial Number" ,
                "SIM Card Serial No." , "SIM Subscriber ID" , "IP Address" ,
                "Wi-Fi Mac Address" , "Bluetooth Mac Address" , "Build Fingerprint"};
        descriptions = new String[]{deviceID , imei , Build.SERIAL , simCardSerial ,
                simSubscriberID , ipAddress , wifiAddress , bt , Build.FINGERPRINT};

        ListView listView = findViewById(R.id.devIdLists);

        //set Adapter
        myAdapter adapter = new myAdapter(this , titles , descriptions);
        listView.setAdapter(adapter);
    }

    private String getWifiIPAddress() {

        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ip = wifiInfo.getIpAddress();

        return Formatter.formatIpAddress(ip);
    }

    private static String getMobileIPAddress() {

        try
        {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface networkInterface : interfaces)
            {
                List<InetAddress> addresses = Collections.list(networkInterface.getInetAddresses());
                for (InetAddress address : addresses)
                {
                    if (!address.isLoopbackAddress())
                    {
                        return address.getHostAddress();
                    }
                }
            }
        }
        catch (Exception e)
        {

        }

        return "";
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode)
        {
            case READ_PHONE_STATE_PERMISSION:{
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    //permission granted
                    recreate(); // restart activity on permission granted
                    imei = telecomManager.getDeviceId();
                    simCardSerial = telecomManager.getSimSerialNumber();
                    simSubscriberID = telecomManager.getSubscriberId();
                }
                else
                {
                    //permission was rejected
                    Toast.makeText(this , "Enable READ_PHONE_STATE Permission from settings" , Toast.LENGTH_LONG).show();
                }
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
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
