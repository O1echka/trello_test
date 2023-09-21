plugins {
    id("java")
}

group = "trello.com"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.rest-assured:rest-assured:5.3.1");
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.0");
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.0");
}

tasks.test {
    useJUnitPlatform()
}