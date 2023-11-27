plugins {
    kotlin("android")
    id ("com.android.library")
    id("maven-publish")
    id("signing")
}

val composeBomVersion : String by project
val composeCompilerVersion : String by project

android {
    namespace = "com.mr0xf00.easycrop"
    compileSdk = 33

    defaultConfig {
        minSdk = 21
        targetSdk = 33

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    composeOptions {
        kotlinCompilerExtensionVersion = composeCompilerVersion
    }
    buildFeatures {
        compose = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    publishing {
        singleVariant("release") {
            withSourcesJar()
            withJavadocJar()
        }
    }
}

dependencies {
    implementation(platform("androidx.compose:compose-bom:$composeBomVersion"))
    implementation ("androidx.core:core-ktx:1.9.0")
    testImplementation ("junit:junit:4.13.2")
    androidTestImplementation ("androidx.test.ext:junit:1.1.5")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.5.1")
    implementation ("androidx.activity:activity-compose:1.6.1")
    implementation ("androidx.compose.material:material")
    implementation ("androidx.compose.ui:ui")
    androidTestImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")
}

publishing {
    publications {
        create<MavenPublication>("jitpack") {
            from(components["java"])

            groupId = "com.github.OctoGeth"
            artifactId = "easycrop"
            version = "0.1.3"

            pom {
                name.set("Easycrop")
                description.set("Simple image cropper/resizer for Android compase app")
            }
        }
    }
}

