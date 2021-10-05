package com.anderscore.refactoring.frontend.mapping;

import com.anderscore.refactoring.data.Scheduler;

public interface SchedulerMapper {
	
	SchedulerUi asUi(Scheduler scheduler);
	
	Scheduler asEntity(SchedulerUi schedulerUi);
}