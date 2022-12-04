package com.example.charity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class organizationBottomNavigation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization_bottom_navigation);

        BottomNavigationView bottomNavigationView=findViewById(R.id.organization_bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(onNav);
        getSupportFragmentManager().beginTransaction().replace(R.id.org_frame_layout,
                new Fragment()).commit();
    }
    private BottomNavigationView.OnNavigationItemSelectedListener onNav= new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selected=null;
            switch (item.getItemId())
            {
                case R.id.org_profile_bottom:
                    selected = new organization_profile();
                    break;
               

            }
            getSupportFragmentManager().beginTransaction().replace(R.id.org_frame_layout,
                    selected).commit();
            return true;
        }

    };
}