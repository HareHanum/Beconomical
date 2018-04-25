package com.apps.harel.beconomical.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.applandeo.materialcalendarview.EventDay;
import com.apps.harel.beconomical.R;
import com.apps.harel.beconomical.fragments.CalendarFragment;
import com.apps.harel.beconomical.fragments.HomeFragment;
import com.apps.harel.beconomical.fragments.PickDateFragment;
import com.apps.harel.beconomical.fragments.RemindersFragment;

public class MainActivity extends AppCompatActivity implements PickDateFragment.GetEvent {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        switchToHomeFragment();


        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    switchToHomeFragment();

                    return true;
                case R.id.navigation_dashboard:
                    switchToCalendarFragment();

                    return true;
                case R.id.navigation_notifications:
                    switchToRemindersFragment();

                    return true;
            }
            return false;
        }
    };

    public void switchToHomeFragment() {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragment_container, new HomeFragment(), "home").commit();
    }

    public void switchToCalendarFragment() {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragment_container, new CalendarFragment(), "calendar").commit();
    }

    public void switchToRemindersFragment() {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragment_container, new RemindersFragment(), "reminders").commit();
    }


    @Override
    public void getEventDay(EventDay eventDay) {
        CalendarFragment frag = (CalendarFragment) getSupportFragmentManager().findFragmentByTag("calendar");
        frag.getEventDay(eventDay);

    }
}
