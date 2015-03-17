package com.example.adil.cookingtime;

import android.app.Activity;
import android.app.Dialog;
import android.app.FragmentTransaction;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Adil on 2/17/2015.
 */
public class timer_fragment extends Fragment implements myDialog.onTimeListener {
    TextView clock;
    int time;
    Button startButton;

    Button stopButton;
    Button setTimeButton;

    Dialog custom;




    public static timer_fragment newInstance(){
        timer_fragment fragment = new timer_fragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_timer, container, false);



        //Start stop buttons
        startButton = (Button)rootView.findViewById(R.id.startButton);
        stopButton = (Button)rootView.findViewById(R.id.mStopButton);

        clock = (TextView)rootView.findViewById(R.id.mTextViewClock);

        setTimeButton = (Button)rootView.findViewById(R.id.mSetTimeButton);


/*
        setTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               timeOnClick(v);

            }
        });
*/
        setTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               onClickSetTime();
            }
        });

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MyCountDownTimer counter = new MyCountDownTimer(time,1000);

                clock.setText("00:00:00");
                counter.start();

                Toast.makeText(getActivity().getApplicationContext(), "Time: " + time,
                        Toast.LENGTH_LONG).show();


                stopButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        counter.cancel();

                        Toast.makeText(getActivity().getApplicationContext(), "Stop",
                                Toast.LENGTH_LONG).show();

                        clock.setText("00:00:00");
                    }
                });
            }
        });
        return rootView;

    }


    public void notification(int str){


        // 1. Get a reference to the NotificationManager
        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager mNotificationManager = (NotificationManager) getActivity().getSystemService(ns);

// 2. Instantiate the Notification
        int icon = R.drawable.ic_launcher;
        CharSequence tickerText = "Hello";
        long when = System.currentTimeMillis();
        Notification notification = new Notification(icon, tickerText, when);

// 3. Define the Notification's expanded message and Intent
        Context context = getActivity().getApplicationContext();
        CharSequence contentTitle = "Cooking Time";
        CharSequence contentText = "Done!";
        Intent notificationIntent = new Intent(getActivity(), timer_fragment.class);
        PendingIntent contentIntent = PendingIntent.getActivity(getActivity(), 0, notificationIntent, 0);
        notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);

// 4. Pass the Notification to the NotificationManager
        final int HELLO_ID = 1;
        mNotificationManager.notify(HELLO_ID, notification);


// ----------------------
//        Add Sound
// ----------------------
// a. Default sound
        notification.defaults |= Notification.DEFAULT_SOUND;


// ----------------------
//     Add Vibration
// ----------------------
// a. Default vibration
        // notification.defaults |= Notification.DEFAULT_VIBRATE;
        Vibrator v = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        //v.vibrate(500);

// b. Custom vibration
        long[] pattern = {0,100,200,300};
        v.vibrate(pattern, 0);

        if (str == 2){
            v.cancel();
        }


// ------------------------
//   Add Flashing Lights
// ------------------------
// a. Default lights
        notification.defaults |= Notification.DEFAULT_LIGHTS;

// b. Custom lights
       /*
       notification.ledARGB = 0xff00ff00;
       notification.ledOnMS = 300;
       notification.ledOffMS = 1000;
       notification.flags |= Notification.FLAG_SHOW_LIGHTS;  */

    }

    public void onClickSetTime(){
       // FragmentTransaction ft = getActivity().getFragmentManager().beginTransaction();
        FragmentManager fm = getActivity().getSupportFragmentManager();
        myDialog dialog1 = new myDialog();
        // dialog1.show(getSupportFragmentManager(), "my_dialog");
        //dialog1.show(ft, "acknowledgements");
        dialog1.setTargetFragment(this, 0);
        //dialog1.show(ft, "acknoledgments");
        dialog1.show(fm, "acknowledgements");


        Toast.makeText(getActivity().getApplicationContext(), "Time: " + time,
                Toast.LENGTH_LONG).show();


        //Log.i("tag", time);
    }

    @Override
    public void onTimeSubmit(int time) {
        this.time = time;
    }

    public class MyCountDownTimer extends CountDownTimer {

        //TextView clock;


        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public MyCountDownTimer(long millisInFuture, long countDownInterval) {

            super(millisInFuture, countDownInterval);

            //this.clock = clock;
        }

        @Override
        public void onTick(long millisUntilFinished) {


            int seconds = (int) (millisUntilFinished / 1000) % 60 ;
            int minutes = (int) ((millisUntilFinished / (1000*60)) % 60);
            int hours   = (int) ((millisUntilFinished / (1000*60*60)) % 24);
  /*          String secondsString = Double.toString(seconds);
            String minutesString = Double.toString(minutes);
            String hoursString = Double.toString(hours);
*/
            String hoursText = String.format("%02d", hours);
            String minutesText = String.format("%02d", minutes);
            String secondsText = String.format("%02d", seconds);
            clock.setText( hoursText + ":" + minutesText + ":" + secondsText);

        }

        @Override
        public void onFinish() {

            clock.setText("DONE!");
            notification(1);
            try {
                wait(4000);
            } catch (Exception ex){

            }

            notification(2);
            clock.setText("00:00:00");

        }


    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        ((MainActivity) activity).onSectionAttached(1);
    }

    public timer_fragment(){


    }
}
