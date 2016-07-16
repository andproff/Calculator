package com.goroshevsky.calculator;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.goroshevsky.calculator.Fragments.GraphFragment;
import com.goroshevsky.calculator.Fragments.MainFragment;
import com.goroshevsky.calculator.Fragments.SQLDataFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private String TAG = "Main Activity";
    private MainFragment mainFragment;
    public static String USER_NAME;
    public EditText txtFormula;
    public EditText txtResult;
    private TextView userNameLabel;
    public static boolean VIBRATE_WAS_CALLED = false;
    public static final int REQUEST_LOCATION = 0;

    static{
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = ((NavigationView) findViewById(R.id.nav_view)).getHeaderView(0);

        userNameLabel = (TextView) header.findViewById(R.id.userNameHeader);
        userNameLabel.setText(USER_NAME);
        txtFormula = (EditText) findViewById(R.id.formula);
        txtResult = (EditText) findViewById(R.id.result);
        Log.d(TAG, "Name is " + USER_NAME);

        if (savedInstanceState != null) {
            mainFragment = (MainFragment) getSupportFragmentManager().findFragmentByTag("mainFragment");
        }

        if (savedInstanceState == null) {
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            mainFragment = new MainFragment();
            fragmentTransaction.replace(R.id.frame_layout, mainFragment, "mainFragment");
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (MainFragment.numberPadPager == null || MainFragment.numberPadPager.getCurrentItem() == 0) {
                super.onBackPressed();
            } else {
                MainFragment.numberPadPager.setCurrentItem(MainFragment.numberPadPager.getCurrentItem() - 1);
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_sql_data) {
            Log.d(TAG, "Switch to SQLDataFragment");
            android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction transaction = fm.beginTransaction();

            Fragment visualizeFragment = new SQLDataFragment();
            transaction.replace(R.id.frame_layout, visualizeFragment, "SQLDataFragment");
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
            transaction.addToBackStack(null);
            transaction.commit();


        } else if (id == R.id.nav_calculator) {
            Log.d(TAG, "Switch to MainFragment");
            android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction transaction = fm.beginTransaction();
            mainFragment = new MainFragment();
            transaction.replace(R.id.frame_layout, mainFragment, "mainFragment");
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
            transaction.addToBackStack(null);
            transaction.commit();


        } else if (id == R.id.nav_graph) {
            Log.d(TAG, "Switch to GraphFragment");
            android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction transaction = fm.beginTransaction();
            Fragment graphFragment = new GraphFragment();
            transaction.replace(R.id.frame_layout, graphFragment, "GraphFragment");
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
            transaction.addToBackStack(null);
            transaction.commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
