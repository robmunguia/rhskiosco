package com.rh_synergy.kiosco;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rh_synergy.kiosco.Models.Nomina;

import java.text.NumberFormat;
import java.util.List;

/**
 * Created by PC-108 on 1/27/2016.
 */
public class NomiAdapter extends BaseAdapter {
    private Context context;
    private List<Nomina> lstNominas;

    public NomiAdapter(Context context, List<Nomina> Nominas) {
        this.context = context;
        this.lstNominas = Nominas;
    }

    @Override
    public int getCount() {
        return lstNominas.size();
    }

    @Override
    public Object getItem(int position) {
        return lstNominas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.nomilist, parent, false);

        TextView text1 = (TextView)row.findViewById(R.id.txtTitle);
        TextView text2 = (TextView) row.findViewById(R.id.txtSubtitle);

        NumberFormat format = NumberFormat.getCurrencyInstance();
        String Neto = format.format(lstNominas.get(position).NomiNeto);

        text1.setText("Depósito: " + Neto);
        text1.setTextColor(Color.parseColor("#0FB34B"));
        text1.setGravity(1);
        text2.setText("Periodo: " + lstNominas.get(position).NomiPeriodo + " del " + lstNominas.get(position).NomiFecInicio + " al " + lstNominas.get(position).NomiFecFinal);
        text2.setTextColor(Color.GRAY);
        text2.setGravity(1);
        return row;
    }
}
