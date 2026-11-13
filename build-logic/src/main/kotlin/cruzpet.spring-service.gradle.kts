// Microservicio desplegable: aplicación Spring Boot con imagen OCI propia.
plugins {
    id("cruzpet.java-conventions")
    id("org.springframework.boot")
}

tasks.named<org.springframework.boot.gradle.tasks.bundling.BootBuildImage>("bootBuildImage") {
    imageName = "ghcr.io/danielpachon/cruzpet-${project.name}:${project.findProperty("imageTag") ?: "local"}"
    environment = mapOf("BP_JVM_VERSION" to "25")
}
