plugins {
    kotlin("jvm") version "1.7.10"
    kotlin("plugin.allopen") version "1.7.10"
    id("io.quarkus")
}

repositories {
    mavenCentral()
    mavenLocal()
    maven {
        url = uri("https://maven.pkg.github.com/Locus-Software-Limited/lib-odyssey-common")
        credentials {
            username = project.findProperty("package_user") as String? ?: System.getenv("USERNAME")
            password = project.findProperty("package_token") as String? ?: System.getenv("TOKEN")
        }
    }
}

val quarkusPlatformGroupId: String by project
val quarkusPlatformArtifactId: String by project
val quarkusPlatformVersion: String by project

dependencies {
    implementation(enforcedPlatform("${quarkusPlatformGroupId}:${quarkusPlatformArtifactId}:${quarkusPlatformVersion}"))
    implementation("io.quarkus:quarkus-resteasy-reactive-jackson")
    implementation("io.quarkus:quarkus-smallrye-openapi")
    implementation("io.quarkus:quarkus-config-yaml")
    implementation("io.quarkus:quarkus-oidc")
    implementation("io.quarkus:quarkus-kotlin")
    implementation("io.quarkus:quarkus-agroal")
    implementation("io.quarkus:quarkus-jdbc-mysql")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("io.quarkus:quarkus-arc")
    implementation("io.quarkus:quarkus-resteasy-reactive")

    implementation("odyssey:lib-odyssey-common:0.12")

    implementation("net.sf.jasperreports:jasperreports:6.20.0")
    implementation("com.lowagie:itext:2.1.7")

    testImplementation("io.quarkus:quarkus-junit5")
    testImplementation("io.rest-assured:rest-assured")
}

group = "com.locuslive"
version = "1.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

allOpen {
    annotation("javax.ws.rs.Path")
    annotation("javax.enterprise.context.ApplicationScoped")
    annotation("io.quarkus.test.junit.QuarkusTest")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = JavaVersion.VERSION_11.toString()
    kotlinOptions.javaParameters = true
}
