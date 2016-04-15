package com.rh_synergy.kiosco;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rh_synergy.kiosco.Global.Shared;
import com.rh_synergy.kiosco.Models.Retiros;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by PC-108 on 4/12/2016.
 */
public class CardAhorAdapter extends RecyclerView.Adapter<CardAhorAdapter.AhorViewHolder> {

    public static class AhorViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView txtFecha;
        TextView txtSaldo;
        TextView txtObservacion;

        AhorViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cvRetiros);
            txtFecha = (TextView)itemView.findViewById(R.id.txtDepoFecha);
            txtSaldo = (TextView)itemView.findViewById(R.id.txtDepoAhorro);
            txtObservacion = (TextView)itemView.findViewById(R.id.txtDepoPeriodo);
        }
    }

    List<Retiros> lstRetiros;

    CardAhorAdapter(List<Retiros> retiros){
        this.lstRetiros = retiros;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public AhorViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.retiro_card, viewGroup, false);
        AhorViewHolder pvh = new AhorViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(AhorViewHolder RetiViewHolder, final int i) {
        try {
            //Config of date in format "dd/MM-YYY"
            String Fecha = lstRetiros.get(i).RetiFecha;
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = (Date)formatter.parse(Fecha);
            String Observacion = lstRetiros.get(i).RetiObservacion;
            float cargo = lstRetiros.get(i).RetiCargo;

            RetiViewHolder.txtFecha.setText(Shared.getDateFormatSpanish(date));
            RetiViewHolder.txtObservacion.setText(Observacion);
            RetiViewHolder.txtSaldo.setText(Shared.getMoneyFormat(cargo));
        }
        catch (Exception ex){

        }
    }

    @Override
    public int getItemCount() {
        return lstRetiros.size();
    }
}
