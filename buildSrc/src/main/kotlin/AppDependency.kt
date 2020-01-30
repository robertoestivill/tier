object AppDependency {
    const val androidLiveData = "android.arch.lifecycle:livedata:${Version.androidLiveData}"
    const val androidViewModel = "android.arch.lifecycle:viewmodel:${Version.androidViewModel}"

    const val androidxAppCompat = "androidx.appcompat:appcompat:${Version.androidxAppCompat}"
    const val androidxConstraintLayout =
        "androidx.constraintlayout:constraintlayout:${Version.androidxConstraintLayout}"
    const val androidxRecyclerView =
        "androidx.recyclerview:recyclerview:${Version.androidxRecyclerView}"

    const val androidxRoomExt = "androidx.room:room-ktx:${Version.androidxRoom}"
    const val androidxRoomCompiler = "androidx.room:room-compiler:${Version.androidxRoom}"
    const val androidxRoomRuntime = "androidx.room:room-runtime:${Version.androidxRoom}"

    const val googleMaps = "com.google.android.gms:play-services-maps:${Version.googleMaps}"

    const val junit = "junit:junit:${Version.junit}"
    const val mockk = "io.mockk:mockk:${Version.mockk}"

    const val koin = "org.koin:koin-android-scope:${Version.koin}"
    const val koinViewModel = "org.koin:koin-android-viewmodel:${Version.koin}"

    const val kotlinCoroutines =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Version.kotlinCoroutines}"
    const val kotlinStdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Version.kotlin}"
    const val okhttpLogging = "com.squareup.okhttp3:logging-interceptor:${Version.okhttp}"

    const val retrofit = "com.squareup.retrofit2:retrofit:${Version.retrofit}"
    const val retrofitGson = "com.squareup.retrofit2:converter-gson:${Version.retrofit}"
}
