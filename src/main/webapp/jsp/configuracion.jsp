<%-- 
    Document   : configuracion
    Created on : 6 dic. 2024, 18:20:07
    Author     : Hector Marquez
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Configuración del Sistema</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
    <div class="container mt-5">
        <div class="card">
            <div class="card-header bg-primary text-white">
                <h3>Configuración del Sistema</h3>
            </div>
            <div class="card-body">
                <!-- Formulario de Configuración -->
                <form method="post" action="configuracion">
                    <div class="mb-3">
                        <label for="moraDiaria" class="form-label">Mora diaria (USD):</label>
                        <input type="number" step="0.01" id="moraDiaria" name="moraDiaria" 
                               class="form-control" value="${configuracion.moraDiaria}" required>
                    </div>
                    <div class="mb-3">
                        <label for="maxEjemplares" class="form-label">Máximo de ejemplares permitidos:</label>
                        <input type="number" id="maxEjemplares" name="maxEjemplares" 
                               class="form-control" value="${configuracion.maxEjemplares}" required>
                    </div>
                    <button type="submit" class="btn btn-success">Guardar Cambios</button>
                </form>

                <!-- Mensaje de éxito -->
                <c:if test="${not empty mensaje}">
                    <div class="alert alert-success mt-3" role="alert">
                        ${mensaje}
                    </div>
                </c:if>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
