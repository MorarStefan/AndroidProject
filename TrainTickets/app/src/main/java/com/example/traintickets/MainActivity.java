package com.example.traintickets;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;

    private TextView usernameField;
    private TextView emailField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialiseComponents();
        setCredentials();
        setSupportActionBar(toolbar);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_drawer_open, R.string.nav_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        showTicketsFragmentOnStart(savedInstanceState);
    }

    private void initialiseComponents() {
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        View headerView = navigationView.getHeaderView(0);
        usernameField = headerView.findViewById(R.id.header_name_field);
        emailField = headerView.findViewById(R.id.header_email_field);
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
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_account:
                AccountFragment accountFragment = new AccountFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, accountFragment).commit();
                Bundle bundle = new Bundle();
                bundle.putString("EMAIL", emailField.getText().toString());
                accountFragment.setArguments(bundle);
                break;
            case R.id.nav_tickets:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new TicketsFragment()).commit();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    void showTicketsFragmentOnStart(Bundle savedInstanceState) {
        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new TicketsFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_tickets);
        }
    }

    void setCredentials() {
        usernameField.setText(getIntent().getStringExtra("FIRST_NAME") + " " + getIntent().getStringExtra("LAST_NAME"));
        emailField.setText(getIntent().getStringExtra("EMAIL"));
    }

    public TextView getEmailField() {
        return emailField;
    }
}
