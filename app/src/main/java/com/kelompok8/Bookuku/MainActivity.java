package com.kelompok8.Bookuku;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.kelompok8.Bookuku.homescreen.PagerAdapter;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth mAuth;
    private DrawerLayout mDrawerLayout;

    public static final String table1 = "Books";
    public static final String table2 = "Comment";
    public static final String table3 = "Users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create an instance of the tab layout from the view.
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs_layout);
        // Set the text for each tab.
        tabLayout.addTab(tabLayout.newTab().setText("Terbaru"));
        // Set the tabs to fill the entire layout.
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        setNavigationViewListener();
        
        mAuth = FirebaseAuth.getInstance();

        final ViewPager viewPager = (ViewPager) findViewById(R.id.mainPager);
        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()) {
            case R.id.nav_help:
                Intent a = new Intent(MainActivity.this, Bantuan.class);
                startActivity(a);
                break;

            case R.id.nav_home:
                Intent t = new Intent(MainActivity.this, MainActivity.class);
                startActivity(t);
                break;

            case R.id.nav_book:
                Intent te = new Intent(MainActivity.this, bukuSaya.class);
                startActivity(te);
                break;

            case R.id.nav_about:
                Intent tes = new Intent(MainActivity.this, AboutUs.class);
                startActivity(tes);
                break;

            case R.id.nav_Logout:
                mAuth.signOut();
                Intent b = new Intent(MainActivity.this, Login.class);
                startActivity(b);
                break;
        }
        return (super.onOptionsItemSelected(item));
    }


    private void setNavigationViewListener() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
