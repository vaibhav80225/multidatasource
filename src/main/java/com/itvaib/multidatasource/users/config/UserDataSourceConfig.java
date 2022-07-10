package com.itvaib.multidatasource.users.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Objects;

/**
 * Creating seprated config datasource class- which contain all the config related to Users in
 * this configuration
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.itvaib.multidatasource.users.*",
        entityManagerFactoryRef = "userDataSourceManagerFactory",
        transactionManagerRef = "userTransactionManager")
public class UserDataSourceConfig {

    @Bean
    @Primary
    public DataSource userDataSource(){
        return userDataSourceProperties().initializeDataSourceBuilder().build();
    }

    /**
     * Getting all the db related config from the application.yaml file using the
     * configurationproperty annotation
     * @return
     */
    @Bean
    @ConfigurationProperties("spring.datasource")
    public DataSourceProperties userDataSourceProperties(){
        return new DataSourceProperties();
    }

    /**
     * Creating Entity manager which handle all the entity that belong to Users
     * for that we need to define the packages which contain product db entity
     * and also Mraked this bean as primary to set default config
     * @param dataSource
     * @param entityManagerFactoryBuilder
     * @return
     */
    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean userDataSourceManagerFactory(@Qualifier("userDataSource")DataSource dataSource,
                                                                               EntityManagerFactoryBuilder entityManagerFactoryBuilder){
        return entityManagerFactoryBuilder
                .dataSource(userDataSource())
                .packages("com.itvaib.multidatasource.users.entity")
                .build();
    }

    /**
     * This return the jpa transaction manager based on the userDataSourceManagerFactory that we created above
     * @param userDataSourceManagerFactory
     * @return
     */
    @Bean
    @Primary
    public PlatformTransactionManager userTransactionManager(
            @Qualifier("userDataSourceManagerFactory") LocalContainerEntityManagerFactoryBean userDataSourceManagerFactory) {
        return new JpaTransactionManager(Objects.requireNonNull(userDataSourceManagerFactory.getObject()));
    }
}
