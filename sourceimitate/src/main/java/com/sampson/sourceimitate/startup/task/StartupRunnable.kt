package com.sampson.sourceimitate.startup.task

import com.sampson.sourceimitate.startup.StartupManager
import com.sampson.sourceimitate.startup.StartupTaskStore

class StartupRunnable(
    private val startup: StartupTask,
    private val taskStore: StartupTaskStore,
    private val dispatcher: StartupManager
) : Runnable {

    override fun run() {
        // 1、等待依赖的任务执行完成
        startup.waitDependency()
        // 2、执行任务获取结果
        val result = startup.create()
        // 3、将执行结果存入缓存
        dispatcher.taskCaches[startup.javaClass] = result
        // 4、通知被其依赖的任务
        dispatcher.notifyDependency(startup, result, taskStore)
    }

}