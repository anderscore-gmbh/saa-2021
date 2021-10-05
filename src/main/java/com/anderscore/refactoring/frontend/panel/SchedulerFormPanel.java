package com.anderscore.refactoring.frontend.panel;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;

import com.anderscore.refactoring.frontend.mapping.SchedulerUi;

public abstract class SchedulerFormPanel extends Panel{
	private static final long serialVersionUID = 1L;

	public SchedulerFormPanel(String wicketId, IModel<SchedulerUi> scheduler) {
		super(wicketId, new CompoundPropertyModel<>(scheduler));
	}
	
	@Override
	protected void onInitialize() {
		super.onInitialize();
		
		add(createForm("form"));
	}
	
	private Form<SchedulerUi> createForm(String wicketId){
		Form<SchedulerUi> form = new Form<SchedulerUi>(wicketId) {
			private static final long serialVersionUID = 1L;
			
			@Override
			protected void onInitialize() {
				super.onInitialize();
				
				add(new TextField<String>("name"));
				add(new TextField<String>("cron"));
			}
			
			@Override
			protected void onSubmit() {
				onSave((SchedulerUi) SchedulerFormPanel.this.getDefaultModelObject()); 
			}
		};
		
		return form;
	}
	
	protected abstract void onSave(SchedulerUi scheduler);
}