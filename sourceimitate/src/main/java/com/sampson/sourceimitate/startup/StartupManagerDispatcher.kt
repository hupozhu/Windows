package com.sampson.sourceimitate.startup

import com.sampson.sourceimitate.startup.task.StartupRunnable
import com.sampson.sourceimitate.startup.task.StartupTask
import com.sampson.sourceimitate.startup.task.ThreadPoolExecutorDelegate
import java.util.concurrent.Executor

class StartupManagerDispatcher private constructor() {

    companion object {
        val instance: StartupManagerDispatcher by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { StartupManagerDispatcher() }
    }

    var taskDispatcher: Executor by ThreadPoolExecutorDelegate()
    var taskCaches = mutableMapOf<Class<out StartupTask>, Any?>()

    fun dispatch(task: StartupTask, store: StartupTaskStore) {
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

    fun notifyDependency(task: StartupTask, result: Any?, sortStore: StartupTaskStore) {
        // 找到依赖了task的子task，通知其子task依赖完成
        sortStore.startupChildrenMap[task.javaClass.getUniqueKey()]?.forEach {
            sortStore.startupMap[it]?.dependencyComplete(task, result)
        }
    }

}