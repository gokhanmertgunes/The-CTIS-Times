plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
}

android {
    namespace = "com.thectistimes.web"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.thectistimes.web"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures{
        viewBinding=true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.firebase:firebase-crashlytics:18.6.0")
    implementation("com.google.firebase:firebase-analytics:21.5.0")
    implementation ("com.google.code.gson:gson:2.8.8") // gson
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")




    implementation ( "com.squareup.picasso:picasso:2.8")

    //For Glide library to get images from server with https protocol
    implementation ("com.github.bumptech.glide:glide:4.14.2")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.14.2")

    //STEP1: Include retrofit and converter
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")

    // Gson Converter
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")

    val room_version = "2.6.0"
    implementation("androidx.room:room-runtime:$room_version")
    kapt ("androidx.room:room-compiler:$room_version")
    implementation ("androidx.room:room-ktx:$room_version")
    androidTestImplementation ("androidx.room:room-testing:$room_version")

    // Lifecycle components
    implementation( "androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation( "androidx.lifecycle:lifecycle-common-java8:2.2.0")
    implementation( "androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.2..0")

    // Kotlin components
    implementation( "org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.3.72")
    api ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.5")
    api ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.5")

    val worker_version="2.7.1"
    implementation ("androidx.work:work-runtime:$worker_version")
}