package z_machine.vkhackathon.com.z_machine.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import z_machine.vkhackathon.com.z_machine.R;
import z_machine.vkhackathon.com.z_machine.ui.fragment.AddPhotoFragment;
import z_machine.vkhackathon.com.z_machine.ui.fragment.GeolocationFragment;
import z_machine.vkhackathon.com.z_machine.ui.fragment.KudaGoFragment;
import z_machine.vkhackathon.com.z_machine.utils.FragmentLauncher;

public final class MainActivity extends BaseActivity {

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = initNavigationDrawer(new NavigationListener());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private final class NavigationListener implements
            NavigationView.OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            final Fragment fragment;
            switch (item.getItemId()) {
                case R.id.nav_add_photo:
                    fragment = AddPhotoFragment.getInstance();
                    break;
                case R.id.nav_kudago:
                    fragment = KudaGoFragment.getInstance();
                    break;
                case R.id.nav_geolocation:
                    fragment = GeolocationFragment.getInstance();
                    break;
                default:
                    throw new IllegalArgumentException();
            }
            FragmentLauncher.replaceMainContainer(getSupportFragmentManager(), fragment);
            return true;
        }
    }
}