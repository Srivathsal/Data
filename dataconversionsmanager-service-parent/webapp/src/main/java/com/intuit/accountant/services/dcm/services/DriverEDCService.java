package com.intuit.accountant.services.dcm.services;

import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Created by sshashidhar on 07/09/18.
 */

@Component
public class DriverEDCService {
	
	/**
	 * create driver xml string and return
	 */
	public String getDriverXml(String Product,
			String CustomerNumber,String Batch, String Renumbering, String Competitor,String Email, String FirstName,
			String LastName, String Phone, String TaxYear,String SubmitDate) 
	{
		 StringBuilder  driverContent = new StringBuilder();
		 driverContent.append("<EDC>");
		 driverContent.append("<IntuitProduct>");
		 driverContent.append("<Product>");
		 driverContent.append(Product);
		 driverContent.append("</Product>");
		 driverContent.append("<CustomerNumber>");
		 driverContent.append(CustomerNumber);
		 driverContent.append("</CustomerNumber>");
		 driverContent.append("<Batch>");
		 driverContent.append(Batch);
		 driverContent.append("</Batch>");
		 driverContent.append("<Renumbering>");
		 driverContent.append(Renumbering);
		 driverContent.append("</Renumbering>");
		 driverContent.append("<Competitor>");
		 driverContent.append(Competitor);
		 driverContent.append("</Competitor>");
		 driverContent.append("<Email>");
		 driverContent.append(Email);
		 driverContent.append("</Email>");
		 driverContent.append("<FirstName>");
		 driverContent.append(FirstName);
		 driverContent.append("</FirstName>");
		 driverContent.append("<LastName>");
		 driverContent.append(LastName);
		 driverContent.append("</LastName>");
		 driverContent.append("<Phone>");
		 driverContent.append(Phone);
		 driverContent.append("</Phone>");
		 driverContent.append("<TaxYear>");
		 driverContent.append(TaxYear);
		 driverContent.append("</TaxYear>");
		 driverContent.append("<SubmitDate>");
		 driverContent.append(SubmitDate);
		 driverContent.append("</SubmitDate>");
         driverContent.append("</IntuitProduct>");
         driverContent.append("</EDC>");
		 
		 return driverContent.toString();	
	}
	
	/**
	 * Create Edc key string and return
	 */
	public String getEdckey(){
		
		 String edckey = null;
		 
		 UUID idOne = UUID.randomUUID();
		 edckey = String.valueOf(idOne);
		 
		 return edckey;
	}
	
}
