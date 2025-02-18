$(document).ready(function() {
	$("#backBtn").click(function() {
		window.history.back(); // Goes to the previous page
	});
	$.ajax({
		url: "user/checkUser",
		type: "GET",
		xhrFields: {
			withCredentials: true // Important for session handling
		},
		success: function(response) {
			if (response == null || response.status != "success") {
				alert("No User Login.... Please Login First ");
				window.location.href = "./login.html";
			}
		},
		error: function() {
			alert("Session validation failed in order!");
			window.location.href = "./login.html";
		}
	});

	// Get data from URL
	const urlParams = new URLSearchParams(window.location.search);
	const restaurantId = urlParams.get("restaurantId");
	const orderData = JSON.parse(decodeURIComponent(urlParams.get("orderData")));

	console.log("First : " + orderData);
	if (!orderData || orderData.length === 0) {
		alert("No items in the order!");
		window.location.href = "display.html"; // Redirect if no order found
		return;
	}

	let orderList = $("#orderList");
	let totalAmount = 0;

	orderData.forEach(item => {
		let itemTotal = item.quantity * item.price;
		totalAmount += itemTotal;

		let row = `<tr>
            <td>${item.foodName}</td>
            <td>${item.quantity}</td>
            <td>₹${item.price}</td>
            <td>₹${itemTotal}</td>
        </tr>`;

		orderList.append(row);
	});

	$("#totalAmount").text(totalAmount);


	$("#placeOrder").click(function() {
		let paymentMethod = $("#paymentMethod").val();
		let paymentStatus = paymentMethod === "Cash" ? "Pending" : "Paid";

		const orderPayload = {
			restaurantId: parseInt(restaurantId),
			foodDetails: orderData.map(item => ({
				foodId: item.foodId,
				foodName: item.foodName,
				quantity: item.quantity,
				price: item.price
			})),
			totalAmount: totalAmount,
			paymentMethod: paymentMethod,
			paymentStatus: paymentStatus
		};

		console.log("Sending Order Data: ", JSON.stringify(orderPayload));

		$.ajax({
			url: "order/placeOrder",
			type: "POST",
			contentType: "application/json",
			data: JSON.stringify(orderPayload),
			xhrFields: { withCredentials: true },
			success: function(response) {
				console.log(response);
				if (response.status === "success") {
					alert("Status : " + response.message);
					window.location.href = "./home.html";
				} else {
					alert("Please try again.\n" + response.message);
				}
			},
			error: function(xhr) {
				alert("Error placing order: " + xhr.statusText);
			}
		});
	});

	/*
	$("#placeOrder").click(function() {
		let paymentMethod = $("#paymentMethod").val();
		let paymentStatus = paymentMethod === "Cash" ? "Pending" : "Paid";

		// Create proper JSON structure
		const orderPayload = {
				"order.restaurantId": parseInt(restaurantId),
				"order.foodDetails": orderData.map(item => ({
					foodId: item.foodId,
					foodName: item.foodName,
					quantity: item.quantity,
					price: item.price
				})),
				"order.totalAmount": totalAmount,
				"order.paymentMethod": paymentMethod,
				"order.paymentStatus": paymentStatus
			};
			

	const orderPayload = {
		restaurantId: restaurantId,
		foodDetails: orderData,
		totalAmount: totalAmount,
		paymentMethod: paymentMethod,
		paymentStatus: paymentStatus
	};
	console.log(orderPayload);
	$.ajax({
		url: "order/placeOrder.action",
		type: "POST",
		contentType: "application/json",
		data: orderPayload,
		xhrFields: { withCredentials: true },
		success: function(response) {
			if (response.status === "success") {
				alert("Order placed successfully!\n" + response.message);
				window.location.href = "./home.html";
			} else {
				alert("Order failed...Please try again.\n" + response.message);

			}
		},
		error: function(xhr) {
			alert("Error placing order: " + xhr.statusText);
		}
	});
});
 */
});

