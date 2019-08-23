package com.sunil.deviceinfo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;

//https://www.youtube.com/watch?v=o1unLuhatsc&list=PLs1bCj3TvmWn4G2odU5B2ihVsuG5l_Cpe&index=8
public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private MyAdapter myAdapter;

    private TextView mManufacturerTv , mAndroidVersionTv;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        Collapsing tool bar
        initCollapsingToolbar();

        //RecyclerView
        mRecyclerView = findViewById(R.id.myRecycler);

        //set its property
        mRecyclerView.setLayoutManager(new GridLayoutManager(this , 2));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        //Adapter
        myAdapter = new MyAdapter(this , getModels());
        mRecyclerView.setAdapter(myAdapter);

        //get device manufacturer name
        String mManufacturer = Build.MANUFACTURER;

        //getting device model
        String model = Build.MODEL;

        //getting device android Version
        String version = Build.VERSION.RELEASE;

        //getting android version name
        String verName = Build.VERSION_CODES.class.getFields()[Build.VERSION.SDK_INT].getName();


        mManufacturerTv = findViewById(R.id.name_model);
        mAndroidVersionTv = findViewById(R.id.android_version);

        mManufacturerTv.setText(mManufacturer.toUpperCase() + " " + model);
        mAndroidVersionTv.setText(version + " " + verName);

        //display android version logo/icon
        try
        {
            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.JELLY_BEAN)
            {
                Glide.with(this)
                        .load(R.drawable.android41)
                        .into((ImageView) findViewById(R.id.backdrop));
            }

            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.JELLY_BEAN_MR1)
            {
                Glide.with(this)
                        .load(R.drawable.android41)
                        .into((ImageView) findViewById(R.id.backdrop));
            }

            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.JELLY_BEAN_MR2)
            {
                Glide.with(this)
                        .load(R.drawable.android41)
                        .into((ImageView) findViewById(R.id.backdrop));
            }

            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT)
            {
                Glide.with(this)
                        .load(R.drawable.android44)
                        .into((ImageView) findViewById(R.id.backdrop));
            }

            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP)
            {
                Glide.with(this)
                        .load(R.drawable.android50)
                        .into((ImageView) findViewById(R.id.backdrop));
            }

            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP_MR1)
            {
                Glide.with(this)
                        .load(R.drawable.android50)
                        .into((ImageView) findViewById(R.id.backdrop));
            }

            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.M)
            {
                Glide.with(this)
                        .load(R.drawable.android60)
                        .into((ImageView) findViewById(R.id.backdrop));
            }

            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.N)
            {
                Glide.with(this)
                        .load(R.drawable.android70)
                        .into((ImageView) findViewById(R.id.backdrop));
            }

            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.N_MR1)
            {
                Glide.with(this)
                        .load(R.drawable.android70)
                        .into((ImageView) findViewById(R.id.backdrop));
            }

            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.O)
            {
                Glide.with(this)
                        .load(R.drawable.android80)
                        .into((ImageView) findViewById(R.id.backdrop));
            }
            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.O_MR1)
            {
                Glide.with(this)
                        .load(R.drawable.android80)
                        .into((ImageView) findViewById(R.id.backdrop));
            }

            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.P)
            {
                Glide.with(this)
                        .load(R.drawable.android90)
                        .into((ImageView) findViewById(R.id.backdrop));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    private void initCollapsingToolbar() {
        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle("");
        AppBarLayout appBarLayout = findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        //Set collapsing toolbar title
        collapsingToolbarLayout.setTitle("Bhagat Service");
    }

    //Add models to array list
    private ArrayList<Model> getModels()
    {
        ArrayList<Model> models = new ArrayList<>();

        Model model = new Model();

        model = new Model();

        model.setName("General");
        model.setImg(R.drawable.general);
        models.add(model);

        model = new Model();
        model.setName("Device Id");
        model.setImg(R.drawable.devid);
        models.add(model);

        model = new Model();
        model.setName("Display");
        model.setImg(R.drawable.display);
        models.add(model);

        model = new Model();
        model.setName("Battery");
        model.setImg(R.drawable.battery);
        models.add(model);

        model = new Model();
        model.setName("User Apps");
        model.setImg(R.drawable.userapps);
        models.add(model);

        model = new Model();
        model.setName("System Apps");
        model.setImg(R.drawable.systemapps);
        models.add(model);

        model = new Model();
        model.setName("Memory");
        model.setImg(R.drawable.memory);
        models.add(model);

        model = new Model();
        model.setName("CPU");
        model.setImg(R.drawable.cpu);
        models.add(model);

        model = new Model();
        model.setName("Sensors");
        model.setImg(R.drawable.sensor);
        models.add(model);

        model = new Model();
        model.setName("SIM");
        model.setImg(R.drawable.sim);
        models.add(model);

        return models;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu , menu);

        MenuItem item = menu.findItem(R.id.action_search);

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //this function is called when search button in keyboard is pressed
                myAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // this function calls whenever you type in searchView
                myAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        int id = item.getItemId();

        //handle other menu item click here
        if (id == R.id.action_settings)
        {
            Toast.makeText(MainActivity.this , "Settings" , Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
