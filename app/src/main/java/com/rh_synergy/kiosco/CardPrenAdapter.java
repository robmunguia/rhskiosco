package com.rh_synergy.kiosco;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rh_synergy.kiosco.Global.Shared;
import com.rh_synergy.kiosco.Models.Checadas;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by PC-108 on 3/30/2016.
 */
public class CardPrenAdapter extends RecyclerView.Adapter<CardPrenAdapter.PrenoViewHolder> {

    public static class PrenoViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView txtFecha;
        TextView txtDia;
        TextView txtEntrada;
        TextView txtSalida;
        TextView txtHoras;
        TextView txtExtras;
        TextView txtDescripcion;

        TextView lblEntrada;
        TextView lblSalida;
        TextView lblfalta;
        TextView lblOrdinarias;
        TextView lblExtras;

        PrenoViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cvPrenomina);
            txtFecha = (TextView)itemView.findViewById(R.id.txtDepoFecha);
            txtDia = (TextView)itemView.findViewById(R.id.txtDepoAhorro);
            txtEntrada = (TextView)itemView.findViewById(R.id.txtEntrada);
            txtSalida = (TextView)itemView.findViewById(R.id.txtSalida);
            txtHoras = (TextView)itemView.findViewById(R.id.txtHoras);
            txtExtras = (TextView)itemView.findViewById(R.id.txtHExtras);
            txtDescripcion = (TextView)itemView.findViewById(R.id.txtDescripcion);
            //Label's
            lblEntrada = (TextView)itemView.findViewById(R.id.lblentrada);
            lblSalida = (TextView)itemView.findViewById(R.id.lblalida);
            lblfalta = (TextView)itemView.findViewById(R.id.txtDepoPeriodo);
            lblOrdinarias = (TextView)itemView.findViewById(R.id.lblOrdinarias);
            lblExtras = (TextView)itemView.findViewById(R.id.lblExtras);
        }
    }

    List<Checadas> lstChecadas;

    CardPrenAdapter(List<Checadas> checadas){
        this.lstChecadas = checadas;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public PrenoViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.prenom_card, viewGroup, false);
        PrenoViewHolder pvh = new PrenoViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PrenoViewHolder NomiViewHolder, final int i) {
        try {
            //Config of date in format "dd/MM-YYY"
            String Fecha = lstChecadas.get(i).AU_FECHA;
            String FechaActual = lstChecadas.get(i).preFecActual;
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = (Date)formatter.parse(Fecha);
            Date dateActual = (Date)formatter.parse(FechaActual);
            NomiViewHolder.lblfalta.setText("");
            NomiViewHolder.txtDescripcion.setText("");

            String _Entrada = lstChecadas.get(i).preEntrada == null ? "" : lstChecadas.get(i).preEntrada;
            String _Salida = lstChecadas.get(i).preSalida == null ? "" : lstChecadas.get(i).preSalida;
            String _horas = lstChecadas.get(i).preEntrada == null ? "" : Float.toString(lstChecadas.get(i).preHoras);
            String _extras = lstChecadas.get(i).preSalida == null ? "" : Float.toString(lstChecadas.get(i).preNumExt);
            String _Element = lstChecadas.get(i).preElement == null ? "" : lstChecadas.get(i).preElement.trim();

            _Entrada = _Entrada == "" ? "" : _Entrada.substring(0,2) + ":" + _Entrada.substring(2);
            _Salida = _Salida == "" ? "" : _Salida.substring(0,2) + ":" + _Salida.substring(2);

            NomiViewHolder.txtDia.setText(Shared.getSpanishDay(date.getDay()));
            NomiViewHolder.txtFecha.setText(Shared.getDateFormatSpanish(date));
            NomiViewHolder.txtEntrada.setText(_Entrada);
            NomiViewHolder.txtSalida.setText(_Salida);

            NomiViewHolder.txtHoras.setText(_horas);
            NomiViewHolder.txtExtras.setText(_extras);

            if(lstChecadas.get(i).preEstDia != 0 && _horas != ""){
                NomiViewHolder.lblEntrada.setText("");
                NomiViewHolder.lblSalida.setText("");
                NomiViewHolder.lblOrdinarias.setText("");
                NomiViewHolder.lblExtras.setText("");
            }

            //Label's
            if(lstChecadas.get(i).preElement.trim().contains("FALTA")){
                NomiViewHolder.lblEntrada.setText("");
                NomiViewHolder.lblSalida.setText("");
                NomiViewHolder.lblOrdinarias.setText("");
                NomiViewHolder.lblExtras.setText("");
                NomiViewHolder.lblfalta.setText(_Element);
            }
            else{
                NomiViewHolder.lblEntrada.setText("Entrada");
                NomiViewHolder.lblSalida.setText("Salida");
                NomiViewHolder.lblfalta.setText("");
                NomiViewHolder.txtDescripcion.setText(_Element);
            }
            if(date.after(dateActual) || date.compareTo(dateActual) == 0){
                NomiViewHolder.lblfalta.setText("Pendiente");
                NomiViewHolder.txtHoras.setText("");
            }
        }
        catch (Exception ex){

        }
    }

    @Override
    public int getItemCount() {
        return lstChecadas.size();
    }
}
