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
            max-width: 1200px;
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
        <!-- Botón para regresar al dashboard -->
        <div class="d-flex justify-content-end">
            <a href="${pageContext.request.contextPath}/adminDashboard" class="btn btn-outline-dark mb-3">Regresar al Dashboard</a>
        </div>

        <h1 class="text-center mb-4">Gestión de Préstamos</h1>

        <div class="row">
            <!-- Panel izquierdo -->
            <div class="col-md-7">
                <div class="mb-4">
                    <h2>Usuarios</h2>
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
                                    <tr onclick="seleccionarUsuario(${usuario.idUsuario}, '${usuario.nombreUsuario}')">
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
                    <h2>Ejemplares</h2>
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
                                    <tr onclick="seleccionarItem(${item.idItem}, '${item.titulo}')">
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

                <div>
                    <h2>Registrar Préstamo</h2>
                    <form method="post" action="prestamos">
                        <input type="hidden" name="accion" value="crear">
                        <input type="hidden" name="idUsuario" id="idUsuario">
                        <input type="hidden" name="idItem" id="idItem">
                        <div class="mb-3">
                            <label class="form-label">Fecha de Préstamo:</label>
                            <input type="date" class="form-control" name="fechaPrestamo" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Fecha de Devolución:</label>
                            <input type="date" class="form-control" name="fechaDevolucion" required>
                        </div>
                        <button type="submit" class="btn btn-primary">Registrar</button>
                    </form>
                </div>
            </div>

            <!-- Panel derecho -->
            <div class="col-md-5">
                <h2>Préstamos</h2>
                <input type="text" id="buscarPrestamos" class="form-control mb-2" placeholder="Buscar préstamos...">
                <div class="scrollable-table" style="max-height: calc(300px * 2 + 32px);">
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

    <script>
        function seleccionarUsuario(id, nombre) {
            document.getElementById("idUsuario").value = id;
            alert("Usuario seleccionado: " + nombre);
        }

        function seleccionarItem(id, titulo) {
            document.getElementById("idItem").value = id;
            alert("Ejemplar seleccionado: " + titulo);
        }

        function agregarFiltro(buscarId, tablaId) {
            const input = document.getElementById(buscarId);
            const table = document.getElementById(tablaId);
            const rows = table.getElementsByTagName('tr');

            input.addEventListener('input', () => {
                const filter = input.value.toLowerCase();
                for (let i = 0; i < rows.length; i++) {
                    const cells = rows[i].getElementsByTagName('td');
                    let match = false;
                    for (let j = 0; j < cells.length; j++) {
                        if (cells[j].textContent.toLowerCase().includes(filter)) {
                            match = true;
                            break;
                        }
                    }
                    rows[i].style.display = match ? '' : 'none';
                }
            });
        }

        agregarFiltro('buscarUsuarios', 'tablaUsuarios');
        agregarFiltro('buscarEjemplares', 'tablaEjemplares');
        agregarFiltro('buscarPrestamos', 'tablaPrestamos');
    </script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>