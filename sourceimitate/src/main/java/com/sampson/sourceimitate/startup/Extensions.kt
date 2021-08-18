package com.sampson.sourceimitate.startup

import com.sampson.sourceimitate.startup.task.StartupTask


fun Class<out StartupTask>.getUniqueKey(): String {
    val canonicalName = this.canonicalName
    requireNotNull(canonicalName) { "Local and anonymous classes can not be Startup" }
    return "Startup:$canonicalName"
}