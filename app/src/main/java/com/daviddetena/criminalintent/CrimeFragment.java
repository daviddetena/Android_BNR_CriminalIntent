package com.daviddetena.criminalintent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.util.Date;
import java.util.UUID;

public class CrimeFragment extends Fragment{

    // Keys for arguments
    private static final String ARG_CRIME_ID = "crime_id";
    private static final String DIALOG_DATE = "DialogDate";

    // Result for checking Fragment sender code
    private static final int REQUEST_DATE = 0;

    // Model
    private Crime mCrime;

    // Views
    private EditText mTitleField;
    private Button mDateButton;
    private CheckBox mSolvedCheckbox;

    /**
     * Get a CrimeFragment object with arguments from a parameter passed in
     * @param crimeId
     * @return
     */
    public static CrimeFragment newInstance(UUID crimeId){

        // Create a new Bundle for arguments
        Bundle args = new Bundle();
        args.putSerializable(ARG_CRIME_ID, crimeId);

        // Create a new instance of CrimeFragment and set arguments
        CrimeFragment fragment = new CrimeFragment();
        fragment.setArguments(args);

        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get crimeId from its arguments
        UUID crimeId = (UUID) getArguments().getSerializable(ARG_CRIME_ID);

        // Assign mCrime to be the one by the given crimeId
        mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate menu from the fragment_crime layout
        View v = inflater.inflate(R.layout.fragment_crime, container, false);

        // Wire up EditText
        mTitleField = (EditText) v.findViewById(R.id.crime_title);
        mTitleField.setText(mCrime.getTitle());

        // Set listener for the edit text when text changed
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Save the typed text to our crime title
                mCrime.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        // Wire up Date button
        mDateButton = (Button) v.findViewById(R.id.crime_date);
        updateDate();

        // Set a listener on click on dateButton to display a DatePickerFragment dialog
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start a new instance of DatePickerFragment with the date of the current crime
                FragmentManager fragmentManager = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(mCrime.getDate());
                // Set this class as the target to receive data from
                dialog.setTargetFragment(CrimeFragment.this, REQUEST_DATE);
                dialog.show(fragmentManager, DIALOG_DATE);
            }
        });


        // Wire up Solved Checkbox
        mSolvedCheckbox = (CheckBox) v.findViewById(R.id.crime_solved);
        mSolvedCheckbox.setChecked(mCrime.isSolved());

        // Listener for Checkbox toggling
        mSolvedCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Set the crime's solved property
                mCrime.setSolved(isChecked);
                //Log.i("hello", String.format("%b",mCrime.isSolved()));
            }
        });

        return v;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != Activity.RESULT_OK){
            return;
        }

        // Get date selected from the DatePickerFragment, sent as EXTRA, and update the Crime's date
        // with it
        if(requestCode == REQUEST_DATE){
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mCrime.setDate(date);
            updateDate();
        }
    }

    /**
     * Utility method to update UI date button with the Crime's date
     */
    private void updateDate() {
        // Set Crime's date as button default date
        mDateButton.setText(mCrime.getDate().toString());
    }
}
