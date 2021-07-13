package com.sampson.sourceimitate.rxjava

import io.reactivex.rxjava3.internal.schedulers.RxThreadFactory
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class IoScheduler : Scheduler {

    private val executor by lazy {
        Executors.newScheduledThreadPool(
            1,
            RxThreadFactory("RxCachedThreadSchedulerShutdown")
        )
    }

    override fun scheduleDirect(runnable: Runnable) {
        executor.schedule(runnable, 0, TimeUnit.SECONDS)
    }

}