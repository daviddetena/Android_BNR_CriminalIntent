package com.daviddetena.criminalintent;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

public class CrimeListFragment extends Fragment {

    // Recycler View and adapter for it
    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Get the view from the fragment_crime_layout
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);

        // Get the recycler
        mCrimeRecyclerView = (RecyclerView) view.findViewById(R.id.crime_recycler_view);

        // Layout manager
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Update UI
        updateUI();

        return view;
    }

    /**
     * Update UI by creating an instance of crimeLab and passing it in to the CrimeAdapter
     * constructor
     */
    private void updateUI(){

        // Creates the list of models (CrimeLab)
        CrimeLab crimeLab = CrimeLab.getCrimeLab(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();

        // Assign them to the CrimeAdapter's constructor
        mAdapter = new CrimeAdapter(crimes);

        // Set RecyclerView's adapter
        mCrimeRecyclerView.setAdapter(mAdapter);
    }


    /**
     * Inner class to hold the View for each row in the list of crimes. Now the itemView is a view
     * inflated with the list_item_crime layout
     */
    private class CrimeHolder extends RecyclerView.ViewHolder{

        // Widgets in the View
        public TextView mTitleTextView;
        public TextView mDateTextView;
        public CheckBox mSolvedCheckbox;

        // Model
        private Crime mCrime;


        /**
         * This method wires up the widgets from model data
         * @param crime
         */
        public void bindCrime(Crime crime){
            mCrime = crime;
            mTitleTextView.setText(mCrime.getTitle());
            mDateTextView.setText(mCrime.getDate().toString());
            mSolvedCheckbox.setChecked(mCrime.isSolved());
        }

        /**
         * Define a CrimeHolder with the widgets from the list_item_crime layout
         * @param itemView
         */
        public CrimeHolder(View itemView){
            super(itemView);

            // Get the widgets from the view
            mTitleTextView = (TextView) itemView.findViewById(R.id.list_item_crime_title_text_view);
            mDateTextView = (TextView) itemView.findViewById(R.id.list_item_crime_date_text_view);
            mSolvedCheckbox = (CheckBox) itemView.findViewById(R.id.list_item_crime_solved_check_box);
        }
    }

    /**
     * The RecyclerView will communicate with this adapter when a ViewHolder needs to be created or
     * connected with a Crime object.
     */
    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder>{

        private List<Crime> mCrimes;

        public CrimeAdapter(List<Crime> crimes){
            mCrimes = crimes;
        }

        /**
         * Inflates view from Android predefined list item layout and wrap it in the CrimeHolder
         * ViewHolder
         * @param parent
         * @param viewType
         * @return CrimeHolder instance with a view inflated with list_item_crime layout
         */
        @Override
        public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_crime, parent, false);

            return new CrimeHolder(view);
        }

        /**
         * Binds the CrimeHolder's View to the item in the <position> position of the list of crimes
         * from the model
         * @param crimeHolder
         * @param position
         */
        @Override
        public void onBindViewHolder(CrimeHolder crimeHolder, int position) {
            Crime crime = mCrimes.get(position);
            crimeHolder.bindCrime(crime);
        }

        @Override
        public int getItemCount() {
            return mCrimes.size();
        }
    }
}
