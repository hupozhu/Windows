package com.sampson.sourceimitate.startup

import com.sampson.sourceimitate.startup.task.StartupTask

class StartupManager(val readStartupList: List<StartupTask>, val waitTaskCount: Int) {

    fun start() {

    }

    class Builder {

        private var startupList = mutableListOf<StartupTask>()

        fun addStartup(task: StartupTask) {
            startupList.add(task)
        }

        fun build() {
            startupList.forEach { task ->
            }
            StartupManager(startupList,1)
        }

    }
}