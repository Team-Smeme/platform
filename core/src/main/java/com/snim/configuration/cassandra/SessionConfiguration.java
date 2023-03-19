package com.snim.configuration.cassandra;

import com.datastax.oss.driver.api.core.CqlSession;
import com.snim.configuration.SeparatedProfileConfigurable;
import org.cassandraunit.CQLDataLoader;
import org.cassandraunit.dataset.cql.ClassPathCQLDataSet;
import org.cassandraunit.utils.EmbeddedCassandraServerHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class SessionConfiguration implements SeparatedProfileConfigurable<CqlSession> {
    private static final String CQL_SESSION_BEAN = "cqlSessionBean";

    @Override
    @Bean(CQL_SESSION_BEAN)
    public CqlSession embedded() throws IOException, InterruptedException {
        EmbeddedCassandraServerHelper.startEmbeddedCassandra();
        CqlSession session = EmbeddedCassandraServerHelper.getSession();
        new CQLDataLoader(session).load(new ClassPathCQLDataSet("cql/writing.cql", "writing"));

        return session;
    }

    @Override
    public CqlSession remote() {
        return null;
    }
}
