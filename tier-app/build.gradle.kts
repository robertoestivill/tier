plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
}

android {
    compileSdkVersion(29)
    defaultConfig {
        applicationId = "com.robertoestivill.tier"
        minSdkVersion(26)
        targetSdkVersion(29)
        versionCode = 1
        versionName = "1.0"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
}

dependencies {
    implementation(AppDependency.androidLiveData)
    implementation(AppDependency.androidViewModel)

    implementation(AppDependency.androidxAppCompat)
    implementation(AppDependency.androidxConstraintLayout)
    implementation(AppDependency.androidxRecyclerView)

    implementation(AppDependency.androidxRoomExt)
    implementation(AppDependency.androidxRoomRuntime)
    kapt(AppDependency.androidxRoomCompiler)

    implementation(AppDependency.googleMaps)

    implementation(AppDependency.koin)
    implementation(AppDependency.koinViewModel)

    implementation(AppDependency.kotlinStdlib)
    implementation(AppDependency.kotlinCoroutines)
    implementation(AppDependency.okhttpLogging)

    implementation(AppDependency.retrofit)
    implementation(AppDependency.retrofitGson)

    testImplementation(AppDependency.junit)
    testImplementation(AppDependency.mockk)
}
