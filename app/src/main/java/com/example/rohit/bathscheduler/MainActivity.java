package com.example.rohit.bathscheduler;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import static android.widget.Toast.LENGTH_LONG;

public class MainActivity extends AppCompatActivity {

    Button button;
    TextView textView;
    AlarmManager alarmManager;
    Context context;
    int seconds = 9;
    int minutes;
    int phase1Minutes;
    int phase2Minutes;
    int phase3Minutes;
    int timeToStartBath = 6000;
    final String TAG  = "MainActivity";
    Intent intent;
    CountDownTimer timer1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(getResources().getColor(android.R.color.background_dark));
        setSupportActionBar(toolbar);
        getWindow().getDecorView().setBackgroundColor(Color.DKGRAY);

        final Calendar calendar = Calendar.getInstance();
     //   calendar.add(Calendar.MINUTE, 1);
        calendar.add(Calendar.SECOND, 4); /// error of 4 secs.
      //  calendar.set(Calendar.MINUTE,1);
        context = getApplicationContext();
        textView = (TextView) findViewById(R.id.textView);
        textView.setVisibility(View.INVISIBLE);
        button = (Button) findViewById(R.id.button);
        final Button cancelButton = (Button) findViewById(R.id.cancelButton);
        final TextView phase1text = (TextView)findViewById(R.id.phase1Text);
        final TextView phase2text = (TextView)findViewById(R.id.phase2Text);
        final TextView phase3text = (TextView)findViewById(R.id.phase3Text);

        phase1text.setHint("2");
        phase2text.setHint("1");
        phase3text.setHint("2");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  seconds = Integer.parseInt((String) secondsText.getText());
                phase1Minutes = Integer.parseInt((phase1text.getText()).toString());
                phase2Minutes = Integer.parseInt((phase2text.getText()).toString());
                phase3Minutes = Integer.parseInt((phase3text.getText()).toString());

              //  seconds = seconds * 1000;
                minutes = phase1Minutes * 60 * 1000;
                Log.i("minutes", String.valueOf(minutes));

                intent = new Intent(MainActivity.this, Phase1.class);
                Log.i(TAG+"phase2Minutes",String.valueOf(phase2Minutes));

                intent.putExtra("phase1Minutes", phase1Minutes * 60 * 1000); // to convert input number to minutes 1 = 1 * 1000 = 1000 millisec = 1 sec *60 = 1 minute.
                intent.putExtra("phase2Minutes", phase2Minutes * 60 * 1000);
                intent.putExtra("phase3Minutes", phase3Minutes * 60 * 1000);

             //   PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
             //   AlarmManager am = (AlarmManager)getSystemService(Activity.ALARM_SERVICE);
                                // 2 * 60 * 1000; // 2 minutes.
               // am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);               60 * 1000;
           //     am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+7000, pendingIntent); // 5 secs. 5 * 1000
             //   am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+seconds + minutes, pendingIntent); // 5 secs. 5 * 1000
             //   am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + minutes, pendingIntent); // 5 secs. 5 * 1000

                timer1 =  new CountDownTimer(7000, 1000) { // for 7 secs.
               // new CountDownTimer(timeToStartBath, 1000) { // for 3 secs.
               // new CountDownTimer(minutes, 1000) { // for 3 secs.
                    @Override
                    public void onTick(long millisUntilFinished) {
                        if((millisUntilFinished/1000)<=5) // countdown 5 4 3 2 1.
                        {
                            textView.setVisibility(View.VISIBLE);
                            textView.setText("Start shower in.. " + String.valueOf(millisUntilFinished / 1000));
                            Log.i("timer", String.valueOf(millisUntilFinished / 1000));

                            cancelButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    timer1.cancel();
                                    textView.setText("Bath cancelled.. !!");
                                }
                            });

                        }
                    }

                    @Override
                    public void onFinish() {
                        textView.setVisibility(View.INVISIBLE);
                        startActivity(intent);
                    }
                }.start();
            }
        });


    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
