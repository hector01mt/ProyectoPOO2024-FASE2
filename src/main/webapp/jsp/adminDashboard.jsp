<%-- 
    Document   : adminDashboard
    Created on : 6 dic. 2024, 18:33:22
    Author     : Hector Marquez
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    // Evitar caché
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);

    // Verificar si el usuario está autenticado
    if (session.getAttribute("usuario") == null) {
        response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Panel de Administración</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
    <div class="container mt-3">
        <!-- Botón de Cerrar Sesión -->
        <div class="d-flex justify-content-end">
            <form method="get" action="${pageContext.request.contextPath}/logout">
                <button type="submit" class="btn btn-danger">Cerrar Sesión</button>
            </form>
        </div>
        
        <!-- Encabezado -->
        <div class="text-center mt-3">
            <h1 class="mb-4 text-primary">Panel de Administración</h1>
            <p class="mb-4">Seleccione una opción para gestionar el sistema:</p>
        </div>
        
        <!-- Opciones de Administración -->
        <div class="row g-3 justify-content-center">
            <div class="col-12 col-md-4">
                <a href="${pageContext.request.contextPath}/usuarios" class="btn btn-primary w-100 py-3">Gestionar Usuarios</a>
            </div>
            <div class="col-12 col-md-4">
                <a href="${pageContext.request.contextPath}/items" class="btn btn-primary w-100 py-3">Gestionar Ítems</a>
            </div>
            <div class="col-12 col-md-4">
                <a href="${pageContext.request.contextPath}/prestamos" class="btn btn-primary w-100 py-3">Gestionar Préstamos</a>
            </div>
            <div class="col-12 col-md-4">
                <a href="${pageContext.request.contextPath}/devoluciones" class="btn btn-primary w-100 py-3">Gestionar Devoluciones</a>
            </div>
            <div class="col-12 col-md-4">
                <a href="${pageContext.request.contextPath}/configuracion" class="btn btn-primary w-100 py-3">Configuración del Sistema</a>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
