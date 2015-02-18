package com.example.adil.cookingtime;

/**
 * Created by Adil on 2/17/2015.
 */
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
//import android.app.DialogFragment;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;


public class myDialog extends DialogFragment {

    private onTimeListener callback;

    public interface onTimeListener{
        public void onTimeSubmit(int time);
    }

    LayoutInflater inflater;
    View v;
    NumberPicker hour;
    NumberPicker min;
    NumberPicker sec;
    int time;


    public void broadcastIntent(View view){
        Intent intent = new Intent();
        //intent.setAction(time);
        intent.setAction("time");
    }
    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState){
        inflater = getActivity().getLayoutInflater();
        v = inflater.inflate(R.layout.time_picker_dialog, null);
        hour = (NumberPicker) v.findViewById(R.id.mHourPicker);
        hour.setMaxValue(24);
        hour.setMinValue(0);
        hour.setWrapSelectorWheel(true);
        min = (NumberPicker) v.findViewById(R.id.mMinPicker);
        min.setMaxValue(60);
        min.setMinValue(0);
        min.setWrapSelectorWheel(true);
        sec = (NumberPicker) v.findViewById(R.id.mSecondPicker);
        sec.setMaxValue(90);
        sec.setMinValue(0);
        sec.setWrapSelectorWheel(true);

        try{
            callback = (onTimeListener) getTargetFragment();
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



                time  = hours * 3600000 + minutes * 60000 + seconds * 1000;
                callback.onTimeSubmit(time);


/*
                MainActivity main;
                main = (MainActivity)getActivity();
                main.time = time; */

                Toast.makeText(getActivity().getApplicationContext(), "Time: " + time,
                        Toast.LENGTH_LONG).show();

            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {



            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        return builder.create();
    }





}
