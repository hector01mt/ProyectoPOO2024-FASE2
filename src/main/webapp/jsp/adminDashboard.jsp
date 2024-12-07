<%-- 
    Document   : adminDashboard
    Created on : 6 dic. 2024, 18:33:22
    Author     : Hector Marquez
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Panel de Administración</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                text-align: center;
                padding: 20px;
            }
            h1 {
                color: #333;
            }
            .button-container {
                display: flex;
                justify-content: center;
                flex-wrap: wrap;
                gap: 20px;
                margin-top: 30px;
            }
            .button {
                padding: 15px 30px;
                font-size: 16px;
                color: white;
                background-color: #007BFF;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                text-decoration: none;
            }
            .button:hover {
                background-color: #0056b3;
            }
        </style>
    </head>
    <body>
        <h1>Panel de Administración</h1>
        <p>Seleccione una opción para gestionar el sistema:</p>

        <div class="button-container">
            <a href="${pageContext.request.contextPath}/usuarios" class="button">Gestionar Usuarios</a>
            <a href="${pageContext.request.contextPath}/items" class="button">Gestionar Ítems</a>
            <a href="${pageContext.request.contextPath}/prestamos" class="button">Gestionar Préstamos</a>
            <a href="${pageContext.request.contextPath}/devoluciones" class="button">Gestionar Devoluciones</a>
            <a href="${pageContext.request.contextPath}/configuracion" class="button">Configuración del Sistema</a>
        </div>
    </body> 
</html>
