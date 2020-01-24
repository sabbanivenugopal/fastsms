 <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<font color= "red">${msg}</font>

Enter Mobile Number:
<form action="generateOtp" method="post">
<input type="text" name="mobile"/></br></br>
<input type="submit" value="Generate Otp"/>
</form>

</body>
</html>