package com.daviddetena.criminalintent;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DatePickerFragment extends DialogFragment {

    // ARG in Bundle to get data from
    private static final String ARG_DATE = "date";
    // EXTRA to put data in an Intent to send back to the target fragment
    public static final String EXTRA_DATE = "com.daviddetena.android.criminalintent.date";

    // DatePicker to save what user selects
    private DatePicker mDatePicker;

    /**
     * Method from DialogFragment class that is called when putting DialogFragment on screen
     * @param savedInstanceState
     * @return
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Get current crime's Date from the ARGS and create a calendar with it
        Date date = (Date) getArguments().getSerializable(ARG_DATE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Set a view with the layout inflated from dialog_date layout and assign to AlertDialog's
        // view. Create a DatePicker with the crime's date and init it
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_date, null);
        mDatePicker = (DatePicker) view.findViewById(R.id.dialog_date_date_picker);
        mDatePicker.init(year, month, day, null);

        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle(R.string.date_picker_title)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int year = mDatePicker.getYear();
                        int month = mDatePicker.getMonth();
                        int day = mDatePicker.getDayOfMonth();

                        // Compose a date from the integers of year, month, and day selected
                        // in the datepicker
                        Date date = new GregorianCalendar(year, month, day).getTime();
                        sendResult(Activity.RESULT_OK, date);
                    }
                })
                .create();
    }


    /**
     * Get a DatePickerFragment object with arguments from a parameter passed in
     * @param date
     * @return
     */
    public static DatePickerFragment newInstance(Date date){
        // Create a new Bundle for arguments
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATE, date);

        // Create a new instance of DatePickerFragment and return it
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }


    /**
     * Method to send data back as an EXTRA to target fragment
     * @param resultCode
     * @param date
     */
    private void sendResult(int resultCode, Date date){
        if(getTargetFragment() == null){
            return;
        }
        // Creates an intent to send data back to target fragment
        Intent intent = new Intent();
        intent.putExtra(EXTRA_DATE, date);

        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
}
