package com.Shop.ItemStore;

import java.io.File;

import java.util.HashMap;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.shop.cart.CartDisplay;


public class ItemStorePublishImpl implements ItemStorePublish{
	
//This method is use to display items stored in system (ItemData.xml) to the users
	@Override
	public void DisplayMenu() {
		try   
		{  
        //creating a constructor of file class and parsing  ItemData.xml(Where Item details Stored) file.
		File Newfile = new File("C:\\Users\\ISJ\\Desktop\\SA-Assignment\\com.Shop.ItemStore\\src\\com\\Shop\\ItemStore\\ItemData.xml"); 
        //an object of factory that gives a document builder.  		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance(); 
        //an object of builder to parse the  ItemData.xml(Where Item details Stored) file.		
		DocumentBuilder db = dbf.newDocumentBuilder();  
		Document doc = db.parse(Newfile);  
		doc.getDocumentElement().normalize();  
		NodeList nodeList = doc.getElementsByTagName("item");  
		System.out.println("======Item List======");
		
		// nodeList is not iterable, so we are using for loop
		for (int itr = 0; itr < nodeList.getLength(); itr++)   
		{  
		Node node = nodeList.item(itr);  
		if (node.getNodeType() == Node.ELEMENT_NODE)   
		{  
		//print the item details stored in ItemData.xml on the console
		Element eElement = (Element) node;  
		System.out.println("Item id: "+ eElement.getElementsByTagName("id").item(0).getTextContent());  
		System.out.println("Item Name: "+ eElement.getElementsByTagName("name").item(0).getTextContent());  
		System.out.println("Item Price: "+ eElement.getElementsByTagName("price").item(0).getTextContent());
		System.out.println(" ");
		 }  
		}


		}   
		catch (Exception e) //Catch exceptions if any of them are throw  
		{  
		e.printStackTrace();  
		} 
		
	}

	@Override
	public void SelectFromMenu() {
		
		Scanner sc=new Scanner(System.in);
		
		try   
		{   
		//creating a constructor of file class and parsing  ItemData.xml(Where Item details Stored) file.
		File Newfile = new File("C:\\Users\\ISJ\\Desktop\\SA-Assignment\\com.Shop.ItemStore\\src\\com\\Shop\\ItemStore\\ItemData.xml"); 
        //an object of factory that gives a document builder.  			
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        //an object of factory that gives a document builder.  	 		
		DocumentBuilder db = dbf.newDocumentBuilder();  
		Document doc = db.parse(Newfile);  
		doc.getDocumentElement().normalize(); 
		
		//When client select an item we need to save it on xml file for further operation.
		//So we make new xml file for save itens that client selected to buy.
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
	    Document document = docBuilder.newDocument();
	    Element rootElement = document.createElement("cart");
	    document.appendChild(rootElement);
	     
        //getting reference of CartDisplay interface of Cart component to use services based on cart.		 
	    BundleContext context = FrameworkUtil.getBundle(ItemStorePublish.class).getBundleContext();
		ServiceReference CartService=context.getServiceReference(CartDisplay.class.getName());
		CartDisplay myCart=(CartDisplay)context.getService(CartService);
	       
		NodeList nodeList = doc.getElementsByTagName("item"); 
		String response;
		while(true) {
	    //here system ask client to enter the id of the item he wish to buy
		System.out.println("Enter the Item No:");
		String no=sc.next();
		for (int itr = 0; itr < nodeList.getLength(); itr++)   
		{  
		Node node = nodeList.item(itr);  
		if (node.getNodeType() == Node.ELEMENT_NODE)   
		{  
		Element eElement = (Element) node; 
		
        //When customer type id of the item system check the id num that customer entered with item id's in items stored in ItemData.xml file.		
		if(no.equals(eElement.getElementsByTagName("id").item(0).getTextContent())) {
		//after find the client requested item. System ask client to enter the qty of item he/she want to buy.	
		System.out.println("Enter the Qty:");
		int	qty=sc.nextInt();
		//after client enter the qty system will check this request can be fullfill the request.
		boolean avalability=this.checkAvailability(eElement.getElementsByTagName("id").item(0).getTextContent(), qty);
		if(avalability) {
	
			   document=myCart.addToCart(rootElement,document,eElement.getElementsByTagName("name").item(0).getTextContent(),Double.parseDouble(eElement.getElementsByTagName("price").item(0).getTextContent()), qty);
		}
		else {
			System.out.println("This amout of qty not available in this item...\n Item Adding Failed..");
		}
		
		}
		 }  
		}
		System.out.println("Do you want to grab more items?[Y/YES] or [N/NO] to View Cart");
		response=sc.next();
		if(response.equalsIgnoreCase("NO") || response.equalsIgnoreCase("N")) {
			
			myCart.storeCartInXML(document);
			myCart.DisplayCartItems();
			break;
			
		}
		}

		}   
		catch (Exception e)   
		{  
		e.printStackTrace();  
		} 
		
	}


