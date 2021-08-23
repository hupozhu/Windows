package com.sampson.sourceimitate.startup

import com.sampson.sourceimitate.startup.task.StartupTask
import java.util.concurrent.*
import java.util.concurrent.atomic.AtomicInteger

class StartupDispatcher(
    private val readStartupList: List<StartupTask>,
    private val waitTaskCount: AtomicInteger
) {

    private var mAwaitCountDownLatch: CountDownLatch? = null

    fun start(): StartupDispatcher {
        mAwaitCountDownLatch = CountDownLatch(waitTaskCount.get())

        TopologySort.sort(readStartupList).run {
            StartupManager.INSTANCE.taskStore = this

            dispatchStartTask(this)
        }
        return this
    }

    fun await() {
        try {
            mAwaitCountDownLatch?.await()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    private fun dispatchStartTask(tasks: StartupTaskStore) {
        tasks.result.forEach {
            StartupManager.INSTANCE.dispatch(it, tasks)
        }
    }

    class Builder {

        private var startupList = mutableListOf<StartupTask>()
        private var needCountDown = AtomicInteger()

        fun addStartup(task: StartupTask): Builder {
            startupList.add(task)
            return this
        }

        fun setExecutor(dispatcher: Executor): Builder {
            StartupManager.INSTANCE.taskDispatcher = dispatcher
            return this
        }

        fun build(): StartupDispatcher {
            startupList.forEach { task ->
                if (!task.processOnMainThread()) {
                    needCountDown.incrementAndGet()
                }
            }
            return StartupDispatcher(startupList, needCountDown)
        }

    }
}