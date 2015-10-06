package com.daviddetena.criminalintent;

import android.support.v4.app.Fragment;

public class CrimeListActivity extends SingleFragmentActivity{

    /**
     * Implements SingleFragmentActivity's abstract method createFragment to return an instance of
     * CrimeListFragment() to inflate the layout.
     * @return
     */
    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}