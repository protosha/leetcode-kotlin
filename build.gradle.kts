import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    kotlin("jvm") version "2.0.21"
}

version = "0.1.0"

subprojects {
    apply(plugin = "idea")
    apply(plugin = "kotlin")

    repositories {
        mavenCentral()
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_21)
        }
    }

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(21))
        }
    }
}