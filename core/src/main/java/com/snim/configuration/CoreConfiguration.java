package com.snim.configuration;

import com.snim.configuration.jdbc.DataSourceConfiguration;
import com.snim.core.CoreBasePackage;
import com.snim.configuration.cassandra.CassandraConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
@Import({
        DisableAutoConfiguration.class,
        DataSourceConfiguration.class,
        CassandraConfiguration.class
})
@ComponentScan(basePackageClasses = CoreBasePackage.class)
public class CoreConfiguration {
    public static Map<String, Object> getProperties() {
        Map<String, Object> additionalProperties = new ConcurrentHashMap<>();

        additionalProperties.put("spring.config.location", "classpath:/core-config/, classpath:/");
        return additionalProperties;
    }
}
