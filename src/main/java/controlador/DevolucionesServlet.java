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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Listar todos los préstamos para la vista de devoluciones
        List<Prestamos> prestamos = prestamosDAO.listarPrestamosConDetalles();
        request.setAttribute("prestamos", prestamos);
        request.getRequestDispatcher("/jsp/devoluciones.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");

        if ("registrarDevolucion".equals(accion)) {
            int idPrestamo = Integer.parseInt(request.getParameter("idPrestamo"));
            double mora = Double.parseDouble(request.getParameter("mora"));

            boolean actualizado = prestamosDAO.marcarDevuelto(idPrestamo, mora);

            if (actualizado) {
                request.setAttribute("mensaje", "Devolución registrada exitosamente.");
            } else {
                request.setAttribute("mensaje", "Error al registrar la devolución.");
            }

            // Recargar la lista de préstamos después de la acción
            List<Prestamos> prestamos = prestamosDAO.listarPrestamosConDetalles();
            request.setAttribute("prestamos", prestamos);
            request.getRequestDispatcher("/jsp/devoluciones.jsp").forward(request, response);
        }
    }
    
}
