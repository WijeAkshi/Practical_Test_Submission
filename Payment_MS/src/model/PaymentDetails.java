package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

import Util.DB_Connection;

public class PaymentDetails {

	// insert new payment details
	public String insertPaymentDetails(String app_Id, String ctype, String name, String cardno, String pho,
			String expdate, String amount, String status) {
		String output = "";
		try {
			DB_Connection con = new DB_Connection();
			Connection conn = con.connect();

			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			// create a prepared statement
			String query = " insert into paymentdetails (`id`,`app_Id`,`cardType`,`nameOnCard`,`cardno`,`phone`,`expdate`,`amount`)"
					+ " values (?,?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, app_Id);
			preparedStmt.setString(3, ctype);
			preparedStmt.setString(4, name);
			preparedStmt.setString(5, cardno);
			preparedStmt.setString(6, pho);
			preparedStmt.setDate(7, java.sql.Date.valueOf(expdate));
			preparedStmt.setDouble(8, Double.parseDouble(amount));

			// execute the statement
			preparedStmt.execute();
			conn.close();
			String newPaymentDetails  = readPaymentDetails();
			output = "{\"status\":\"success\", \"data\": \"" + newPaymentDetails + "\"}";
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\": \"Error while inserting payment details.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}

	// retrieve payment details that was made by the patients
	public String readPaymentDetails() {
		String output = "";
		try {
			DB_Connection con = new DB_Connection();
			Connection conn = con.connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<table border=\"1\"><tr> <th>AppoID</th> <th>CarsType</th> <th>Name</th> <th>cardno</th> <th>Phone</th ><th>Exp_date</th> <th>Amount</th> <th>Status</th><th>Update</th><th>Remove</th> </tr>";
			String query = "select * from paymentdetails";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {
				String id = Integer.toString(rs.getInt("id"));
				String app_Id = rs.getString("app_Id");
				String cardType = rs.getString("cardType");
				String nameOnCard = rs.getString("nameOnCard");
				String cardno = rs.getString("cardno");
				String phone = Integer.toString(rs.getInt("phone"));
				String expdate = Integer.toString(rs.getInt("expdate"));
				String amount = Double.toString(rs.getDouble("amount"));
				String status = rs.getString("status");
				// Add into the html table
				output += "<tr><td><input id='hidIDUpdate' name='hidIDUpdate' type='hidden' value='" + id + "'>"
				 + app_Id + "</td>";
				output += "<td>" + cardType + "</td>";
				output += "<td>" + nameOnCard + "</td>";
				output += "<td>" + cardno + "</td>";
				output += "<td>" + phone + "</td>";
				output += "<td>" + expdate + "</td>";
				output += "<td>" + amount + "</td>";
				output += "<td>" + status + "</td>";
				// buttons
				output += "<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary'></td>"
						+ "<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-itemid='"
						+ id + "'>" + "</td></tr>";

			}
			conn.close();
			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the card details.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	// update the status of payments
	public String updatePaymentDetails(String id, String app_Id, String ctype, String name, String cardno, String pho, String expdate, String amount, String status) {
		String output = "";
		try {
			DB_Connection con = new DB_Connection();
			Connection conn = con.connect();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}
			// create a prepared statement
			String query = "UPDATE paymentdetails SET app_Id=?,cardType=?,nameOnCard=?,cardno=?,phone=?,expdate=?,amount=?,status=? WHERE id=?";
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			// binding values
			preparedStmt.setString(1, app_Id);
			preparedStmt.setString(2, ctype);
			preparedStmt.setString(3, name);
			preparedStmt.setString(4, cardno);
			preparedStmt.setInt(5, Integer.parseInt(pho));
			preparedStmt.setDate(6, java.sql.Date.valueOf(expdate));
			preparedStmt.setDouble(7, Double.parseDouble(amount));
			preparedStmt.setString(8, status);
			preparedStmt.setInt(9, Integer.parseInt(id));

			// execute the statement
			preparedStmt.execute();
			conn.close();
			String newPaymentDetails = readPaymentDetails();
			output = "{\"status\":\"success\", \"data\": \"" + newPaymentDetails + "\"}";
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\": \"Error while updating the payment details.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}

	// delete if a payment is not valid
	public String deletePaymentDetails(String id) {
		String output = "";
		try {
			DB_Connection con = new DB_Connection();
			Connection conn = con.connect();
			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}
			// create a prepared statement
			String query = "delete from paymentdetails where id=?";
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(id));
			// execute the statement
			preparedStmt.execute();
			conn.close();
			String newPaymentDetails = readPaymentDetails();
			output = "{\"status\":\"success\", \"data\": \"" + newPaymentDetails + "\"}";
		} catch (Exception e) {
			output = "Error while deleting the card details.";
			System.err.println(e.getMessage());
		}
		return output;
	}
/*
	// retrieve the appointment details with the payment amount
	public String readAppointmentDetails() {

		String output = "";
		try {
			DB_Connection con = new DB_Connection();
			Connection conn = con.connect();

			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<table border=\"1\"><tr> <th>AppoID</th> <th>date</th> <th>Name</th> <th>doctor id</th> <th>doc fee</th ><th>hospital fee</th>  <th>total</th></tr>";
			String query = "select a.app_Id,a.date,a.patient,a.doctor,d.fee  from doctor d,appointment a where a.doctor= d.did ";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {
				String app_Id = rs.getString("app_Id");
				String date = rs.getString("date");
				String patient = rs.getString("patient");
				String doctor = rs.getString("doctor");
				String fee = Double.toString(rs.getDouble("fee"));
				String amount = fee + 2000;
				// Add into the html table
				output += "<tr><td>" + app_Id + "</td>";
				output += "<td>" + date + "</td>";
				output += "<td>" + patient + "</td>";
				output += "<td>" + doctor + "</td>";
				output += "<td>" + fee + "</td>";
				output += "<td>" + "2000.00" + "</td>";
				output += "<td>" + "doctorfee+hospitalfee" + "</td>";

			}
			conn.close();
			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the card details.";
			System.err.println(e.getMessage());
		}
		return output;
	}
*/
}
