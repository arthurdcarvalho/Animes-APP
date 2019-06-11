package br.com.arthur.appanimes.data.repository

import android.util.Log

class ConnectionException(val error: String) : Exception() {

    init {
        Log.v("Error ${ConnectionException::class.java.canonicalName}", error)
    }

}