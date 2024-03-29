package com.piitw.plugin.gaea.artifacts

import com.piitw.plugin.gaea.extension.PublishExtension
import org.gradle.api.Project
import org.gradle.api.tasks.bundling.Jar

class JavaArtifacts implements Artifacts {

    private PublishExtension extension

    JavaArtifacts(PublishExtension extension) {
        this.extension = extension
    }

    @Override
    def all(String publicationName, Project project) {
        if (extension.uploadSourcesJar) {
            [sourcesJar(publicationName, project), javadocJar(publicationName, project)]
        } else {
            [javadocJar(publicationName, project)]
        }
    }

    def sourcesJar(String publicationName, Project project) {
        project.task(publicationName + 'SourcesJar', type: Jar) {
            group = 'jar'
            from project.sourceSets.main.allSource
            classifier = 'sources'
        }
    }

    def javadocJar(String publicationName, Project project) {
        project.task(publicationName + 'JavadocJar', type: Jar) {
            group = 'jar'
            from project.javadoc.destinationDir
            classifier = 'javadoc'
        }
    }

    def from(Project project) {
        project.components.java
    }
}
