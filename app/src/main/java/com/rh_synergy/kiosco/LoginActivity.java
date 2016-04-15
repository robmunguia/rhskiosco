package com.rh_synergy.kiosco;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.rh_synergy.kiosco.Global.Shared;
import com.rh_synergy.kiosco.Models.Usuario;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements Serializable {

    private UserLoginTask mAuthTask = null;

    // Variables
    private AutoCompleteTextView txtNoEmpl;
    private EditText txtPassword;
    private View mProgressView;
    private View mLoginFormView;
    private String URLkiosco = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        // Set up the login form.
        txtNoEmpl = (AutoCompleteTextView) findViewById(R.id.txtnoEmpl);
        txtPassword = (EditText) findViewById(R.id.txtpassword);
        txtPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button btnSignIn = (Button) findViewById(R.id.btn_sign_in);
        btnSignIn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    /**
     * Validacion de los campos de inicio de sesion
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }
        txtNoEmpl.setError(null);
        txtPassword.setError(null);

        String noEmpl = txtNoEmpl.getText().toString();
        String password = txtPassword.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(password)) {
            txtPassword.setError(getString(R.string.error_field_required));
            focusView = txtPassword;
            cancel = true;
        }

        if (TextUtils.isEmpty(noEmpl)) {
            txtNoEmpl.setError(getString(R.string.error_field_required));
            focusView = txtNoEmpl;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            showProgress(true);
            mAuthTask = new UserLoginTask(Integer.parseInt(noEmpl), password);
            mAuthTask.execute((Void) null);
        }
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

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

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
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    //Abrir la coneccion para acceso JSON (Web Api Kiosco)
    public String readJSONFeed(final int NoEmpl, final String pass) {
        HttpURLConnection c = null;
        try {
            URLkiosco = Shared.getURL() + "Usuarios";
            java.net.URL u = new URL(URLkiosco + "?noEmpleado="+NoEmpl+"&pass="+pass);
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
                    return "Usuario Inválido";
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
    public class UserLoginTask extends AsyncTask<Void, Void, String> {

        private final int _noEmpl;
        private final String _Password;

        UserLoginTask(int email, String password) {
            _noEmpl = email;
            _Password = password;
        }

        @Override
        protected String doInBackground(Void... params) {
            return readJSONFeed(_noEmpl,_Password);
        }

        @Override
        protected void onPostExecute(final String result) {
            mAuthTask = null;
            showProgress(false);
            try {
                if(result == "Usuario Inválido"){
                    txtPassword.setError(getString(R.string.error_incorrect_password));
                    txtPassword.requestFocus();
                }
                else {
                    Toast toast;
                    //Obtiene el Objeto Json
                    JSONObject jsonObject = new JSONObject(result);
                    switch (jsonObject.getString("UsuaEstatus"))
                    {
                        case "bloqueado":
                            toast = Toast.makeText(getApplicationContext(), getString(R.string.bloqueo), Toast.LENGTH_SHORT);
                            toast.show();
                            break;
                        case "ok":
                            Intent i = new Intent(LoginActivity.this, MainActivity.class);
                            i.putExtra("KEY", result);
                            startActivity(i);
                            finish();
                            break;
                        default:
                            toast = Toast.makeText(getApplicationContext(), getString(R.string.no_found), Toast.LENGTH_SHORT);
                            toast.show();
                            break;
                    }
                }
            } catch (Exception e) {

            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}

