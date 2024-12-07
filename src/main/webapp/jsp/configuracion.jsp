<%-- 
    Document   : configuracion
    Created on : 6 dic. 2024, 18:20:07
    Author     : Hector Marquez
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
    <title>Configuración del Sistema</title>
</head>
<body>
    <h1>Configuración del Sistema</h1>
    <form method="post" action="configuracion">
        <table>
            <tr>
                <td><label for="moraDiaria">Mora diaria (USD):</label></td>
                <td><input type="number" step="0.01" id="moraDiaria" name="moraDiaria" 
                           value="${configuracion.moraDiaria}" required></td>
            </tr>
            <tr>
                <td><label for="maxEjemplares">Máximo de ejemplares permitidos:</label></td>
                <td><input type="number" id="maxEjemplares" name="maxEjemplares" 
                           value="${configuracion.maxEjemplares}" required></td>
            </tr>
        </table>
        <button type="submit">Guardar Cambios</button>
    </form>

    <c:if test="${not empty mensaje}">
        <p style="color: green;">${mensaje}</p>
    </c:if>
</body>
    
</html>
