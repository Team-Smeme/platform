package com.snim.core.writing;

import com.datastax.oss.driver.api.core.CqlSession;
import com.google.common.collect.Lists;
import com.snim.configuration.CassandraSeparatedProfileConfigurable;
import org.cassandraunit.CQLDataLoader;
import org.cassandraunit.dataset.cql.ClassPathCQLDataSet;
import org.cassandraunit.utils.EmbeddedCassandraServerHelper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.cassandra.config.CassandraEntityClassScanner;
import org.springframework.data.cassandra.config.CqlSessionFactoryBean;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.config.SessionFactoryFactoryBean;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.core.convert.CassandraConverter;
import org.springframework.data.cassandra.core.convert.CassandraCustomConversions;
import org.springframework.data.cassandra.core.convert.MappingCassandraConverter;
import org.springframework.data.cassandra.core.mapping.CassandraMappingContext;
import org.springframework.data.cassandra.core.mapping.NamingStrategy;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;
import org.springframework.data.mapping.model.SimpleTypeHolder;

import java.io.IOException;
import java.util.Objects;

@Configuration(value = "writingCassandraConfiguration")
@EnableCassandraRepositories(basePackageClasses = WritingCorePackage.class)
public class CassandraConfiguration implements CassandraSeparatedProfileConfigurable {
    private static final String WRITING_CQL_SESSION_BEAN = "writingCqlSessionBean";
    private static final String WRITING_SESSION_FACTORY_FACTORY_BEAN = "writingSessionFactoryFactoryBean";
    private static final String WRITING_CASSANDRA_MAPPING_CONTEXT = "writingCassandraMappingContext";
    private static final String WRITING_CASSANDRA_CONVERTER = "writingCassandraConverter";
    private static final String WRITING_CASSANDRA_OPERATIONS = "writingCassandraOperations";

    @Override
    @Bean(WRITING_CQL_SESSION_BEAN)
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

    @Bean(WRITING_CASSANDRA_MAPPING_CONTEXT)
    @Primary
    public CassandraMappingContext cassandraMappingContext() throws ClassNotFoundException {
        CassandraMappingContext cassandraMappingContext = new CassandraMappingContext();

        cassandraMappingContext.setInitialEntitySet(CassandraEntityClassScanner.scan(WritingCorePackage.class));
        cassandraMappingContext.setNamingStrategy(NamingStrategy.SNAKE_CASE);
        cassandraMappingContext.setSimpleTypeHolder(SimpleTypeHolder.DEFAULT);

        return cassandraMappingContext;
    }

    @Bean(WRITING_CASSANDRA_CONVERTER)
    @Primary
    public CassandraConverter cassandraConverter(
            @Qualifier(WRITING_CASSANDRA_MAPPING_CONTEXT) CassandraMappingContext cassandraMappingContext
    ) {
        MappingCassandraConverter mappingCassandraConverter = new MappingCassandraConverter(cassandraMappingContext);
        mappingCassandraConverter.setCustomConversions(new CassandraCustomConversions(
                Lists.newArrayList(
                        new WritingData2JsonConverter(),
                        new Json2WritingDataConverter()
                )
        ));

        return mappingCassandraConverter;
    }

    @Bean(WRITING_SESSION_FACTORY_FACTORY_BEAN)
    @Primary
    @Override
    public SessionFactoryFactoryBean SessionFactoryFactoryBeanForCreateIfNotExists(
            @Qualifier(WRITING_CQL_SESSION_BEAN) CqlSession session,
            @Qualifier(WRITING_CASSANDRA_CONVERTER) CassandraConverter cassandraConverter
    ) {
        SessionFactoryFactoryBean sessionFactoryFactoryBean = new SessionFactoryFactoryBean();

        sessionFactoryFactoryBean.setSession(session);
        sessionFactoryFactoryBean.setConverter(cassandraConverter);
        sessionFactoryFactoryBean.setSchemaAction(SchemaAction.CREATE_IF_NOT_EXISTS);

        return sessionFactoryFactoryBean;
    }

    @Bean(WRITING_SESSION_FACTORY_FACTORY_BEAN)
    @Primary
    @Override
    public SessionFactoryFactoryBean SessionFactoryFactoryBeanForNone(
            @Qualifier(WRITING_CQL_SESSION_BEAN) CqlSession session,
            @Qualifier(WRITING_CASSANDRA_CONVERTER) CassandraConverter cassandraConverter
    ) {
        SessionFactoryFactoryBean sessionFactoryFactoryBean = new SessionFactoryFactoryBean();

        sessionFactoryFactoryBean.setSession(session);
        sessionFactoryFactoryBean.setConverter(cassandraConverter);
        sessionFactoryFactoryBean.setSchemaAction(SchemaAction.NONE);

        return sessionFactoryFactoryBean;
    }

    @Bean(WRITING_CASSANDRA_OPERATIONS)
    @Primary
    public CassandraOperations cassandraTemplate(
            @Qualifier(WRITING_SESSION_FACTORY_FACTORY_BEAN) SessionFactoryFactoryBean sessionFactory,
            @Qualifier(WRITING_CASSANDRA_CONVERTER) CassandraConverter cassandraConverter
    ) throws Exception {
        return new CassandraTemplate(
                Objects.requireNonNull(sessionFactory.getObject()) // DefaultSessionFactory
                , cassandraConverter
        );
    }
}
