package com.daviddetena.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.UUID;

public class CrimeActivity extends SingleFragmentActivity {

    // Extra to the crime id used as a param to passing data through activities
    private static final String EXTRA_CRIME_ID = "com.daviddetena.criminalintent.crime_id";

    /**
     * Implements SingleFragmentActivity's abstract method createFragment to return an instance of
     * CrimeFragment() to inflate the layout.
     * @return
     */
    @Override
    protected Fragment createFragment() {

        // Get crimeId from Intent extra an passing in to get a CrimeFragment instance
        UUID crimeId = (UUID)getIntent().getSerializableExtra(EXTRA_CRIME_ID);
        return CrimeFragment.newInstance(crimeId);
    }


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
}
