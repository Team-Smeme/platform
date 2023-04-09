package com.snim.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
        com.snim.core.writing.DataSourceConfiguration.class,
        com.snim.core.notee.DataSourceConfiguration.class
})
public class DataSourcesConfiguration {
}
