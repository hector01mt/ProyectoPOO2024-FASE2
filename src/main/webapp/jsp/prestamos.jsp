<%-- 
    Document   : prestamos
    Created on : 6 dic. 2024, 16:25:25
    Author     : Hector Marquez
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
    <title>Gestión de Préstamos</title>
</head>
<body>
    <h1>Gestión de Préstamos</h1>

    <!-- Sección de Búsqueda -->
    <form method="post" action="prestamos">
        <input type="hidden" name="accion" value="buscar">
        <label>Buscar Usuarios:</label>
        <input type="text" name="criterioUsuario" placeholder="Nombre o email">
        <button type="submit">Buscar</button>
    </form>
    <form method="post" action="prestamos">
        <input type="hidden" name="accion" value="buscar">
        <label>Buscar Ejemplares:</label>
        <input type="text" name="criterioItem" placeholder="Título o autor">
        <button type="submit">Buscar</button>
    </form>
    <form method="post" action="prestamos">
        <input type="hidden" name="accion" value="buscar">
        <label>Buscar Préstamos:</label>
        <input type="text" name="criterioPrestamo" placeholder="Usuario o título">
        <button type="submit">Buscar</button>
    </form>

    <!-- Tabla de Usuarios -->
    <h2>Usuarios</h2>
    <table border="1">
        <thead>
            <tr>
                <th>ID</th>
                <th>Nombre</th>
                <th>Email</th>
                <th>Tipo</th>
                <th>Seleccionar</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="usuario" items="${usuarios}">
                <tr>
                    <td>${usuario.idUsuario}</td>
                    <td>${usuario.nombreUsuario}</td>
                    <td>${usuario.email}</td>
                    <td>${usuario.tipoUsuario}</td>
                    <td>
                        <button onclick="seleccionarUsuario(${usuario.idUsuario}, '${usuario.nombreUsuario}')">Seleccionar</button>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <!-- Tabla de Ejemplares -->
    <h2>Ejemplares</h2>
    <table border="1">
        <thead>
            <tr>
                <th>ID</th>
                <th>Título</th>
                <th>Autor</th>
                <th>Tipo</th>
                <th>Seleccionar</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="item" items="${items}">
                <tr>
                    <td>${item.idItem}</td>
                    <td>${item.titulo}</td>
                    <td>${item.autor}</td>
                    <td>${item.tipoItem}</td>
                    <td>
                        <button onclick="seleccionarItem(${item.idItem}, '${item.titulo}')">Seleccionar</button>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <!-- Formulario de Registro de Préstamos -->
    <h2>Registrar Préstamo</h2>
    <form method="post" action="prestamos">
        <input type="hidden" name="accion" value="crear">
        <input type="hidden" name="idUsuario" id="idUsuario">
        <input type="hidden" name="idItem" id="idItem">
        <label>Fecha de Préstamo:</label>
        <input type="date" name="fechaPrestamo" required><br>
        <label>Fecha de Devolución:</label>
        <input type="date" name="fechaDevolucion" required><br>
        <button type="submit">Registrar</button>
    </form>

    <!-- Tabla de Préstamos -->
    <h2>Préstamos</h2>
    <table border="1">
        <thead>
            <tr>
                <th>ID</th>
                <th>Usuario</th>
                <th>Ejemplar</th>
                <th>Fecha de Préstamo</th>
                <th>Fecha de Devolución</th>
                <th>Estado</th>
                <th>Acciones</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="prestamo" items="${prestamos}">
                <tr>
                    <td>${prestamo.idPrestamo}</td>
                    <td>${prestamo.nombreUsuario}</td>
                    <td>${prestamo.tituloEjemplar}</td>
                    <td>${prestamo.fechaPrestamo}</td>
                    <td>${prestamo.fechaDevolucion}</td>
                    <td>${prestamo.devuelto ? "Devuelto" : "No Devuelto"}</td>
                    <td>
                        <form method="post" action="prestamos">
                            <input type="hidden" name="accion" value="devolver">
                            <input type="hidden" name="idPrestamo" value="${prestamo.idPrestamo}">
                            <button type="submit">Registrar Devolución</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <script>
        function seleccionarUsuario(id, nombre) {
            document.getElementById("idUsuario").value = id;
            alert("Usuario seleccionado: " + nombre);
        }

        function seleccionarItem(id, titulo) {
            document.getElementById("idItem").value = id;
            alert("Ejemplar seleccionado: " + titulo);
        }
    </script>
</body>

</html>
