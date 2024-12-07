<%-- 
    Document   : login
    Created on : 6 dic. 2024, 01:41:38
    Author     : Hector Marquez
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
   <head>
    <title>Inicio de Sesión</title>
</head>
<body>
    <h1>Iniciar Sesión</h1>
    
    <!-- Mostrar error si existe -->
    <c:if test="${not empty error}">
        <p style="color: red;">${error}</p>
    </c:if>

    <form method="post" action="login">
        <label for="email">Correo Electrónico:</label><br>
        <input type="email" name="email" id="email" required><br>
        
        <label for="contrasena">Contraseña:</label><br>
        <input type="password" name="contrasena" id="contrasena" required><br><br>
        
        <button type="submit">Ingresar</button>
    </form>
</body>
</html>
