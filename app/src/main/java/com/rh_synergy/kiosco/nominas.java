package com.rh_synergy.kiosco;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rh_synergy.kiosco.Global.Shared;
import com.rh_synergy.kiosco.Models.Nomina;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class nominas extends AppCompatActivity {

    private NominasTask mAuthTask = null;
    private RecyclerView rv;

    private View mProgressView;
    private String URLkiosco = "";
    private int NoEmpleado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nominas);
        //Inserta el tool bar Principal
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Obtiene el Numero de Empleado.
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            NoEmpleado = extras.getInt("NoEmpleado");
        }

        mProgressView = findViewById(R.id.login_progress);
        showProgress(true);
        mAuthTask = new NominasTask(NoEmpleado);
        mAuthTask.execute((Void) null);

        rv = (RecyclerView)findViewById(R.id.rvNominas);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);
    }

    //Inicializa el adaptador de Nominas
    private void initializeAdapter(List<Nomina> UsuaNominas){
        CardNomiAdapter adapter = new CardNomiAdapter(UsuaNominas);
        rv.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    /**
     * Muestra el Loading y oculta el formulario de inicio de sesion
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }

    //Abrir la coneccion para acceso JSON (Web Api Kiosco)
    public String readJSONFeed(final int NoEmpl) {
        HttpURLConnection c = null;
        try {
            URLkiosco = Shared.getURL() + "Nomina";
            java.net.URL u = new URL(URLkiosco + "?noEmpleado="+NoEmpl);
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
            mAuthTask = null;
            showProgress(false);
            try {
                if(result == "Error"){
                    Toast toast;
                    toast = Toast.makeText(getApplicationContext(), "Ocurrio un error al leer las nóminas", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else{
                    Toast toast;
                    //lectora del Listado Json (Nominas y Detalle Json)
                    String jsonNom = result;
                    Type listType = new TypeToken<ArrayList<Nomina>>() {
                    }.getType();
                    List<Nomina> lstnominas = new Gson().fromJson(jsonNom, listType);
                    initializeAdapter(lstnominas);
                }
            } catch (Exception e) {
                Toast toast;
                toast = Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG);
                toast.show();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}
