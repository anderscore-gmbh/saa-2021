package com.anderscore.refactoring.frontend.mapping;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class SchedulerUi implements Serializable{
	private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String cron;
    private Date createdAt;
    private Date updatedAt;
    
    public boolean hasId() {
    	return id != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SchedulerUi scheduler = (SchedulerUi) o;
        return id.equals(scheduler.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCron() {
		return cron;
	}

	public void setCron(String cron) {
		this.cron = cron;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
}