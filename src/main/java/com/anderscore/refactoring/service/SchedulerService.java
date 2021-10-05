package com.anderscore.refactoring.service;

import java.util.List;
import java.util.Optional;

import com.anderscore.refactoring.data.Scheduler;

public interface SchedulerService {
	
	void store(Scheduler scheduler);
	
	Optional<Scheduler> find(long id);
	
	Optional<Scheduler> findByName(String name);
	
	List<Scheduler> findAll();
	
	List<Scheduler> findAll(long first, long count);
	
	long count();
	
	void update(Scheduler scheduler);
	
	void delete(Scheduler scheduler);
}