pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

plugins {
    id("com.louiscad.complete-kotlin") version "1.0.0"
}

rootProject.name = "First_Application"
include(":androidApp")
include(":shared")