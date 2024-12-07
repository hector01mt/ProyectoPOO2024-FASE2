<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Página Principal</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
    <!-- Barra de navegación -->
    <nav class="navbar navbar-expand-lg navbar-light bg-primary">
        <div class="container">
            <a class="navbar-brand text-white" href="#">Sistema de Biblioteca</a>
            <div>
                <a href="${pageContext.request.contextPath}/login" class="btn btn-light">Iniciar Sesión</a>
            </div>
        </div>
    </nav>

    <!-- Contenido principal -->
    <div class="container mt-5">
        <h1 class="text-center text-primary mb-4">Bienvenido al Sistema de Biblioteca</h1>
        <p class="text-center">Consulta los ítems disponibles en nuestra colección.</p>

        <!-- Tabla de ítems -->
        <div class="table-responsive">
            <table class="table table-striped table-bordered">
                <thead class="table-primary">
                    <tr>
                        <th>ID</th>
                        <th>Título</th>
                        <th>Tipo</th>
                        <th>Autor</th>
                        <th>Año de Publicación</th>
                        <th>Ubicación</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="item" items="${items}">
                        <tr>
                            <td>${item.idItem}</td>
                            <td>${item.titulo}</td>
                            <td>${item.tipoItem}</td>
                            <td>${item.autor}</td>
                            <td>${item.anioPublicacion}</td>
                            <td>${item.ubicacionFisica}</td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty items}">
                        <tr>
                            <td colspan="6" class="text-center">No hay ítems disponibles.</td>
                        </tr>
                    </c:if>
                </tbody>
            </table>
        </div>
    </div>

    <!-- Pie de página -->
    <footer class="bg-primary text-white text-center py-3 mt-5">
        <p>Sistema de Biblioteca &copy; 2024</p>
    </footer>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>