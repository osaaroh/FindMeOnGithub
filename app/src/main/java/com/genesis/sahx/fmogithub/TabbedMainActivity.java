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
        Log.w("TabbedMainActivity","Will Connect using Username from text: "+ username);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "History Feature will be available in next update", Snackbar.LENGTH_LONG)
                        .setAction("Action ", null).show();
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

    /**Helper method to pass username input to into a Fragment
    public void createCustomFragment(){
        Log.w("TabbedMainActivity","Came from text: "+ username);
        SummaryFragment summaryFragment = new SummaryFragment();
        summaryFragment.newInstance(username);
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
