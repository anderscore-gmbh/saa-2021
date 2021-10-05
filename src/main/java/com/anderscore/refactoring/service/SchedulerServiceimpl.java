package com.anderscore.refactoring.service;

import static java.util.stream.Collectors.toList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.anderscore.refactoring.data.Scheduler;
import com.anderscore.refactoring.data.SchedulerRepository;

@Service
public class SchedulerServiceimpl implements SchedulerService {
	
	@Inject
	private SchedulerRepository repository;

	@Override
	public void store(Scheduler scheduler) {
		Date now = new Date();
		
		scheduler.setCreatedAt(now);
		scheduler.setUpdatedAt(now);
		
		repository.save(scheduler);
	}
	
	@Override
	public Optional<Scheduler> find(long id) {
		return repository.findById(id);
	}
	
	@Override
	public Optional<Scheduler> findByName(String name) {
		return repository.findByName(name);
	}

	@Override
	public List<Scheduler> findAll() {
		return repository.findAll();
	}

	@Override
	public List<Scheduler> findAll(long first, long count) {
		return repository.findAll().stream().sorted().skip(first).limit(count).collect(toList());
	}
	
	@Override
	public long count() {
		return repository.count();
	}

	@Override
	public void update(Scheduler scheduler) {
		scheduler.setUpdatedAt(new Date());
		repository.save(scheduler);
	}

	@Override
	public void delete(Scheduler scheduler) {
		repository.delete(scheduler);
	}
}