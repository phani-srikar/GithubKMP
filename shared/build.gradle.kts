
import org.gradle.kotlin.dsl.kotlin
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

val kotlin_version: String by extra

buildscript {
    dependencies {
        classpath(kotlin("gradle-plugin", version = "1.3.71"))
    }
}
plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.native.cocoapods")
    kotlin("multiplatform")
    kotlin("plugin.serialization")
}

repositories {
    mavenCentral()
}

android {
    compileSdkVersion(29)
    defaultConfig {
        minSdkVersion(15)
    }
    packagingOptions {
        exclude("META-INF/*.kotlin_module")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

kotlin {
    android {
    }

    val iOSTarget: (String, KotlinNativeTarget.() -> Unit) -> KotlinNativeTarget =
        if (System.getenv("SDK_NAME")?.startsWith("iphoneos") == true) ::iosArm64
        else ::iosX64

    iOSTarget("ios") {
        cocoapods {
            // Configure fields required by CocoaPods.
            summary = "Test Pod with AFNetworking Dependency from Kotlin/Native using CocoaPods"
            homepage = "https://github.com/aws-amplify"

            // Configure a dependency on AFNetworking. It will be added in all macOS and iOS targets.
            pod("AFNetworking", "~> 4.0")
            frameworkName = "shared"
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                api("org.jetbrains.kotlin:kotlin-stdlib-common")
                implementation("io.ktor:ktor-client-core:${rootProject.extra.get("ktor_version")}")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-common:${rootProject.extra.get("coroutines_version")}")
                implementation(kotlin("stdlib-common"))
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-common:${rootProject.extra.get("serialization_version")}")

            }
        }

        val androidMain by getting {
            dependencies {
                api("org.jetbrains.kotlin:kotlin-stdlib")
                implementation("io.ktor:ktor-client-android:${rootProject.extra.get("ktor_version")}")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:${rootProject.extra.get("kotlin_coroutines_version")}")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime:${rootProject.extra.get("serialization_version")}")
            }
        }

        val iosMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-ios:${rootProject.extra.get("ktor_version")}")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-native:${rootProject.extra.get("kotlin_coroutines_version")}")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-native:${rootProject.extra.get("serialization_version")}")
            }
        }
    }
}
dependencies {
    implementation("androidx.core:core-ktx:1.2.0")
    implementation(kotlin("stdlib-jdk7", rootProject.extra.get("kotlin_version").toString()))
}


val packForXcode by tasks.creating(Sync::class) {
    group = "build"

    //selecting the right configuration for the iOS framework depending on the Xcode environment variables
    val mode = System.getenv("CONFIGURATION") ?: "DEBUG"
    val framework = kotlin.targets.getByName<KotlinNativeTarget>("ios").binaries.getFramework(mode)

    inputs.property("mode", mode)
    dependsOn(framework.linkTask)

    val targetDir = File(buildDir, "xcode-frameworks")
    from({ framework.outputDirectory })
    into(targetDir)

    doLast {
        val gradlew = File(targetDir, "gradlew")
        gradlew.writeText("#!/bin/bash\nexport 'JAVA_HOME=${System.getProperty("java.home")}'\ncd '${rootProject.rootDir}'\n./gradlew \$@\n")
        gradlew.setExecutable(true)
    }
}

tasks.getByName("build").dependsOn(packForXcode)