package com.sampson.sourceimitate.startup.task


interface StartupTask {

    fun processOnMainThread() = false

    fun create()

    fun onComplete(result: Any?)

    fun dependencies(): List<Class<out StartupTask>>?

    fun waitDependency()

    fun dependencyComplete(dependency: StartupTask, result: Any?)

    fun waitCurrentTask(futureObserver: TaskFutureObserver? = null)

}