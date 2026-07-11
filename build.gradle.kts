plugins {
    java
    id("io.qameta.allure") version "2.12.0"
}

group = "com.sdet.builder"
version = "0.1.0"

val seleniumVersion = "4.45.0"
val selenideVersion = "7.16.2"
val junitVersion = "5.14.4"
val cucumberVersion = "7.34.3"
val allureVersion = "2.33.0"
val extentVersion = "5.1.2"
val extentCucumberAdapterVersion = "1.14.0"
val flywayVersion = "10.22.0"
val postgresqlVersion = "42.7.4"

java {
    sourceCompatibility = JavaVersion.VERSION_22
    targetCompatibility = JavaVersion.VERSION_22
}

dependencies {

    // JUnit BOM
    testImplementation(platform("org.junit:junit-bom:$junitVersion"))

    // Cucumber BOM
    testImplementation(platform("io.cucumber:cucumber-bom:$cucumberVersion"))

    // Allure BOM
    testImplementation(platform("io.qameta.allure:allure-bom:$allureVersion"))

    // Selenium
    testImplementation("org.seleniumhq.selenium:selenium-java:$seleniumVersion")

    // Selenide
    testImplementation("com.codeborne:selenide:$selenideVersion")

    // JUnit
    testImplementation("org.junit.jupiter:junit-jupiter")

    // JUnit Suite
    testImplementation("org.junit.platform:junit-platform-suite")

    // Cucumber
    testImplementation("io.cucumber:cucumber-java")
    testImplementation("io.cucumber:cucumber-junit-platform-engine")
    testImplementation("io.cucumber:cucumber-picocontainer")

    // Allure
    testImplementation("io.qameta.allure:allure-junit5")
    testImplementation("io.qameta.allure:allure-cucumber7-jvm")

    // Extent Reports
    testImplementation("com.aventstack:extentreports:$extentVersion")
    testImplementation("tech.grasshopper:extentreports-cucumber7-adapter:$extentCucumberAdapterVersion")

    // Logging
    testImplementation("org.slf4j:slf4j-api:2.0.17")
    testRuntimeOnly("ch.qos.logback:logback-classic:1.5.18")

    // Flyway
    testImplementation("org.flywaydb:flyway-core:$flywayVersion")
    testImplementation("org.flywaydb:flyway-database-postgresql:$flywayVersion")

    // PostgreSQL Driver
    testImplementation("org.postgresql:postgresql:$postgresqlVersion")
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
    options.release.set(17)
}

tasks.withType<Test>().configureEach {

    useJUnitPlatform()

    systemProperty(
        "baseUrl",
        providers.gradleProperty("baseUrl")
            .orElse("http://localhost:5173")
            .get()
    )

    systemProperty(
        "headless",
        providers.gradleProperty("headless")
            .orElse("false")
            .get()
    )

    systemProperty(
        "browser",
        providers.gradleProperty("browser")
            .orElse("chrome")
            .get()
    )

    systemProperty(
        "build.label",
        providers.gradleProperty("buildLabel")
            .orElse("gradle-local")
            .get()
    )

    systemProperty("cucumber.publish.quiet", "true")

    testLogging {
        events("passed", "failed", "skipped")
        showStandardStreams = true
        exceptionFormat =
            org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
    }
}

fun Test.useProjectTestClasses() {
    testClassesDirs = sourceSets.test.get().output.classesDirs
    classpath = sourceSets.test.get().runtimeClasspath
}

tasks.test {

    description = "Runs Order tests."

    include("**/OrderTest.class")

    // Change this if your actual class name differs.
    include("**/AllureReportingTest.class")

    maxParallelForks = 1
}

val orderTest by tasks.registering(Test::class) {

    group = "verification"

    description = "Runs Order repository tests."

    useProjectTestClasses()

    useJUnitPlatform()

    include("**/OrderTest.class")

    maxParallelForks = 1
}

tasks.register("w6d2BuildSummary") {

    group = "help"

    doLast {

        println(
            """
            W6D2 Build Tooling Summary

            Maven:
            mvn clean test

            Gradle:
            ./gradlew clean test

            Report:
            build/reports/tests/test/index.html

            Allure Results:
            build/allure-results

            """.trimIndent()
        )
    }
}