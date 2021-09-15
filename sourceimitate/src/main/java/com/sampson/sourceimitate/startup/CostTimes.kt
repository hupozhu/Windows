package com.sampson.sourceimitate.startup

data class CostTimes(
    val name: String,
    val threadName: String,
    val startTime: Long,
    var endTime: Long = 0L
)