package com.exe.paradox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.exe.paradox.MainFragments.HomeFragment;
import com.exe.paradox.MainFragments.LeaderboardFragment;
import com.exe.paradox.MainFragments.PrizesFragment;
import com.exe.paradox.MainFragments.ProfileFragment;
import com.exe.paradox.MainFragments.RulesFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    HomeFragment homeFragment;
    LeaderboardFragment leaderboardFragment;
    ProfileFragment profileFragment;
    RulesFragment rulesFragment;
    PrizesFragment prizesFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpBottomBarAndFrags();
    }

    private void setUpBottomBarAndFrags() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        homeFragment = new HomeFragment();
        prizesFragment = new PrizesFragment();
        profileFragment = new ProfileFragment();
        rulesFragment = new RulesFragment();
        leaderboardFragment = new LeaderboardFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, homeFragment)
                .commit();
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                switch (item.getItemId()){
                    case R.id.home:
                        transaction.replace(R.id.fragmentContainer, homeFragment)
                                .commit();
                        break;
                    case R.id.profile:
                        transaction.replace(R.id.fragmentContainer, profileFragment)
                                .commit();
                        break;
                    case R.id.leaderboard:
                        transaction.replace(R.id.fragmentContainer, leaderboardFragment).commit();
                        break;
                    case R.id.rules:
                        transaction.replace(R.id.fragmentContainer, rulesFragment).commit();
                        break;
                    case R.id.prizes:
                        transaction.replace(R.id.fragmentContainer, prizesFragment).commit();
                        break;
                }
                return true;
            }
        });
    }
}