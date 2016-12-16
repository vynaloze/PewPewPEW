package com.vynaloze.pewpewpew.options;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.vynaloze.pewpewpew.R;
import com.vynaloze.pewpewpew.logic.Protagonist;

import java.util.ArrayList;


public class OptionsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        initialiseRecyclerView();
        initialiseViewPager();
    }

    private void initialiseRecyclerView() {
        // initialise data
        ArrayList<Protagonist> protagonistList = new ArrayList<>();
        protagonistList.add(new Protagonist(R.drawable.ship1, getResources().getString(R.string.ship1_name), getResources().getString(R.string.ship1_desc)));
        protagonistList.add(new Protagonist(R.drawable.ship2, getResources().getString(R.string.ship2_name), getResources().getString(R.string.ship2_desc)));
        protagonistList.add(new Protagonist(R.drawable.ship3, getResources().getString(R.string.ship3_name), getResources().getString(R.string.ship3_desc)));

        // display chosen spaceship
        TextView textView = (TextView) findViewById(R.id.text_choice_pl);
        ImageView imageView = (ImageView) findViewById(R.id.imageview_choice_pl);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String desc = preferences.getString("SHIP_DESC", "Some error occured");
        int imageID = preferences.getInt("SHIP_IMAGE_ID", R.drawable.ship1);

        textView.setText(desc);
        imageView.setImageResource(imageID);

        // initialise RecyclerView itself
        RecyclerView rv = (RecyclerView) findViewById(R.id.rv);
        rv.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        rv.setLayoutManager(llm);

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, protagonistList, imageView, textView);
        rv.setAdapter(adapter);
    }

    private void initialiseViewPager() {
        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        // Set up the ViewPager with the sections adapter.
        final ViewPager mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
    }

}