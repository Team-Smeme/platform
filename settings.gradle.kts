rootProject.name = "platform"
include( "core", "api")

pluginManagement {
    val springBootVersion: String by settings
    val springDependencyManagementVersion: String by settings
    val kotlinVersion: String by settings
    val kotlinSpringVersion: String by settings
    val kotlinJpaVersion: String by settings

    plugins {
        id("org.springframework.boot") version springBootVersion
        id("io.spring.dependency-management") version springDependencyManagementVersion
        kotlin("jvm") version kotlinVersion
        kotlin("plugin.spring") version kotlinSpringVersion
        kotlin("plugin.jpa") version kotlinJpaVersion
    }
}