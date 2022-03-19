package com.artkachenko.plugins

import com.android.build.gradle.LibraryExtension
import org.gradle.api.Action
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

class CommonFeaturePlugin : Plugin<Project> {

    private val Project.android: LibraryExtension
        get() = extensions.findByName("android") as? LibraryExtension
            ?: error("Not an Android module: $name")

    override fun apply(project: Project) =
        with(project) {
            applyPlugins()
            androidConfig()
        }

    private fun LibraryExtension.kotlinOptions(configure: Action<KotlinJvmOptions>): Unit =
        (this as org.gradle.api.plugins.ExtensionAware).extensions.configure("kotlinOptions", configure)

    private fun Project.applyPlugins() {
        plugins.run {
            apply("com.android.library")
            apply("kotlin-android")
            apply("kotlin-kapt")
            apply("kotlin-parcelize")
            apply("androidx.navigation.safeargs.kotlin")
            apply("dagger.hilt.android.plugin")
        }
    }

    private fun Project.androidConfig() {
        android.run {
            compileSdk = Versions.Android.compileSdk

            defaultConfig {
                minSdk = Versions.Android.minSdk
                targetSdk = Versions.Android.compileSdk

                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            }

            compileOptions {
                isCoreLibraryDesugaringEnabled = true
                sourceCompatibility = JavaVersion.VERSION_1_8
                targetCompatibility = JavaVersion.VERSION_1_8
            }
            kotlinOptions {
                jvmTarget = JavaVersion.VERSION_1_8.toString()
            }
            packagingOptions {
                resources.excludes.apply {
                    add("META-INF/*")
                }
            }

            buildFeatures {
                viewBinding = true
            }

            flavorDimensions += "version"

            productFlavors {
                create("googlePlay") {
                    dimension = "version"
                }

                create("uiTest") {
                    minSdk = 29
                }
            }

            variantFilter {
                if (name == "uiTest" && name == "release") {
                    ignore = true
                }
            }

            lint {
                abortOnError = false
                checkDependencies = true
                htmlReport = true
                ignoreTestSources = true
                textReport = false
                xmlReport = false
            }
        }
    }
}