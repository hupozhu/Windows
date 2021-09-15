package com.sampson.sourceimitate.startup

import android.util.Log
import com.sampson.sourceimitate.Constant
import com.sampson.sourceimitate.startup.task.StartupTask

class StartupCostTimeRecord {

    private val accuracy = 1000 * 1000L
    private val costTimesMap = mutableMapOf<String, CostTimes>()

    private var startTime = 0L
    private var endTime: Long? = null

    private val mainThreadTimes
        get() = (endTime ?: System.nanoTime()) - startTime

    fun startTrace() {
        startTime = System.nanoTime()
    }

    fun endTrace() {
        endTime = System.nanoTime()

        printAll()
    }

    fun startOneTaskTrace(startup: StartupTask) {
        val model = CostTimes(startup.javaClass.simpleName, Thread.currentThread().name, System.nanoTime() / accuracy)
        costTimesMap[startup.javaClass.getUniqueKey()] = model
    }

    fun endOneTaskTrace(startup: StartupTask) {
        costTimesMap[startup.javaClass.getUniqueKey()]?.endTime = System.nanoTime() / accuracy
    }

    private fun printAll() {
        val log = buildString {
            append("startup detail:")
            append("\n")
            append("|=================================================================")
            costTimesMap.values.forEach {
                append("\n")
                append("|      Startup Name       |   ${it.name}")
                append("\n")
                append("| ----------------------- | --------------------------------------")
                append("\n")
                append("|     Work Thread name    |   ${it.threadName}")
                append("\n")
                append("| ----------------------- | --------------------------------------")
                append("\n")
                append("|       Cost Times        |   ${it.endTime - it.startTime} ms")
                append("\n")
                append("|=================================================================")
            }
            append("\n")
            append("| Total Main Thread Times |   ${mainThreadTimes / accuracy} ms")
            append("\n")
            append("|=================================================================")
        }
        Log.i(Constant.START_UP_TAG, log)
    }

}