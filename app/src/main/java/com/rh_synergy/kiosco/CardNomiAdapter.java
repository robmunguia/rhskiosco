package com.rh_synergy.kiosco;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rh_synergy.kiosco.Models.Nomina;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;


/**
 * Created by PC-108 on 3/2/2016.
 */
public class CardNomiAdapter extends RecyclerView.Adapter<CardNomiAdapter.NomiViewHolder> {

    public static class NomiViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView NominaNo;
        TextView PeriFechas;
        TextView NomiDeposito;
        TextView NomiVales;
        TextView NomiTotal;

        NomiViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            NominaNo = (TextView)itemView.findViewById(R.id.lblDia);
            PeriFechas = (TextView)itemView.findViewById(R.id.lblPeriodo);

            NomiDeposito = (TextView)itemView.findViewById(R.id.txtSalida);
            NomiVales = (TextView)itemView.findViewById(R.id.txtEntrada);
            NomiTotal = (TextView)itemView.findViewById(R.id.txtTotal);
        }
    }

    List<Nomina> lstNominas;

    CardNomiAdapter(List<Nomina> nominas){
        this.lstNominas = nominas;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public NomiViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.nomi_card, viewGroup, false);
        NomiViewHolder pvh = new NomiViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(NomiViewHolder NomiViewHolder, final int i) {
        String periodos = "Del " + lstNominas.get(i).NomiFecInicio + " al " + lstNominas.get(i).NomiFecFinal;
        String NoNomina = "Nómina " + lstNominas.get(i).NomiPeriodo;

        Locale locale = new Locale("en", "US");
        NumberFormat moneyFormat = NumberFormat.getCurrencyInstance(locale);
        String Deposito = moneyFormat.format(lstNominas.get(i).NomiNeto);
        String Vales = moneyFormat.format(lstNominas.get(i).NomiVales);
        String Total = moneyFormat.format(lstNominas.get(i).NomiNeto + lstNominas.get(i).NomiVales);

        NomiViewHolder.NominaNo.setText(NoNomina);
        NomiViewHolder.PeriFechas.setText(periodos);
        NomiViewHolder.NomiDeposito.setText(Deposito);
        NomiViewHolder.NomiVales.setText(Vales);
        NomiViewHolder.NomiTotal.setText(Total);

        NomiViewHolder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Nomina Nomi = lstNominas.get(i);
                Intent i = new Intent(v.getContext(), NominaActivity.class);
                i.putExtra("Nomina", Nomi);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                v.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lstNominas.size();
    }
}
