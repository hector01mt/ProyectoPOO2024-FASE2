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
import jakarta.servlet.http.HttpSession;
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
            HttpSession session = request.getSession();
            Usuarios usuario = (Usuarios) session.getAttribute("usuario");

            if (usuario != null) {
                if ("Admin".equals(usuario.getTipoUsuario())) {
                    // Obtener datos necesarios para la vista de administración
                    List<Prestamos> prestamos = prestamosDAO.listarPrestamosConDetalles();
                    List<Usuarios> usuarios = usuariosDAO.listarTodos();
                    List<Item> items = itemDAO.listarTodos();

                    // Asegurarse de enviar datos al JSP
                    request.setAttribute("prestamos", prestamos);
                    request.setAttribute("usuarios", usuarios);
                    request.setAttribute("items", items);

                    request.getRequestDispatcher("/jsp/prestamos.jsp").forward(request, response);
                } else {
                    // Redirigir al dashboard de usuario si no es admin
                    response.sendRedirect(request.getContextPath() + "/usuariosDashboard");
                }
            } else {
                // Redirigir al login si no hay sesión activa
                response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
            }
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
        Usuarios usuario = (Usuarios) request.getSession().getAttribute("usuario");

        if (usuario == null) {
            response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
            return;
        }

        try {
            if ("crear".equals(accion)) {
                int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));
                int idItem = Integer.parseInt(request.getParameter("idItem"));
                Date fechaPrestamo = Date.valueOf(request.getParameter("fechaPrestamo"));
                Date fechaDevolucion = Date.valueOf(request.getParameter("fechaDevolucion"));

                Prestamos prestamo = new Prestamos(0, idUsuario, idItem, fechaPrestamo, fechaDevolucion, false, 0.0);
                prestamosDAO.insertarPrestamo(prestamo);
            } else if ("devolver".equals(accion)) {
                int idPrestamo = Integer.parseInt(request.getParameter("idPrestamo"));
                prestamosDAO.marcarDevuelto(idPrestamo, 0.0);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al procesar la solicitud: " + e.getMessage());
            request.getRequestDispatcher("/jsp/error.jsp").forward(request, response);
            return;
        }

        response.sendRedirect(request.getContextPath() + "/prestamos");
    }

    private void cargarDatosAdmin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Usuarios> usuarios = usuariosDAO.listarTodos();
        List<modelo.Item> items = itemDAO.listarTodos();
        List<Prestamos> prestamos = prestamosDAO.listarPrestamosConDetalles();

        request.setAttribute("usuarios", usuarios);
        request.setAttribute("items", items);
        request.setAttribute("prestamos", prestamos);
        request.getRequestDispatcher("/jsp/prestamos.jsp").forward(request, response);
    }

    private void cargarDatosUsuario(HttpServletRequest request, HttpServletResponse response, Usuarios usuario)
            throws ServletException, IOException {
        List<Prestamos> prestamosActivos = prestamosDAO.listarPrestamosPorUsuario(usuario.getIdUsuario());
        List<modelo.Item> items = itemDAO.listarTodos();

        request.setAttribute("prestamosActivos", prestamosActivos);
        request.setAttribute("items", items);

        String mensaje = (String) request.getSession().getAttribute("mensaje");
        if (mensaje != null) {
            request.setAttribute("mensaje", mensaje);
            request.getSession().removeAttribute("mensaje");
        }

        request.getRequestDispatcher("/jsp/usuariosDashboard.jsp").forward(request, response);
    }

    private void crearPrestamo(HttpServletRequest request, Usuarios usuario) throws IOException {
        int idUsuario = usuario.getIdUsuario();
        int idItem = Integer.parseInt(request.getParameter("idItem"));
        Date fechaPrestamo = new Date(System.currentTimeMillis());
        Date fechaDevolucion = new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000);

        Prestamos prestamo = new Prestamos(0, idUsuario, idItem, fechaPrestamo, fechaDevolucion, false, 0.0);
        if (!prestamosDAO.insertarPrestamo(prestamo)) {
            throw new IllegalStateException("Error al registrar el préstamo.");
        }

        request.getSession().setAttribute("mensaje", "Préstamo registrado con éxito.");
    }

    private void devolverPrestamo(HttpServletRequest request) throws IOException {
        int idPrestamo = Integer.parseInt(request.getParameter("idPrestamo"));
        if (!prestamosDAO.marcarDevuelto(idPrestamo, 0.0)) {
            throw new IllegalStateException("Error al devolver el préstamo.");
        }
    }

}
