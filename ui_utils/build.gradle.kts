import Deps.coroutinesDependencies
import Deps.hiltDependencies
import Deps.lifecycleDependencies
import Deps.navigationDependencies
import Deps.ktorDependencies
import Deps.commonDependencies

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("androidx.navigation.safeargs.kotlin")
    id("dagger.hilt.android.plugin")
}

android {
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
}

dependencies {
    implementation(project(Modules.core))

    commonDependencies()
    coroutinesDependencies()
    navigationDependencies()
    lifecycleDependencies()
    ktorDependencies()
    implementation(Deps.Libs.hiltAndroid)
    implementation(Deps.Libs.hiltCommon)
    kapt(Deps.Libs.hiltCompiler)
    kapt(Deps.Libs.hiltKaptAndroidCompiler)
}