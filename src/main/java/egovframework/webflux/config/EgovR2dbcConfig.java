package egovframework.webflux.config;

import io.r2dbc.spi.ConnectionFactory;
import org.egovframe.rte.psl.reactive.r2dbc.connect.EgovR2dbcConnectionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.r2dbc.connection.R2dbcTransactionManager;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;
import org.springframework.transaction.ReactiveTransactionManager;

@Configuration
@PropertySource("classpath:application.yml")
public class EgovR2dbcConfig {

    @Value("${spring.r2dbc.url}")
    private String r2dbcUrl;

    @Bean(name="connectionFactory")
    public ConnectionFactory connectionFactory() {
        EgovR2dbcConnectionFactory egovR2dbcConnectionFactory = new EgovR2dbcConnectionFactory(this.r2dbcUrl);
        return egovR2dbcConnectionFactory.connectionFactory();
    }

    @Bean
    public ConnectionFactoryInitializer initializer(@Qualifier("connectionFactory") ConnectionFactory connectionFactory) {
        ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
        initializer.setConnectionFactory(connectionFactory);
        initializer.setDatabasePopulator(new ResourceDatabasePopulator(new ClassPathResource("db/sampledb.sql")));
        return initializer;
    }

    @Bean
    public ReactiveTransactionManager transactionManager(@Qualifier("connectionFactory") ConnectionFactory connectionFactory) {
        return new R2dbcTransactionManager(connectionFactory);
    }

}
