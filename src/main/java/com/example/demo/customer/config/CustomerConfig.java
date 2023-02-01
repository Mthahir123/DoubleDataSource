package com.example.demo.customer.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "customerEntityManagerFactory", transactionManagerRef = "customerTransactionManager", basePackages = {
		"com.example.demo.customer.repository" })
public class CustomerConfig {

	@Primary
	@Bean(name = "customerDataSource")
	@ConfigurationProperties(prefix = "spring.datasource")
	DataSource customerDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Primary
	@Bean(name = "customerEntityManagerFactory")
	LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder,
			@Qualifier("customerDataSource") DataSource dataSource) {
		return builder.dataSource(dataSource).packages("com.example.demo.customer.model").persistenceUnit("db1")
				.build();
	}

	@Primary
	@Bean(name = "customerTransactionManager")
	PlatformTransactionManager customerTransactionManager(
			@Qualifier("customerEntityManagerFactory") EntityManagerFactory customerEntityManagerFactory) {
		return new JpaTransactionManager(customerEntityManagerFactory);
	}
}
