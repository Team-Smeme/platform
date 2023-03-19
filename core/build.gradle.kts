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

dependencies {
    implementation("org.springframework.data:spring-data-cassandra:4.0.3")
    implementation("org.cassandraunit:cassandra-unit:4.3.1.0")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("com.h2database:h2")
}