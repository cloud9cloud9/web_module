<%--
  Created by IntelliJ IDEA.
  User: Vladick
  Date: 06/02/2024
  Time: 22:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User Registration</title>
</head>
<body>

<h2>User Registration</h2>

<form action="${pageContext.request.contextPath}/registration" method="post">
    <label for="email">Електронна пошта:</label>
    <input type="email" id="email" name="email" required><br><br>

    <label for="password">Пароль:</label>
    <input type="password" id="password" name="password" required><br><br>

    <select id="userRole" name="role" required>
        <option value="USER">User</option>
        <option value="ADMIN">Admin</option>
    </select><br>

    <input type="submit" value="Register">
</form>

<c:if test="${not empty accountExists}">
    <p style="color:red">Користувач з такою адресою електронної пошти вже існує. <a href="${pageContext.request.contextPath}/login">Увійти тут</a>.</p>
</c:if>

</body>
</html>
