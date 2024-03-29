package com.piitw.plugin.gaea.util

import org.gradle.api.Project
import org.gradle.api.artifacts.ExcludeRule
import org.gradle.api.publish.maven.MavenPom

static def addDependencies(MavenPom pom, Project project) {

    pom.withXml {
        Node root = asNode()
        Node dependenciesNode = root.appendNode('dependencies')

        def addDependency = { configuration, scope ->
            configuration.allDependencies.each {
                if (it.group == null || it.version == null || it.name == null || it.name == 'unspecified') {
                    return
                }

                Node dependencyNode = dependenciesNode.appendNode('dependency')
                dependencyNode.appendNode('groupId', it.group)
                dependencyNode.appendNode('artifactId', it.name)
                dependencyNode.appendNode('version', it.version)
                dependencyNode.appendNode('scope', scope)

                if (!it.transitive) {
                    Node exclusionNode = dependencyNode.appendNode('exclusions').appendNode('exclusion')
                    exclusionNode.appendNode('groupId', '*')
                    exclusionNode.appendNode('artifactId', '*')
                } else if (!it.properties.excludeRules.empty) {
                    Node exclusionNode = dependencyNode.appendNode('exclusions').appendNode('exclusion')
                    it.properties.excludeRules.each { ExcludeRule rule ->
                        exclusionNode.appendNode('groupId', rule.group ?: '*')
                        exclusionNode.appendNode('artifactId', rule.module ?: '*')
                    }
                }
            }
        }

        if (project.configurations.findByName('implementation') != null) {
            addDependency(project.configurations.implementation, 'compile')
        }
        if (project.configurations.findByName('compileOnly') != null) {
            addDependency(project.configurations.compileOnly, 'provided')
        }
        if (project.configurations.findByName('runtimeOnly') != null) {
            addDependency(project.configurations.runtimeOnly, 'runtime')
        }
        if (project.configurations.findByName('provided') != null) {
            addDependency(project.configurations.provided, 'provided')
        }
        if (project.configurations.findByName('apk') != null) {
            addDependency(project.configurations.apk, 'runtime')
        }
    }
}

static def removeDependencies(MavenPom pom) {

    pom.withXml {
        Node root = asNode()
        def dependenciesNode = root.children().find {
            it.name().getLocalPart().equals('dependencies')
        }

        if (dependenciesNode != null) {
            root.remove(dependenciesNode)
        }
    }
}
