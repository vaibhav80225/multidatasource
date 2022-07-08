package com.itvaib.multidatasource.users.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Objects;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.itvaib.multidatasource.users.*",
        entityManagerFactoryRef = "userDataSourceManagerFactory",
        transactionManagerRef = "userTransactionManager")
public class UserDataSourceConfig {

    @Bean
    public DataSource userDataSource(){
        return userDataSourceProperties().initializeDataSourceBuilder().build();
    }
    @Bean
    @ConfigurationProperties("my.datasource")
    public DataSourceProperties userDataSourceProperties(){
        return new DataSourceProperties();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean userDataSourceManagerFactory(@Qualifier("userDataSource")DataSource dataSource,
                                                                               EntityManagerFactoryBuilder entityManagerFactoryBuilder){
        return entityManagerFactoryBuilder
                .dataSource(userDataSource())
                .packages("com.itvaib.multidatasource.users.entity")
                .build();
    }

    @Bean
    public PlatformTransactionManager userTransactionManager(
            @Qualifier("userDataSourceManagerFactory") LocalContainerEntityManagerFactoryBean userDataSourceManagerFactory) {
        return new JpaTransactionManager(Objects.requireNonNull(userDataSourceManagerFactory.getObject()));
    }
}
