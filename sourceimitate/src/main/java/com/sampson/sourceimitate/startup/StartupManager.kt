package com.sampson.sourceimitate.startup

import com.sampson.sourceimitate.startup.task.StartupTask
import java.util.concurrent.CountDownLatch
import java.util.concurrent.atomic.AtomicInteger

class StartupManager(val readStartupList: List<StartupTask>, private val waitTaskCount: AtomicInteger) {

    private var mAwaitCountDownLatch: CountDownLatch? = null

    fun start() {
        mAwaitCountDownLatch = CountDownLatch(waitTaskCount.get())

        TopologySort.sort(readStartupList).run {

        }
    }

    class Builder {

        private var startupList = mutableListOf<StartupTask>()
        private var needCountDown = AtomicInteger()

        fun addStartup(task: StartupTask) {
            startupList.add(task)
        }

        fun build() {
            startupList.forEach { task ->
                if (!task.processOnMainThread()) {
                    needCountDown.incrementAndGet()
                }
            }
            StartupManager(startupList, needCountDown)
        }

    }
}