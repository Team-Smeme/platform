package com.snim.api

import com.snim.configuration.CoreConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ApiApplication

fun main(args: Array<String>) {
    runApplication<ApiApplication>(*args) {
        setDefaultProperties(CoreConfiguration.getProperties())
    }
}
