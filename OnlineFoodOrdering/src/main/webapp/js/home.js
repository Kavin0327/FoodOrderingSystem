$(document).ready(function() {
	$.ajax({
		url: "user/checkUser",
		type: "GET",
		xhrFields: {
			withCredentials: true // Important for session handling
		},
		success: function(response) {
			if (response.status === "success") {
				//	console.log(response);
				$("#welcomeMessage").html("Welcome, " + response.username + " - " + response.role);
				renderMenu(response.allowedActions);
			} else {
				alert("No User Login....\n Please Login First ");
				window.location.href = "./login.html";
			}
		},
		error: function() {
			alert("Session validation failed in home !");
			window.location.href = "./login.html";
		}
	});

	function renderMenu(allowedActions) {
		let menuHtml = "";
		if (allowedActions.includes("showProfile")) {
			menuHtml += '<div class="showProfile">Show Profile</div>';
		}
		if (allowedActions.includes("showRestaurant")) {
			menuHtml += '<div class="showRestaurant">Order Food</div>';
		}
		if (allowedActions.includes("addRestuarant")) {
			menuHtml += '<div class="addRestuarant">Add Restuarant</div>';
		}
		if (allowedActions.includes("addManager")) {
			menuHtml += '<div class="addManager">Add Manager</div>';
		}
		//if (allowedActions.includes("addFoodItem")) {
		//	menuHtml += '<div class="addFoodItem">Add FoodItem</div>';
		//}
		if (allowedActions.includes("listRestaurant")) {
			menuHtml += '<div class="listRestaurant">List Restaurants</div>';
		}

		$(".menu-container").html(menuHtml);
	}
	
	
	$("#logoutBtn").click(function() {
		$.ajax({
			url: "logout",
			type: "GET",
			xhrFields: {
				withCredentials: true // Important for session handling
			},
			success: function() {
				localStorage.clear();
				window.location.href = "./login.html";
			}
		});
	});
	let uname = "";
	let addressId = 0;

	$(document).on('click', '.showProfile', function() {
		$.ajax({
			url: "user/getUser",
			type: "GET",
			xhrFields: {
				withCredentials: true // Important for session handling
			},
			success: function(response) {
				if (response.status == 'success') {
					$("#fullname").text(response.fullname);
					$("#username").text(response.username);
					uname = response.username;
					addressId = response.addressId;
					$("#password").text(response.password);
					$("#email").text(response.email);
					$("#address").text(response.address);
					$("#phoneNumber").text(response.phoneNumber);

					// Show profile when "Show Profile" is clicked
					$("#profileSection").css("font-size", 29).show();
					$(".showProfile").hide();
					$("#editProfileSection").hide();
				} else {
					$('#userDetail').css("color", "red").html("Could not load user details.");
				}
			},
			error: function(xhr) {
				alert("Error fetching user details: " + xhr.statusText);
			}
		});


	});
	$(document).on('click', '.backToProfileBtn', function() {
		$("#profileSection").show();
		$('editProfileSection').hide();
	});
	$(document).on('click', '.backToHomeBtn', function() {
		$("#profileSection").hide();
		$(".showProfile").show();
	});

	$(document).on('click', '.showRestaurant', function() {
		$.ajax({
			url: "restaurant/getAllRestaurant",
			type: "GET",
			xhrFields: {
				withCredentials: true // Important for session handling
			},
			success: function(response) {
				//console.log(response);
				if (response.status == 'success') {
					let tbodyEle = $("#table_content");
					tbodyEle.empty(); // Clear previous entries

					response.restaurantList.forEach(restaurant => {
						let row = `<tr>
                        <td>
                            <a href="display.html?restaurantId=${restaurant.restuarantId}">
                                ${restaurant.restuarantName}
                            </a>
                        </td>
                        <td>${restaurant.description || 'N/A'}</td>
                        <td>${restaurant.rating || 'N/A'}</td>
                        <td>${restaurant.address || 'N/A'}</td>
                    </tr>`;
						tbodyEle.append(row);
					});


					$("#restaurantListSection").css("font-size", 29).show();
					$(".showRestaurant").hide();

				} else {
					$('#userDetail').css("color", "red").html("Could not load restaurant details.");
				}
			},
			error: function(xhr) {
				alert("Error : " + xhr.statusText);
			}

		});
		$("#backBtn").click(function() {
			location.href = "home.html";
		});

	});

	$(".updateProfile").click(function() {

		$("#profileSection").hide();
		$("#editProfileSection").css("font-size", 25).show();

		// Populate the edit form with current user data
		$("#editFullname").val($("#fullname").text());
		$("#editEmail").val($("#email").text());
		$("#editPassword").val($("#password").text());
		$("#editAddress").val($("#address").text());
		$("#editPhoneNumber").val($("#phoneNumber").text());
		let addressValue = $("#address").val();
		let pincode = addressValue.split("-")[1];
		$("#editPincode").val(pincode);
	});

	// Send updated data to the server for saving
	$("#saveProfileBtn").click(function(event) {
		event.preventDefault(); // Prevent form submission

		// Get updated values from the form
		const updatedData = {
			"cUser.userId": uname,
			"cUser.name": $("#editFullname").val(),
			"cUser.email": $("#editEmail").val(),
			"cUser.password": $("#editPassword").val(),
			"cUser.phoneNumber": $("#editPhoneNumber").val(),
			"address.addressId": addressId,
			"address.address": $("#editAddress").val(),
			"address.pincode": $("#editPincode").val(),
			"address.landmark": $("#editLandmark").val()
		};

		// Send updated data to the server for saving
		$.ajax({
			url: "user/updateUser",
			type: "POST",
			xhrFields: {
				withCredentials: true // Important for session handling
			},
			data: updatedData,
			success: function(response) {
				console.log(response);
				if (response.jsonData.status === "success") {
					// Update the displayed profile details
					$("#fullname").text(updatedData["cUser.name"]);
					$("#email").text(updatedData["cUser.email"]);
					$("#password").text(updatedData["cUser.password"]);
					$("#address").text(updatedData["address.address"] + " - " + updatedData["address.pincode"]);
					$("#phoneNumber").text(updatedData["cUser.phoneNumber"]);

					// Show updated profile section and hide the edit form
					$("#profileSection").show();
					$("#editProfileSection").hide();
					alert("Profile updated successfully!");
				} else {
					alert("Failed to update profile: " + response.message);
				}
			},
			error: function(xhr) {
				alert("Error saving profile data: " + xhr.statusText);
			}
		});
	});

	$(document).on('click', '.addManager', function() {
		alert("Enter the Manager Details..");
		if (localStorage.getItem("logedIn")) {
			location.href = "addManager.html";
		}
		else {
			window.history.back();
		}
	});

	$(document).on('click', '.addRestuarant', function() {
		alert("Enter the Restaurant Details..");
		if (localStorage.getItem("logedIn"))
			location.href = "addRestuarant.html";
		else
			window.history.back();
	});

	$(document).on('click', '.listRestaurant', function() {
		if (localStorage.getItem("logedIn")) {
			// Fetch assigned restaurants for the logged-in manager
			$.ajax({
				url: "restaurant/getAssignedRestaurants",
				method: "GET",
				xhrFields: {
					withCredentials: true
				},
				success: function(response) {
					if (response.status === "success") {
						const restaurants = response.restaurantList;
						console.log(restaurants);
						const restaurantGrid = $("#restaurantGrid");
						restaurantGrid.empty();
						restaurants.forEach(restaurant => {
							const restaurantBox = `
											<div class="restaurant-box" data-restaurant-id="${restaurant.restuarantId}">
												<h3>${restaurant.restuarantName}</h3>
												<p>${restaurant.description}</p>
												<p><b>Rating:</b> ${restaurant.rating}</p>
												<p><b>Address:</b> ${restaurant.address}</p>
											</div>
										`;
							restaurantGrid.append(restaurantBox);
						});


						$("#restaurantList").show();
					} else {
						alert("Failed to fetch restaurants.");
					}
				},
				error: function(xhr) {
					alert("Error fetching restaurants: " + xhr.statusText);
				}

			});

			// Handle restaurant box click
			$(document).on("click", ".restaurant-box", function() {
				const restaurantId = $(this).data("restaurant-id");
				window.location.href = `foodList.html?restaurantId=${restaurantId}`;
			});

			$(document).on("click", "#backBtn", function() {
				//window.history.back();
				window.location.href = 'home.html';
			});

		}
		else {
			window.history.back();
		}

	});

});