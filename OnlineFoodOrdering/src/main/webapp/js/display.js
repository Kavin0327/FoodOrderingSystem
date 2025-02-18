$(document).ready(function() {
	$("#backBtn").click(function() {
		window.history.back(); // Goes to the previous page
	});
	// Get the restaurantId from URL
	const urlParams = new URLSearchParams(window.location.search);
	const restaurantId = urlParams.get("restaurantId");

	console.log("Restuarant Id : " + restaurantId);
	if (!restaurantId) {
		alert("No restaurant selected!");
		window.location.href = "home.html"; // Redirect to home if no ID found
		return;
	}
	//console.log("Display ...");

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
			alert("Session validation failed in display!");
			window.location.href = "./login.html";
		}
	});



	// Fetch food items based on restaurant ID
	$.ajax({
		url: `fooditem/getAllFoodItems?restaurantId=${restaurantId}`,
		type: "GET",
		xhrFields: { withCredentials: true },
		success: function(response) {
			console.log(response);
			if (response.status === "success" && response.foodItems) {
				let foodList = $("#foodList");
				foodList.empty(); // Clear existing list

				response.foodItems.forEach(item => {
					let row = `<tr>
                        <td class="name" data-id="${item.foodId}">${item.foodName}</td>
                        <td class="price" data-id="${item.foodId}">${item.price}</td>
                        <td>${item.description}</td>
                        <td><input type="number" class="quantity" data-id="${item.foodId}" value="0" min="0" style="width: 50px;"></td>
                        <td><input type="checkbox" class="selectFood" data-id="${item.foodId}"></td>
                    </tr>`;
					foodList.append(row);
				});

				// Add a button to add multiple items to cart
			}
			else if (response.status === "failure") {
				//console.log("failure");
				alert(response.message);
			} else {

				$("#foodList").html("<tr><td colspan='5'>No food items available.</td></tr>");
			}
		},
		error: function(xhr, status) {
			if (status === 403) {
				alert("Error: " + xhr.responseText);
			} else {
				alert("Unexpected error: " + xhr.statusText);
			}
		}
	});

	$(document).on("click", "#addMultipleToCart", function() {
		let selectedItems = [];

		$(".selectFood:checked").each(function() {
			let foodId = $(this).data("id");
			let foodName = $(this).closest("tr").find(".name").text().trim();
			let quantity = $(`.quantity[data-id='${foodId}']`).val();
			let price = $(`.price[data-id='${foodId}']`).text();

			if (quantity > 0) {
				selectedItems.push({ foodId, foodName, quantity, price });
			}
		});

		if (selectedItems.length === 0) {
			alert("Please select at least one food item with a valid quantity!");
			return;
		}

		// Redirect to order page with selected items
		let orderData = encodeURIComponent(JSON.stringify(selectedItems));
		alert("Confirm to continue...");
		window.location.href = `order.html?restaurantId=${restaurantId}&orderData=${orderData}`;
	});
});
