package com.genesis.sahx.fmogithub;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

public class AboutActivity extends AppCompatActivity
        implements AppBarLayout.OnOffsetChangedListener {

    private static final int PERCENTAGE_TO_ANIMATE_AVATAR = 20;
    private boolean mIsAvatarShown = true;

    private ImageView mProfileImage;
    private int mMaxScrollSize;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.materialup_tabs);
        ViewPager viewPager  = (ViewPager) findViewById(R.id.materialup_viewpager);
        AppBarLayout appbarLayout = (AppBarLayout) findViewById(R.id.materialup_appbar);
        mProfileImage = (ImageView) findViewById(R.id.materialup_profile_image);
        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.aboutFab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"));//only email apps should handle this
                intent.putExtra(intent.EXTRA_EMAIL, new String[] { "saro9600@hotmail.com" });
                intent.putExtra(intent.EXTRA_SUBJECT, "RE: REACH OUT TO OSARO IYOHA");

                if(intent.resolveActivity(getPackageManager())!=null){
                    startActivity(intent);
                }
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.materialup_toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                onBackPressed();
            }
        });

        appbarLayout.addOnOffsetChangedListener(this);
        mMaxScrollSize = appbarLayout.getTotalScrollRange();

        viewPager.setAdapter(new TabsAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
    }

    public static void start(Context c) {
        c.startActivity(new Intent(c, AboutActivity.class));
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
        if (mMaxScrollSize == 0)
            mMaxScrollSize = appBarLayout.getTotalScrollRange();

        int percentage = (Math.abs(i)) * 100 / mMaxScrollSize;

        if (percentage >= PERCENTAGE_TO_ANIMATE_AVATAR && mIsAvatarShown) {
            mIsAvatarShown = false;

            mProfileImage.animate()
                    .scaleY(0).scaleX(0)
                    .setDuration(200)
                    .start();
        }

        if (percentage <= PERCENTAGE_TO_ANIMATE_AVATAR && !mIsAvatarShown) {
            mIsAvatarShown = true;

            mProfileImage.animate()
                    .scaleY(1).scaleX(1)
                    .start();
        }
    }

    private static class TabsAdapter extends FragmentPagerAdapter {
        private static final int TAB_COUNT = 1;

        TabsAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return TAB_COUNT;
        }

        @Override
        public Fragment getItem(int i) {
            return AboutPageFragment.newInstance();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            //return "Tab " + String.valueOf(position);
            return "FMO-Github";
        }
    }
}
