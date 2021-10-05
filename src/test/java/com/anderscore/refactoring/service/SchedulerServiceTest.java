package com.anderscore.refactoring.service;

import com.anderscore.refactoring.config.TestConfig;
import com.anderscore.refactoring.data.Scheduler;
import com.anderscore.refactoring.service.SchedulerService;

import org.flywaydb.test.FlywayTestExecutionListener;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

@SpringJUnitConfig(TestConfig.class)
@ActiveProfiles("test")
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, FlywayTestExecutionListener.class})
public class SchedulerServiceTest {

    @Inject
    private SchedulerService schedulerService;

    @Test
    @FlywayTest(locationsForMigrate = {"db/scheduler_data"})
    public void findAll() {
        List<Scheduler> result = schedulerService.findAll();

        assertEquals(2, result.size());
    }

    @Test
    public void store() {
        Scheduler scheduler = new Scheduler();
        scheduler.setName("ElBarto");
        scheduler.setCron("45 23 * * 6");

        schedulerService.store(scheduler);

        Optional<Scheduler> saveScheduler = schedulerService.findByName("ElBarto");

        assertTrue(saveScheduler.isPresent());
        assertEquals(saveScheduler.get().getName(), scheduler.getName());
        assertEquals(saveScheduler.get().getCron(), scheduler.getCron());
    }
}