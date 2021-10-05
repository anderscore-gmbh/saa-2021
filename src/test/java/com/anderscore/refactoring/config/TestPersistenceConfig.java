package com.anderscore.refactoring.config;

import com.anderscore.refactoring.config.PersistenceConfig;

import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.*;
import javax.sql.DataSource;

@Configuration
@Profile("test")
@Import(PersistenceConfig.class)
public class TestPersistenceConfig {

    @Bean
    public Flyway flyway(DataSource dataSource) {
        Flyway flyway = Flyway.configure().dataSource(dataSource).load();
        flyway.migrate();

        return flyway;
    }
}