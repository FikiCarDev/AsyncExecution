/*
 * Created by FikiCarDev
 * Copyright (c) 2022. All rights reserved.
 */

package com.fikicar.asyncexampleapp

import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.fikicar.asyncexecution.AsyncTask

class MainActivityKotlin : AppCompatActivity() {
    private var tvLabel: TextView? = null
    private var btRunDemo: Button? = null
    private var btOpenJavaDemo: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_kotlin)

        tvLabel = findViewById(R.id.tvLabel)
        btRunDemo = findViewById(R.id.btRunDemo)
        btOpenJavaDemo = findViewById(R.id.btOpenJava)

        btRunDemo!!.setOnClickListener{
            val runDemo: RunDemo = RunDemo()
            runDemo.execute("Value from Background")
        }

        btOpenJavaDemo!!.setOnClickListener {
            openJavaDemo()
        }
    }

    private inner class RunDemo : AsyncTask<String?, Void?, String?>() {

        override fun onPreExecute() {
            tvLabel!!.text = "PreExecuted"
        }

        override fun doInBackground(msg: String?): String? {
            SystemClock.sleep(2000)
            return msg
        }

        override fun onPostExecute(msg: String?) {
            val tmp = StringBuilder(tvLabel!!.text)
            tmp.append(" ").append(msg).append(" OnPostExecute")
            tvLabel!!.text = tmp
        }

        override fun onBackgroundError(e: Exception?) {
            e!!.printStackTrace()
        }
    }

    private fun openJavaDemo() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}