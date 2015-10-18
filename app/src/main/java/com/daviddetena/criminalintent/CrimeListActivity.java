package com.daviddetena.criminalintent;

import android.content.Intent;
import android.support.v4.app.Fragment;

public class CrimeListActivity extends SingleFragmentActivity
        implements CrimeListFragment.Callbacks, CrimeFragment.Callbacks{

    /**
     * Implements SingleFragmentActivity's abstract method createFragment to return an instance of
     * CrimeListFragment() to inflate the layout.
     * @return
     */
    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }

    /**
     * Gets the ID for layout. Default layout will be loaded, and two_panes will for tablets
     * @return
     */
    @Override
    protected int getLayoutResId(){
        return R.layout.activity_masterdetail;
    }

    /**
     * Method from CrimeListFragment.callbacks interface executed when a crime is selected
     * @param crime
     */
    @Override
    public void onCrimeSelected(Crime crime) {

        // If no detail view (phones) => start CrimePagerActivity
        if(findViewById(R.id.detail_fragment_container) == null){
            Intent intent = CrimePagerActivity.newIntent(this, crime.getId());
            startActivity(intent);
        }
        else{
            // Tablets (detail view does exist) => replace current detail view with new one
            Fragment newDetail = CrimeFragment.newInstance(crime.getId());
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.detail_fragment_container, newDetail)
                    .commit();
        }
    }

    /**
     * When crime updated, get the CrimeListFragment to refresh its UI for changes to appear.
     * @param crime
     */
    @Override
    public void onCrimeUpdated(Crime crime) {
        CrimeListFragment listFragment = (CrimeListFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);
        listFragment.updateUI();
    }
}
