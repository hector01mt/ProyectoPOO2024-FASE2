/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.ConfiguracionSistema;
import modelo.dao.ConfiguracionSistemaDao;

import java.io.IOException;
/**
 *
 * @author Hector Marquez
 */
@WebServlet("/configuracion")
public class ConfiguracionSistemaServlet extends HttpServlet  {
    
    private final ConfiguracionSistemaDao configuracionDao = new ConfiguracionSistemaDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Obtener la configuración actual
        ConfiguracionSistema configuracion = configuracionDao.obtenerConfiguracion();
        request.setAttribute("configuracion", configuracion);

        // Redirigir a la página JSP para visualizar y editar configuración
        request.getRequestDispatcher("/jsp/configuracion.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Obtener parámetros del formulario
        try {
            double moraDiaria = Double.parseDouble(request.getParameter("moraDiaria"));
            int maxEjemplares = Integer.parseInt(request.getParameter("maxEjemplares"));

            // Crear el objeto ConfiguracionSistema
            ConfiguracionSistema configuracion = new ConfiguracionSistema(moraDiaria, maxEjemplares);

            // Guardar la configuración en la base de datos
            boolean actualizado = configuracionDao.guardarConfiguracion(configuracion);

            if (actualizado) {
                request.setAttribute("mensaje", "Configuración actualizada correctamente.");
            } else {
                request.setAttribute("mensaje", "Error al actualizar la configuración.");
            }
        } catch (NumberFormatException e) {
            request.setAttribute("mensaje", "Valores inválidos. Por favor, revise los campos.");
        }

        // Volver a cargar la configuración actual
        doGet(request, response);
    }
    
}
