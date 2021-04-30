package com.shop.login;

import org.osgi.framework.BundleActivator;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class LoginActivator implements BundleActivator {

ServiceRegistration LoginService;

	public void start(BundleContext context) throws Exception {
		//Registering and start Loging componet
		System.out.println("Login Service Started..");
		LoginServicePublish Login=new LoginServicePublishImpl();
		LoginService=context.registerService(LoginServicePublish.class.getName(), Login, null);

	}

	public void stop(BundleContext bundleContext) throws Exception {
		//unregistering and stop Login Component
		System.out.println("Login Service Stopped");
		LoginService.unregister();
	}

}
