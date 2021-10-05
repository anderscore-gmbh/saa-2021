package com.anderscore.refactoring.frontend.page;

import javax.inject.Inject;

import org.apache.wicket.model.IModel;

import com.anderscore.refactoring.frontend.component.PageLink;
import com.anderscore.refactoring.frontend.mapping.SchedulerMapper;
import com.anderscore.refactoring.frontend.mapping.SchedulerUi;
import com.anderscore.refactoring.frontend.panel.SchedulerFormPanel;
import com.anderscore.refactoring.service.SchedulerService;

public class SchedulerEditPage extends SchedulerPage {
	private static final long serialVersionUID = 1L;
	
	@Inject
	private SchedulerService service;
	@Inject
	private SchedulerMapper mapper;
	
	private IModel<SchedulerUi> scheduler;
	
	public SchedulerEditPage(IModel<SchedulerUi> scheduler) {
		this.scheduler = scheduler;
	}
	
	@Override
	protected void onInitialize() {
		super.onInitialize();
		
		SchedulerFormPanel formPanel = createFormPanel("formPanel", scheduler);
		PageLink backButton = new PageLink("back", SchedulerOverviewPage.class);
		
		add(formPanel);
		add(backButton);
	}
	
	private SchedulerFormPanel createFormPanel(String wicketId, IModel<SchedulerUi> scheduler){
		SchedulerFormPanel formPanel = new SchedulerFormPanel(wicketId, scheduler) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSave(SchedulerUi scheduler) {
				service.update(mapper.asEntity(scheduler));
				setResponsePage(SchedulerOverviewPage.class);
			}
		};
		
		return formPanel;
	}
}