<%-- 
    Document   : items
    Created on : 6 dic. 2024, 16:02:14
    Author     : Hector Marquez
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestión de Ítems</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .table-container {
            max-height: 400px;
            overflow-y: auto;
        }
    </style>
</head>
<body class="bg-light">
<div class="container mt-4">
    <!-- Botón para regresar al Admin Dashboard -->
    <div class="d-flex justify-content-between mb-3">
        <h1 class="text-center">Gestión de Ítems</h1>
        <a href="${pageContext.request.contextPath}/adminDashboard" class="btn btn-outline-primary">Volver al Dashboard</a>
    </div>

    <!-- Mensajes -->
    <c:if test="${not empty param.mensaje}">
        <div class="alert alert-success">${param.mensaje}</div>
    </c:if>
    <c:if test="${not empty param.error}">
        <div class="alert alert-danger">${param.error}</div>
    </c:if>

    <!-- Buscador -->
    <div class="mb-3">
        <label for="buscar" class="form-label">Buscar Ítem</label>
        <input type="text" id="buscar" class="form-control" placeholder="Título, Autor o Tipo..." oninput="filtrarItems()">
    </div>

    <!-- Tabla de Ítems -->
    <div class="table-container border mb-4">
        <table class="table table-striped" id="tablaItems">
            <thead class="table-dark">
                <tr>
                    <th>ID</th>
                    <th>Título</th>
                    <th>Tipo</th>
                    <th>Autor</th>
                    <th>Año</th>
                    <th>Ubicación</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="item" items="${items}">
                    <tr onclick="seleccionarItem(${item.idItem}, '${item.titulo}', '${item.tipoItem}', '${item.autor}', '${item.anioPublicacion}', '${item.ubicacionFisica}')">
                        <td>${item.idItem}</td>
                        <td>${item.titulo}</td>
                        <td>${item.tipoItem}</td>
                        <td>${item.autor}</td>
                        <td>${item.anioPublicacion}</td>
                        <td>${item.ubicacionFisica}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

    <!-- Formulario -->
    <form method="post" action="items" class="mt-4">
        <input type="hidden" name="idItem" id="idItem">
        <div class="mb-3">
            <label for="titulo" class="form-label">Título</label>
            <input type="text" class="form-control" name="titulo" id="titulo" required>
        </div>
        <div class="mb-3">
            <label for="tipoItem" class="form-label">Tipo</label>
            <select class="form-select" name="tipoItem" id="tipoItem">
                <option value="Libro">Libro</option>
                <option value="Revista">Revista</option>
                <option value="CD">CD</option>
                <option value="Tesis">Tesis</option>
                <option value="Documento">Documento</option>
            </select>
        </div>
        <div class="mb-3">
            <label for="autor" class="form-label">Autor</label>
            <input type="text" class="form-control" name="autor" id="autor">
        </div>
        <div class="mb-3">
            <label for="anioPublicacion" class="form-label">Año de Publicación</label>
            <input type="number" class="form-control" name="anioPublicacion" id="anioPublicacion">
        </div>
        <div class="mb-3">
            <label for="ubicacionFisica" class="form-label">Ubicación Física</label>
            <input type="text" class="form-control" name="ubicacionFisica" id="ubicacionFisica" required>
        </div>
        <div class="d-flex justify-content-between">
            <button type="submit" name="accion" value="crear" class="btn btn-success">Agregar</button>
            <button type="submit" name="accion" value="editar" class="btn btn-warning">Editar</button>
            <button type="submit" name="accion" value="eliminar" class="btn btn-danger">Eliminar</button>
            <button type="button" class="btn btn-outline-secondary" onclick="limpiarCampos()">Limpiar campos</button>
        </div>
    </form>
</div>

<script>
    function seleccionarItem(id, titulo, tipo, autor, anio, ubicacion) {
        document.getElementById("idItem").value = id;
        document.getElementById("titulo").value = titulo;
        document.getElementById("tipoItem").value = tipo;
        document.getElementById("autor").value = autor;
        document.getElementById("anioPublicacion").value = anio || ""; // Valor predeterminado vacío
        document.getElementById("ubicacionFisica").value = ubicacion;
    }

    function filtrarItems() {
        const buscar = document.getElementById("buscar").value.toLowerCase();
        const filas = document.querySelectorAll("#tablaItems tbody tr");

        filas.forEach(fila => {
            const texto = fila.innerText.toLowerCase();
            fila.style.display = texto.includes(buscar) ? "" : "none";
        });
    }

    function limpiarCampos() {
        document.getElementById("idItem").value = "";
        document.getElementById("titulo").value = "";
        document.getElementById("tipoItem").value = "Libro"; // Resetear al valor predeterminado
        document.getElementById("autor").value = "";
        document.getElementById("anioPublicacion").value = "";
        document.getElementById("ubicacionFisica").value = "";
    }
</script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
