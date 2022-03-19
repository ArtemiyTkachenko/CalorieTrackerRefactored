import Deps.coroutinesDependencies
import Deps.lifecycleDependencies
import Deps.navigationDependencies
import Deps.commonDependencies

plugins {
    id("common-feature-plugin")
}

dependencies {
    implementation(project(Modules.navigation))
    implementation(project(Modules.core))
    implementation(project(Modules.uiUtils))

    commonDependencies()
    coroutinesDependencies()
    navigationDependencies()
    lifecycleDependencies()
    implementation(Deps.Libs.hiltAndroid)
    implementation(Deps.Libs.hiltCommon)
    kapt(Deps.Libs.hiltCompiler)
    kapt(Deps.Libs.hiltKaptAndroidCompiler)
}