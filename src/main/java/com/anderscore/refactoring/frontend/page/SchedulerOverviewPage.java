package com.anderscore.refactoring.frontend.page;

import static java.util.Locale.GERMAN;
import static java.util.ResourceBundle.getBundle;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.MissingResourceException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.link.ResourceLink;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.resource.CharSequenceResource;
import org.apache.wicket.request.resource.IResource;

import com.anderscore.refactoring.csv.products.Exception;
import com.anderscore.refactoring.csv.products.ProductField;
import com.anderscore.refactoring.frontend.mapping.SchedulerMapper;
import com.anderscore.refactoring.frontend.mapping.SchedulerUi;
import com.anderscore.refactoring.service.SchedulerService;
import com.giffing.wicket.spring.boot.context.scan.WicketHomePage;

@WicketHomePage
public class SchedulerOverviewPage extends SchedulerPage {
	private static final long serialVersionUID = 1L;
	
	@Inject
	private SchedulerService service;
	@Inject
	private SchedulerMapper mapper;
	
	@Override
	protected void onInitialize() {
		super.onInitialize();
		
		IDataProvider<SchedulerUi> dataProvider = createDataProvider();
		DataView<SchedulerUi> dataView = createDataView("schedulers", dataProvider);
		Link<Void> newLink = createNewLink("new");
		
		add(dataView);
		add(newLink);
	}
	
	private IDataProvider<SchedulerUi> createDataProvider(){
		IDataProvider<SchedulerUi> dataProvider = new IDataProvider<SchedulerUi>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Iterator<? extends SchedulerUi> iterator(long first, long count) {
				return service.findAll(first, count).stream().map(mapper::asUi).iterator();
			}

			@Override
			public long size() {
				return service.count();
			}

			@Override
			public IModel<SchedulerUi> model(SchedulerUi object) {
				return Model.of(object);
			}
		};
		
		return dataProvider;
	}
	
	private DataView<SchedulerUi>  createDataView(String wicketId, IDataProvider<SchedulerUi> dataProvider) {
		DataView<SchedulerUi> dataView = new DataView<SchedulerUi>(wicketId, dataProvider) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(Item<SchedulerUi> item) {
				IModel<SchedulerUi> model = new CompoundPropertyModel<>(item.getModel());
				item.setDefaultModel(model);
				
				Link<Void> editLink = new Link<Void>("edit") {
					private static final long serialVersionUID = 1L;

					@Override
					public void onClick() {
						SchedulerEditPage editPage = new SchedulerEditPage(model);
						setResponsePage(editPage);
					}
				};
				
				Link<Void> deleteLink = new Link<Void>("delete") {
					private static final long serialVersionUID = 1L;

					@Override
					public void onClick() {
						service.delete(mapper.asEntity(model.getObject()));
					}
				};
				
				IResource resource = new CharSequenceResource("text/csv", null, "scheduler_" + model.getObject().getId()  + ".csv") {
					private static final long serialVersionUID = 1L;

					@Override
					protected CharSequence getData(final Attributes attributes){
						DateUtils utils = new DateUtils(Arrays.asList(new ProductField() {
							@Override
							public String getLabel() {
								return "Name";
							}
							@Override
							public String getProperty() {
								return "name";
							}
						}, new ProductField() {
							@Override
							public String getLabel() {
								return "Cron";
							}
							@Override
							public String getProperty() {
								return "cron";
							}
						}, new ProductField() {
							@Override
							public String getLabel() {
								return "Erstellt am";
							}
							@Override
							public String getProperty() {
								return "createdAt";
							}
						}, new ProductField() {
							@Override
							public String getLabel() {
								return "Aktualisiert am";
							}
							@Override
							public String getProperty() {
								return "updatedAt";
							}
						}));
						
						return new StringBuilder().append(utils.format(mapper.asEntity(model.getObject())).toString());
					}
				};
				
				ResourceLink<Void> csvExportLink = new ResourceLink<>("csvExport", resource);
				
				item.add(new Label("id"));
				item.add(new Label("name"));
				item.add(new Label("cron"));
				item.add(new Label("createdAt"));
				item.add(new Label("updatedAt"));
				
				item.add(editLink);
				item.add(deleteLink);
				item.add(csvExportLink);
			}
		};
		
		return dataView;
	}
	
	private Link<Void> createNewLink(String wicketId){
		Link<Void> newLink = new Link<Void>(wicketId) {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				setResponsePage(SchedulerCreationPage.class);
			}
		};
		
		return newLink;
	}
	
	static class DateUtils {
	    private final List<ProductField> dataProductFields;

	    public DateUtils(List<ProductField> dataProductFields) {
	        this.dataProductFields = new ArrayList<>(dataProductFields);
	    }

	    public String format(Object productData) {
	    	// Format the product data
	        return format(productData, 0);
	    }

	    public String format(Object productData, int mode) {	        
	    List<String> headers = new ArrayList<>();
        for (ProductField current : dataProductFields) {
            String listAsString = current.getProperty();
          //String[] properties = listAsString.split(',');
            String[] properties = StringUtils.splitPreserveAllTokens(listAsString, ',');
            for (String current2 : properties) {
                String str = null;
                try {
                    final ResourceBundle rb = getBundle("messages", GERMAN);
                    str = rb.getString(current2);
                    if (StringUtils.isNotBlank(str)) {
                        headers.add(str);
                        continue;
                    }
                } catch (MissingResourceException mre) {
                    // this could be ignored
                }
                str = current.getLabel();
                if (StringUtils.isNotBlank(str)){
                    headers.add(str);
                    continue;
                }
                headers.add(current.getProperty());
            }
        }
        
        String header = headers.stream().collect(Collectors.joining(";"));
        
	        List<Object> out = new ArrayList<>(dataProductFields.size());
	        for (ProductField current : dataProductFields) {
	            String listAsString = current.getProperty();
	            //String[] properties = listAsString.split(',');
	            String[] properties = StringUtils.splitPreserveAllTokens(listAsString, ',');
	            for (String current2 : properties) {
	                try {
	                    out.add(PropertyUtils.getProperty(productData, current2));
	                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
	                    if (mode == 1) {
	                    	// No output
	                        out.add("");
	                    } else {
	                        throw new Exception("Something must have gone wrong.", e);
	                    }
	                }
	            }
	        }
	        return header + "\n" + out.stream().map(o -> processStrings(Optional.ofNullable(o).orElse("").toString())).map(s -> s.equals("ä") ? "ae" : s.equals("ü") ? "ue" : s.equals("ö") ? "oe": s).collect(Collectors.joining(";"));
	    }

	    private String processStrings(String in) {
	        return in.replace(";", "");
	    }
	    
//	    private String processData(String in) {
//	        return in.replace(",", "");
//	    }
	}
}