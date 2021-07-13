package com.sampson.sourceimitate.rxjava

interface Scheduler {

    fun scheduleDirect(runnable: Runnable)

}