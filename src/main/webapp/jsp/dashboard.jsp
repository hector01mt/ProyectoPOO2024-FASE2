<%-- 
    Document   : dashboard
    Created on : 6 dic. 2024, 01:49:20
    Author     : Hector Marquez
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="modelo.Usuarios" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%
    
    Usuarios usuario = (Usuarios) session.getAttribute("usuario");

    if (usuario == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
    <head>
    <title>Panel Principal</title>
</head>
<body>
    <h1>Bienvenido, <%= usuario.getNombreUsuario() %>!</h1>
    <p>Email: <%= usuario.getEmail() %></p>
    <p>Tipo de Usuario: <%= usuario.getTipoUsuario() %></p>
    <a href="logout">Cerrar Sesión</a>
</body>
</html>
