package com.example.rohit.bathscheduler;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Rohit on 11/12/2015.
 */
public class FinalPhase extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.final_phase);
        TextView textView = (TextView)findViewById(R.id.textView);
        textView.setText("Final Phase babe !!!");
    }
}
