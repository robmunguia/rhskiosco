package com.rh_synergy.kiosco;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rh_synergy.kiosco.Models.Nomina;
import com.rh_synergy.kiosco.Models.Prenomina;

import java.util.List;

/**
 * Created by PC-108 on 3/30/2016.
 */
public class TabFragment extends Fragment {
    String valor;

    public static final String posision = "item_id";
    public Prenomina prenomina;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        valor = getArguments().getString(posision);
        prenomina = (Prenomina)getArguments().getSerializable("PreNomina");
        View rootView = inflater.inflate(R.layout.tabs, container, false);

        RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.rvPreNomina);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);
        CardPrenAdapter adapter;
        if(valor == "0"){
            adapter = new CardPrenAdapter(prenomina.PrePrenomina);
        }
        else {
            adapter = new CardPrenAdapter(prenomina.PreAnterior);
        }
        rv.setAdapter(adapter);
        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();  // Always call the superclass method first
        // Release the Camera because we don't need it when paused
        valor = getArguments().getString(posision);
    }
}
