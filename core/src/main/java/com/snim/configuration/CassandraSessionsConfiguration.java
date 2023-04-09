package com.snim.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
        com.snim.core.writing.CassandraConfiguration.class,
        com.snim.core.notee.CassandraConfiguration.class
})
public class CassandraSessionsConfiguration {
}
