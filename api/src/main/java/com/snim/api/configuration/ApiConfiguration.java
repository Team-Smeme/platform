package com.snim.api.configuration;

import com.snim.api.ApiBasePackage;
import com.snim.configuration.CoreConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackageClasses = ApiBasePackage.class)
@Import(
        CoreConfiguration.class
)
public class ApiConfiguration {
}
