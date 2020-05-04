package com;

import model.PaymentDetails;

import java.sql.Date;

import javax.annotation.security.RolesAllowed;
//For REST Service
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
//For JSON
import com.google.gson.*;
//For XML
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

@Path("/{Payments}")
public class PaymentDetailsService {

	PaymentDetails PayObj = new PaymentDetails();

	// method to read the appointment details alone with the amount
	@GET
	@Path("/amountnew")
	@Produces(MediaType.TEXT_HTML)
	public String readAppointmentDetails() {
		return PayObj.readAppointmentDetails();
	}

//method to read the payment details
	@RolesAllowed("Accountant")
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readPaymentDetails() {
		return PayObj.readPaymentDetails();
	}

//method to insert a payment details
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertPaymentDetails(@FormParam("appno") String appno, @FormParam("cardType") String cardType,
			@FormParam("nameOnCard") String nameOnCard, @FormParam("cardno") String cardno,
			@FormParam("phone") String phone, @FormParam("expdate") String expdate, @FormParam("amount") String amount,
			@FormParam("status") String status) {
		String output = PayObj.insertPaymentDetails(appno, cardType, nameOnCard, cardno, phone, expdate, amount,
				status);
		return output;
	}

//method to update the status details	
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updatePaymentDetails(String payData) {

//Convert the input string to a JSON object
		JsonObject PaymentObj = new JsonParser().parse(payData).getAsJsonObject();

//Read the values from the JSON object
		String id = PaymentObj.get("id").getAsString();
		String appno = PaymentObj.get("appno").getAsString();
		String cardType = PaymentObj.get("cardType").getAsString();
		String nameOnCard = PaymentObj.get("nameOnCard").getAsString();
		String cardno = PaymentObj.get("cardno").getAsString();
		String phone = PaymentObj.get("phone").getAsString();
		String expdate = PaymentObj.get("expdate").getAsString();
		String amount = PaymentObj.get("amount").getAsString();
		String status = PaymentObj.get("status").getAsString();

		String output = PayObj.updatePaymentDetails(id, appno, cardType, nameOnCard, cardno, phone, expdate, amount,
				status);
		return output;
	}

	// method to delete the payment if it not valid
	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deletePaymentDetails(String payData) {

//Convert the input string to an XML document
		Document doc = Jsoup.parse(payData, "", Parser.xmlParser());

//Read the value from the element <itemID>
		String id = doc.select("id").text();
		String output = PayObj.deletePaymentDetails(id);
		return output;
	}

}
