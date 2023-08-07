package acs.upb.licenta.aplicatiegrup.others;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import acs.upb.licenta.aplicatiegrup.MainMenuActivity;
import acs.upb.licenta.aplicatiegrup.userRelated.LoginActivity;
import acs.upb.licenta.aplicatiegrup.userRelated.ProfileActivity;
import acs.upb.licenta.aplicatiegrup.R;
import acs.upb.licenta.aplicatiegrup.eventActivities.CalendarActivity;
import acs.upb.licenta.aplicatiegrup.eventActivities.MyEventsActivity;
import acs.upb.licenta.aplicatiegrup.groupActivities.MyGroupsActivity;

public class DrawerBaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;

    @Override
    public void setContentView(View view) {
        drawerLayout = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_drawer_base, null);
        FrameLayout container = drawerLayout.findViewById(R.id.activityContainer);
        container.addView(view);
        super.setContentView(drawerLayout);

        Toolbar toolbar = drawerLayout.findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        NavigationView navigationView = drawerLayout.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.menu_drawer_open, R.string.menu_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);

        switch (item.getItemId()) {
            case R.id.nav_main_menu:
                startActivity(new Intent(this, MainMenuActivity.class));
                overridePendingTransition(0, 0);
                break;

            case R.id.nav_profile:
                startActivity(new Intent(this, ProfileActivity.class));
                overridePendingTransition(0, 0);
                break;

            case R.id.nav_calendar:
                startActivity(new Intent(this, CalendarActivity.class));
                overridePendingTransition(0, 0);
                break;

            case R.id.nav_events:
                startActivity(new Intent(this, MyEventsActivity.class));
                overridePendingTransition(0, 0);
                break;

            case R.id.nav_groups:
                startActivity(new Intent(this, MyGroupsActivity.class));
                overridePendingTransition(0, 0);
                break;

            case R.id.nav_about:
                startActivity(new Intent(this, AboutActivity.class));
                overridePendingTransition(0, 0);
                break;

            case R.id.nav_logout:
                startActivity(new Intent(this, LoginActivity.class));
                FirebaseAuth.getInstance().signOut();
                overridePendingTransition(0, 0);
                break;
        }

        return false;
    }

    protected void allocateActivityTitle(String titleString) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(titleString);
        }
    }
}