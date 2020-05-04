<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="model.PaymentDetails" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Payment Management</title>
<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Components/jquery-3.2.1.min.js"></script>
<script src="Components/Payments.js"></script>
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-6">
				<h1>Payment Management</h1>
				<form id="formItem" name="formItem">
					Appointment no: <input id="appno" name="appno" type="text"
						class="form-control form-control-sm"> <br>
					 Card Type: <input id="cardtype" name="cardtype" type="text"
						class="form-control form-control-sm"> <br>
					 Name on Card: <input id="nameOnCard" name="nameOnCard" type="text"
						class="form-control form-control-sm"> <br>
				     Card no: <input id="cardno" name="cardno" type="text"
						class="form-control form-control-sm"> <br>
					Phone: <input id="phone" name="phone" type="text"
						class="form-control form-control-sm"> <br>
					Expire date: <input id="expdate" name="expdate" type="text"
						class="form-control form-control-sm"> <br>
					Amount: <input id="amount" name="amount" type="text"
						class="form-control form-control-sm"> <br>
					 <input id="btnSave" name="btnSave" type="button" value="Save"
						class="btn btn-primary">
					<input type="hidden"id="hidItemIDSave" name="hidItemIDSave" value="">
				</form>
				<div id="alertSuccess" ass="alert alert-danger"></div>
				<br>
				<div id="divItemsGrid">
					<%
						PaymentDetails paymentdetails = new PaymentDetails();
						out.print(paymentdetails.readPaymentDetails());
					%>
				</div>
			</div>
		</div>
	</div>

</body>
</html>