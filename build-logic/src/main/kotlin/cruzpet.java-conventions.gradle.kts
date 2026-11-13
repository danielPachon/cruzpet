// Convenciones comunes a todo módulo Java del monorepo.
plugins {
    java
}

val libs = the<VersionCatalogsExtension>().named("libs")

group = "co.cruzpet"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(libs.findVersion("java").get().requiredVersion)
    }
}

dependencies {
    // Los BOM fijan las versiones de Spring y sus dependencias en un solo lugar.
    implementation(platform(libs.findLibrary("spring-boot-bom").get()))
    implementation(platform(libs.findLibrary("spring-modulith-bom").get()))
    annotationProcessor(platform(libs.findLibrary("spring-boot-bom").get()))
    testImplementation(platform(libs.findLibrary("spring-boot-bom").get()))

    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.assertj:assertj-core")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
    options.compilerArgs.addAll(listOf("-parameters", "-Xlint:all,-processing,-serial"))
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
    testLogging {
        events("failed")
        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
    }
}
