$(document).ready(function() {
	$(".box").click(function() {
		if ($(this).text() === "Login") {
			location.href = "./login.html";
		} else if ($(this).text() === "SignUp") {
			location.href = "./signup.html";
		}
	});

});
