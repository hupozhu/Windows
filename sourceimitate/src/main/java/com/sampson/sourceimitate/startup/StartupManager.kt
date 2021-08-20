package com.sampson.sourceimitate.startup

import com.sampson.sourceimitate.startup.task.StartupTask
import java.util.concurrent.*
import java.util.concurrent.atomic.AtomicInteger

class StartupManager(
    private val readStartupList: List<StartupTask>,
    private val waitTaskCount: AtomicInteger
) {

    private var mAwaitCountDownLatch: CountDownLatch? = null

    fun start() {
        mAwaitCountDownLatch = CountDownLatch(waitTaskCount.get())

        TopologySort.sort(readStartupList).run {
            dispatchStartTask(this)
        }
    }

    private fun dispatchStartTask(tasks: StartupTaskStore) {
        tasks.result.forEach {
            StartupManagerDispatcher.instance.dispatch(it, tasks)
        }
    }

    class Builder {

        private var startupList = mutableListOf<StartupTask>()
        private var needCountDown = AtomicInteger()

        fun addStartup(task: StartupTask): Builder {
            startupList.add(task)
            return this
        }

        fun setDispatcher(dispatcher: Executor): Builder {
            StartupManagerDispatcher.instance.taskDispatcher = dispatcher
            return this
        }

        fun build(): StartupManager {
            startupList.forEach { task ->
                if (!task.processOnMainThread()) {
                    needCountDown.incrementAndGet()
                }
            }
            return StartupManager(startupList, needCountDown)
        }

    }
}