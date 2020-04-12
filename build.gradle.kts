// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("base")
}
buildscript {
    extra.set("kotlin_version", "1.3.71")
    extra.set("kotlin_native_version", "1.3.71")
    extra.set("ktor_version", "1.3.2")
    extra.set("coroutines_version", "1.3.5")
    extra.set("kotlin_coroutines_version", "1.3.5")
    extra.set("serialization_version", "0.20.0")

    repositories {
        maven(url = "https://plugins.gradle.org/m2/")
        maven(url = "http://dl.bintray.com/kotlin/kotlin-eap")
        maven(url = "https://dl.bintray.com/jetbrains/kotlin-native-dependencies")
        maven(url = "https://dl.bintray.com/kotlin/ktor")
        maven(url = "https://kotlin.bintray.com/kotlinx")
        google()
        jcenter()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:3.6.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${rootProject.extra.get("kotlin_version")}")
        classpath("org.jetbrains.kotlin:kotlin-native-gradle-plugin:1.3.21")
        classpath("org.jetbrains.kotlin:kotlin-serialization:${rootProject.extra.get("kotlin_version")}")
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
        classpath(kotlin("gradle-plugin", rootProject.extra.get("kotlin_version").toString()))
    }
}

allprojects {
    repositories {
        maven(url = "https://kotlin.bintray.com/ktor")
        maven(url = "https://kotlin.bintray.com/kotlinx")
        google()
        jcenter()
    }
}

val removeBuildFiles by tasks.creating(Delete::class) {
    doLast {
        delete(buildDir)
    }
}
val clean by tasks.getting(Delete::class)
clean.dependsOn(removeBuildFiles)
