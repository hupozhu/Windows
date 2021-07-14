package com.sampson.sourceimitate.startup

import com.sampson.sourceimitate.startup.task.StartupTask

data class StartupTaskStore(
    val result: MutableList<StartupTask>,
    val startupMap: Map<String, StartupTask>,
    val startupChildrenMap: Map<String, MutableList<String>>
)