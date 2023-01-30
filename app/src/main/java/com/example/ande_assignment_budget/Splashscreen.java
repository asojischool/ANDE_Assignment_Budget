package com.example.ande_assignment_budget;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public class Splashscreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Launch the layout -> splash.xml
        setContentView(R.layout.splash);
        Thread splashThread = new Thread() {

            public void run() {
                try {

                    // to skip splashscreen, change skip to true
                    boolean skip =  true;
                    if(!skip) {
                        sleep(4000);
                    }

                }  catch(InterruptedException e) {
                    // Trace the error
                    e.printStackTrace();
                } finally
                {
                    // Change the string to target any activity on start up
                    Class targetActivity = null;
                    try {
                        targetActivity = Class.forName("com.example.ande_assignment_budget.MainActivity");
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                    Intent i = new Intent(Splashscreen.this, targetActivity);
                    startActivity(i);
                    finish();
                }

            }
        };
        // To Start the thread
        splashThread.start();
    }
}