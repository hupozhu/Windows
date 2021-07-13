package com.sampson.sourceimitate.rxjava

import android.os.Handler
import android.os.Looper
import android.os.Message

class MainHandlerScheduler: Scheduler {

    private val handler by lazy { Handler(Looper.getMainLooper()) }

    override fun scheduleDirect(runnable: Runnable) {
        val msg = Message.obtain(handler, runnable)
        handler.sendMessage(msg)
    }

}