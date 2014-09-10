package com.brianckrebs.randomphoto.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ShareActionProvider;

import com.brianckrebs.randomphoto.R;
import com.brianckrebs.randomphoto.activities.model.MainModel;
import com.brianckrebs.randomphoto.activities.presenter.MainPresenter;


public class MainActivity extends BaseActivity {

    private DrawerLayout drawerLayout;
    private ListView nav;
    private ActionBarDrawerToggle drawerToggle;
    private ImageView mainImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this,
                drawerLayout,
                R.drawable.ic_drawer,
                R.string.drawer_open,
                R.string.drawer_close) {
            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
            }
            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);
        nav = (ListView) findViewById(R.id.left_drawer);
        mainImage = (ImageView) findViewById(R.id.main_image);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        MainModel model = new MainModel();
        presenter = new MainPresenter(this, model);
        presenter.start();
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

        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        int id = item.getItemId();
        switch(id) {
            case R.id.menu_item_share:
                ((MainPresenter)presenter).onShareClick();
                return true;
            case R.id.menu_item_next:
                ((MainPresenter)presenter).onNextClick();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        if(drawerToggle!=null) drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        if(drawerToggle!=null) drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {
        // Close the menu or the notifications bar on back pressed
        if (drawerLayout.isDrawerOpen(nav)) {
            drawerLayout.closeDrawer(nav);
        } else {
            super.onBackPressed();
        }
    }

    public ImageView getMainImage() {
        return mainImage;
    }

    public DrawerLayout getDrawerLayout() {
        return drawerLayout;
    }

    public ListView getNav() {
        return nav;
    }
}
