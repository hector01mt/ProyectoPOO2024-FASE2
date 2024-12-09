<%-- 
    Document   : prestamos
    Created on : 6 dic. 2024, 16:25:25
    Author     : Hector Marquez
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestión de Préstamos</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f8f9fa;
        }
        .container {
            max-width: 1400px;
        }
        .scrollable-table {
            max-height: 300px;
            overflow-y: auto;
        }
        .scrollable-table table {
            white-space: nowrap;
        }
    </style>
</head>
<body>
    <div class="container mt-4">
        <div class="d-flex justify-content-end">
            <a href="${pageContext.request.contextPath}/adminDashboard" class="btn btn-outline-dark mb-3">Regresar al Dashboard</a>
        </div>

        <h1 class="text-center mb-4">Gestión de Préstamos</h1>

        <div class="row">
            <!-- Panel izquierdo -->
            <div class="col-md-4">
                <div class="mb-4">
                    <h2>Seleccione Usuario</h2>
                    <input type="text" id="buscarUsuarios" class="form-control mb-2" placeholder="Buscar usuarios...">
                    <div class="scrollable-table">
                        <table class="table table-striped table-hover">
                            <thead class="table-dark">
                                <tr>
                                    <th>ID</th>
                                    <th>Nombre</th>
                                    <th>Email</th>
                                    <th>Tipo</th>
                                </tr>
                            </thead>
                            <tbody id="tablaUsuarios">
                                <c:forEach var="usuario" items="${usuarios}">
                                    <tr onclick="seleccionarUsuario('${usuario.idUsuario}', '${usuario.nombreUsuario}')">
                                        <td>${usuario.idUsuario}</td>
                                        <td>${usuario.nombreUsuario}</td>
                                        <td>${usuario.email}</td>
                                        <td>${usuario.tipoUsuario}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>

                <div class="mb-4">
                    <h2>Seleccione Ejemplar</h2>
                    <input type="text" id="buscarEjemplares" class="form-control mb-2" placeholder="Buscar ejemplares...">
                    <div class="scrollable-table">
                        <table class="table table-striped table-hover">
                            <thead class="table-dark">
                                <tr>
                                    <th>ID</th>
                                    <th>Título</th>
                                    <th>Autor</th>
                                    <th>Tipo</th>
                                </tr>
                            </thead>
                            <tbody id="tablaEjemplares">
                                <c:forEach var="item" items="${items}">
                                    <tr onclick="seleccionarItem('${item.idItem}', '${item.titulo}')">
                                        <td>${item.idItem}</td>
                                        <td>${item.titulo}</td>
                                        <td>${item.autor}</td>
                                        <td>${item.tipoItem}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>

            <!-- Panel derecho -->
            <div class="col-md-8">
                <h2>Asignar fecha para préstamo</h2>
                <form method="post" action="prestamos" onsubmit="mostrarResumen(event)">
                    <input type="hidden" name="accion" value="crear">
                    <input type="hidden" name="idUsuario" id="idUsuario">
                    <input type="hidden" name="idItem" id="idItem">
                    <div class="mb-3">
                        <label class="form-label">Fecha de Préstamo:</label>
                        <input type="date" class="form-control" id="fechaPrestamo" name="fechaPrestamo" required>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Fecha de Devolución:</label>
                        <input type="date" class="form-control" id="fechaDevolucion" name="fechaDevolucion" required>
                    </div>
                    <button type="submit" class="btn btn-primary mb-4">Registrar</button>
                </form>

                <h2>Préstamos registrados</h2>
                <input type="text" id="buscarPrestamos" class="form-control mb-2" placeholder="Buscar préstamos...">
                <div class="scrollable-table">
                    <table class="table table-striped table-hover">
                        <thead class="table-dark">
                            <tr>
                                <th>ID</th>
                                <th>Usuario</th>
                                <th>Ejemplar</th>
                                <th>Fecha de Préstamo</th>
                                <th>Fecha de Devolución</th>
                                <th>Estado</th>
                            </tr>
                        </thead>
                        <tbody id="tablaPrestamos">
                            <c:forEach var="prestamo" items="${prestamos}">
                                <tr>
                                    <td>${prestamo.idPrestamo}</td>
                                    <td>${prestamo.nombreUsuario}</td>
                                    <td>${prestamo.tituloEjemplar}</td>
                                    <td>${prestamo.fechaPrestamo}</td>
                                    <td>${prestamo.fechaDevolucion}</td>
                                    <td>${prestamo.devuelto ? "Devuelto" : "No Devuelto"}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <!-- Ventanas emergentes (modales) -->
    <div class="modal" id="modalMensaje" tabindex="-1" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Mensaje</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Cerrar"></button>
                </div>
                <div class="modal-body" id="modalBody">
                    <!-- Mensaje dinámico -->
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                </div>
            </div>
        </div>
    </div>

    <script>
        function seleccionarUsuario(id, nombre) {
            document.getElementById("idUsuario").value = id;
            mostrarModal(`Usuario seleccionado: ${nombre}`);
        }

        function seleccionarItem(id, titulo) {
            document.getElementById("idItem").value = id;
            mostrarModal(`Ejemplar seleccionado: ${titulo}`);
        }

        function mostrarResumen(event) {
            event.preventDefault();
            const idUsuario = document.getElementById("idUsuario").value;
            const idItem = document.getElementById("idItem").value;
            const fechaPrestamo = document.getElementById("fechaPrestamo").value;
            const fechaDevolucion = document.getElementById("fechaDevolucion").value;

            if (!idUsuario || !idItem) {
                mostrarModal("Por favor, seleccione un usuario y un ejemplar antes de registrar el préstamo.");
                return;
            }

            mostrarModal(`Préstamo registrado:<br>Usuario ID: ${idUsuario}<br>Ejemplar ID: ${idItem}<br>Fecha Préstamo: ${fechaPrestamo}<br>Fecha Devolución: ${fechaDevolucion}`);
            setTimeout(() => event.target.submit(), 2000);
        }

        function mostrarModal(mensaje) {
            const modalBody = document.getElementById("modalBody");
            modalBody.innerHTML = mensaje;
            const modal = new bootstrap.Modal(document.getElementById("modalMensaje"));
            modal.show();
        }
    </script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>