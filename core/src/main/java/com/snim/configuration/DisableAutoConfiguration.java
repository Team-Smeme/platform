package com.snim.configuration;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.cassandra.CassandraAutoConfiguration;
import org.springframework.boot.autoconfigure.data.cassandra.CassandraDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.cassandra.CassandraReactiveDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.cassandra.CassandraRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration(exclude = {
        // jdbc datasource
        DataSourceAutoConfiguration.class,

        // cassandra
        CassandraAutoConfiguration.class,
        CassandraDataAutoConfiguration.class,
        CassandraRepositoriesAutoConfiguration.class,
        CassandraReactiveDataAutoConfiguration.class
})
public class DisableAutoConfiguration {
}
