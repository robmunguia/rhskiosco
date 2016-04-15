package com.rh_synergy.kiosco;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.rh_synergy.kiosco.Models.Prenomina;


/**
 * Created by PC-108 on 3/30/2016.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private Prenomina prenomina;

    public ViewPagerAdapter(FragmentManager fm, Prenomina prenom) {
        super(fm);
        this.prenomina = prenom;
    }

    @Override
    public Fragment getItem(int position) {
        /*var position {0 Checadas Actuales, 1 Checadas Anteriores}*/
        Bundle arguments = new Bundle();
        arguments.putString(TabFragment.posision, Integer.toString(position));
        arguments.putSerializable("PreNomina", prenomina);

        TabFragment fragment = new TabFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;// Solo son 2 Tabs
    }
}
