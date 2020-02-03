package com.example.intentserviceexample

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File


const val ACTION_DOWNLOAD_URL = "ACTION_DOWNLOAD_URL"
const val FILE_NAME_KEY = "FILE_NAME_KEY"


class MainActivity : AppCompatActivity() {

    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(contxt: Context?, intent: Intent?) {
            when (intent?.action) {
                ACTION_DOWNLOAD_URL -> {
                    val fileName = intent.getStringExtra(FILE_NAME_KEY)
                    fileName?.let{
                        text_data.text = File(this@MainActivity.filesDir, fileName).readText()
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        LocalBroadcastManager.getInstance(this)
            .registerReceiver(broadcastReceiver, IntentFilter(ACTION_DOWNLOAD_URL))

        download_google.setOnClickListener{
            startIntentService("https://www.google.com/", "google.txt")
        }

        download_facebook.setOnClickListener{
            startIntentService("https://www.facebook.com/", "facebook.txt")
        }

    }

    private fun startIntentService(url: String, fileName: String){
        Intent(this, MyIntentService::class.java).apply {
            action = ACTION_DOWNLOAD_URL
            data = Uri.parse(url)
            putExtra(FILE_NAME_KEY, fileName)
            startService(this)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        LocalBroadcastManager.getInstance(this)
            .unregisterReceiver(broadcastReceiver)
    }

}
