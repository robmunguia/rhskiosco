package com.rh_synergy.kiosco;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rh_synergy.kiosco.Models.Nomina;
import com.rh_synergy.kiosco.Models.Usuario;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.Type;

public class MainActivity extends AppCompatActivity {

    //Defining Variables
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private TextView lblNombre;
    private TextView lblApellidos;
    private TextView lblNombreUser;
    private ImageView UsuaImage;
    private ImageView UsuaImageContent;
    private String Resultado;
    private Usuario user;

    private RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Initializing Toolbar and setting it as the actionbar
        toolbar = (Toolbar) findViewById(R.id.toolbar); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            try {
                Resultado = extras.getString("KEY");
                JSONObject jsonObject = new JSONObject(Resultado);
                user = new Usuario();
                user.UsuaNoEmpleado = Integer.parseInt(jsonObject.getString("UsuaNoEmpleado"));
                user.UsuaNombres = jsonObject.getString("UsuaNombres");
                user.UsuaApellidos = jsonObject.getString("UsuaApellidos");
                //Ahorro
                if(jsonObject.get("Ahorro") != null) {

                }
                //Nominas
                if(jsonObject.get("Nomina") != null) {
                    String jsonNom = jsonObject.getString("Nomina");
                    Type listType = new TypeToken<ArrayList<Nomina>>() {
                    }.getType();
                    List<Nomina> lstnominas = new Gson().fromJson(jsonNom, listType);
                    user.UsuaNominas = lstnominas;
                }
            }
            catch (JSONException ex){
                Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        //Initializing NavigationView
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        //navigationView.getHeaderView(0);
        View vwHeader = navigationView.getHeaderView(0);
        lblNombre = (TextView) vwHeader.findViewById(R.id.username);
        lblNombre.setText(user.UsuaNombres);

        lblApellidos = (TextView) vwHeader.findViewById(R.id.userApellidos);
        lblApellidos.setText(user.UsuaApellidos);

        lblNombreUser = (TextView) findViewById(R.id.lblUserNombre);
        lblNombreUser.setText(user.UsuaNombres);

        lblNombreUser = (TextView) findViewById(R.id.lblUserApellido);
        lblNombreUser.setText(user.UsuaApellidos);

        //Download User Image
        UsuaImage = (ImageView)vwHeader.findViewById(R.id.profile_image);
        UsuaImageContent = (ImageView)findViewById(R.id.imgUser);
        String pathToFile = "https://kiosco.rh-synergy.com/Temp/" + user.UsuaNoEmpleado + ".jpg";
        DownloadImageWithURLTask downloadTask = new DownloadImageWithURLTask(UsuaImage, UsuaImageContent);
        downloadTask.execute(pathToFile);

        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);

                //Closing drawer on item click
                drawerLayout.closeDrawers();

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.recibos:
                        Intent i = new Intent(MainActivity.this, nominas.class);
                        i.putExtra("NoEmpleado", user.UsuaNoEmpleado);
                        startActivity(i);
                        return true;
                    // For rest of the options we just show a toast on click
                    case R.id.prenomina:
                        i = new Intent(MainActivity.this, PrenominaActivity.class);
                        i.putExtra("NoEmpleado", user.UsuaNoEmpleado);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                        return true;
                    case R.id.ahorro:
                        i = new Intent(MainActivity.this, AhorroActivity.class);
                        i.putExtra("NoEmpleado", user.UsuaNoEmpleado);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                        return true;
                    case R.id.drafts:
                        Toast.makeText(getApplicationContext(), "Drafts Selected", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.allmail:
                        Toast.makeText(getApplicationContext(), "All Mail Selected", Toast.LENGTH_SHORT).show();
                        return true;
                    default:
                        Toast.makeText(getApplicationContext(), "Somethings Wrong", Toast.LENGTH_SHORT).show();
                        return true;
                }
            }
        });

        // Initializing Drawer Layout and ActionBarToggle
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.openDrawer, R.string.closeDrawer){
            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };
        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();

        rv = (RecyclerView)findViewById(R.id.rv);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);

        initializeAdapter(user.UsuaNominas);
    }

    private void initializeAdapter(List<Nomina> UsuaNominas){
        CardNomiAdapter adapter = new CardNomiAdapter(UsuaNominas);
        rv.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class DownloadImageWithURLTask extends AsyncTask<String, Void, Bitmap> {

        ImageView bmImage;
        ImageView bmImageUser;

        public DownloadImageWithURLTask(ImageView bmImage, ImageView bmUserImage) {
            this.bmImage = bmImage;
            this.bmImageUser = bmUserImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String pathToFile = urls[0];
            Bitmap bitmap = null;
            try {
                InputStream in = new java.net.URL(pathToFile).openStream();
                bitmap = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
            bmImageUser.setImageBitmap(result);
        }
    }
}