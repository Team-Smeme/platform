package com.snim.configuration.cassandra;

import com.datastax.oss.driver.api.core.CqlSession;
import com.snim.configuration.SeparatedProfileConfigurable;
import com.snim.core.CoreBasePackage;
import org.cassandraunit.CQLDataLoader;
import org.cassandraunit.dataset.cql.ClassPathCQLDataSet;
import org.cassandraunit.utils.EmbeddedCassandraServerHelper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.cassandra.config.CqlSessionFactoryBean;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.config.SessionFactoryFactoryBean;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.core.convert.CassandraConverter;
import org.springframework.data.cassandra.core.convert.MappingCassandraConverter;
import org.springframework.data.cassandra.core.mapping.CassandraMappingContext;
import org.springframework.data.cassandra.core.mapping.NamingStrategy;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;
import org.springframework.data.mapping.model.SimpleTypeHolder;

import java.io.IOException;
import java.util.Objects;

@Configuration
@EnableCassandraRepositories(basePackageClasses = CoreBasePackage.class)
public class SessionConfiguration implements SeparatedProfileConfigurable<CqlSession> {
    private static final String CQL_SESSION_BEAN = "cqlSessionBean";
    private static final String CQL_SESSION_FACTORY_BEAN = "cqlSessionFactoryBean";
    private static final String SESSION_FACTORY_FACTORY_BEAN = "sessionFactoryFactoryBean";
    private static final String CASSANDRA_MAPPING_CONTEXT = "cassandraMappingContext";
    private static final String CASSANDRA_CONVERTER = "cassandraConverter";
    private static final String CASSANDRA_OPERATIONS = "cassandraOperations";

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
        CqlSessionFactoryBean cqlSessionFactoryBean = new CqlSessionFactoryBean();

        return cqlSessionFactoryBean.getObject();
    }

    @Bean(CASSANDRA_MAPPING_CONTEXT)
    @Primary
    public CassandraMappingContext cassandraMappingContext() {
        CassandraMappingContext cassandraMappingContext = new CassandraMappingContext();

        cassandraMappingContext.setNamingStrategy(NamingStrategy.SNAKE_CASE);
        cassandraMappingContext.setSimpleTypeHolder(SimpleTypeHolder.DEFAULT);

        return cassandraMappingContext;
    }

    @Bean(CASSANDRA_CONVERTER)
    @Primary
    public CassandraConverter cassandraConverter(
            @Qualifier(CASSANDRA_MAPPING_CONTEXT) CassandraMappingContext cassandraMappingContext
    ) {
        return new MappingCassandraConverter(cassandraMappingContext);
    }

    @Profile({"test", "local"})
    @Bean(SESSION_FACTORY_FACTORY_BEAN)
    @Primary
    public SessionFactoryFactoryBean SessionFactoryFactoryBeanForCreateIfNotExists(
            @Qualifier(CQL_SESSION_BEAN) CqlSession session,
            @Qualifier(CASSANDRA_CONVERTER) CassandraConverter cassandraConverter
    ) {
        SessionFactoryFactoryBean sessionFactoryFactoryBean = new SessionFactoryFactoryBean();

        sessionFactoryFactoryBean.setSession(session);
        sessionFactoryFactoryBean.setConverter(cassandraConverter);
        sessionFactoryFactoryBean.setSchemaAction(SchemaAction.CREATE_IF_NOT_EXISTS);

        return sessionFactoryFactoryBean;
    }

    @Profile({"!test", "!local"})
    @Bean(SESSION_FACTORY_FACTORY_BEAN)
    @Primary
    public SessionFactoryFactoryBean SessionFactoryFactoryBeanForNone(
            @Qualifier(CQL_SESSION_BEAN) CqlSession session,
            @Qualifier(CASSANDRA_CONVERTER) CassandraConverter cassandraConverter
    ) {
        SessionFactoryFactoryBean sessionFactoryFactoryBean = new SessionFactoryFactoryBean();

        sessionFactoryFactoryBean.setSession(session);
        sessionFactoryFactoryBean.setConverter(cassandraConverter);
        sessionFactoryFactoryBean.setSchemaAction(SchemaAction.NONE);

        return sessionFactoryFactoryBean;
    }

    @Bean(CASSANDRA_OPERATIONS)
    @Primary
    public CassandraOperations cassandraTemplate(
            @Qualifier(SESSION_FACTORY_FACTORY_BEAN) SessionFactoryFactoryBean sessionFactory,
            @Qualifier(CASSANDRA_CONVERTER) CassandraConverter cassandraConverter
    ) throws Exception {
        return new CassandraTemplate(
                Objects.requireNonNull(sessionFactory.getObject()) // DefaultSessionFactory
                , cassandraConverter
        );
    }
}
