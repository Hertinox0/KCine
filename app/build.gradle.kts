import com.android.build.api.dsl.Packaging

plugins {
    alias(libs.plugins.androidApplication)
}

android {
    namespace = "edu.equipe_a.kcine"
    compileSdk = 34

    defaultConfig {
        applicationId = "edu.equipe_a.kcine"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    packagingOptions {
        exclude("META-INF/DEPENDENCIES")
        exclude("META-INF/INDEX.LIST")
        exclude("META-INF/io.netty.versions.properties")
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation ("com.squareup.retrofit2:converter-jackson:2.7.2")
    implementation ("com.fasterxml.jackson.core:jackson-databind:2.10.3")
    implementation ("com.fasterxml.jackson.core:jackson-core:2.10.3")
    implementation ("com.fasterxml.jackson.core:jackson-annotations:2.10.3")
    implementation ("androidx.work:work-runtime:2.7.0")

    implementation ("com.squareup.picasso:picasso:2.71828")

    implementation(libs.bar) // Librairie utilisée pour la barre de navigation
    // https://mvnrepository.com/artifact/org.asynchttpclient/async-http-client
    implementation(libs.async)
    implementation(libs.jackson)
}