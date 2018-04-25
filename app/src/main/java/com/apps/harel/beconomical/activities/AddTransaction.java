package com.apps.harel.beconomical.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.apps.harel.beconomical.R;
import com.apps.harel.beconomical.fragments.ExpensesFragment;
import com.apps.harel.beconomical.fragments.IncomesFragment;


public class AddTransaction extends AppCompatActivity  {

    private Toolbar toolbar;

    private TabLayout tabLayout;

    private AppBarLayout appBarLayout;

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    private Intent dateIntent;

    private int whichPage;

    private FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);

        dateIntent = getIntent();

        appBarLayout = findViewById(R.id.appbar);
        appBarLayout.setBackgroundColor(getResources().getColor(R.color.green));

        toolbar = findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.green));

        tabLayout = findViewById(R.id.tabsLayout);
        tabLayout.setBackgroundColor(getResources().getColor(R.color.green));

        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        // Create the adapter that will return a fragment for each of the two
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container);

        mViewPager.setAdapter(mSectionsPagerAdapter);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        appBarLayout.setBackgroundColor(getResources().getColor(R.color.green));
                        toolbar.setBackgroundColor(getResources().getColor(R.color.green));
                        tabLayout.setBackgroundColor(getResources().getColor(R.color.green));
                        whichPage = 0;
                        break;
                    case 1:
                        appBarLayout.setBackgroundColor(getResources().getColor(R.color.red));
                        toolbar.setBackgroundColor(getResources().getColor(R.color.red));
                        tabLayout.setBackgroundColor(getResources().getColor(R.color.red));
                        whichPage = 1;
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        if (dateIntent.getIntExtra("fromPickDate",0) == 1){
            if (whichPage == 0) {
                String d = dateIntent.getStringExtra("data");
                Fragment fragment = fragmentManager.findFragmentByTag(mSectionsPagerAdapter.getFragmentTag(mViewPager.getId(), 0));
                TextView date = fragment.getView().findViewById(R.id.full_date);
                date.setText(d);
            }else {
                String d = dateIntent.getStringExtra("data");
                Fragment fragment2 = fragmentManager.findFragmentByTag(mSectionsPagerAdapter.getFragmentTag(mViewPager.getId(), 1));
                TextView date2 = fragment2.getView().findViewById(R.id.full_date);
                date2.setText(d);
            }


        }
    }



    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {


        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
            fragmentManager = fm;
        }

        private String getFragmentTag(int viewPagerId, int fragmentPosition) {
            return "android:switcher:" + viewPagerId + ":" + fragmentPosition;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new IncomesFragment();

                case 1:
                    return new ExpensesFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.transaction_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.save:
                if (whichPage == 0) {
                    Fragment fragment = fragmentManager.findFragmentByTag(mSectionsPagerAdapter.getFragmentTag(mViewPager.getId(), 0));
                    TextView amount = fragment.getView().findViewById(R.id.amount);
                    String check = amount.getText().toString();
                    if (check.isEmpty()) {
                        Toast.makeText(this, "You must fill Amount and Category", Toast.LENGTH_SHORT).show();
                    } else {
                        finish();
                    }

                } else {
                    Fragment fragment = fragmentManager.findFragmentByTag(mSectionsPagerAdapter.getFragmentTag(mViewPager.getId(), 1));
                    TextView amount = fragment.getView().findViewById(R.id.amount);
                    String check = amount.getText().toString();
                    if (check.isEmpty()) {
                        Toast.makeText(this, "You must fill Amount and Category", Toast.LENGTH_SHORT).show();
                    } else {
                        finish();
                    }
                }
                return true;

            default:
                break;

        }


        return super.onOptionsItemSelected(item);
    }


}
