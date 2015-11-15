package com.example.rohit.bathscheduler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

/**
 * Created by Rohit on 10/30/2015.
 */

public class Phase1 extends AppCompatActivity {


    private String TAG = getClass().getName();
    private MediaPlayer mMediaPlayer;
    CountDownTimer  timer1, timer2,timer3;
    int flag1=0,flag2=0,flag3=0;
    Integer phase1Minutes, phase2Minutes, phase3Minutes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        final Intent intent1 = new Intent(Phase1.this, MainActivity.class);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.phase1);

   //     Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setBackgroundColor(getResources().getColor(android.R.color.background_dark));
    //    setSupportActionBar(toolbar);
        getWindow().getDecorView().setBackgroundColor(Color.DKGRAY);

        final TextView phaseTitle = (TextView)findViewById(R.id.PhaseTitle);
        final TextView timerText = (TextView)findViewById(R.id.timerText);
        final Button cancelButton = (Button)findViewById(R.id.cancelButton);

        phase1Minutes = getIntent().getExtras().getInt("phase1Minutes");
        phase2Minutes = getIntent().getExtras().getInt("phase2Minutes");
        phase3Minutes = getIntent().getExtras().getInt("phase3Minutes");


        Log.i(TAG+ "passed intent 1", phase1Minutes+" minutes");
        Log.i(TAG+ "passed intent 2", phase2Minutes+" minutes");
        Log.i(TAG + "passed intent 3", phase3Minutes+ " minutes");

        //Toast.makeText(getApplicationContext(), (phase2Text), Toast.LENGTH_SHORT).show();
        //Toast.makeText(getApplicationContext(), "hello", Toast.LENGTH_LONG).show();
        final Vibrator v = (Vibrator) this.getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        v.vibrate(1000);



        //playSound(this, getAlarmUri());

//        final Handler handler = new Handler(); // to make alarm ring only for 3 secs.
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                //Do something after 100ms
//                mMediaPlayer.stop();
//                Intent intent = new Intent(getApplicationContext(), FinalPhase.class);
//                startActivity(intent);
//            }
//        }, 3000); /// 1000 =  1 sec.

         //   timer1 =    new CountDownTimer(5000, 1000){
        timer1 =    new CountDownTimer(phase1Minutes, 1000){

            @Override
            public void onTick(long millisUntilFinished) {

                flag1 = 1;
                phaseTitle.setText("Phase 1 : Shower.. !!");
                timerText.setText("timer : " + String.valueOf(millisUntilFinished / 1000));

//                cancelButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        //Context context = getApplicationContext();
//                        startActivity(intent1);
//                        finish();
//                        timer1.cancel();
//                      //  onBackPressed();
//                    }
//                });

                if((millisUntilFinished / 1000)==1) {
                    playSound(getApplicationContext(), getAlarmUri());
                    v.vibrate(1000);
                }
            }

            @Override
            public void onFinish() {
                //Intent intent = new Intent(getApplicationContext(), FinalPhase.class);
                //startActivity(intent);
                mMediaPlayer.stop();
                //new CountDownTimer(5000,1000){
              timer2 = new CountDownTimer(phase2Minutes,1000){

                    @Override
                    public void onTick(long millisUntilFinished) {
                        flag1 = 0;
                        flag2 = 1;
                        phaseTitle.setText("Phase 2 : Soap.. !!");
                        timerText.setText("timer : " + String.valueOf(millisUntilFinished / 1000));

                        if((millisUntilFinished / 1000)==1) {
                            playSound(getApplicationContext(), getAlarmUri());
                            v.vibrate(1000);
                        }
                    }

                    @Override
                    public void onFinish() {
                        mMediaPlayer.stop();
                        //new CountDownTimer(5000,1000){
                     timer3 =  new CountDownTimer(phase3Minutes,1000){
                            @Override
                            public void onTick(long millisUntilFinished) {
                                flag2 = 0;
                                flag3 = 1;

                                phaseTitle.setText("Phase 3 : Shower.. !!");
                                timerText.setText("timer : " + String.valueOf(millisUntilFinished / 1000));

                                if((millisUntilFinished / 1000)==1) {
                                    playSound(getApplicationContext(), getAlarmUri());
                                    v.vibrate(1000);
                                }
                            }

                            @Override
                            public void onFinish() {
                                mMediaPlayer.stop();
                                phaseTitle.setText("Phase 4 : Time to leave.. !!");

                                startActivity(intent1);
                            }
                        }.start();
                    }
                }.start();
            }
        }.start();

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Context context = getApplicationContext();
                if(flag1 == 1)
                timer1.cancel();

                if(flag2 == 1)
                    timer2.cancel();

                if(flag3 == 3)
                    timer3.cancel();

//                timer2.cancel();
//                timer3.cancel();
                startActivity(intent1);
                finish();
                onBackPressed();

            }
        });
    }

    private void playSound(Context context, Uri alert) {
        mMediaPlayer = new MediaPlayer();
        try {
            mMediaPlayer.setDataSource(context, alert);
            final AudioManager audioManager = (AudioManager) context
                    .getSystemService(Context.AUDIO_SERVICE);
            if (audioManager.getStreamVolume(AudioManager.STREAM_ALARM) != 0) {
                mMediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
                mMediaPlayer.prepare();
                mMediaPlayer.start();
            }
        } catch (IOException e) {
            System.out.println("OOPS");
        }
    }

    //Get an alarm sound. Try for an alarm. If none set, try notification,
    //Otherwise, ringtone.
    private Uri getAlarmUri() {
        Uri alert = RingtoneManager
                .getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alert == null) {
            alert = RingtoneManager
                    .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            if (alert == null) {
                alert = RingtoneManager
                        .getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            }
        }
        return alert;
    }


}




