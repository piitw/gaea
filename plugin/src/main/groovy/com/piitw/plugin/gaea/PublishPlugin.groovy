package com.piitw.plugin.gaea

import com.piitw.plugin.gaea.artifacts.AndroidArtifacts
import com.piitw.plugin.gaea.artifacts.Artifacts
import com.piitw.plugin.gaea.artifacts.JavaArtifacts
import com.piitw.plugin.gaea.extension.PublishExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Create by wyman
 * Email  piitw@qq.com
 * Create at 2019-08-28 14:52
 * Desc :
 */
class PublishPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        final PublishExtension extension = project.extensions.create('publish', PublishExtension)
        project.afterEvaluate {
            extension.validate(project)
            if (!project.plugins.hasPlugin('maven')) {
                project.apply([plugin: 'maven'])
            }
            attachArtifacts(extension, project)
            publish(project, extension)
        }
    }

    private void attachArtifacts(PublishExtension extension, Project project) {
        if (project.plugins.hasPlugin('com.android.library')) {
            project.android.libraryVariants.each { variant ->
                addArtifact(project, new AndroidArtifacts(variant, extension))
            }
        } else {
            addArtifact(project, new JavaArtifacts(extension))
        }
    }

    private void addArtifact(Project project, Artifacts artifacts) {
        project.artifacts {
            artifacts.all(project.name, project).each {
                delegate.archives it
            }
        }
    }

    private void publish(Project project, PublishExtension extension) {
        project.uploadArchives {
            repositories.mavenDeployer {
                snapshotRepository(url: extension.snapshotRepositoryUrl) {
                    authentication(userName: extension.username, password: extension.password)
                }
                repository(url: extension.repositoryUrl) {
                    authentication(userName: extension.username, password: extension.password)
                }

                pom.packaging = extension.packaging
                if (extension.isDebug) {
                    pom.version = extension.publishVersion + '-SNAPSHOT'
                } else {
                    pom.version = extension.publishVersion
                }
                pom.groupId = extension.groupId
                pom.artifactId = extension.artifactId

                Utils.removeDependencies(pom)
                if (extension.withPomDependencies) {
                    Utils.addDependencies(pom, project)
                }
            }
        }
    }
}
