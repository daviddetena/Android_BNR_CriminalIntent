package com.daviddetena.criminalintent;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CrimeListFragment extends Fragment {

    // Recycler View
    private RecyclerView mCrimeRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Get the view from the fragment_crime_layout
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);

        // Get the recycler
        mCrimeRecyclerView = (RecyclerView) view.findViewById(R.id.crime_recycler_view);

        // Layout manager
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }
}
