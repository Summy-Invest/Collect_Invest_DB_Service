
plugins {
    kotlin("jvm") version "1.9.0"
    application
    id("io.ktor.plugin") version "2.3.5"
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
    implementation("org.postgresql:postgresql:42.2.27")
    implementation("io.ktor:ktor-server-core-jvm:2.3.5")
    implementation("io.ktor:ktor-server-netty-jvm:2.3.5")
    implementation("ch.qos.logback:logback-classic:1.4.11")
    implementation("io.ktor:ktor-server-swagger-jvm")
    implementation ("io.ktor:ktor-locations:1.6.4")
    implementation("io.ktor:ktor-serialization-gson-jvm")
    implementation("io.ktor:ktor-server-cors:2.3.5")
    implementation("io.ktor:ktor-server-content-negotiation:2.3.5")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}

application {
    mainClass.set("MainKt")
}