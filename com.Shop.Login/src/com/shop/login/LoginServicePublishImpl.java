package com.shop.login;

import java.util.Scanner;

import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

import com.Shop.ItemStore.ItemStorePublish;

public class LoginServicePublishImpl implements LoginServicePublish {
	
	Scanner sc=new Scanner(System.in);
	
	@Override
	public void LoginToSystem() {
		System.out.println("\n======== Welcome to Login page ==========");
		System.out.println();
		System.out.print("Enter the user type [cus/seller]: ");
		String type=sc.next();
		
		   //Getting service reference from  ItemStore interface to access ItemStore component services.
		   BundleContext context = FrameworkUtil.getBundle(LoginServicePublish.class).getBundleContext();
		   ServiceReference StoreService=context.getServiceReference(ItemStorePublish.class.getName());
		   ItemStorePublish Store=(ItemStorePublish)context.getService(StoreService);
		
		//check customer type
		if(type.equalsIgnoreCase("cus")) {
			Store.DisplayMenu();
			Store.SelectFromMenu();
			
		}
		
		/*
		 * If user type equals to seller 
		 *first execute Insert item method
		 *after exiting insert item method then display console message and redirect user selected page[login or exit]
		 */
		else if(type.equalsIgnoreCase("seller")) {
			//call Insert item method
			Store.InsertItems();
			System.out.println();
			System.out.print("Do you want to go to loggin page again[1] or exit from system[2]");
			int res=sc.nextInt();
			if(res==1) {
				this.LoginToSystem();
			}
			else {
				System.out.println("=====Thanks for using our service=====");
				return;
			}
			
		}
		else {
			//If user enters invalid user type recall the same method again
			System.out.println("Please enter a valid user type (Seller,cus):");
			this.LoginToSystem();
		}
		
	}

	
	
}
