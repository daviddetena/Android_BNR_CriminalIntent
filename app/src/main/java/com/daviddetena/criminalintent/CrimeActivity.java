package com.daviddetena.criminalintent;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class CrimeActivity extends SingleFragmentActivity {

    /**
     * Implements SingleFragmentActivity's abstract method createFragment to return an instance of
     * CrimeFragment() to inflate the layout.
     * @return
     */
    @Override
    protected Fragment createFragment() {
        return new CrimeFragment();
    }
}
