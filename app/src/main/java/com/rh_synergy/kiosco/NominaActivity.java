package com.rh_synergy.kiosco;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rh_synergy.kiosco.Models.DetalleNomina;
import com.rh_synergy.kiosco.Models.Nomina;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class NominaActivity extends AppCompatActivity {

    Nomina _nomina;
    TextView txtTitulo;
    TextView txtPercTotal;
    TextView txtDecTotal;
    TextView txtDeposito;
    TextView txtVales;
    TextView txtTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nomina);
        //Inserta el tool bar Principal
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtTitulo = (TextView)findViewById(R.id.lblDia);
        //Obtiene el objeto de Nomina
        Bundle extras = getIntent().getExtras();
        _nomina = (Nomina)extras.getSerializable("Nomina");

        //Generar Lista de Percepciones
        float TotalPerc = 0;
        float TotalDeduccion = 0;
        List<DetalleNomina> lstPercepciones = new ArrayList<DetalleNomina>();
        for(int i = 0; i < _nomina.DetalleNomina.size(); i++ ){
            if(_nomina.DetalleNomina.get(i).MO_PERCEPC != 0 && _nomina.DetalleNomina.get(i).CO_TIPO != 4){
                lstPercepciones.add(_nomina.DetalleNomina.get(i));
                TotalPerc +=_nomina.DetalleNomina.get(i).MO_PERCEPC;
            }
        }
        txtPercTotal = (TextView)findViewById(R.id.txtotalPerc);
        Locale locale = new Locale("en", "US");
        NumberFormat moneyFormat = NumberFormat.getCurrencyInstance(locale);
        String Monto = moneyFormat.format(TotalPerc);
        txtPercTotal.setText(Monto);

        //Generar Lista de Deducciones
        List<DetalleNomina> lstDeducciones = new ArrayList<DetalleNomina>();
        for(int i = 0; i < _nomina.DetalleNomina.size(); i++ ){
            if(_nomina.DetalleNomina.get(i).MO_DEDUCCI != 0){
                lstDeducciones.add(_nomina.DetalleNomina.get(i));
                TotalDeduccion +=_nomina.DetalleNomina.get(i).MO_DEDUCCI;
            }
        }
        txtDecTotal = (TextView)findViewById(R.id.txtDedTotal);
        Monto = moneyFormat.format(TotalDeduccion);
        txtDecTotal.setText(Monto);

        //Presentacion de la informacion
        llenaDetalles(lstPercepciones, lstDeducciones);
        //Mumero de Nomina
        txtTitulo.setText("Nómina " + _nomina.NomiPeriodo);

        //Resumen de Nomina
        txtDeposito = (TextView)findViewById(R.id.txtSalida);
        Monto = moneyFormat.format(_nomina.NomiNeto);
        txtDeposito.setText(Monto);
        txtVales = (TextView)findViewById(R.id.txtEntrada);
        Monto = moneyFormat.format(_nomina.NomiVales);
        txtVales.setText(Monto);
        txtTotal = (TextView)findViewById(R.id.txtTotal);
        Monto = moneyFormat.format(_nomina.NomiVales + _nomina.NomiNeto);
        txtTotal.setText(Monto);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    //Metodos Privados
    private void llenaDetalles(List<DetalleNomina> lstPercep, List<DetalleNomina> lstDeduccion)
    {
        RelativeLayout layoutPercepcion = (RelativeLayout) findViewById(R.id.rlPercepciones);
        int PaddingTop = 90;

        final TextView[] txtPercepciones = new TextView[(lstPercep.size() * 2)];
        for (int i = 0; i < lstPercep.size(); i++) {
            //Percepcion Descripcion
            if(lstPercep.get(i).CO_DESCRIP != "TOTAL") {
                final TextView perDescrip = new TextView(this);
                perDescrip.setText(lstPercep.get(i).CO_DESCRIP);
                perDescrip.setPadding(16, PaddingTop, 16, 16);
                layoutPercepcion.addView(perDescrip);
                txtPercepciones[i] = perDescrip;
            }

            //Percepcion Monto
            final TextView perMonto = new TextView(this);
            Locale locale = new Locale("en", "US");
            NumberFormat moneyFormat = NumberFormat.getCurrencyInstance(locale);
            String Monto = moneyFormat.format(lstPercep.get(i).MO_PERCEPC);
            perMonto.setText(Monto);
            // text view layout params
            RelativeLayout.LayoutParams MontoLayoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            // add a rule to align to the right
            MontoLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            perMonto.setLayoutParams(MontoLayoutParams);
            perMonto.setPadding(16, PaddingTop, 16, 16);
            layoutPercepcion.addView(perMonto);
            txtPercepciones[(i + 1)] = perMonto;
            PaddingTop = PaddingTop + 45;
        }

        //Deducciones
        RelativeLayout layoutDeduccion = (RelativeLayout) findViewById(R.id.rlDeducciones);
        PaddingTop = 90;
        final TextView[] txtDeducciones = new TextView[lstDeduccion.size() * 2];
        for (int i = 0; i < lstDeduccion.size(); i++) {
            //Deduccion Descripcion
            final TextView perDescrip = new TextView(this);
            perDescrip.setText(lstDeduccion.get(i).CO_DESCRIP);
            perDescrip.setPadding(16, PaddingTop, 16, 16);
            layoutDeduccion.addView(perDescrip);
            txtDeducciones[i] = perDescrip;

            //Percepcion Monto
            final TextView perMonto = new TextView(this);
            Locale locale = new Locale("en", "US");
            NumberFormat moneyFormat = NumberFormat.getCurrencyInstance(locale);
            String Monto = moneyFormat.format(lstDeduccion.get(i).MO_DEDUCCI);
            perMonto.setText(Monto);
            // text view layout params
            RelativeLayout.LayoutParams MontoLayoutParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            // add a rule to align to the right
            MontoLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            perMonto.setLayoutParams(MontoLayoutParams);
            perMonto.setPadding(16, PaddingTop, 16, 16);
            layoutDeduccion.addView(perMonto);
            txtDeducciones[(i + 1)] = perMonto;
            PaddingTop = PaddingTop + 45;
        }
    }
}
