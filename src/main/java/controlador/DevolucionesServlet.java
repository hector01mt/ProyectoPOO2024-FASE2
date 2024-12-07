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
import modelo.Prestamos;
import modelo.dao.PrestamosDAO;

import java.io.IOException;
import java.util.List;

/**
 *
 * @author Hector Marquez
 */
@WebServlet("/devoluciones")
public class DevolucionesServlet extends HttpServlet {
    private final PrestamosDAO prestamosDAO = new PrestamosDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // Cargar todos los préstamos y enviarlos a la vista
        List<Prestamos> prestamos = prestamosDAO.listarPrestamosConDetalles();
        request.setAttribute("prestamos", prestamos);

        // Redirigir a la página devoluciones.jsp
        request.getRequestDispatcher("/jsp/devoluciones.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String accion = request.getParameter("accion");

        if ("registrarDevolucion".equals(accion)) {
            try {
                int idPrestamo = Integer.parseInt(request.getParameter("idPrestamo"));
                // Registrar devolución en la base de datos
                boolean actualizado = prestamosDAO.marcarDevuelto(idPrestamo, 0.0);
                if (actualizado) {
                    response.sendRedirect(request.getContextPath() + "/devoluciones");
                } else {
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al registrar la devolución");
                }
            } catch (Exception e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Datos inválidos");
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción no válida");
        }
    }
    
}
