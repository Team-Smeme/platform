package com.snim.core.notee;

import com.datastax.oss.driver.api.core.CqlSession;
import com.snim.configuration.CassandraSeparatedProfileConfigurable;
import org.cassandraunit.CQLDataLoader;
import org.cassandraunit.dataset.cql.ClassPathCQLDataSet;
import org.cassandraunit.utils.EmbeddedCassandraServerHelper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
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

@Configuration(value = "noteeCassandraConfiguration")
@EnableCassandraRepositories(basePackageClasses = NoteeCorePackage.class)
public class CassandraConfiguration implements CassandraSeparatedProfileConfigurable {
    private static final String NOTEE_CQL_SESSION_BEAN = "noteeCqlSessionBean";
    private static final String NOTEE_SESSION_FACTORY_FACTORY_BEAN = "noteeSessionFactoryFactoryBean";
    private static final String NOTEE_CASSANDRA_MAPPING_CONTEXT = "noteeCassandraMappingContext";
    private static final String NOTEE_CASSANDRA_CONVERTER = "noteeCassandraConverter";
    private static final String NOTEE_CASSANDRA_OPERATIONS = "noteeCassandraOperations";

    @Override
    @Bean(NOTEE_CQL_SESSION_BEAN)
    public CqlSession embedded() throws IOException, InterruptedException {
        EmbeddedCassandraServerHelper.startEmbeddedCassandra();
        CqlSession session = EmbeddedCassandraServerHelper.getSession();
        new CQLDataLoader(session).load(new ClassPathCQLDataSet("cql/notee.cql", "notee"));

        return session;
    }

    @Override
    public CqlSession remote() {
        CqlSessionFactoryBean cqlSessionFactoryBean = new CqlSessionFactoryBean();

        return cqlSessionFactoryBean.getObject();
    }

    @Bean(NOTEE_CASSANDRA_MAPPING_CONTEXT)
    @Primary
    public CassandraMappingContext cassandraMappingContext() {
        CassandraMappingContext cassandraMappingContext = new CassandraMappingContext();

        cassandraMappingContext.setNamingStrategy(NamingStrategy.SNAKE_CASE);
        cassandraMappingContext.setSimpleTypeHolder(SimpleTypeHolder.DEFAULT);

        return cassandraMappingContext;
    }

    @Bean(NOTEE_CASSANDRA_CONVERTER)
    @Primary
    public CassandraConverter cassandraConverter(
            @Qualifier(NOTEE_CASSANDRA_MAPPING_CONTEXT) CassandraMappingContext cassandraMappingContext
    ) {
        return new MappingCassandraConverter(cassandraMappingContext);
    }

    @Bean(NOTEE_SESSION_FACTORY_FACTORY_BEAN)
    @Primary
    @Override
    public SessionFactoryFactoryBean SessionFactoryFactoryBeanForCreateIfNotExists(
            @Qualifier(NOTEE_CQL_SESSION_BEAN) CqlSession session,
            @Qualifier(NOTEE_CASSANDRA_CONVERTER) CassandraConverter cassandraConverter
    ) {
        SessionFactoryFactoryBean sessionFactoryFactoryBean = new SessionFactoryFactoryBean();

        sessionFactoryFactoryBean.setSession(session);
        sessionFactoryFactoryBean.setConverter(cassandraConverter);
        sessionFactoryFactoryBean.setSchemaAction(SchemaAction.CREATE_IF_NOT_EXISTS);

        return sessionFactoryFactoryBean;
    }

    @Bean(NOTEE_SESSION_FACTORY_FACTORY_BEAN)
    @Primary
    @Override
    public SessionFactoryFactoryBean SessionFactoryFactoryBeanForNone(
            @Qualifier(NOTEE_CQL_SESSION_BEAN) CqlSession session,
            @Qualifier(NOTEE_CASSANDRA_CONVERTER) CassandraConverter cassandraConverter
    ) {
        SessionFactoryFactoryBean sessionFactoryFactoryBean = new SessionFactoryFactoryBean();

        sessionFactoryFactoryBean.setSession(session);
        sessionFactoryFactoryBean.setConverter(cassandraConverter);
        sessionFactoryFactoryBean.setSchemaAction(SchemaAction.NONE);

        return sessionFactoryFactoryBean;
    }

    @Bean(NOTEE_CASSANDRA_OPERATIONS)
    @Primary
    public CassandraOperations cassandraTemplate(
            @Qualifier(NOTEE_SESSION_FACTORY_FACTORY_BEAN) SessionFactoryFactoryBean sessionFactory,
            @Qualifier(NOTEE_CASSANDRA_CONVERTER) CassandraConverter cassandraConverter
    ) throws Exception {
        return new CassandraTemplate(
                Objects.requireNonNull(sessionFactory.getObject()) // DefaultSessionFactory
                , cassandraConverter
        );
    }
}
