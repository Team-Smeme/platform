package com.snim.core.notee;

import com.snim.configuration.SeparatedProfileConfigurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseFactory;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

@Configuration(value = "noteeDataSourceConfiguration")
public class DataSourceConfiguration implements SeparatedProfileConfigurable<DataSource> {
    private static final String NOTEE_DATA_SOURCE_BEAN = "noteeDataSourceBean";

    @Override
    @Bean(NOTEE_DATA_SOURCE_BEAN)
    public DataSource embedded() {
        return H2Factory.embeddedDatabaseFactory().getDatabase();
    }

    @Override
    @Bean(NOTEE_DATA_SOURCE_BEAN)
    public DataSource remote() {
        return null;
    }

    private static class H2Factory {
        private static EmbeddedDatabaseFactory embeddedDatabaseFactory() {
            EmbeddedDatabaseFactory embeddedDatabaseFactory = new EmbeddedDatabaseFactory();

            embeddedDatabaseFactory.setDatabaseName("notee");
            embeddedDatabaseFactory.setDatabaseType(EmbeddedDatabaseType.H2);
            return embeddedDatabaseFactory;
        }
    }

    private static class MySqlFactory {

    }
}
