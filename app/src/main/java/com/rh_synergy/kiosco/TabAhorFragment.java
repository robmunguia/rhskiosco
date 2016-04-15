package com.rh_synergy.kiosco;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rh_synergy.kiosco.Models.Ahorro;
import com.rh_synergy.kiosco.Models.Prenomina;

/**
 * Created by PC-108 on 4/13/2016.
 */
public class TabAhorFragment extends Fragment {
    String valor;

    public static final String posision = "item_id";
    public Ahorro ahorro;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        valor = getArguments().getString(posision);
        ahorro = (Ahorro) getArguments().getSerializable("Ahorro");
        View rootView = inflater.inflate(R.layout.tabs, container, false);

        RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.rvPreNomina);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);

        if (valor == "0") {
            CardAhorAdapter adapter = new CardAhorAdapter(ahorro.AhorRetiros);
            rv.setAdapter(adapter);
        } else {
            CardDepoAdapter adapter = new CardDepoAdapter(ahorro.AhorDepositos);
            rv.setAdapter(adapter);
        }

        return rootView;
    }
}
