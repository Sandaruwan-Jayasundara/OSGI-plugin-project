package com.shop.cart;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class CartActivator implements BundleActivator {

ServiceRegistration CartService;


	public void start(BundleContext context) throws Exception {
		System.out.println("Start Cart Service ");
	    CartDisplay cartPublisher=new CartDisplayImpl();
	    CartService=context.registerService(CartDisplay.class.getName(),cartPublisher, null);
	}

	public void stop(BundleContext bundleContext) throws Exception {
		System.out.println("Stop Cart Service");
		CartService.unregister();
		
	}

}
