package com.Shop.ItemStore;

public interface ItemStorePublish {


//This method is use to display items stored in system (ItemData.xml) to the users
	public void DisplayMenu();
	
//This method is use to select items from dispalyed menu based on item id 	
	public void SelectFromMenu();

	
//This method is use by seller to add items to the system.(ItemData.xml)	
	public void InsertItems();
	
	
//This method is used to check the amount of  item qty customer requested to buy can be fullfill by the system.
	public boolean checkAvailability(String id,int qty);
}
