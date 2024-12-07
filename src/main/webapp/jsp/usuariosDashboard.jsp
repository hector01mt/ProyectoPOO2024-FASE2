<%-- 
    Document   : usuariosDashboard
    Created on : 6 dic. 2024, 21:24:03
    Author     : Hector Marquez
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard - Usuario</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f8f9fa;
        }
        .welcome {
            text-align: center;
            margin-top: 20px;
        }
        .container {
            margin-top: 30px;
        }
        .table-container {
            max-height: 300px;
            overflow-y: auto;
        }
        .btn-primary {
            background-color: white;
            color: black;
            border: 1px solid #ccc;
        }
        .btn-primary:hover {
            background-color: #e0e0e0;
        }
    </style>
</head>
<body>
    <div class="container">
        <!-- Botón para regresar -->
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h1>Bienvenido, ${usuario.nombreUsuario}</h1>
            <form method="post" action="${pageContext.request.contextPath}/logout">
                <button type="submit" class="btn btn-danger">Cerrar Sesión</button>
            </form>
        </div>

        <div class="row">
            <!-- Libros Disponibles -->
            <div class="col-md-6">
                <h3 class="text-primary">Libros Disponibles</h3>
                <div class="table-container">
                    <table class="table table-bordered table-hover">
                        <thead class="table-dark">
                            <tr>
                                <th>Título</th>
                                <th>Autor</th>
                                <th>Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="item" items="${items}">
                                <tr onclick="seleccionarLibro(${item.idItem}, '${item.titulo}')">
                                    <td>${item.titulo}</td>
                                    <td>${item.autor}</td>
                                    <td>
                                        <form method="post" action="${pageContext.request.contextPath}/prestamos">
                                            <input type="hidden" name="accion" value="crear">
                                            <input type="hidden" name="idItem" value="${item.idItem}">
                                            <button type="submit" class="btn btn-success btn-sm">Solicitar Préstamo</button>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>

            <!-- Préstamos Activos -->
            <div class="col-md-6">
                <h3 class="text-primary">Préstamos Activos</h3>
                <div class="table-container">
                    <table class="table table-bordered table-hover">
                        <thead class="table-dark">
                            <tr>
                                <th>Título</th>
                                <th>Fecha Préstamo</th>
                                <th>Fecha Devolución</th>
                                <th>Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="prestamo" items="${prestamosActivos}">
                                <tr>
                                    <td>${prestamo.tituloEjemplar}</td>
                                    <td>${prestamo.fechaPrestamo}</td>
                                    <td>${prestamo.fechaDevolucion}</td>
                                    <td>
                                        <form method="post" action="${pageContext.request.contextPath}/prestamos">
                                            <input type="hidden" name="accion" value="devolver">
                                            <input type="hidden" name="idPrestamo" value="${prestamo.idPrestamo}">
                                            <button type="submit" class="btn btn-warning btn-sm" ${prestamo.devuelto ? "disabled" : ""}>
                                                Registrar Devolución
                                            </button>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
