package com.shop.cart;

import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public interface CartDisplay {

	//this method is to use store client selected items in the system.
	public void storeCartInXML(Document doc); 
	
	//this method is used to display the items that saved in the cart to user 
	public void DisplayCartItems();
	
	//this method is use to navigate user from cart to payment
	public void NavigateToPayment(double total);
	
	//this method is used to clear the cart and go back to shopping
	public void NavigateToItems();

    //this mehtod is used to store client selected item in document before save in xml form.
	public Document addToCart(Element rootElement, Document document, String name, double price, int qty);

	


}
