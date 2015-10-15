package com.daviddetena.criminalintent;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

public class CrimeListFragment extends Fragment {

    // Recycler View and adapter for it
    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mAdapter;
    private boolean mSubtitleVisible;
    private static final String SAVED_SUBTITLE_VISIBLE = "subtitle";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Let the hosting Activity's Fragment Manager know that this Fragment needs to receive
        // menu callbacks
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Get the view from the fragment_crime_layout
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);

        // Get the recycler
        mCrimeRecyclerView = (RecyclerView) view.findViewById(R.id.crime_recycler_view);

        // Layout manager
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Get subtitle's visibility status from savedInstanceState
        if(savedInstanceState != null){
           mSubtitleVisible = savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE);
        }

        // Update UI
        updateUI();

        return view;
    }

    /**
     * Needs to update the interface when back to foreground
     */
    @Override
    public void onResume(){
        super.onResume();
        updateUI();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // Save subtitle's visibility in bundle to preserve when view re-created
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVED_SUBTITLE_VISIBLE, mSubtitleVisible);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        // Populate the Menu instance with the items defined in fragment_crime_list.xml menu
        inflater.inflate(R.menu.fragment_crime_list, menu);

        // Get "show subtitle" menu option
        MenuItem subtitleItem = menu.findItem(R.id.menu_item_show_subtitle);
        // Toggle label of the Menu Action in relation of the current visibility
        if(mSubtitleVisible){
            subtitleItem.setTitle(R.string.hide_subtitle);
        }
        else{
            subtitleItem.setTitle(R.string.show_subtitle);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Process to be done regarding the item selected
        switch(item.getItemId()){

            // Create new Crime, add it to the list and present this Crime with ViewPager
            case R.id.menu_item_new_crime:

                Crime crime = new Crime();
                CrimeLab.get(getActivity()).addCrime(crime);
                Intent intent = CrimePagerActivity.newIntent(getActivity(), crime.getId());
                startActivity(intent);
                return true;

            // Toggle toolbar's subtitle visibility with the crime counter
            case R.id.menu_item_show_subtitle:
                mSubtitleVisible = !mSubtitleVisible;
                getActivity().invalidateOptionsMenu();
                updateSubtitle();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    /**
     * Update the toolbar's subtitle with the number of crimes, in relation to its visibility
     */
    private void updateSubtitle(){
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        int crimeCount = crimeLab.getCrimes().size();

        // Get subtitle label from string resources
        String subtitle = getString(R.string.subtitle_format, crimeCount);

        if(!mSubtitleVisible){
            subtitle = null;
        }

        // And set as Toolbar's subtitle
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(subtitle);
    }

    /**
     * Update UI by creating an instance of crimeLab and passing it in to the CrimeAdapter
     * constructor
     */
    private void updateUI(){

        // Creates the list of models (CrimeLab)
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();

        // Check if adapter already exists
        if(mAdapter == null){
            // Assign them to the CrimeAdapter's constructor
            mAdapter = new CrimeAdapter(crimes);

            // Set RecyclerView's adapter
            mCrimeRecyclerView.setAdapter(mAdapter);
        }
        else{
            // Already exists => notify changes
            mAdapter.notifyDataSetChanged();
        }

        updateSubtitle();
    }


    /**
     * Inner class to hold the View for each row in the list of crimes. Now the itemView is a view
     * inflated with the list_item_crime layout
     */
    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        // Widgets in the View
        public TextView mTitleTextView;
        public TextView mDateTextView;
        public CheckBox mSolvedCheckbox;

        // Model
        private Crime mCrime;

        /**
         * Define a CrimeHolder with the widgets from the list_item_crime layout
         * @param itemView
         */
        public CrimeHolder(View itemView){
            super(itemView);
            // Set this class as the listener for the view, that is, the CrimeHolder is set as the
            // receiver of click events for itemView, which is the View for the entire row
            itemView.setOnClickListener(this);

            // Get the widgets from the view
            mTitleTextView = (TextView) itemView.findViewById(R.id.list_item_crime_title_text_view);
            mDateTextView = (TextView) itemView.findViewById(R.id.list_item_crime_date_text_view);
            mSolvedCheckbox = (CheckBox) itemView.findViewById(R.id.list_item_crime_solved_check_box);
        }

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
         * Method executed when tapping on a item. We make CrimePagerActivity
         * @param v
         */
        @Override
        public void onClick(View v) {
            // New intent from an static method in CrimePagerActivity that returns a new
            // Intent with a context and the crimeID
            Intent intent = CrimePagerActivity.newIntent(getActivity(), mCrime.getId());
            startActivity(intent);
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
