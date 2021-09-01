# Gaea

[ ![License](http://img.shields.io/badge/License-Apache%202.0-blue.svg?style=flat-square)](http://www.apache.org/licenses/LICENSE-2.0)

一款**Groovy**编写 发布**Android**项目到maven仓库的轻量级插件

## Usage

根目录build.gradle添加

``` groovy
buildscript {
    repositories {
        jcenter()

    }
    dependencies {
        classpath 'com.piitw.plugin:gaea:<Latest-Version>'
    }
}
```

module build.gradle添加

``` groovy
apply plugin: 'com.piitw.plugin'

publish {
    groupId = groupId
    artifactId = artifactId
    publishVersion = version
    isDebug = isDebug 如为true，发布到快照仓库；反之则发布到正式仓库

    repositoryUrl = 正式仓库地址
    snapshotRepositoryUrl = 测试仓库地址
    username = username
    password = password
}
```
使用 task `uploadArchives`进行发布

```bash
$ ./gradlew clean build :<library>:uploadArchives
```


## LICENSE

This library is licensed under the [Apache Software License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0).

See [`LICENSE`](LICENSE) for full of the license text.

    Copyright (C) 2019 piitw.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.