package com.sampson.sourceimitate.startup.task


interface StartupTask {

    fun processOnMainThread() = false

    fun create()

    fun dependencies(): List<Class<out StartupTask>>?

}