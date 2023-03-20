package com.snim.configuration;

import com.datastax.oss.driver.api.core.CqlSession;
import org.springframework.context.annotation.Profile;
import org.springframework.data.cassandra.config.SessionFactoryFactoryBean;
import org.springframework.data.cassandra.core.convert.CassandraConverter;

public interface CassandraSeparatedProfileConfigurable extends SeparatedProfileConfigurable<CqlSession> {
    @Profile({"test", "local"})
    SessionFactoryFactoryBean SessionFactoryFactoryBeanForCreateIfNotExists(
            CqlSession session,
            CassandraConverter cassandraConverter
    );

    @Profile({"!test", "!local"})
    SessionFactoryFactoryBean SessionFactoryFactoryBeanForNone(
            CqlSession session,
            CassandraConverter cassandraConverter
    );
}
