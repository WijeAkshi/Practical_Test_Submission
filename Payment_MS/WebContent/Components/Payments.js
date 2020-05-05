$(document).ready(function() {
	if ($("#alertSuccess").text().trim() == "") {
		$("#alertSuccess").hide();
	}
	$("#alertError").hide();
});
// SAVE ============================================
$(document).on("click", "#btnSave", function(event) {

	// Clear alerts---------------------
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertError").text("");
	$("#alertError").hide();

	// Form validation-------------------
	var status = validateItemForm();
	if (status != true) {
		$("#alertError").text(status);
		$("#alertError").show();
		return;
	}
	// If valid------------------------
	var type = ($("#hidItemIDSave").val() == "") ? "POST" : "PUT";

	$.ajax({
		url : "PaymentDetailsAPI",
		type : type,
		data : $("#formItem").serialize(),
		dataType : "text",
		complete : function(response, status) {
			onItemSaveComplete(response.responseText, status);
		}
	});
});

function onItemSaveComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully saved.");
			$("#alertSuccess").show();
			$("#divItemsGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error") {
		$("#alertError").text("Error while saving.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while saving..");
		$("#alertError").show();
	}
	$("#hidItemIDSave").val("");
	$("#formItem")[0].reset();
}
// UPDATE==========================================
$(document).on(
		"click",
		".btnUpdate",
		function(event) {
			$("#hidIDSave").val(
					$(this).closest("tr").find('#hidIDUpdate').val());
			$("#app_Id").val($(this).closest("tr").find('td:eq(0)').text());
			$("#ctype").val($(this).closest("tr").find('td:eq(1)').text());
			$("#name").val($(this).closest("tr").find('td:eq(2)').text());
			$("#cardno").val($(this).closest("tr").find('td:eq(3)').text());
			$("#pho").val($(this).closest("tr").find('td:eq(4)').text());
			$("#expdate").val($(this).closest("tr").find('td:eq(5)').text());
			$("#amount").val($(this).closest("tr").find('td:eq(6)').text());
			$("#status").val($(this).closest("tr").find('td:eq(7)').text());
		
		});

// REMOVE ====================================================

$(document).on("click", ".btnRemove", function(event) {
	$.ajax({
		url : "PaymentDetailsAPI",
		type : "DELETE",
		data : "id=" + $(this).data("id"),
		dataType : "text",
		complete : function(response, status) {
			onItemDeleteComplete(response.responseText, status);
		}
	});
});
function onItemDeleteComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully deleted.");
			$("#alertSuccess").show();
			$("#divItemsGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error") {
		$("#alertError").text("Error while deleting.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while deleting..");
		$("#alertError").show();
	}
}

// CLIENTMODEL=========================================================================
function validateItemForm() {
	// CODE
	if ($("#appno").val().trim() == "") {
		return "Insert Item Code.";
	}
	// NAME
	if ($("#cardtype").val().trim() == "") {
		return "Insert Item Name.";
	}
	// PRICE-------------------------------
	if ($("#nameOnCard").val().trim() == "") {
		return "Insert Item Price.";
	}
	// is numerical value
	var tmpPrice = $("#cardno").val().trim();
	if (!$.isNumeric(tmpPrice)) {
		return "Insert a numerical value for Item Price.";
	}
	// NAME
	if ($("#phone").val().trim() == "") {
		return "Insert Phone number.";
	}
	// NAME
	if ($("#expdate").val().trim() == "") {
		return "Insert Expire date.";
	}
	// NAME
	var tmpPrice = $("#cardno").val().trim();
	if (!$.isNumeric(tmpPrice)) {
		return "Insert a numerical value for Item Price.";
	}
	// convert to decimal price
	$("#cardno").val(parseFloat(tmpPrice).toFixed(2));
	// DESCRIPTION------------------------
	//if ($("#itemDesc").val().trim() == "") {
	//	return "Insert Item Description.";
	}
	return true;


