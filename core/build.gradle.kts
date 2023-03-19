import org.springframework.boot.gradle.tasks.bundling.BootJar

group = "com.snim.core"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

tasks.named<BootJar>("bootJar") {
    this.enabled = false
}

tasks.named<Jar>("jar") {
    this.enabled = true
}