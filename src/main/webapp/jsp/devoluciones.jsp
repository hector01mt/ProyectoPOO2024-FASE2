<%-- 
    Document   : devoluciones
    Created on : 6 dic. 2024, 17:40:24
    Author     : Hector Marquez
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestión de Devoluciones</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f8f9fa;
        }
        .container {
            max-width: 1200px;
        }
        .scrollable-table {
            max-height: 450px;
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

        <h1 class="text-center mb-4">Gestión de Devoluciones</h1>

        <div class="row">
            <!-- Panel principal -->
            <div class="col-12">
                <h2>Devoluciones</h2>
                <input type="text" id="buscarDevoluciones" class="form-control mb-2" placeholder="Buscar devoluciones...">
                <div class="scrollable-table">
                    <table class="table table-bordered table-hover">
                        <thead class="table-dark">
                            <tr>
                                <th>ID</th>
                                <th>Usuario</th>
                                <th>Ejemplar</th>
                                <th>Fecha Préstamo</th>
                                <th>Fecha Devolución</th>
                                <th>Estado</th>
                                <th>Mora</th>
                                <th>Acciones</th>
                            </tr>
                        </thead>
                        <tbody id="tablaDevoluciones">
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
                                        <form method="post" action="${pageContext.request.contextPath}/devoluciones">
                                            <input type="hidden" name="accion" value="registrarDevolucion">
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

    <script>
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

        agregarFiltro('buscarDevoluciones', 'tablaDevoluciones');
    </script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
