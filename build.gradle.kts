
plugins {
    kotlin("jvm") version "1.9.0"
    application
    id("io.ktor.plugin") version "2.3.4"
}

group = "org.collect.invest"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap") }
    maven { url = uri("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/dev")}

}

dependencies {
    testImplementation(kotlin("test"))
    implementation("io.ktor:ktor-server-core-jvm:2.3.4")
    implementation("io.ktor:ktor-server-netty-jvm:2.3.4")
    implementation("ch.qos.logback:logback-classic:1.4.11")

}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(8)
}

application {
    mainClass.set("MainKt")
}