plugins {
    id("cruzpet.java-library")
}

dependencies {
    api("org.springframework.boot:spring-boot-starter-security")
    api("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
    api("org.springframework.boot:spring-boot-starter-webmvc")

    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
}
