<%-- 
    Document   : usuarios
    Created on : 5 dic. 2024, 23:48:34
    Author     : Hector Marquez
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Gestión de Usuarios</title>
    </head>
    <body>
        <h1>Gestión de Usuarios</h1>

        <!-- Formulario de Entrada -->
        <form method="post" action="usuarios">
            <input type="hidden" name="accion" value="guardar">
            <label>Nombre:</label>
            <input type="text" name="nombre" id="nombre" value="${usuario.nombreUsuario}" required><br>
            <label>Email:</label>
            <input type="email" name="email" id="email" value="${usuario.email}" required><br>
            <label>Contraseña:</label>
            <input type="password" name="contrasena" id="contrasena"><br>
            <label>Tipo de Usuario:</label>
            <select name="tipoUsuario" id="tipoUsuario">
                <option value="Admin" ${usuario.tipoUsuario == "Admin" ? "selected" : ""}>Admin</option>
                <option value="Alumno" ${usuario.tipoUsuario == "Alumno" ? "selected" : ""}>Alumno</option>
                <option value="Profesor" ${usuario.tipoUsuario == "Profesor" ? "selected" : ""}>Profesor</option>
            </select><br>
            <button type="submit">Guardar</button>
            <a href="usuarios">Cancelar</a>
        </form>

        <!-- Tabla de Usuarios -->
        <h2>Lista de Usuarios</h2>
        <table border="1">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Nombre</th>
                    <th>Email</th>
                    <th>Tipo</th>
                    <th>Acciones</th>
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
                            <form method="post" action="usuarios">
                                <input type="hidden" name="accion" value="editar">
                                <input type="hidden" name="idUsuario" value="${usuario.idUsuario}">
                                <button type="submit">Editar</button>
                            </form>
                            <form method="post" action="usuarios">
                                <input type="hidden" name="accion" value="desactivar">
                                <input type="hidden" name="idUsuario" value="${usuario.idUsuario}">
                                <button type="submit">Desactivar</button>
                            </form>
                            <form method="post" action="usuarios">
                                <input type="hidden" name="accion" value="restablecer">
                                <input type="hidden" name="idUsuario" value="${usuario.idUsuario}">
                                <button type="submit">Restablecer Contraseña</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </body>

</html>
