package com.sampson.sourceimitate.startup

import com.sampson.sourceimitate.startup.task.StartupRunnable
import com.sampson.sourceimitate.startup.task.StartupTask
import com.sampson.sourceimitate.startup.task.TaskFutureObserver
import com.sampson.sourceimitate.startup.task.ThreadPoolExecutorDelegate
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executor

class StartupManager() {

    lateinit var taskStore: StartupTaskStore
    var taskCaches = mutableMapOf<Class<out StartupTask>, Any?>()
    var taskDispatcher: Executor by ThreadPoolExecutorDelegate()
    var timeRecord: StartupCostTimeRecord? = null
    var mainCountDownLaunch: CountDownLatch? = null

    fun awaitTask(startupTask: Class<StartupTask>, futureObserver: TaskFutureObserver? = null) {
        if (this::taskStore.isInitialized) {
            taskStore.startupMap[startupTask.getUniqueKey()]?.waitCurrentTask(futureObserver)
        }
    }

    internal fun dispatch(task: StartupTask, store: StartupTaskStore) {
        if (taskCaches.containsKey(task.javaClass)) {
            val result = taskCaches[task.javaClass]
            notifyDependency(task, result, store)
        } else {
            val taskRunnable = StartupRunnable(task, store, this)
            // 如果task需要在主线程运行，直接调用run方法。如果是在子线程中运行，则通过dispatcher调度到线程池
            if (task.processOnMainThread()) {
                taskRunnable.run()
            } else {
                taskDispatcher.execute(taskRunnable)
            }
        }
    }

    internal fun notifyDependency(task: StartupTask, result: Any?, sortStore: StartupTaskStore) {
        if (!task.processOnMainThread() && task.waitInAppOnCreate()) {
            mainCountDownLaunch?.countDown()
        }
        // 找到依赖了task的子task，通知其子task依赖完成
        sortStore.startupChildrenMap[task.javaClass.getUniqueKey()]?.forEach {
            sortStore.startupMap[it]?.dependencyComplete(task, result)
        }
    }

}