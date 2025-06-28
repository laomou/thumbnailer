plugins {
    kotlin("jvm") version "2.1.20"
    `maven-publish`
}

group = "com.laomou"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.apache.commons:commons-imaging:1.0-alpha3")
    implementation("com.twelvemonkeys.imageio:imageio-jpeg:3.12.0")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(8)
}

java {
    withSourcesJar()
    withJavadocJar()
}

tasks.processResources {
    from("src/main/resources")

    from("libs") {
        into("META-INF/lib")
    }

    duplicatesStrategy = DuplicatesStrategy.INCLUDE
    fileMode = 775
}


publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }
}