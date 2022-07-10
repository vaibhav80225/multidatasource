package com.itvaib.multidatasource.product.config;

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

/**
 * Creating seprated config datasource class- which contain all the config related to product in
 * this configuration
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.itvaib.multidatasource.product.*",
entityManagerFactoryRef = "productEntityManager",
transactionManagerRef = "productTransactionManager")
public class ProductDataSourceConfig {

    @Bean
    public DataSource productDataSource(){
        return productDataSourceProperties()
                .initializeDataSourceBuilder()
                .build();
    }

    /**
     * Getting all the db related config from the application.yaml file using the
     * configurationproperty annotation
     * @return
     */
    @Bean
    @ConfigurationProperties("spring.product")
    public DataSourceProperties productDataSourceProperties(){
        return new DataSourceProperties();

    }

    /**
     * Creating Entity manager which handle all the entity that belong to product
     * for that we need to define the packages which contain product db entity
     * @param dataSource
     * @param entityManagerFactoryBuilder
     * @return
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean productEntityManager(@Qualifier("productDataSource") DataSource dataSource,
                                                                       EntityManagerFactoryBuilder entityManagerFactoryBuilder){
        return entityManagerFactoryBuilder
                .dataSource(productDataSource())
                .packages("com.itvaib.multidatasource.product.entity")
                .build();

    }

    /**
     * This return the jpa transaction manager based on the productEntityManager that we created above
     * @param productEntityManager
     * @return
     */
    @Bean
    public PlatformTransactionManager productTransactionManager(
        @Qualifier("productEntityManager") LocalContainerEntityManagerFactoryBean productEntityManager) {
            return new JpaTransactionManager(Objects.requireNonNull(productEntityManager.getObject()));
    }
}
