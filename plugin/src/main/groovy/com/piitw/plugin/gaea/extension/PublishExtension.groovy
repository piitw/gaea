package com.piitw.plugin.gaea.extension

import groovy.transform.PackageScope
import org.gradle.api.Project

class PublishExtension {

    private final ERROR_PREFIX_FORMAT = "%s in 'publish' configuration must not be empty!"
    //package Android下默认aar
    String packaging = 'aar'
    //maven groupId
    String groupId
    //maven atrifactId
    String artifactId
    //version
    String publishVersion

    //私有仓库账户密码
    String username
    String password
    //release地址
    String repositoryUrl
    //快照地址
    String snapshotRepositoryUrl
    boolean isDebug = true

    boolean uploadSourcesJar = true
    boolean withPomDependencies = true

    @PackageScope
    void validate(Project project) {
        final LOCAL_REPOSITORIES = project.uri("${project.rootDir}/.maven")

        StringBuilder stringBuilder = new StringBuilder()
        final String SPLIT = ', '

        if (isEmpty(groupId)) {
            stringBuilder.append('groupId').append(SPLIT)
        }

        if (isEmpty(artifactId)) {
            stringBuilder.append('artifactId').append(SPLIT)
        }

        if (isEmpty(publishVersion)) {
            stringBuilder.append('publishVersion').append(SPLIT)
        }

        if (isEmpty(username)) {
            stringBuilder.append('username').append(SPLIT)
        }

        if (isEmpty(password)) {
            stringBuilder.append('password').append(SPLIT)
        }

        if (isEmpty(repositoryUrl)) {
            repositoryUrl = LOCAL_REPOSITORIES
        }

        if (isEmpty(snapshotRepositoryUrl)) {
            snapshotRepositoryUrl = LOCAL_REPOSITORIES
        }

        String error = stringBuilder.toString()
        if (!isEmpty(error)) {
            throw new IllegalStateException(String.format(ERROR_PREFIX_FORMAT, error.substring(0, error.length() - SPLIT.length())))
        }
    }

    private static boolean isEmpty(String s) {
        if (s == null) {
            return true
        } else {
            return s.isEmpty()
        }
    }
}
