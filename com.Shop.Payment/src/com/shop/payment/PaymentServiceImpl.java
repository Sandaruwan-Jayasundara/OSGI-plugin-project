package com.shop.payment;


import java.io.File;
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


import com.shop.delivery.DeliveryPublish;


public class PaymentServiceImpl implements PaymentService {
	
	

	@Override
	public void MakePayment(double total) {
		System.out.println("\n-----------Payment gateway------------");
		
		Scanner scanner1 =new Scanner(System.in);
		
		int counter=0;
		    try
		    {
		        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		        Document doc = docBuilder.newDocument();		 
		        Element rootElement = doc.createElement("payment");
		        doc.appendChild(rootElement);
		        while(true) {
		 
		        	counter++;
		            Element order = doc.createElement("order");
		            
		            Element ID = doc.createElement("oid");
		            ID.setTextContent(String.valueOf(counter));
		            
		            Element Bill = doc.createElement("total");
		            Bill.setTextContent(String.valueOf(total));
		            
		            System.out.println("--------------------------------");
		            System.out.println("Please Select your payment method[Enter 1-4]");
		            System.out.println("\nCredit Card[1]");
		            System.out.println("\nDebit Card[2]");
		            System.out.println("\nPayPal[3]");
		            System.out.println("\nCash[4]");
		            System.out.println("---------------------------------"); 
		            String str = scanner1.nextLine();
		            int choice = Integer.parseInt(str);
		           
		            
		            switch (choice) {
		            
		            case 1:
		            	Element Method = doc.createElement("method");		           
			            Method.setTextContent("Credit Card");  
			            
			            Element cNumber = doc.createElement("cnumber");
			            System.out.println("Please enter your card number[8-digits]");
			            cNumber.setTextContent(scanner1.nextLine());
			            order.appendChild(Method);
			            order.appendChild(cNumber);

		                break;
		            case 2:
		            	Element Method2 = doc.createElement("method");
			            Method2.setTextContent("Debit Card");  
			            
			            System.out.println("Please enter your card number[8-digits]");
			            Element dNumber = doc.createElement("dnumber");
			            dNumber.setTextContent(scanner1.nextLine());
			            
			            order.appendChild(Method2);
			            order.appendChild(dNumber);
		                break;
		           
		            case 3: 
		        	    Element Method3 = doc.createElement("method");
			            Method3.setTextContent("Paypal");  
		        	   
			            System.out.println("Please enter your Paypal Email address");
				        Element Email = doc.createElement("email");
			            Email.setTextContent(scanner1.nextLine());
			            
			            order.appendChild(Method3);
			            order.appendChild(Email);
		                break;
		            case 4:
		            	Element Method4 = doc.createElement("method");
				        Method4.setTextContent("Cash"); 
				        
				        System.out.println("You have allocated to Pay-On-Door Service ");
				        System.out.println("Please Enter your Contact number to verify this service:");
				        Element Contact = doc.createElement("contact");
				       
				        
				        try {
		        			Thread.sleep(2000);
		        		} catch (InterruptedException e) {
		        			// TODO Auto-generated catch block
		        			e.printStackTrace();
		        		}
				        //Prints below notification (after 2000ms )
				        System.out.println("----------------------------------------");
				        System.out.println("Delivery person will contact you shortly!");
				        System.out.println("----------------------------------------");
				        
				        Contact.setTextContent(scanner1.nextLine());
				        order.appendChild(Contact);
				        order.appendChild(Method4);
		                break;
		            default:
		                throw new IllegalStateException("Invalid Choice: " + choice);
		        }
		        	   
		            order.appendChild(ID);
		            order.appendChild(Bill);
		            rootElement.appendChild(order);
          
		            System.out.println("Do you want to proceed the payment[Y/YES]: or [N/NO] to change");
		            String value = scanner1.nextLine();
		            
		            if (value.equalsIgnoreCase("YES")|| value.equalsIgnoreCase("Y")) {
		            	System.out.println("\n");
		            	try {
		        			Thread.sleep(2600);
		        		} catch (InterruptedException e) {
		        			// TODO Auto-generated catch block
		        			e.printStackTrace();
		        		}
		            	System.out.println("Your Payment of" +total+ "LKR is successfully completed!");
		            	System.out.println("Congratz! Your order is placed");
		            	this.NavigateToDelivery();
						break;
					} 
					 else if(value.equalsIgnoreCase("NO")|| value.equalsIgnoreCase("N")) {
						continue;  
					 }
		            else {
		            	break;
		            }

		        }
		        
		        // Write the content into XML file
		        DOMSource source = new DOMSource(doc);
		        StreamResult result = new StreamResult(new File("C:\\Users\\ISJ\\Desktop\\SA-Assignment\\com.Shop.Payment\\src\\com\\shop\\payment\\Orders.xml"));
		        
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

	private void NavigateToDelivery(){
	
			   BundleContext context = FrameworkUtil.getBundle(PaymentService.class).getBundleContext();
			   ServiceReference DeliveryService=context.getServiceReference(DeliveryPublish.class.getName());
			   DeliveryPublish delivery=(DeliveryPublish)context.getService(DeliveryService);
               
			   try {
				delivery.Delivery();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
			   
		}
		
	

}
