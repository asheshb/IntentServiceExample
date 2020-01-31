package com.example.intentserviceexample

import java.net.HttpURLConnection
import java.net.URL

//A crude way to fetch data from network. Don;t use in production code.
fun getDataFromNetwork(url: String): String{
    val urlConnection = URL(url).openConnection()
            as HttpURLConnection
    try {
        return urlConnection.inputStream.bufferedReader().readText()
    } finally {
        urlConnection.disconnect()
    }
}