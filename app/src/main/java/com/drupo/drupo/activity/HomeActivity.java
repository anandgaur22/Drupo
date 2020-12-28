package com.drupo.drupo.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import com.drupo.drupo.R;
import com.drupo.drupo.adapter.TaskAdapter;
import com.drupo.drupo.model.TaskModel;
import com.drupo.drupo.utils.AppUtils;
import com.drupo.drupo.utils.PrefrenceManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private boolean doubleBackToExitPressedOnce = false;
    private DrawerLayout drawer;
    private List<TaskModel> taskModelList;
    private RecyclerView recyclerView;
    private PrefrenceManager prefrenceManager;
    private TextView name_tv;
    private RelativeLayout nav_logout_rl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();
    }

    @SuppressLint("SetTextI18n")
    public void init() {

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        nav_logout_rl = findViewById(R.id.nav_logout_rl);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View view = navigationView.getHeaderView(0);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        recyclerView = findViewById(R.id.recyclerView);
        name_tv = findViewById(R.id.name_tv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        taskModelList = new ArrayList<>();

        prefrenceManager = new PrefrenceManager(getApplicationContext());
        String name = prefrenceManager.fetchLoginName();
        TextView nav_header_name_tv = view.findViewById(R.id.nav_header_name_tv);
        nav_header_name_tv.setText(name);
        name_tv.setText("Hi, " + name);

        nav_logout_rl.setOnClickListener(this);

        taskModelList.add(
                new TaskModel(
                        "Meeting with Jatin Singh",
                        "9:10 AM",
                        "Knowledge park greater noida alpha pahse 1",
                        R.drawable.critical_icon));
        taskModelList.add(
                new TaskModel(
                        "E&Y demo shedule again",
                        "11:10 AM",
                        "Noida park near sector 22 alpha pahse 1",
                        R.drawable.medium_icon));
        taskModelList.add(
                new TaskModel(
                        "Working with dev team",
                        "2:10 PM",
                        "Noida village greater Kailash alpha pahse 2",
                        R.drawable.high_icon));
        taskModelList.add(
                new TaskModel(
                        "Meeting with Saurabh",
                        "5:10 PM",
                        "Noida sector 2 near metro station 15",
                        R.drawable.low_icon));
        taskModelList.add(
                new TaskModel(
                        "New Client Metting",
                        "3:10 PM",
                        "Delhi near ina metro station new delhi india",
                        R.drawable.none_icon));
        taskModelList.add(
                new TaskModel(
                        "Meeting with Anand",
                        "9:10 AM",
                        "Knowledge park greater noida alpha pahse 1",
                        R.drawable.critical_icon));
        taskModelList.add(
                new TaskModel(
                        "Meeting with Jatin Singh",
                        "5:10 AM",
                        "Knowledge park greater noida alpha pahse 1",
                        R.drawable.medium_icon));
        taskModelList.add(
                new TaskModel(
                        "Meeting with Drupo",
                        "10:10 AM",
                        "Noida sector 63 noida alpha pahse 1",
                        R.drawable.low_icon));


        //creating recyclerview adapter
        TaskAdapter adapter = new TaskAdapter(this, taskModelList);
        recyclerView.setAdapter(adapter);

    }


    @Override
    public void onBackPressed() {
        View view = findViewById(android.R.id.content);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
                drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                super.onBackPressed();
            } else if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                if (doubleBackToExitPressedOnce) {
                    super.onBackPressed();
                    return;
                }
                this.doubleBackToExitPressedOnce = true;
                Snackbar.make(view, "Press again to Exit", Snackbar.LENGTH_LONG).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        doubleBackToExitPressedOnce = false;
                    }
                }, 2000);
            } else {
                drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                super.onBackPressed();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.nav_logout_rl:
                logout_alert_dialog();
                drawer.closeDrawers();
                break;

            default:
                break;
        }

    }

    private void isUserLogedOut(boolean status) {
        prefrenceManager.isUserLogedOut();
    }

    public void logout_alert_dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.logout_dialog, null);
        Button yes_btn = dialogView.findViewById(R.id.yes_btn);
        Button no_btn = dialogView.findViewById(R.id.no_btn);
        ImageView close_iv = dialogView.findViewById(R.id.close_iv);
        builder.setView(dialogView);
        final AlertDialog dialog = builder.create();
        dialog.getWindow().getAttributes().windowAnimations = R.style.animationdialog;
        close_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Dismiss the alert dialog
                dialog.cancel();
            }
        });
        no_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Dismiss the alert dialog
                dialog.cancel();
            }
        });
        yes_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Dismiss the alert dialog
                isUserLogedOut(true);
                Intent intent = new Intent(HomeActivity.this, IntroActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

}
