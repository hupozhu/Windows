package com.sampson.util

import org.gradle.api.Plugin
import org.gradle.api.Project

class CommonUtils implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.task("gitUtil", GitUtils)
    }
}