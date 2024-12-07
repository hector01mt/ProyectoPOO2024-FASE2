<%-- 
    Document   : usuarios
    Created on : 5 dic. 2024, 23:48:34
    Author     : Hector Marquez
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestión de Usuarios</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .table-container {
            max-height: 400px;
            overflow-y: auto;
        }
    </style>
</head>
<body>
<div class="container mt-4">
    <!-- Botón para regresar al Admin Dashboard -->
    <div class="d-flex justify-content-between mb-3">
        <h1 class="text-center">Gestión de Usuarios</h1>
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
        <label for="buscar" class="form-label">Buscar Usuario</label>
        <input type="text" id="buscar" class="form-control" placeholder="Nombre o email..." oninput="filtrarUsuarios()">
    </div>

    <!-- Tabla de Usuarios -->
    <div class="table-container border">
        <table class="table table-striped" id="tablaUsuarios">
            <thead class="table-dark">
                <tr>
                    <th>ID</th>
                    <th>Nombre</th>
                    <th>Email</th>
                    <th>Tipo</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="usuario" items="${usuarios}">
                    <tr onclick="seleccionarUsuario(${usuario.idUsuario}, '${usuario.nombreUsuario}', '${usuario.email}', '${usuario.tipoUsuario}')">
                        <td>${usuario.idUsuario}</td>
                        <td>${usuario.nombreUsuario}</td>
                        <td>${usuario.email}</td>
                        <td>${usuario.tipoUsuario}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

    <!-- Formulario -->
    <form method="post" action="usuarios" class="mt-4">
        <input type="hidden" name="idUsuario" id="idUsuario">
        <div class="mb-3">
            <label for="nombre" class="form-label">Nombre</label>
            <input type="text" class="form-control" name="nombre" id="nombre" required>
        </div>
        <div class="mb-3">
            <label for="email" class="form-label">Email</label>
            <input type="email" class="form-control" name="email" id="email" required>
        </div>
        <div class="mb-3">
            <label for="contrasena" class="form-label">Contraseña</label>
            <input type="password" class="form-control" name="contrasena" id="contrasena">
        </div>
        <div class="mb-3">
            <label for="tipoUsuario" class="form-label">Tipo de Usuario</label>
            <select class="form-select" name="tipoUsuario" id="tipoUsuario">
                <option value="Admin">Admin</option>
                <option value="Alumno">Alumno</option>
                <option value="Profesor">Profesor</option>
            </select>
        </div>
        <div class="d-flex justify-content-between">
            <button type="submit" name="accion" value="guardar" class="btn btn-primary">Guardar</button>
            <button type="submit" name="accion" value="editar" class="btn btn-warning">Editar</button>
            <button type="submit" name="accion" value="desactivar" class="btn btn-danger">Desactivar</button>
            <button type="submit" name="accion" value="restablecer" class="btn btn-secondary">Restablecer Contraseña</button>
            <button type="button" class="btn btn-outline-secondary" onclick="limpiarCampos()">Limpiar campos</button>
        </div>
    </form>
</div>

<script>
    function seleccionarUsuario(id, nombre, email, tipo) {
        document.getElementById("idUsuario").value = id;
        document.getElementById("nombre").value = nombre;
        document.getElementById("email").value = email;
        document.getElementById("tipoUsuario").value = tipo;
        document.getElementById("contrasena").value = ""; // Limpiar campo de contraseña
    }

    function filtrarUsuarios() {
        const buscar = document.getElementById("buscar").value.toLowerCase();
        const filas = document.querySelectorAll("#tablaUsuarios tbody tr");

        filas.forEach(fila => {
            const texto = fila.innerText.toLowerCase();
            fila.style.display = texto.includes(buscar) ? "" : "none";
        });
    }

    function limpiarCampos() {
        document.getElementById("idUsuario").value = "";
        document.getElementById("nombre").value = "";
        document.getElementById("email").value = "";
        document.getElementById("contrasena").value = "";
        document.getElementById("tipoUsuario").value = "Admin"; // Resetear al valor predeterminado
    }
</script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
