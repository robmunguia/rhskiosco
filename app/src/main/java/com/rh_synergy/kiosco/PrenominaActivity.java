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
import com.rh_synergy.kiosco.Models.Checadas;
import com.rh_synergy.kiosco.Models.Prenomina;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PrenominaActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;

    private String URLkiosco = "";
    private NominasTask mAuthTask = null;
    private Prenomina prenomina;

    TextView txtHoras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_prenomina);

        //Obtiene el Numero de Empleado
        Bundle extras = getIntent().getExtras();
        int noEmpleado = extras.getInt("NoEmpleado");
        //Consume la Api
        mAuthTask = new NominasTask(noEmpleado);
        mAuthTask.execute((Void) null);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        txtHoras = (TextView)findViewById(R.id.txtTotalHoras);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    //Inicializa el tab
    private void InitializateData(){
        /*Instancia del Adaptador*/
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), prenomina);

        viewPager.setAdapter(viewPagerAdapter);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final TabLayout.Tab actual = tabLayout.newTab();
        final TabLayout.Tab anterior = tabLayout.newTab();

        /*Titulos de los tabs*/
        actual.setText("Actual");
        anterior.setText("Anterior");

        /*Agregando las posiciones de los tabs*/
        tabLayout.addTab(actual, 0);
        tabLayout.addTab(anterior, 1);

        /*Agrega los colores al los eventos seleccionados y presionados*/
        tabLayout.setTabTextColors(ContextCompat.getColorStateList(this, R.color.tab_selector));
        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.indicator));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                float THora = tab.getText() == "Actual" ? getTotalHoras(prenomina.PrePrenomina) : getTotalHoras(prenomina.PreAnterior);
                float THoraExtra = tab.getText() == "Actual" ? getTotalHorasExtras(prenomina.PrePrenomina) : getTotalHorasExtras(prenomina.PreAnterior);
                txtHoras.setText("Total Horas     " + String.format("%.2f", THora) + "    Extras      " + String.format("%.2f", THoraExtra));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        float THora = getTotalHoras(prenomina.PrePrenomina);
        float THoraExtra = getTotalHorasExtras(prenomina.PrePrenomina);
        txtHoras.setText("Total Horas     " + String.format("%.2f", THora) + "    Extras      " + String.format("%.2f", THoraExtra));
    }

    /*
    Sumatoria de las horas en checadas
    * */
    private float getTotalHoras(List<Checadas> lstChecadas) {
        float horas = 0;
        for (int i = 0; i < lstChecadas.size(); i++){
            horas += lstChecadas.get(i).preHoras;
        }
        return horas;
    }
    /*
    Sumatoria de las horas Extras en checadas
    * */
    private float getTotalHorasExtras(List<Checadas> lstChecadas) {
        float horas = 0;
        for (int i = 0; i < lstChecadas.size(); i++){
            horas += lstChecadas.get(i).preNumExt;
        }
        return horas;
    }

    //Abrir la coneccion para acceso JSON (Web Api Kiosco)
    public String readJSONFeed(final int NoEmpl) {
        HttpURLConnection c = null;
        try {
            URLkiosco = Shared.getURL() + "Nomina";
            java.net.URL u = new URL(URLkiosco + "?id="+NoEmpl);
            c = (HttpURLConnection) u.openConnection();
            c.setRequestMethod("GET");
            c.setRequestProperty("Content-length", "0");
            c.setUseCaches(false);
            c.setAllowUserInteraction(false);
            c.connect();
            int status = c.getResponseCode();
            switch (status) {
                case 200:
                case 201:
                    BufferedReader br = new BufferedReader(new
                            InputStreamReader(c.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line+"\n");
                    }
                    br.close();
                    return sb.toString();
                case 404:
                    return "Error";
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (c != null) {
                try {
                    c.disconnect();
                } catch (Exception ex) {
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
    }

    /**
     * Regresa la respuesta del servicio (Kiosco Api) para verificar el usuario
     */
    public class NominasTask extends AsyncTask<Void, Void, String> {

        private final int _noEmpl;

        NominasTask(int noEmpleado) {
            _noEmpl = noEmpleado;
        }

        @Override
        protected String doInBackground(Void... params) {
            return readJSONFeed(_noEmpl);
        }

        @Override
        protected void onPostExecute(final String result) {
            try {
                if(result == "Error"){

                }
                else{
                    //lectora del Listado Json (Nominas y Detalle Json)
                    String jsonNom = result;
                    Type listType = new TypeToken<Prenomina>() {
                    }.getType();
                    prenomina = new Gson().fromJson(jsonNom, listType);
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
