package com.adilpatel.adil.cookingtime;

import android.app.AlertDialog;
import android.app.Dialog;
//import android.app.DialogFragment;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

/**
 * Created by Adil on 2/19/2015.
 */
public class dialog_foods extends DialogFragment {

    private foodsDialogListener callback;

    public interface foodsDialogListener{
        public void nameTimeSubmit(int time, String name);
    }


    LayoutInflater inflater;
    View v;
    NumberPicker hour;
    NumberPicker min;
    NumberPicker sec;
    int time;
    EditText nameField;
    String name;

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState){
        inflater = getActivity().getLayoutInflater();
        v = inflater.inflate(R.layout.fragment_add_food, null);
        hour = (NumberPicker) v.findViewById(R.id.mHoursNumberPickerFoods);
        hour.setMaxValue(24);
        hour.setMinValue(0);
        hour.setWrapSelectorWheel(true);
        min = (NumberPicker) v.findViewById(R.id.mMinutesNumberPickerFoods);
        min.setMaxValue(60);
        min.setMinValue(0);
        min.setWrapSelectorWheel(true);
        sec = (NumberPicker) v.findViewById(R.id.mSecondsNumberPickerFoods);
        sec.setMaxValue(90);
        sec.setMinValue(0);
        sec.setWrapSelectorWheel(true);
        nameField = (EditText) v.findViewById(R.id.mNameEditText);

        try{
            callback = (foodsDialogListener) getTargetFragment();
        } catch (ClassCastException e){
            throw new ClassCastException("calling Fragment must implement onTimeListener");
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setView(v).setPositiveButton("OK", new DialogInterface.OnClickListener() {



            @Override
            public void onClick(DialogInterface dialog, int which) {


                int hours = hour.getValue();
                int minutes = min.getValue();
                int seconds = sec.getValue();

                name =  nameField.getText().toString();

                time  = hours * 3600000 + minutes * 60000 + seconds * 1000;
                callback.nameTimeSubmit(time, name);


/*
                MainActivity main;
                main = (MainActivity)getActivity();
                main.time = time; */

              //  Toast.makeText(getActivity().getApplicationContext(), "Time: " + time,
                //        Toast.LENGTH_LONG).show();

            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {



            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        return builder.create();
    }
}
