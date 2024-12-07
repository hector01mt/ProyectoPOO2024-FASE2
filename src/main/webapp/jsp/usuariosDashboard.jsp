<%-- 
    Document   : usuariosDashboard
    Created on : 6 dic. 2024, 21:24:03
    Author     : Hector Marquez
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Dashboard - Usuarios</title>
    </head>
    <body>
        <h1>Bienvenido, ${usuario.nombreUsuario}</h1>

        <c:choose>
     
            <c:when test="${usuario.tipoUsuario == 'Profesor'}">
                <h2>Opciones para Profesores</h2>
                <ul>
                    <li><a href="${pageContext.request.contextPath}/prestamos">Consultar Préstamos</a></li>
                    <li><a href="${pageContext.request.contextPath}/devoluciones">Registrar Devoluciones</a></li>
                </ul>
            </c:when>


            <c:when test="${usuario.tipoUsuario == 'Alumno'}">
                <h2>Opciones para Alumnos</h2>
                <ul>
                    <li><a href="${pageContext.request.contextPath}/prestamos">Consultar Préstamos</a></li>
                </ul>
            </c:when>

  
            <c:otherwise>
                <h2>No tiene permisos para acceder a esta página.</h2>
            </c:otherwise>
        </c:choose>

        <p><a href="${pageContext.request.contextPath}/logout">Cerrar Sesión</a></p>
    </body>

    
</html>
