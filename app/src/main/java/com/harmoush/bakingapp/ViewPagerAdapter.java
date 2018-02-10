package com.harmoush.bakingapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.harmoush.bakingapp.Activities.StepFragmentActivity;

/**
 * Created by Harmoush on 2/10/2018.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public ViewPagerAdapter(FragmentManager fragmentManager, int mNumOfTabs) {
        super(fragmentManager);
        this.mNumOfTabs = mNumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        if(position>=0 && position < mNumOfTabs) {
            StepFragmentActivity stepFragment = new StepFragmentActivity() ;
            Bundle bundle = new Bundle();
            bundle.putInt("mPosition", position);
            stepFragment.setArguments(bundle);
            return stepFragment;
        }
        else{
            return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}