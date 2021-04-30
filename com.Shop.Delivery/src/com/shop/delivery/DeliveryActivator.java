package com.shop.delivery;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class DeliveryActivator implements BundleActivator {

	ServiceRegistration DeliveryService;
	
	//start the bundle
	public void start(BundleContext context) throws Exception {
		System.out.println("Start Delivery Service ");
	    DeliveryPublish deliveryPublisher=new DeliveryPublishImpl();
	    DeliveryService=context.registerService(DeliveryPublish.class.getName(),deliveryPublisher, null);
	
	}

	//stop the bundle
	public void stop(BundleContext context) throws Exception {
		System.out.println("Stop Delivery Service");
		DeliveryService.unregister();
	}

}
