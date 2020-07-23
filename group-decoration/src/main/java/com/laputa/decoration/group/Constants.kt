package com.laputa.decoration.group

import android.util.Log

const val TAG = "zeej"

fun log(msg:()->Any){
    Log.i(TAG,msg().toString())
}