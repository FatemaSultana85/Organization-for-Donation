package com.example.charity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class volunteer_bottom_navigation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_bottom_navigation);
        BottomNavigationView bottomNavigationView=findViewById(R.id.volunteer_bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(onNav);
        getSupportFragmentManager().beginTransaction().replace(R.id.volunteer_frame_layout,
                new Fragment()).commit();
    }
    private BottomNavigationView.OnNavigationItemSelectedListener onNav= new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selected=null;
            switch (item.getItemId())
            {
                case R.id.volunteer_profile_bottom:
                    selected = new volunteer_profile();
                    break;

            }
            getSupportFragmentManager().beginTransaction().replace(R.id.volunteer_frame_layout,
                    selected).commit();
            return true;
        }

    };
}