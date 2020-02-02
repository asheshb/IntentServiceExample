package com.example.intentserviceexample

import android.app.IntentService
import android.content.Context
import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import java.io.File

class MyIntentService : IntentService("MyIntentService") {


    override fun onHandleIntent(intent: Intent?) {
        // Background thread
        if(intent?.action == ACTION_DOWNLOAD_URL){
            val url = intent.dataString
            val fileName = intent.getStringExtra(FILE_NAME_KEY)
            if(url != null && fileName != null){
                val content = getDataFromNetwork(url)
                File(this.filesDir, fileName).writeText(content)
                Intent(ACTION_DOWNLOAD_URL).also {
                    it.putExtra(FILE_NAME_KEY, fileName)
                    LocalBroadcastManager.getInstance(this).sendBroadcast(it)
                }
            }

        }

    }
}