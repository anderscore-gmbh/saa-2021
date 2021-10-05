package com.anderscore.refactoring.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.anderscore.refactoring.data.SchedulerRepository;
import com.anderscore.refactoring.service.SchedulerService;

@Configuration
@Profile("test")
@Import(TestPersistenceConfig.class)
@ComponentScan(basePackageClasses = {SchedulerService.class})
@EnableJpaRepositories(basePackageClasses = SchedulerRepository.class)
public class TestConfig {
}