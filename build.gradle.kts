// SPDX-License-Identifier: MIT
// Copyright (c) 2022 Lukas Zeller

import com.adarshr.gradle.testlogger.TestLoggerExtension
import com.adarshr.gradle.testlogger.theme.ThemeType

plugins {
    kotlin("jvm") version "1.7.0"
    java

    // pretty print tests
    id("com.adarshr.test-logger") version "3.2.0"

    // test coverage
    jacoco
    id("org.barfuin.gradle.jacocolog") version "1.2.4"

    // code quality
    id("org.sonarqube") version "3.4.0.2513"

    // add support for building a CLI application
    application
}

repositories {
    mavenCentral()
    maven { setUrl("https://jitpack.io") }
}

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("com.github.bhlangonijr:chesslib:1.3.3")

    implementation("io.github.microutils:kotlin-logging-jvm:2.1.21")
    implementation("org.slf4j:slf4j-api:1.7.36")
    testImplementation("org.slf4j:slf4j-log4j12:1.7.36")

    testImplementation(platform("org.junit:junit-bom:5.8.2"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.assertj:assertj-core:3.23.1")
}

application {
    mainClass.set("com.lz101010.AppKt")
}

tasks.test {
    useJUnitPlatform()
}

configure<TestLoggerExtension> {
    theme = ThemeType.MOCHA
    logLevel = LogLevel.LIFECYCLE
}

jacoco {
    toolVersion = "0.8.8"
}

tasks.jacocoTestReport {
    reports {
        csv.isEnabled = false
        html.isEnabled = false
        xml.isEnabled = true

        xml.outputLocation.set(layout.buildDirectory.file("reports/jacoco/test/jacocoTestReport.xml"))
    }
    dependsOn(tasks.check)
}
