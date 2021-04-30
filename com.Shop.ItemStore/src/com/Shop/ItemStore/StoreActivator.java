package com.Shop.ItemStore;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class StoreActivator implements BundleActivator {

ServiceRegistration StoreService;

	public void start(BundleContext context) throws Exception {
	//Start and registed ItemStore component
	System.out.println("Store servie started");
	ItemStorePublish storePublisher=new ItemStorePublishImpl();
	StoreService=context.registerService(ItemStorePublish.class.getName(), storePublisher, null);
	}

	public void stop(BundleContext bundleContext) throws Exception {
	//Stop and unregister ItemStore component
	System.out.println("Store service stopped");
	StoreService.unregister();
	}

}
