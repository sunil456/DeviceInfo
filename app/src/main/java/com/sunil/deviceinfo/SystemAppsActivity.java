package com.sunil.deviceinfo;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SystemAppsActivity extends AppCompatActivity {

    private List<AppList> installedApps;
    private AppAdapter installedAppAdapter;

    //ListView
    ListView systemAppListView;

    List<PackageInfo> packs;
    List<AppList> apps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_apps);

        //Actionbar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("System App");

            // set back button in actionbar
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        //ListView
        systemAppListView = findViewById(R.id.system_app_list);

        //call method to get installed apps
        installedApps = getInstalledApps();

        //Adapter
        installedAppAdapter = new AppAdapter(SystemAppsActivity.this , installedApps);

        //set Adapter
        systemAppListView.setAdapter(installedAppAdapter);

        //list item click listener
        systemAppListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {

                /**
                 * we are going to display alert dialog on item click
                 * with three options
                 * 1) Open App
                 * 2) App Info
                 * 3) Uninstall
                 * */

                // options to display in alert dialog
                String[] options = {"Open App" , "App Info"};

                //Alert dialog builder
                AlertDialog.Builder builder = new AlertDialog.Builder(SystemAppsActivity.this);

                //set title
                builder.setTitle("Choose Action");

                //set options
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {

                        if (which == 0)
                        {
                            //it means "Open App" is clicked
                            Intent intent = getPackageManager()
                                    .getLaunchIntentForPackage(installedApps.get(i).packages);

                            if (intent != null)
                            {
                                startActivity(intent);
                            }
                            else
                            {
                                //if anything goes wrong display error message
                                Toast.makeText(SystemAppsActivity.this ,
                                        "Error , Please try again latter." ,
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                        if (which == 1)
                        {
                            //it means "App Info" is clicked
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            intent.setData(Uri.parse("package:" + installedApps.get(i).packages));

                            //show package name in toast(optional)
                            Toast.makeText(SystemAppsActivity.this ,
                                    installedApps.get(i).packages ,
                                    Toast.LENGTH_LONG).show();

                            startActivity(intent);
                        }
                    }
                });

                //show Dialog
                builder.show();
            }
        });

        //getting total number of installed apps(i.e List Size)
        String size = systemAppListView.getCount() + "";

        //show in textView above our listview
        TextView countApps = findViewById(R.id.countApps);
        countApps.setText("Total System Apps: " +  size);
    }

    public class AppList
    {
        String name;
        Drawable icon;
        String packages;
        String version;

        public AppList(String name, Drawable icon, String packages, String version) {
            this.name = name;
            this.icon = icon;
            this.packages = packages;
            this.version = version;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Drawable getIcon() {
            return icon;
        }

        public void setIcon(Drawable icon) {
            this.icon = icon;
        }

        public String getPackages() {
            return packages;
        }

        public void setPackages(String packages) {
            this.packages = packages;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }
    }

    //creating AppAdapter class
    public class AppAdapter extends BaseAdapter {
        LayoutInflater layoutInflater;
        List<AppList> appLists;

        public AppAdapter(Context context, List<AppList> appLists) {
            //layout inflater
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.appLists = appLists;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
               ViewHolder listViewHolder;
            if (convertView == null)
            {
                listViewHolder = new ViewHolder();
                convertView = layoutInflater.inflate(R.layout.modelapps  , parent, false);

                listViewHolder.textInListView = convertView.findViewById(R.id.list_app_name);
                listViewHolder.imageInListView = convertView.findViewById(R.id.app_icon);
                listViewHolder.packageInListView = convertView.findViewById(R.id.app_package);
                listViewHolder.versionInListView = convertView.findViewById(R.id.version);

                convertView.setTag(listViewHolder);
            }
            else
            {
                listViewHolder = (ViewHolder) convertView.getTag();
            }
            //set data to our views
            listViewHolder.textInListView.setText(appLists.get(position).getName());
            listViewHolder.packageInListView.setText(appLists.get(position).getPackages());
            listViewHolder.versionInListView.setText(appLists.get(position).getVersion());
            listViewHolder.imageInListView.setImageDrawable(appLists.get(position).getIcon());


            return convertView;
        }

        @Override
        public int getCount() {
            return appLists.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        class ViewHolder
        {
            //our Views from modelsAdapter
            TextView textInListView;
            ImageView imageInListView;
            TextView packageInListView;
            TextView versionInListView;
        }
    }

    //get app information
    private List<AppList> getInstalledApps()
    {
        apps = new ArrayList<>();
        packs = getPackageManager().getInstalledPackages(0);

        for (int i = 0 ; i < packs.size() ; i++)
        {
            PackageInfo packageInfo = packs.get(i);

            //validate if app is not system app
            if ((isSystemPackage(packageInfo)))
            {
                // get Application Name
                String appName = packageInfo.applicationInfo.loadLabel(getPackageManager()).toString();

                //get Application Icon
                Drawable icon = packageInfo.applicationInfo.loadIcon(getPackageManager());

                //get Application Package Name
                String packages = packageInfo.applicationInfo.packageName;

                //get Application Version
                String version = packageInfo.versionName;

                //add data
                apps.add(new AppList(appName , icon , packages , version));
            }
        }

        return apps;
    }

    // function to check if app is not system app
    private boolean isSystemPackage(PackageInfo packageInfo)
    {
        return (packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0;
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
