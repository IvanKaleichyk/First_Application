import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
}

version = "1.0"

kotlin {
    android()

    val iosTarget: (String, KotlinNativeTarget.() -> Unit) -> KotlinNativeTarget =
        if (System.getenv("SDK_NAME")?.startsWith("iphoneos") == true)
            ::iosArm64
        else
            ::iosX64

    iosTarget("ios") {}

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        ios.deploymentTarget = "14.1"
        frameworkName = "shared"
        podfile = project.file("../iosApp/Podfile")
    }
    
    sourceSets {
        val ktorVersion = "1.6.1"
        val serializationVersion = "1.0.1"
        val coroutinesVersion = "1.4.0"

        val commonMain by getting{
            dependencies {
                api("org.jetbrains.kotlinx:kotlinx-serialization-core:${serializationVersion}")

                // HTTP
                implementation("io.ktor:ktor-client-core:${ktorVersion}")
                implementation("io.ktor:ktor-client-json:${ktorVersion}")
                implementation("io.ktor:ktor-client-serialization:${ktorVersion}")
                implementation("io.ktor:ktor-client-logging:$ktorVersion")

                // Coroutines
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${coroutinesVersion}")

                // DI
                implementation("org.kodein.di:kodein-di:7.1.0")
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val androidMain by getting{
            dependencies {
                implementation("io.ktor:ktor-client-android:$ktorVersion")
                implementation("io.ktor:ktor-client-okhttp:${ktorVersion}")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion")
            }
        }

        val androidTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation("junit:junit:4.13.2")
            }
        }
        val iosMain by getting {
            dependencies {
                implementation ("io.ktor:ktor-client-ios:$ktorVersion")
            }
        }
        val iosTest by getting
    }
}

android {
    compileSdkVersion(30)
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdkVersion(21)
        targetSdkVersion(30)
    }
}