import Deps.hiltDependencies

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("kotlinx-serialization")
    id("androidx.navigation.safeargs.kotlin")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = Versions.Android.compileSdk

    defaultConfig {
        applicationId = "com.artkachenko.calorietracker"
        minSdk = Versions.Android.minSdk
        targetSdk = Versions.Android.compileSdk
        versionCode = 1
        versionName = "1.0"
        multiDexEnabled = true

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "$project.rootDir/tools/proguard-rules-debug.pro"
            )
            buildConfigField(
                "String",
                "URL_BASE",
                "\"https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com\""
            )

        }
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "$project.rootDir/tools/proguard-rules.pro"
            )
            buildConfigField(
                "String",
                "URL_BASE",
                "\"https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com\""
            )
        }
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
    coreLibraryDesugaring(Deps.Libs.desugaring)
    implementation(Deps.Libs.androidCore)
    implementation(Deps.Libs.multidex)
    implementation(Deps.Libs.hiltAndroid)
    implementation(Deps.Libs.hiltCommon)
    kapt(Deps.Libs.hiltCompiler)
    kapt(Deps.Libs.hiltKaptAndroidCompiler)

    api(project(Modules.core))
    api(project(Modules.uiCore))
}