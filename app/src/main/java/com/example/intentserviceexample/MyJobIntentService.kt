package com.example.intentserviceexample

import android.content.Context
import android.content.Intent
import androidx.core.app.JobIntentService
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import java.io.File


class MyJobIntentService : JobIntentService()   {

    companion object{
        private const val JOB_ID = 1000

        fun enqueueWork(context: Context, work: Intent) {
            enqueueWork(context, MyJobIntentService::class.java, JOB_ID, work)
        }
    }

    override fun onHandleWork(intent: Intent) {
        if(intent.action == ACTION_DOWNLOAD_URL){
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