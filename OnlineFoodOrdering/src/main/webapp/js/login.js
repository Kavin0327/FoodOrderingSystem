$(document).ready(function() {
	$('button').click(function(event) {
		event.preventDefault();
		let userId = $("#uname").val().trim();
		let password = $("#password").val().trim();
		$.ajax({
			url: 'user/loginAction',
			type: "POST",
			data: { "user.userId": userId, "user.password": password },
			success: function(response) {
				console.log(response);
				if (response.status == "success") {
					localStorage.setItem("logedIn", true);
					//alert("Success Login : " + localStorage.getItem("logedIn"));
					$('#message').css("color", "green").text(response.message);
					location.href = "./home.html";
				}
				else {
					$('#message').css("color", "red").text(response.message);
				}

			},
			error: function(xhr) {
				alert("Error: " + xhr.status + " - " + xhr.message);
			}
		});
	});
});