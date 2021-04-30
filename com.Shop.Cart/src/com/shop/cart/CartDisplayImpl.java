package com.shop.cart;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;

import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import com.Shop.ItemStore.ItemStorePublish;
import com.shop.payment.PaymentService;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class CartDisplayImpl implements CartDisplay {

	//this method is to use store client selected items in the system.
	@Override
	public void DisplayCartItems() {
		try{  
			 
			double sum=0;
			File Newfile = new File("C:\\Users\\ISJ\\Desktop\\SA-Assignment\\com.Shop.Cart\\src\\com\\shop\\cart\\Cart.xml");  
	 
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();  
			DocumentBuilder db = dbf.newDocumentBuilder();  
			Document doc = db.parse(Newfile);  
			doc.getDocumentElement().normalize();  
			NodeList nodeList = doc.getElementsByTagName("item");  
			System.out.println("----------Cart-----------");
			for (int itr = 0; itr < nodeList.getLength(); itr++)   
			{  
			Node node = nodeList.item(itr);  
			if (node.getNodeType() == Node.ELEMENT_NODE)   
			{  
		//display items stored in cart 
			Element eElement = (Element) node;  
			System.out.println("Item Name: "+ eElement.getElementsByTagName("name").item(0).getTextContent());  
			System.out.println("Item Price: "+ eElement.getElementsByTagName("price").item(0).getTextContent());
			System.out.println("Item Qty: "+ eElement.getElementsByTagName("qty").item(0).getTextContent());
			sum+=(Double.parseDouble(eElement.getElementsByTagName("price").item(0).getTextContent())*Double.parseDouble(eElement.getElementsByTagName("qty").item(0).getTextContent()));
			
			System.out.println(" ");
			 }  
			}

            System.out.println("Your total cost :"+sum);
            Scanner scanner=new Scanner(System.in);
            
            System.out.println("Do you wish to checkout this cart?[Y/YES] or [N/NO] to quit");
			String response=scanner.nextLine();
    		if(response.equalsIgnoreCase("Y") || response.equalsIgnoreCase("YES") ) {
    			this.NavigateToPayment(sum);
    			
    		}
    		
    		if(response.equalsIgnoreCase("N") || response.equalsIgnoreCase("NO") ) {
    			System.out.println("Do you wish to clear cart and select Items again[1] or [2] for exit system");
    			int res=scanner.nextInt();
    			if(res==1) {	
    				  this.NavigateToItems();
    			}
    			else{
    				  System.out.println("------Thanks for using our service-----");
    				  return;
    			}
    			
    			}
    		
    		
			}   
			catch (Exception e)   
			{  
			e.printStackTrace();  
			} 
			
	}
	
    //this mehtod is used to store client selected item in document before save in xml form.

   @Override
	public Document addToCart(Element rootElement,Document document,String name,double price,int qty) {
	      Document doc=document;
	try {
	

	            Element product = doc.createElement("item");
 
	            Element ItemName = doc.createElement("name");
	            ItemName.setTextContent(name);
	          
	            Element Price = doc.createElement("price");
	            Price.setTextContent(String.valueOf(price));

	            Element Qty = doc.createElement("qty");
	            Qty.setTextContent(String.valueOf(qty));
	            
	    
	            product.appendChild(ItemName);
	            product.appendChild(Price);
	            product.appendChild(Qty);
	            rootElement.appendChild(product);

		    }
		    catch(Exception ex)
		    {
		        ex.printStackTrace();
		    }
	return doc;
	}
	
   //this method is to use store client selected items in the system.
	@Override
	public void storeCartInXML(Document doc) {
		try {
	    DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File("C:\\Users\\ISJ\\Desktop\\SA-Assignment\\com.Shop.Cart\\src\\com\\shop\\cart\\Cart.xml"));
        
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        // Beautify the format of the resulted XML
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(source, result);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	//this method is use to navigate user from cart to payment	
	@Override
	public void NavigateToPayment(double total) {
		System.out.println("Pending Order........");
		System.out.println("Loading Payment Gateaway........");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   BundleContext context = FrameworkUtil.getBundle(CartDisplay.class).getBundleContext();
		   ServiceReference PayService=context.getServiceReference(PaymentService.class.getName());
		   PaymentService mypay=(PaymentService)context.getService(PayService);
		   mypay.MakePayment(total);
		   
	}

	//this method is used to clear the cart and go back to shopping
	@Override
	public void NavigateToItems(){
		System.out.println("Please wait........");
		System.out.println("Loading Payments Panel.....");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		BundleContext context = FrameworkUtil.getBundle(CartDisplay.class).getBundleContext();
		ServiceReference StoreService=context.getServiceReference(ItemStorePublish.class.getName());
		ItemStorePublish Store=(ItemStorePublish)context.getService(StoreService);

		Store.DisplayMenu();
		Store.SelectFromMenu();
		   
	}
	
}
