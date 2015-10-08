package com.daviddetena.criminalintent;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CrimeLab {

    // Singleton
    private static CrimeLab sCrimeLab;

    // List of Crimes
    private List<Crime> mCrimes;

    /**
     * Static method to get the singleton of CrimeLab
     * @param context
     * @return
     */
    public static CrimeLab get(Context context){
        if(sCrimeLab == null){
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    /**
     * Private constructor to hold the singleton. Init the list of crimes
     * @param context
     */
    private CrimeLab(Context context){
        mCrimes = new ArrayList<>();

        for(int i=0; i<100; i++){
            Crime crime = new Crime();
            String title = String.format("Crime #%d", i);
            crime.setTitle(title);
            crime.setSolved(i % 2 == 0);    // Set 1 of each 2 as solved by default
            mCrimes.add(crime);
        }
    }


    /**
     * Get the list of crimes
     * @return
     */
    public List<Crime> getCrimes(){
        return mCrimes;
    }

    /**
     * Return the Crime with the given id which is in the list
     * @param id
     * @return
     */
    public Crime getCrime(UUID id){
        // Search for the Crime with the given id
        for(Crime crime: mCrimes){
            if(crime.getId().equals(id)){
                return crime;
            }
        }
        return null;
    }
}
