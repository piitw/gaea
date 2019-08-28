package com.piitw.plugin.gaea.artifacts;

import org.gradle.api.Project

interface Artifacts {
    def all(String publicationName, Project project)
}
