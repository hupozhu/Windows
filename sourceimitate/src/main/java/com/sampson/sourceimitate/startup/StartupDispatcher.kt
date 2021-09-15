package com.sampson.sourceimitate.startup

import com.sampson.sourceimitate.startup.task.StartupTask
import java.util.concurrent.*
import java.util.concurrent.atomic.AtomicInteger

class StartupDispatcher(
    private val readStartupList: List<StartupTask>,
    private val waitTaskCount: AtomicInteger,
    private val dispatcher: Executor?
) {

    private var mAwaitCountDownLatch: CountDownLatch? = null
    private val mTimeRecord: StartupCostTimeRecord = StartupCostTimeRecord()
    private val startupManager = StartupManager()

    init {
        startupManager.apply {
            this.timeRecord = mTimeRecord
            if (dispatcher != null) {
                this.taskDispatcher = dispatcher
            }
        }
    }

    fun start(): StartupDispatcher {
        mTimeRecord.startTrace()
        mAwaitCountDownLatch = CountDownLatch(waitTaskCount.get())
        startupManager.mainCountDownLaunch = mAwaitCountDownLatch

        TopologySort.sort(readStartupList).run {
            startupManager.taskStore = this
            dispatchStartTask(this)
        }
        return this
    }

    fun await() {
        try {
            mAwaitCountDownLatch?.await(10, TimeUnit.SECONDS)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        } finally {
            mTimeRecord.endTrace()
        }
    }

    private fun dispatchStartTask(tasks: StartupTaskStore) {
        tasks.result.forEach {
            startupManager.dispatch(it, tasks)
        }
    }

    class Builder {

        private var startupList = mutableListOf<StartupTask>()
        private var needCountDown = AtomicInteger()
        private var dispatcher: Executor? = null

        fun addStartup(task: StartupTask): Builder {
            startupList.add(task)
            return this
        }

        fun setExecutor(dispatcher: Executor): Builder {
            this.dispatcher = dispatcher
            return this
        }

        fun build(): StartupDispatcher {
            startupList.forEach { task ->
                if (!task.processOnMainThread() && task.waitInAppOnCreate()) {
                    needCountDown.incrementAndGet()
                }
            }
            return StartupDispatcher(startupList, needCountDown, dispatcher)
        }

    }
}