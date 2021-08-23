package com.sampson

import AndroidStartup
import android.app.Application
import android.util.Log
import com.sampson.sourceimitate.Constant
import com.sampson.sourceimitate.startup.StartupDispatcher
import com.sampson.sourceimitate.startup.task.StartupTask

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        customStartup()
    }

    private fun customStartup() {
        StartupDispatcher.Builder()
            .addStartup(TestStartup1())
            .addStartup(TestStartup2())
            .build()
            .start()
            .await()
    }

    class TestStartup1: AndroidStartup() {

        override fun createTask(): Any? {
            Log.i(Constant.START_UP_TAG, "task:1 start")
            Thread.sleep(2000)
            Log.i(Constant.START_UP_TAG, "task:1 complete")
            return null
        }

        override fun processOnMainThread(): Boolean = false

        override fun dependencies(): List<Class<out StartupTask>>? = null

    }

    class TestStartup2: AndroidStartup() {
        override fun createTask(): Any? {
            Log.i(Constant.START_UP_TAG, "task:2 start")
            Thread.sleep(1000)
            Log.i(Constant.START_UP_TAG, "task:2 complete")
            return null
        }

        override fun processOnMainThread(): Boolean {
            return true
        }

        override fun dependencies(): List<Class<out StartupTask>>? {
            return listOf(TestStartup1::class.java)
        }

    }

}