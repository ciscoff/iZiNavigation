package s.yarlykov.izinavigation.utils

import android.util.Log

fun logIt(message: String, stamp: Boolean = false, tag: String = "APP_TAG") {

    val sz = if (stamp) {
        message + ", time=${System.currentTimeMillis().toString(16).takeLast(4)}"
    } else message


    Log.d(tag, sz)
}