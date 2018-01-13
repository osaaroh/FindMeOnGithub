package com.genesis.sahx.fmogithub;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.genesis.sahx.fmogithub.SummaryData.SummaryFragment;

public class TabbedMainActivity extends AppCompatActivity {

    public Bundle bundle;
    public String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed_main);
        bundle = getIntent().getExtras();
        username = bundle.getString("username");
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(new Intent(TabbedMainActivity.this, HistoryActivity.class));
            }
        });
        /**Connect Viewpager to Xml code*/
        ViewPager viewPager = (ViewPager)findViewById(R.id.viewPager);
        /**Create an instance of the pager adapter in order to move through the fragments easily*/
        PagerAdapter adapter =  new PagerAdapter(getSupportFragmentManager());
        /**Let our Viewpager use this adapter*/
        viewPager.setAdapter(adapter);
        //link up the tabbed layout in Xml and set it up to work with the viewpager
        TabLayout tabLayout = (TabLayout)findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }
}
