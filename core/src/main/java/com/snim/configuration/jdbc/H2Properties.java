package com.snim.configuration.jdbc;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseFactory.DEFAULT_DATABASE_NAME;

@ConfigurationProperties("h2")
@ConstructorBinding
public class H2Properties {
    private final String dbName;

    public H2Properties(String dbName) {
        this.dbName = dbName;
    }

    public String getDbName() {
        return dbName != null
                ? dbName
                : DEFAULT_DATABASE_NAME;
    }
}
