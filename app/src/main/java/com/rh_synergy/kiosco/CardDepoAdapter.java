package com.rh_synergy.kiosco;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rh_synergy.kiosco.Global.Shared;
import com.rh_synergy.kiosco.Models.Depositos;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by PC-108 on 4/13/2016.
 */
public class CardDepoAdapter extends RecyclerView.Adapter<CardDepoAdapter.DepoViewHolder> {

    public static class DepoViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView txtFecha;
        TextView txtSaldo;
        TextView txtPeriodo;

        DepoViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cvRetiros);
            txtFecha = (TextView)itemView.findViewById(R.id.txtDepoFecha);
            txtSaldo = (TextView)itemView.findViewById(R.id.txtDepoAhorro);
            txtPeriodo = (TextView)itemView.findViewById(R.id.txtDepoPeriodo);
        }
    }

    List<Depositos> lstDepositos;

    CardDepoAdapter(List<Depositos> lstDepositos){
        this.lstDepositos = lstDepositos;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public DepoViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.retiro_card, viewGroup, false);
        DepoViewHolder pvh = new DepoViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(DepoViewHolder RetiViewHolder, final int i) {
        try {
            //Config of date in format "dd/MM-YYY"
            String Fecha = lstDepositos.get(i).DepoFecha;
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = (Date)formatter.parse(Fecha);
            String Periodo = "Periodo: " + lstDepositos.get(i).DepoPeriodo;
            float cargo = lstDepositos.get(i).DepoDeposito;

            RetiViewHolder.txtFecha.setText(Shared.getDateFormatSpanish(date));
            RetiViewHolder.txtPeriodo.setText(Periodo);
            RetiViewHolder.txtSaldo.setText(Shared.getMoneyFormat(cargo));
        }
        catch (Exception ex){

        }
    }
    @Override
    public int getItemCount() {
        return lstDepositos.size();
    }
}

