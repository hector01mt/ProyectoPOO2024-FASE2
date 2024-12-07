<%-- 
    Document   : devoluciones
    Created on : 6 dic. 2024, 17:40:24
    Author     : Hector Marquez
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Gestión de Devoluciones</title>
    </head>
    <body>
        <h1>Gestión de Devoluciones</h1>

        <!-- Botones de acción -->
        <form method="get" action="PrestamosServlet">
            <input type="hidden" name="accion" value="listarPendientes">
            <button type="submit">Filtrar Pendientes</button>
        </form>
        <form method="get" action="PrestamosServlet">
            <input type="hidden" name="accion" value="listarTodos">
            <button type="submit">Actualizar Tabla</button>
        </form>

        <!-- Búsqueda -->
        <form method="get" action="PrestamosServlet">
            <input type="hidden" name="accion" value="buscar">
            <label for="criterioPrestamo">Buscar:</label>
            <input type="text" id="criterioPrestamo" name="criterioPrestamo" placeholder="Buscar...">
            <button type="submit">Buscar</button>
        </form>

        <!-- Tabla de devoluciones -->
        <table border="1">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Usuario</th>
                    <th>Ejemplar</th>
                    <th>Fecha Préstamo</th>
                    <th>Fecha Devolución</th>
                    <th>Estado</th>
                    <th>Mora</th>
                    <th>Acción</th>
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
                        <td>${prestamo.mora}</td>
                        <td>
                            <form method="post" action="PrestamosServlet">
                                <input type="hidden" name="accion" value="registrarDevolucion">
                                <input type="hidden" name="idPrestamo" value="${prestamo.idPrestamo}">
                                <button type="submit" ${prestamo.devuelto ? "disabled" : ""}>Registrar Devolución</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </body>
</html>
