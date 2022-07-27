/*
 * Created by FikiCarDev
 * Copyright (c) 2022. All rights reserved.
 */

package com.fikicar.asyncexampleapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.fikicar.asyncexecution.AsyncTask;

public class MainActivity extends AppCompatActivity {

    private TextView tvLabel;
    private Button btRunDemo;
    private Button btOpenKotlinDemo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvLabel = findViewById(R.id.tvLabel);
        btRunDemo = findViewById(R.id.btRunDemo);
        btOpenKotlinDemo = findViewById(R.id.btOpenKotlin);

        btRunDemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RunDemo runDemo = new RunDemo();
                runDemo.execute("Value from Background");
            }
        });

        btOpenKotlinDemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenKotlinDemo();
            }
        });
    }

    private class RunDemo extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            tvLabel.setText("PreExecuted");
        }

        @Override
        protected String doInBackground(String msg) throws Exception {
            SystemClock.sleep(2000);
            return msg;
        }

        @Override
        protected void onPostExecute(String msg) {
            StringBuilder tmp = new StringBuilder(tvLabel.getText());
            tmp.append(" ").append(msg).append(" OnPostExecute");
            tvLabel.setText(tmp);
        }

        @Override
        protected void onBackgroundError(Exception e) {
            e.printStackTrace();
        }
    }

    private void OpenKotlinDemo() {
        Intent intent = new Intent(this, MainActivityKotlin.class);
        startActivity(intent);
    }
}