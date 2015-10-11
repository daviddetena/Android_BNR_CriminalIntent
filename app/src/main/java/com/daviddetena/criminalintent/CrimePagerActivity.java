package com.daviddetena.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.List;
import java.util.UUID;

public class CrimePagerActivity extends FragmentActivity {

    // EXTRA for current CrimeFragment to display
    private static final String EXTRA_CRIME_ID = "com.daviddetena.criminalintent.crime_id";

    private ViewPager mViewPager;
    private List<Crime> mCrimes;

    /**
     * Static method to get an intent and pass an argument in to communicate with
     * other activities this one could be called from
     * @param packageContext
     * @param crimeId
     * @return
     */
    public static Intent newIntent(Context packageContext, UUID crimeId){

        // Create a new intent with the EXTRA param with the crimeId
        Intent intent = new Intent(packageContext, CrimeActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeId);

        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_pager);

        UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);

        // Wire up
        mViewPager = (ViewPager) findViewById(R.id.activity_crime_pager_view_pager);

        // Set up model
        mCrimes = CrimeLab.get(this).getCrimes();

        // Set up Fragment Manager and PagerAdapter via FragmentStatePagerAdapter inner class
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                // Get Crime at position i
                Crime crime = mCrimes.get(position);
                return CrimeFragment.newInstance(crime.getId());
            }

            @Override
            public int getCount() {
                return mCrimes.size();
            }
        });

        for (int i = 0; i < mCrimes.size(); i++) {
            if (mCrimes.get(i).getId().equals(crimeId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }


}
