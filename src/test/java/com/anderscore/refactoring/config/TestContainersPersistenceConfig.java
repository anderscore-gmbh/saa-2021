package com.anderscore.refactoring.config;

import com.anderscore.refactoring.config.PersistenceConfig;
import com.anderscore.refactoring.util.DatabaseContainerHolder;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.testcontainers.containers.JdbcDatabaseContainer;

import javax.inject.Inject;
import javax.sql.DataSource;

@Configuration
@Profile("test")
@Import(PersistenceConfig.class)
@ComponentScan(basePackageClasses = DatabaseContainerHolder.class)
public class TestContainersPersistenceConfig {

    @Inject
    private Environment env;

    @Bean
    public Flyway flyway(DatabaseContainerHolder containerHolder) {
        JdbcDatabaseContainer<?> dbContainer = containerHolder.get();

        Flyway flyway = Flyway.configure().dataSource(dbContainer.getJdbcUrl(),
                dbContainer.getUsername(),
                dbContainer.getPassword())
                .load();

        flyway.migrate();

        return flyway;
    }

    @Bean
    public DataSource dataSource(DatabaseContainerHolder containerHolder) {
        JdbcDatabaseContainer<?> dbContainer = containerHolder.get();

        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(dbContainer.getJdbcUrl());
        hikariConfig.setUsername(dbContainer.getUsername());
        hikariConfig.setPassword(dbContainer.getPassword());
        hikariConfig.setDriverClassName(env.getProperty("jdbc.driverClassName"));

        return new HikariDataSource(hikariConfig);
    }
}