     //This method is used by the client to store items in the system.
	 @Override
	 public void InsertItems() {
		Scanner scan=new Scanner(System.in);

		int count=0;
		    try
		    {	System.out.println();
				System.out.println("========== ADD ITEMS ========== \n");
				
		        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		        Document doc = docBuilder.newDocument();
		    	//XML first header name
		        Element rootElement = doc.createElement("store");
		        doc.appendChild(rootElement);
		        while(true) {
		        	
		        	//count is used to add item number
		        	count++;
		            Element product = doc.createElement("item");
		            
		            //get user inputs
		            System.out.print("Enter your product name :"); 
		            
		            Element ID = doc.createElement("id");
		            ID.setTextContent(String.valueOf(count));
		            
		            
		            Element ItemName = doc.createElement("name");
		            ItemName.setTextContent(scan.nextLine());
		            
		            
		            System.out.print("Enter price :");
		            Element price = doc.createElement("price");
		            price.setTextContent(scan.nextLine());
		            
		            
		            
		            System.out.print("Enter item quanity:");
		            Element Qty = doc.createElement("qty");
		            Qty.setTextContent(scan.nextLine());
		            
		            //append all inserted values
		            product.appendChild(ID);
		            product.appendChild(ItemName);
		            product.appendChild(price);
		            product.appendChild(Qty);
		            
		            
		            rootElement.appendChild(product);
		            
		          //these codes are used to continue or break the loops
		            System.out.print("Do you want to add more items [Y/YES] or[N/NO]:");
		            String value = scan.nextLine();
		            System.out.println();
		            
		            if (value.equalsIgnoreCase("YES") || value.equalsIgnoreCase("Y")) {
						continue;
					}
		            
					
					  else if(value.equalsIgnoreCase("N") || value.equalsIgnoreCase("NO")) {
						  
						   break;
					  
					 }
					
		            else {
		            	System.out.println(" *Invalid");
		            	
			            System.out.print("Do you want to add more items [Y/YES] or[N/NO]:");
			            String values = scan.nextLine();
			            System.out.println();
		            	
			            if (values.equalsIgnoreCase("YES") || values.equalsIgnoreCase("Y")) {
							continue;
						}
			            
						
						  else if(values.equalsIgnoreCase("N") || values.equalsIgnoreCase("NO")) {
							  
							   break;
						  
						 }
						  else {
							  break;
						  }
		            }

		        }
		        
		        // Write the content into XML file
		        DOMSource source = new DOMSource(doc);
		        StreamResult result = new StreamResult(new File("C:\\Users\\ISJ\\Desktop\\SA-Assignment\\com.Shop.ItemStore\\src\\com\\Shop\\ItemStore\\ItemData.xml"));
		        
		        TransformerFactory transformerFactory = TransformerFactory.newInstance();
		        Transformer transformer = transformerFactory.newTransformer();
		        // Beautify the format of the resulted XML
		        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		        transformer.transform(source, result);
		    }
		    catch(Exception ex)
		    {
		        ex.printStackTrace();
		    }
		
	}
	
	//This method is use to check the avalability of the  items 

	@Override
	public boolean checkAvailability(String id, int qty) {
	
		
		try   
		{   
		//creating a constructor of file class and parsing  ItemData.xml(Where Item details Stored) file.
		File Newfile = new File("C:\\Users\\ISJ\\Desktop\\SA-Assignment\\com.Shop.ItemStore\\src\\com\\Shop\\ItemStore\\ItemData.xml");
        //an object of factory that gives a document builder. 		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        //an object of factory that gives a document builder.  		
		DocumentBuilder db = dbf.newDocumentBuilder();  
		Document doc = db.parse(Newfile);  
		doc.getDocumentElement().normalize();  
		NodeList nodeList = doc.getElementsByTagName("item"); 
		String response;
		while(true) {
		for (int itr = 0; itr < nodeList.getLength(); itr++)   
		{  
		Node node = nodeList.item(itr);  
		if (node.getNodeType() == Node.ELEMENT_NODE)   
		{  
		Element eElement = (Element) node;  
		if(id.equals(eElement.getElementsByTagName("id").item(0).getTextContent())) {
	int Quantity=Integer.parseInt(eElement.getElementsByTagName("qty").item(0).getTextContent());
	//Check the Item qty available in the system against qty client requested
	if(Quantity>qty) {
		return true;
	}
	else {
		return false;
	}

		
		}
		 }
		}

		
		}

		}   
		catch (Exception e)   
		{  
		e.printStackTrace();  
		}
		return false; 
	}


	
	
	
	

}
