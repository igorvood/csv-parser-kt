pluginManagement {


    plugins {
        kotlin("jvm") version "2.1.0"
//        kotlin("plugin.spring") version "2.1.0"
//        id("org.springframework.boot") version "3.4.4"
//        id("io.spring.dependency-management") version "1.1.7"
    }

    repositories {
        jcenter()
        gradlePluginPortal()
        mavenCentral()
    }
}

//include(":example-app")
include(":csv-parser-kt")
