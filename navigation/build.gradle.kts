import Deps.navigationDependencies

plugins {
    id("com.android.library")
    id("kotlin-android")
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
    navigationDependencies()
}