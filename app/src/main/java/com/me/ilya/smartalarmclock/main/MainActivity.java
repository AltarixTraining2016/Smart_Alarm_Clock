package com.me.ilya.smartalarmclock.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


import com.me.ilya.smartalarmclock.AlarmItem;
import com.me.ilya.smartalarmclock.R;
import com.me.ilya.smartalarmclock.Titleable;
import com.me.ilya.smartalarmclock.main.AlarmListFragment;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private List<AlarmItem> nAlarmList;

    private ActionBarDrawerToggle drawerToggle;
    private static final String STATE_TITLE = "title";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      // lvAlarm=(ListView)findViewById(R.id.listview_alarm);
      // lvAlarm.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      //     @Override
      //     public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
      //         Toast.makeText(getApplicationContext(),"lalala",Toast.LENGTH_SHORT).show();
      //     }
        //});
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        if (savedInstanceState == null) {
            replaceContent(AlarmListFragment.create());
        } else {
            setTitle(savedInstanceState.getString(STATE_TITLE));
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle= new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){
            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                //getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

        };
drawer.addDrawerListener(drawerToggle);

        navigationView.setNavigationItemSelectedListener(this);
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
        getMenuInflater().inflate(R.menu.main, menu);
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
        android.app.FragmentTransaction fragmentTrans=getFragmentManager().beginTransaction();
        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }else if(id==R.id.nav_about){

        }fragmentTrans.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void replaceContent(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.container_content, fragment).commit();
        if (fragment instanceof Titleable) {
            String title = ((Titleable) fragment).getTitle(this);
            setTitle(title);
        }
    }
}
