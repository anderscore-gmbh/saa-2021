package com.anderscore.refactoring.frontend.mapping;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.anderscore.refactoring.data.Scheduler;
import com.anderscore.refactoring.service.SchedulerService;

@Component
public class SchedulerMapperImpl implements SchedulerMapper{
	
	@Inject
	private SchedulerService schedulerService;

	@Override
	public SchedulerUi asUi(Scheduler scheduler) {
		SchedulerUi schedulerUi = new SchedulerUi();
		schedulerUi.setId(scheduler.getId());
		schedulerUi.setName(scheduler.getName());
		schedulerUi.setCron(scheduler.getCron());
		schedulerUi.setCreatedAt(scheduler.getCreatedAt());
		schedulerUi.setUpdatedAt(scheduler.getUpdatedAt());
		
		return schedulerUi;
	}

	@Override
	public Scheduler asEntity(SchedulerUi schedulerUi) {
		Scheduler scheduler = schedulerUi.hasId() ? schedulerService.find(schedulerUi.getId()).get() : new Scheduler();
		scheduler.setName(schedulerUi.getName());
		scheduler.setCron(schedulerUi.getCron());
		
		return scheduler;
	}
}