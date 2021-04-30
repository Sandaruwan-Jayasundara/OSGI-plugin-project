import java.util.HashMap;


import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;


import com.shop.login.LoginServicePublish;

public class ClientActivator implements BundleActivator {

ServiceReference LoginService;


public void start(BundleContext context) throws Exception {
 //Start Client component and get reference of LoginServicePublish interface to access services in Login componenet		
 System.out.println("Client Start Using Services");
 LoginService=context.getServiceReference(LoginServicePublish.class.getName());
 LoginServicePublish Login=(LoginServicePublish)context.getService(LoginService);

 //call to login to system service in Login component
 Login.LoginToSystem();
;

}
	
	

public void stop(BundleContext context) throws Exception {
 //stop Client component
 System.out.println("Client Stop Using Services");
	}

}
