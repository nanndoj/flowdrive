<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<script type="text/javascript">
			function storeUserData() {
				// Define user data to be stored
				var userData = {
					id: '${requestScope.user.id}',
					token: '${requestScope.token}',
					name: '${requestScope.user.name}',
					email: '${requestScope.user.email}',
					profileURL: '${requestScope.user.profileImageURL}',
					logonTime: new Date()
				}
				
				// Store the user data while browser is active
				sessionStorage.setItem("loggedUser", JSON.stringify(userData));
				
				// Redirect to home page
				document.location.href = "/app";
			}
		</script>
	</head>
	<body onload="storeUserData()">
		Please wait while you are redirected...
	</body>
</html>