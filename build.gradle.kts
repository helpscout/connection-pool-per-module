plugins {
    application
    id("net.helpscout.core.jvm-conventions")
}

group = "helpscout"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

allprojects {
    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "net.helpscout.core.jvm-conventions")

    tasks {
        bootJar {
            enabled = false
        }

        jar {
            enabled = true
        }
    }
}

dependencies {
    implementation(project(":notification"))
    implementation(project(":shared"))

    implementation("org.flywaydb:flyway-core")
    implementation("org.flywaydb:flyway-mysql:10.19.0")
}
