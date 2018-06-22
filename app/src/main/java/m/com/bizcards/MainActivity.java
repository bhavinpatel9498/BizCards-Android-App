package m.com.bizcards;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import m.com.bo.ContactBO;
import m.com.bo.UserProfileBO;
import m.com.common.CommonFunctions;
import m.com.dboperation.FBContactDBOperation;
import m.com.dboperation.FBUserProfileOperation;
import m.com.fragment.CardList;
import m.com.fragment.ContactDetails;
import m.com.fragment.ContactUS;
import m.com.fragment.ProfileDetails;
import m.com.fragment.ScanQR;
import m.com.fragment.ViewQR;
import m.com.storage.LocalProfileOperation;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private NavigationView navigationView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        */

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView1 = navigationView;

        View headerView = navigationView.getHeaderView(0);

        /*Do not forget this code*/
        Firebase.setAndroidContext(this);


        /* Setting this as Home Page */


        Bundle dataBundle =  getIntent().getExtras();
        String strContact="";
        String emailCont = "";

        if(dataBundle!=null)
        {
            strContact = dataBundle.getString("contactFragment");
            emailCont = dataBundle.getString("emaild");
        }

        if(!"true".equalsIgnoreCase(strContact))
        {

            UserProfileBO upboTemp = LocalProfileOperation.readData(this);

            if (upboTemp != null) {
                String strName = upboTemp.getFirstName() + " " + upboTemp.getLastName();
                String strEmail = upboTemp.getEmail();

                TextView nameField = (TextView) headerView.findViewById(R.id.headernameid);
                nameField.setText(strName);

                TextView emailField = (TextView) headerView.findViewById(R.id.headeremailid);
                emailField.setText(strEmail);

                CardList cardList = new CardList();
                android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.frameid, cardList).commit();
            } else {
                TextView nameField = (TextView) headerView.findViewById(R.id.headernameid);
                nameField.setText("**User Profile Not Available**");

                TextView emailField = (TextView) headerView.findViewById(R.id.headeremailid);
                emailField.setText("");

                ProfileDetails profileDet = new ProfileDetails();
                android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.frameid, profileDet).commit();
            }

        }
        else
        {
            ContactDetails contactDetails = new ContactDetails();
            Bundle b = new Bundle();
            b.putString("emailid", emailCont);
            contactDetails.setArguments(b);
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frameid, contactDetails).commit();
        }

    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //super.onBackPressed();
            //drawer.closeDrawer(GravityCompat.START);
            this.moveTaskToBack(true);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.contlist)
        {
            CardList cardList = new CardList();
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frameid, cardList).commit();
        }
        else if (id == R.id.viewqrid)
        {
            ViewQR scanQR = new ViewQR();
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frameid, scanQR).commit();

        }
        else if (id == R.id.scanqrid)
        {
            ScanQR scanQR = new ScanQR();
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frameid, scanQR).commit();

        }
        else if (id == R.id.profileid)
        {

            ProfileDetails profileDet = new ProfileDetails();
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frameid, profileDet).commit();

        }
        else if (id == R.id.nav_send)
        {
            ContactUS contus = new ContactUS();
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frameid, contus).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    public void toastMsg(UserProfileBO upbo, Context ctx)
    {
        Toast.makeText(ctx, upbo.getEmail(), Toast.LENGTH_SHORT).show();
    }



}


