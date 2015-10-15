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


    /**
     * Adds a new Crime to the list
     * @param c
     */
    public void addCrime(Crime c){
        mCrimes.add(c);
    }


    /**
     * Removes a Crime object by its given id
     * @param id
     */
    public void deleteCrime(UUID id){
        // Search for the Crime with the given id and delete it
        for(Crime crime: mCrimes){
            if(crime.getId().equals(id)){
                mCrimes.remove(crime);
                return;
            }
        }
    }
}
