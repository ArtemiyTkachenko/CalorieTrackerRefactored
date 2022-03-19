import org.gradle.kotlin.dsl.`kotlin-dsl`

plugins {
    `kotlin-dsl`
}

gradlePlugin {
    plugins {
        register("common-plugin") {
            id = "common-plugin"
            implementationClass = "com.artkachenko.plugins.CommonPlugin"
        }
        register("common-feature-plugin") {
            id = "common-feature-plugin"
            implementationClass = "com.artkachenko.plugins.CommonFeaturePlugin"
        }
    }
}

repositories {
    google()
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.31")
    implementation("com.android.tools.build:gradle:7.1.0")
    implementation("com.google.dagger:hilt-android-gradle-plugin:2.40.5")
}