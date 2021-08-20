package com.sampson.sourceimitate.startup

import com.sampson.sourceimitate.startup.task.StartupTask
import java.util.*

/**
 * 针对启动列表进行拓扑排序
 */
object TopologySort {

    fun sort(startupList: List<StartupTask>): StartupTaskStore {
        val mainResult = mutableListOf<StartupTask>()
        val ioResult = mutableListOf<StartupTask>()
        val temp = mutableListOf<StartupTask>()

        val startupMap = hashMapOf<String, StartupTask>()
        val startupChildrenMap = hashMapOf<String, MutableList<String>>()

        val zeroDeque = ArrayDeque<String>()
        val inDegreeMap = hashMapOf<String, Int>()

        startupList.forEach {
            val uniqueKey = it::class.java.getUniqueKey()
            if (!startupMap.containsKey(uniqueKey)) {
                startupMap[uniqueKey] = it
                // save in-degree
                inDegreeMap[uniqueKey] = it.dependencies()?.size ?: 0
                if (it.dependencies().isNullOrEmpty()) {
                    zeroDeque.offer(uniqueKey)
                } else {
                    // add key parent, value list children
                    it.dependencies()?.forEach { parent ->
                        val parentUniqueKey = parent.getUniqueKey()
                        if (startupChildrenMap[parentUniqueKey] == null) {
                            startupChildrenMap[parentUniqueKey] = arrayListOf()
                        }
                        startupChildrenMap[parentUniqueKey]?.add(uniqueKey)
                    }
                }
            } else {
                throw Exception("$it multiple add.")
            }
        }

        while (!zeroDeque.isEmpty()) {
            zeroDeque.poll()?.let {
                startupMap[it]?.let { androidStartup ->
                    temp.add(androidStartup)
                    // add zero in-degree to result list
                    if (androidStartup.processOnMainThread()) {
                        mainResult.add(androidStartup)
                    } else {
                        ioResult.add(androidStartup)
                    }
                }
                startupChildrenMap[it]?.forEach { children ->
                    inDegreeMap[children] = inDegreeMap[children]?.minus(1) ?: 0
                    // add zero in-degree to deque
                    if (inDegreeMap[children] == 0) {
                        zeroDeque.offer(children)
                    }
                }
            }
        }

        if (mainResult.size + ioResult.size != startupList.size) {
            throw Exception("lack of dependencies or have circle dependencies.")
        }

        val result = mutableListOf<StartupTask>().apply {
            addAll(ioResult)
            addAll(mainResult)
        }

        return StartupTaskStore(
            result,
            startupMap,
            startupChildrenMap
        )
    }

}