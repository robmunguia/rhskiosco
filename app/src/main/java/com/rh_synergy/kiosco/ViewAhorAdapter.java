package com.rh_synergy.kiosco;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.rh_synergy.kiosco.Models.Ahorro;

/**
 * Created by PC-108 on 4/12/2016.
 */
public class ViewAhorAdapter extends FragmentStatePagerAdapter {
    private Ahorro ahorro;

    public ViewAhorAdapter(FragmentManager fm, Ahorro ahorro) {
        super(fm);
        this.ahorro = ahorro;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle arguments = new Bundle();
        arguments.putString(TabAhorFragment.posision, Integer.toString(position));
        arguments.putSerializable("Ahorro", ahorro);

        TabAhorFragment fragment = new TabAhorFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;// Solo son 2 Tabs
    }
}
