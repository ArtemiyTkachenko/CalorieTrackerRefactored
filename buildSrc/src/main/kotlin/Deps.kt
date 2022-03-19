import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.PluginDependenciesSpecScope

object Deps {

    object Libs {
        const val desugaring = "com.android.tools:desugar_jdk_libs:${Versions.Libs.desugaring}"
        const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.Build.kotlin}"
        const val androidCore = "androidx.core:core-ktx:${Versions.Libs.androidxCore}"
        const val junit = "junit:junit:${Versions.Libs.junit}"
        const val junitAndroidTest = "androidx.test.ext:${Versions.Libs.junitAndroid}"
        const val espresso = "androidx.test.espresso:espresso-core:${Versions.Libs.espresso}"
        const val multidex = "androidx.multidex:multidex:${Versions.Libs.multidex}"
        const val fragment = "androidx.fragment:fragment-ktx:${Versions.Libs.fragment}"
        const val material = "com.google.android.material:material:${Versions.Libs.material}"
        const val constraintLayout =
            "androidx.constraintlayout:constraintlayout:${Versions.Libs.constraint}"
        const val coroutinesAndroid =
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.Libs.coroutines}"
        const val coroutinesCore =
            "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.Libs.coroutines}"
        const val coroutineTest =
            "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.Libs.coroutines}"
        const val navigationFragment =
            "androidx.navigation:navigation-fragment-ktx:${Versions.Libs.navigation}"
        const val navigationUi = "androidx.navigation:navigation-ui-ktx:${Versions.Libs.navigation}"

        const val recycler = "androidx.recyclerview:recyclerview:${Versions.Libs.recycler}"

        const val ktorClientAndroid = "io.ktor:ktor-client-android:${Versions.Libs.ktor}"
        const val ktorClientSerialization =  "io.ktor:ktor-client-serialization:${Versions.Libs.ktor}"
        const val ktorLoggingClient = "io.ktor:ktor-client-logging-jvm:${Versions.Libs.ktor}"
        const val ktorClientMock = "io.ktor:ktor-client-mock:${Versions.Libs.ktor}"

        const val kotlinSerialization = "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.Libs.kotlinxSerialization}"

        const val lifecycleExtensions = "androidx.lifecycle:lifecycle-extensions:${Versions.Libs.lifecycle}"
        const val lifecycleViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.Libs.lifecycle}"
        const val lifecyclerSavedstate = "androidx.lifecycle:lifecycle-viewmodel-savedstate:${Versions.Libs.lifecycle}"
        const val coil = "io.coil-kt:coil:${Versions.Libs.coil}"
        const val hiltAndroid = "com.google.dagger:hilt-android:${Versions.Libs.hiltAndroid}"
        const val hiltCommon = "androidx.hilt:hilt-common:${Versions.Libs.hilt}"
        const val hiltKaptAndroidCompiler = "com.google.dagger:hilt-android-compiler:${Versions.Libs.hiltAndroid}"
        const val hiltCompiler = "androidx.hilt:hilt-compiler:${Versions.Libs.hilt}"

        const val roomRuntime = "androidx.room:room-runtime:${Versions.Libs.room}"
        const val roomKtx = "androidx.room:room-ktx:${Versions.Libs.room}"

        const val roomKaptPersistence = "android.arch.persistence.room:compiler:${Versions.Libs.roomCompiler}"
        const val roomCompiler = "androidx.room:room-compiler:${Versions.Libs.room}"

        const val calendarView = "com.github.kizitonwose:CalendarView:${Versions.Libs.calendar}"

        const val mockK = "io.mockk:mockk-android:${Versions.Libs.mockk}"

        const val kotlinTest = "org.jetbrains.kotlin:kotlin-test-junit:${Versions.Build.kotlin}"

        const val junitAndroid = "androidx.test.ext:junit:${Versions.Libs.junitAndroid}"
        const val uiautomator = "androidx.test.uiautomator:uiautomator:${Versions.Libs.uiautomator}"
        const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.Libs.espressoCore}"
        const val kaspresso = "com.kaspersky.android-components:kaspresso:${Versions.Libs.kaspresso}"
        const val navigationTesting = "androidx.navigation:navigation-testing:${Versions.Libs.navigation}"
        const val coreTesting = "androidx.arch.core:core-testing:${Versions.Libs.coreTesting}"

        const val fragmentTesting = "androidx.fragment:fragment-testing:${Versions.Libs.fragmentTesting}"

        const val hiltTesting = "com.google.dagger:hilt-android-testing:${Versions.Libs.hiltAndroid}"
        const val hiltTestingAnnotation = "com.google.dagger:hilt-android-compiler:${Versions.Libs.hiltAndroid}"

        const val composeUI = "androidx.compose.ui:ui:${Versions.Libs.compose}"
        const val composeTooling = "androidx.compose.ui:ui-tooling:${Versions.Libs.compose}"
        const val foundation = "androidx.compose.foundation:foundation:${Versions.Libs.compose}"
        const val composeMaterial = "androidx.compose.material:material:${Versions.Libs.compose}"
        const val composeIcons = "androidx.compose.material:material-icons-core:${Versions.Libs.compose}"
        const val composeIconsExtended = "androidx.compose.material:material-icons-extended:${Versions.Libs.compose}"
        const val composeAnimations = "androidx.compose.animation:animation:${Versions.Libs.compose}"
    }

    private const val implementation = "implementation"
    private const val kapt = "kapt"
    private const val coreLibraryDesugaring = "coreLibraryDesugaring"

