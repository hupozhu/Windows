package com.sampson.sourceimitate.startup.task

import com.sampson.sourceimitate.startup.StartupManagerDispatcher
import java.util.concurrent.Executor
import java.util.concurrent.LinkedBlockingDeque
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import kotlin.reflect.KProperty

class ThreadPoolExecutorDelegate {

    private lateinit var threadPoolExecutor: Executor

    operator fun getValue(thisRef: StartupManagerDispatcher, property: KProperty<*>): Executor {
        if (!this::threadPoolExecutor.isInitialized) {
            val cpuCount = Runtime.getRuntime().availableProcessors()
            threadPoolExecutor = ThreadPoolExecutor(
                cpuCount + 1,
                cpuCount * 2 + 1,
                500,
                TimeUnit.MILLISECONDS,
                LinkedBlockingDeque<Runnable>()
            )
        }
        return threadPoolExecutor
    }

    operator fun setValue(thisRef: StartupManagerDispatcher, property: KProperty<*>, value: Executor) {
        threadPoolExecutor = value
    }
}