package com.example.FloodAlert.Citoyens;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.TextView;


import com.example.FloodAlert.CallingTelephone.Calling;
import com.example.FloodAlert.Conseils.MainConseils;


import com.example.FloodAlert.MainActivity;

import com.example.FloodAlert.MapsActivity;

import com.example.FloodAlert.Prevision.WebviewActivity;
import com.example.FloodAlert.Propos;
import com.example.FloodAlert.R;

import com.example.FloodAlert.Reclamation.ReclamationActivity;


import com.example.FloodAlert.TakePicture;
import com.google.firebase.auth.FirebaseAuth;

public class NavigationCitoyens extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_citoyens);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        firebaseAuth = FirebaseAuth.getInstance();

       // TextView userEmail = (TextView) findViewById(R.id.userEmail);
     //  EditText email = (EditText) findViewById(R.id.mail);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_citoyens, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_profile) {
            Intent it1 = new Intent(NavigationCitoyens.this,UpdateProfile.class);
            startActivity(it1);
        }
        if (id == R.id.action_password) {
            Intent it = new Intent(NavigationCitoyens.this,AncienPasswordActivity.class);
            startActivity(it);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        TextView nav_observation = (TextView)findViewById(R.id.nav_observation);
        TextView nav_person = (TextView)findViewById(R.id.nav_person);
        TextView nav_prevision = (TextView)findViewById(R.id.nav_prevision);
        TextView nav_conseils = (TextView)findViewById(R.id.nav_conseils);
        TextView propos = (TextView)findViewById(R.id.propos);
        TextView telephoner = (TextView)findViewById(R.id.telephoner);
        TextView logout = (TextView)findViewById(R.id.nav_send);


switch(id){
    case R.id.nav_person:
    Intent intent = new Intent(NavigationCitoyens.this,Profile.class);
    startActivity(intent);
    break;
    case R.id.nav_observation:
        Intent intent1 = new Intent(NavigationCitoyens.this,TakePicture.class);
        startActivity(intent1);
        break;
    case R.id.nav_prevision:
        Intent intent2 = new Intent(NavigationCitoyens.this,WebviewActivity.class);
        startActivity(intent2);
        break;
    case R.id.nav_conseils:
        Intent intent3 = new Intent(NavigationCitoyens.this,MainConseils.class);
        startActivity(intent3);
        break;
    case R.id.map:
        Intent intent6 = new Intent(NavigationCitoyens.this,MapsActivity.class);
        startActivity(intent6);
        break;
    case R.id.telephoner:
        Intent intent5 = new Intent(NavigationCitoyens.this,Calling.class);
        startActivity(intent5);
        break;
    case R.id.reclamation:
        Intent intent7 = new Intent(NavigationCitoyens.this,ReclamationActivity.class);
        startActivity(intent7);
        break;

    case R.id.propos:
        Intent intent4 = new Intent(NavigationCitoyens.this,Propos.class);
        startActivity(intent4);
        break;
    case R.id.nav_send:
              Logout();
               // FirebaseAuth.getInstance().signOut();
              //  finish();
               // Intent intent7 = new Intent(NavigationCitoyens.this,MainActivity.class);
                //startActivity(intent7);
                break;
}


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void Logout(){
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(NavigationCitoyens.this, MainActivity.class));
    }
}
