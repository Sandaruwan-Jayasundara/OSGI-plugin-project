package com.shop.delivery;
import java.io.File;
import java.util.Scanner;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class DeliveryPublishImpl implements DeliveryPublish {
		@Override
		public void Delivery()  {
		try {
			Scanner scn=new Scanner(System.in);
			
			
		    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
	        Document doc = docBuilder.newDocument();
	        Element rootElement = doc.createElement("Delivery");
	        doc.appendChild(rootElement);
			
			
					String Name;
					String No;
					String Street;
					String City;
					String contact;
				
	
			while(true){
			
			//get the delivery details
			System.out.println("Enter your name :");
			Name=scn.nextLine();
			System.out.println("Enter your Contect number :");
			contact=scn.nextLine();
			
			System.out.println("-------Delivery Information-----\n");
		
			System.out.println("Enter No:");
			No=scn.nextLine();
			
			System.out.println("Enter Street :");
			Street=scn.nextLine();
			
			System.out.println("Enter City :");
			City=scn.nextLine();
			
            Element deliveryinfo = doc.createElement("Informations");

            Element contactNO = doc.createElement("contactnumber");
            contactNO.setTextContent(contact);
            
            Element no = doc.createElement("no");
            no.setTextContent(No);
            
            Element street = doc.createElement("street");
            street.setTextContent(Street);
            
            Element city = doc.createElement("city");
            city.setTextContent(City);
            
            deliveryinfo.setAttribute("name", Name);
            
            deliveryinfo.appendChild(contactNO);
            deliveryinfo.appendChild(no);
            
            deliveryinfo.appendChild(street);
            deliveryinfo.appendChild(city);
            
 
            rootElement.appendChild(deliveryinfo);
		
		
	        //save the delivery details to the xml file
	        DOMSource dOMSource = new DOMSource(doc);
	        StreamResult deliveryDetails = new StreamResult(new File("C:\\Users\\ISJ\\Desktop\\SA-Assignment\\com.Shop.Delivery\\src\\com\\shop\\delivery\\Shipping.xml"));
	        
	        TransformerFactory transformerFactory = TransformerFactory.newInstance();
	        Transformer transformer = transformerFactory.newTransformer();
	        
	        
	        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	        transformer.transform(dOMSource, deliveryDetails);
	        
	        //confirm the delivery details
	        System.out.println("Confirm[1]");
	        String next=scn.nextLine();
	        
	        if(next.equals("1")){
	        	try {
	    			Thread.sleep(2000);
	    		} catch (InterruptedException e) {
	    			// TODO Auto-generated catch block
	    			e.printStackTrace();
	    		}
	        	System.out.println("----------------------");
	        	System.out.println("Thank you, " + Name + "!");
	        	System.out.println("Your package is on the way");
	        	System.out.println("----------------------");
	        	break;
	        
	        }
	        }
	        
	    
	}
		 catch(Exception ex)
		    {
		        ex.printStackTrace();
		    }

}
}
