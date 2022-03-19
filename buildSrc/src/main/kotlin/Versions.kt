import org.gradle.api.JavaVersion

object Versions {
    object Android {
        const val compileSdk = 31
        const val buildTools = "30.0.3"
        const val targetSdk = 31
        const val minSdk = 21
    }

    object Build {
        const val gradleVersion = "4.1.1"
        const val kotlin = "1.6.0"
    }

    object Libs {
        const val coroutines = "1.4.2"
        const val navigation = "2.4.0-alpha03"
        const val lifecycle = "2.2.0"
        const val coil = "1.0.0"
        const val retrofit = "2.9.0"
        const val recycler = "1.2.0-rc01"
        const val constraint = "2.0.4"
        const val material = "1.3.0"
        const val hiltAndroid = "2.40.5"
        const val hilt = "1.0.0"
        const val room = "2.2.6"
        const val calendar = "0.4.4"
        const val roomCompiler = "1.1.1"
        const val desugaring = "1.1.5"
        const val androidxCore = "1.3.2"
        const val junit = "4.13.2"
        const val junitAndroid = "1.1.2"
        const val gms = "4.3.4"
        const val ktor = "1.5.3"
        const val kotlinxSerialization = "1.0.1"
        const val mockk = "1.11.0"
        const val espresso = "3.3.0"
        const val espressoCore = "3.2.0"
        const val kaspresso = "1.1.0"
        const val multidex = "2.0.1"
        const val fragment = "1.3.2"
        const val fragmentTesting = "1.3.4"
        const val uiautomator = "2.2.0"
        const val coreTesting = "2.1.0"
        const val compose = "1.0.5"
    }
}