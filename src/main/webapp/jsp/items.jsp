<%-- 
    Document   : items
    Created on : 6 dic. 2024, 16:02:14
    Author     : Hector Marquez
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
   <head>
    <title>Gestión de Ítems</title>
</head>
<body>
    <h1>Gestión de Ítems</h1>
    
    <!-- Formulario para agregar/editar ítems -->
    <form method="post" action="items">
        <input type="hidden" name="idItem" value="${itemSeleccionado.idItem}" />
        <label for="titulo">Título:</label>
        <input type="text" id="titulo" name="titulo" value="${itemSeleccionado.titulo}" required /><br/>

        <label for="tipoItem">Tipo:</label>
        <select id="tipoItem" name="tipoItem">
            <option value="Libro" ${itemSeleccionado.tipoItem == 'Libro' ? 'selected' : ''}>Libro</option>
            <option value="Revista" ${itemSeleccionado.tipoItem == 'Revista' ? 'selected' : ''}>Revista</option>
            <option value="CD" ${itemSeleccionado.tipoItem == 'CD' ? 'selected' : ''}>CD</option>
            <option value="Tesis" ${itemSeleccionado.tipoItem == 'Tesis' ? 'selected' : ''}>Tesis</option>
            <option value="Documento" ${itemSeleccionado.tipoItem == 'Documento' ? 'selected' : ''}>Documento</option>
        </select><br/>

        <label for="autor">Autor:</label>
        <input type="text" id="autor" name="autor" value="${itemSeleccionado.autor}" /><br/>

        <label for="anioPublicacion">Año de Publicación:</label>
        <input type="number" id="anioPublicacion" name="anioPublicacion" value="${itemSeleccionado.anioPublicacion}" /><br/>

        <label for="ubicacionFisica">Ubicación Física:</label>
        <input type="text" id="ubicacionFisica" name="ubicacionFisica" value="${itemSeleccionado.ubicacionFisica}" required /><br/>

        <button type="submit" name="accion" value="crear">Agregar</button>
        <button type="submit" name="accion" value="editar">Editar</button>
        <button type="reset">Limpiar</button>
    </form>

    <!-- Tabla para listar ítems -->
    <h2>Lista de Ítems</h2>
    <form method="get" action="items">
        <label for="criterio">Buscar:</label>
        <input type="text" id="criterio" name="criterio" value="${criterio}" />
        <button type="submit">Buscar</button>
    </form>
    <table border="1">
        <thead>
            <tr>
                <th>ID</th>
                <th>Título</th>
                <th>Tipo</th>
                <th>Autor</th>
                <th>Año</th>
                <th>Ubicación</th>
                <th>Acciones</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="item" items="${items}">
                <tr>
                    <td>${item.idItem}</td>
                    <td>${item.titulo}</td>
                    <td>${item.tipoItem}</td>
                    <td>${item.autor}</td>
                    <td>${item.anioPublicacion}</td>
                    <td>${item.ubicacionFisica}</td>
                    <td>
                        <form method="post" action="items" style="display:inline;">
                            <input type="hidden" name="idItem" value="${item.idItem}" />
                            <button type="submit" name="accion" value="cargar">Editar</button>
                        </form>
                        <form method="post" action="items" style="display:inline;">
                            <input type="hidden" name="idItem" value="${item.idItem}" />
                            <button type="submit" name="accion" value="eliminar">Eliminar</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>
