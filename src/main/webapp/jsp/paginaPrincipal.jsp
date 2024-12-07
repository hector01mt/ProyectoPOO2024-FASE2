<%-- 
    Document   : paginaPrincipal
    Created on : 7 dic. 2024, 15:13:10
    Author     : Hector Marquez
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Página Principal</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body class="bg-light">
        <div class="container mt-3">
            <div class="d-flex justify-content-end">
                <a href="${pageContext.request.contextPath}/jsp/login.jsp" class="btn btn-primary">Iniciar Sesión</a>
            </div>
            <h1 class="text-center text-primary mt-4">Lista de Ítems Disponibles</h1>
            <table class="table table-striped mt-4">
                <thead class="table-dark">
                    <tr>
                        <th>ID</th>
                        <th>Título</th>
                        <th>Autor</th>
                        <th>Tipo</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="item" items="${items}">
                        <tr>
                            <td>${item.idItem}</td>
                            <td>${item.titulo}</td>
                            <td>${item.autor}</td>
                            <td>${item.tipoItem}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>