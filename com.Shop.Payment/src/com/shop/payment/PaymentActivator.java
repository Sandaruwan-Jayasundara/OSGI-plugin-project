package com.shop.payment;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class PaymentActivator implements BundleActivator {

ServiceRegistration PaymentService;

	public void start(BundleContext context) throws Exception {
		System.out.println("Payment Service Started..");
		PaymentService Payment= new PaymentServiceImpl();
		PaymentService=context.registerService(PaymentService.class.getName(),Payment, null);

	}

	public void stop(BundleContext bundleContext) throws Exception {
		System.out.println("Payment Service Stopped");
		PaymentService.unregister();
	}
}