//    fun DependencyHandler.hiltDependencies() {
//        add(implementation, Libs.hiltAndroid)
//        add(implementation, Libs.hiltCommon)
//        add(kapt, Libs.hiltCompiler)
//        add(kapt, Libs.hiltKaptAndroidCompiler)
//    }

    fun DependencyHandler.hiltDependencies() {

    }

    fun DependencyHandler.coroutinesDependencies() {
        add(implementation, Libs.coroutinesAndroid)
        add(implementation, Libs.coroutinesCore)
        add(implementation, Libs.coroutineTest)
    }

    fun DependencyHandler.navigationDependencies() {
        add(implementation, Libs.navigationFragment)
        add(implementation, Libs.navigationUi)
    }

    fun DependencyHandler.lifecycleDependencies() {
        add(implementation, Libs.lifecyclerSavedstate)
        add(implementation, Libs.lifecycleExtensions)
        add(implementation, Libs.lifecycleViewModel)
    }

    fun DependencyHandler.ktorDependencies() {
        add(implementation, Libs.ktorClientAndroid)
        add(implementation, Libs.ktorLoggingClient)
        add(implementation, Libs.ktorClientSerialization)
        add(implementation, Libs.ktorLoggingClient)
    }

    fun DependencyHandler.roomDependencies() {
        add(implementation, Libs.roomKtx)
        add(implementation, Libs.roomRuntime)
        add(kapt, Libs.roomCompiler)
        add(kapt, Libs.roomKaptPersistence)
    }

    fun DependencyHandler.composeDependencies() {
        add(implementation, Libs.composeUI)
        add(implementation, Libs.composeTooling)
        add(implementation, Libs.composeMaterial)
        add(implementation, Libs.composeAnimations)
        add(implementation, Libs.composeIcons)
        add(implementation, Libs.composeIconsExtended)
    }

    fun DependencyHandler.commonDependencies() {
        add(implementation, Libs.androidCore)
        add(implementation, Libs.fragment)
        add(implementation, Libs.material)
        add(implementation, Libs.constraintLayout)
        add(implementation, Libs.coil)
        add(implementation, Libs.calendarView)
        add(implementation, Libs.constraintLayout)
        add(implementation, Libs.recycler)
    }
}