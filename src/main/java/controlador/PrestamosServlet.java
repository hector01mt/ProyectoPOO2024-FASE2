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
import modelo.dao.UsuariosDAO;
import modelo.dao.ItemDAO;
import modelo.Usuarios;
import modelo.Item;

import java.io.IOException;
import java.sql.Date;
import java.util.List;


/**
 *
 * @author Hector Marquez
 */
@WebServlet("/prestamos")
public class PrestamosServlet extends HttpServlet {
    
    private final PrestamosDAO prestamosDAO = new PrestamosDAO();
    private final UsuariosDAO usuariosDAO = new UsuariosDAO();
    private final ItemDAO itemDAO = new ItemDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Usuarios> usuarios = usuariosDAO.listarTodos();
            List<Item> items = itemDAO.listarTodos();
            List<Prestamos> prestamos = prestamosDAO.listarPrestamosConDetalles();

            request.setAttribute("usuarios", usuarios);
            request.setAttribute("items", items);
            request.setAttribute("prestamos", prestamos);

            request.getRequestDispatcher("/jsp/prestamos.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al cargar los datos: " + e.getMessage());
            request.getRequestDispatcher("/jsp/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");

        try {
            if ("crear".equals(accion)) {
                crearPrestamo(request);
            } else if ("devolver".equals(accion)) {
                devolverPrestamo(request);
            } else if ("buscar".equals(accion)) {
                buscarDatos(request, response);
                return; // Evitar redirección después de búsqueda
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Ocurrió un error: " + e.getMessage());
            request.getRequestDispatcher("/jsp/error.jsp").forward(request, response);
        }

        response.sendRedirect("prestamos");
    }

    private void crearPrestamo(HttpServletRequest request) {
        int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));
        int idItem = Integer.parseInt(request.getParameter("idItem"));
        Date fechaPrestamo = Date.valueOf(request.getParameter("fechaPrestamo"));
        Date fechaDevolucion = Date.valueOf(request.getParameter("fechaDevolucion"));

        Prestamos prestamo = new Prestamos(0, idUsuario, idItem, fechaPrestamo, fechaDevolucion, false, 0.0);
        prestamosDAO.insertarPrestamo(prestamo);
    }

    private void devolverPrestamo(HttpServletRequest request) {
        int idPrestamo = Integer.parseInt(request.getParameter("idPrestamo"));
        prestamosDAO.marcarDevuelto(idPrestamo, 0.0);
    }

    private void buscarDatos(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String criterioUsuario = request.getParameter("criterioUsuario");
        String criterioItem = request.getParameter("criterioItem");
        String criterioPrestamo = request.getParameter("criterioPrestamo");

        // Filtrar Usuarios
        List<Usuarios> usuariosFiltrados = usuariosDAO.listarTodos().stream()
                .filter(usuario -> (criterioUsuario != null && !criterioUsuario.isEmpty())
                && (usuario.getNombreUsuario().toLowerCase().contains(criterioUsuario.toLowerCase())
                || usuario.getEmail().toLowerCase().contains(criterioUsuario.toLowerCase())))
                .toList();
        request.setAttribute("usuarios", usuariosFiltrados);

        // Filtrar Ítems
        List<Item> itemsFiltrados = itemDAO.listarTodos().stream()
                .filter(item -> (criterioItem != null && !criterioItem.isEmpty())
                && (item.getTitulo().toLowerCase().contains(criterioItem.toLowerCase())
                || item.getAutor().toLowerCase().contains(criterioItem.toLowerCase())))
                .toList();
        request.setAttribute("items", itemsFiltrados);

        // Filtrar Préstamos
        List<Prestamos> prestamosFiltrados = prestamosDAO.listarPrestamosConDetalles().stream()
                .filter(prestamo -> (criterioPrestamo != null && !criterioPrestamo.isEmpty())
                && (prestamo.getNombreUsuario().toLowerCase().contains(criterioPrestamo.toLowerCase())
                || prestamo.getTituloEjemplar().toLowerCase().contains(criterioPrestamo.toLowerCase())))
                .toList();
        request.setAttribute("prestamos", prestamosFiltrados);

        // Redirigir a la página de préstamos
        request.getRequestDispatcher("/jsp/prestamos.jsp").forward(request, response);
    }

}
