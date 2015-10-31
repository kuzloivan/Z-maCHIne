package z_machine.vkhackathon.com.z_machine.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import z_machine.vkhackathon.com.z_machine.R;
import z_machine.vkhackathon.com.z_machine.core.appinterface.AppBridge;

public abstract class BaseActivity extends AppCompatActivity {

    protected AppBridge appBridge;
    protected Toolbar toolbar;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appBridge = (AppBridge) getApplicationContext();
    }

    protected final DrawerLayout initNavigationDrawer(
            @NonNull final NavigationView.OnNavigationItemSelectedListener listener) {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        final NavigationItem navigationItem = new NavigationItem(listener);
        navigationView.setNavigationItemSelectedListener(navigationItem);
        navigationItem.onNavigationItemSelected(navigationView.getMenu().getItem(0)); //Todo: выпилить костылину !!!!
        return drawerLayout;
    }

    private class NavigationItem implements NavigationView.OnNavigationItemSelectedListener {

        private NavigationView.OnNavigationItemSelectedListener listener;

        public NavigationItem(NavigationView.OnNavigationItemSelectedListener listener) {
            this.listener = listener;
        }

        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            listener.onNavigationItemSelected(item);
            item.setChecked(true);
            setTitle(item.getTitle());
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        }

    }
}
