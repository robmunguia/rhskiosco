package com.rh_synergy.kiosco;

import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rh_synergy.kiosco.Global.Shared;
import com.rh_synergy.kiosco.Models.Ahorro;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AhorroActivity extends AppCompatActivity {
    //Variables Gloables

    private String URLkiosco = "";
    private Ahorro ahorro;
    private AhorroTask mAuthTask = null;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewAhorAdapter viewAhorAdapter;

    private TextView txtAhorFec;
    private TextView txtAhorReti;
    private TextView txtAhorDescPer;
    private TextView txtAhorTotal;
    private TextView txtAhorSaldo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ahorro_activity);

        //Obtiene el Numero de Empleado
        Bundle extras = getIntent().getExtras();
        int noEmpleado = extras.getInt("NoEmpleado");
        //Consume la Api
        mAuthTask = new AhorroTask(noEmpleado);
        mAuthTask.execute((Void) null);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        //Ahorro encabezado
        txtAhorFec = (TextView)findViewById(R.id.txtAhorFec);
        txtAhorReti = (TextView)findViewById(R.id.txtAhorReti);
        txtAhorDescPer = (TextView)findViewById(R.id.txtAhorDescPer);
        txtAhorTotal = (TextView)findViewById(R.id.txtAhorTotal);
        txtAhorSaldo = (TextView)findViewById(R.id.txtAhorSaldo);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    /*
    * Inicializa los controles despues de obtener el Json
    * */
    private void InitializateData() {
        /*Instancia del Adaptador*/
        viewAhorAdapter = new ViewAhorAdapter(getSupportFragmentManager(), ahorro);

        viewPager.setAdapter(viewAhorAdapter);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final TabLayout.Tab actual = tabLayout.newTab();
        final TabLayout.Tab anterior = tabLayout.newTab();

        /*Titulos de los tabs*/
        actual.setText("Retiros");
        anterior.setText("Depositos");

        /*Agregando las posiciones de los tabs*/
        tabLayout.addTab(actual, 0);
        tabLayout.addTab(anterior, 1);

        /*Agrega los colores al los eventos seleccionados y presionados*/
        tabLayout.setTabTextColors(ContextCompat.getColorStateList(this, R.color.tab_selector));
        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.indicator));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        //Encabezado de Ahorro
        try {
            String _Fecha = ahorro.AhorFecha;
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = (Date) formatter.parse(_Fecha);
            txtAhorFec.setText(Shared.getSpanishDate(date));
            txtAhorReti.setText(Shared.getMoneyFormat(ahorro.AhorCargos));
            txtAhorDescPer.setText(Shared.getMoneyFormat(ahorro.AhorDescPeriodo));
            txtAhorTotal.setText(Shared.getMoneyFormat(ahorro.AhorTotal));
            txtAhorSaldo.setText("Saldo: "+Shared.getMoneyFormat(ahorro.AhorSaldo));
        }
        catch (Exception ex){

        }
    }


    /**
     * Regresa la respuesta del servicio (Kiosco Api)
     */
    public class AhorroTask extends AsyncTask<Void, Void, String> {

        private final int _noEmpl;

        AhorroTask(int noEmpleado) {
            _noEmpl = noEmpleado;
        }

        @Override
        protected String doInBackground(Void... params) {
            URLkiosco = Shared.getURL() + "Consultas" + "/" + _noEmpl;
            return Shared.readJSONFeed(_noEmpl, URLkiosco);
        }

        @Override
        protected void onPostExecute(final String result) {
            try {
                if(result == "Error"){

                }
                else{
                    //lectora del Listado Json (Nominas y Detalle Json)
                    String jsonNom = result;
                    Type listType = new TypeToken<Ahorro>() {
                    }.getType();
                    ahorro = new Gson().fromJson(jsonNom, listType);
                    InitializateData();
                }
            } catch (Exception e) {

            }
        }

        @Override
        protected void onCancelled() {

        }
    }
}
