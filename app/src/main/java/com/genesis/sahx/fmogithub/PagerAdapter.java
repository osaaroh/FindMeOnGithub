package com.genesis.sahx.fmogithub;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.genesis.sahx.fmogithub.RepoData.ReposFragment;
import com.genesis.sahx.fmogithub.SummaryData.SummaryFragment;

/**
 * Created by SAHX on 12/23/2017.
 */

public class PagerAdapter extends FragmentPagerAdapter {

    public PagerAdapter(FragmentManager fm){
        super(fm);
    }



    @Override
    public Fragment getItem(int position) {
        if(position==0){
            return new SummaryFragment();
        }
        else{
            return new ReposFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(position==0){
            return "SUMMARY";
        }
        else{
            return "REPO-LIST";
        }
    }
}
