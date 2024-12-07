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
import modelo.Usuarios;

import java.io.IOException;
import java.util.List;
import modelo.Item;
import modelo.Prestamos;
import modelo.dao.ItemDAO;
import modelo.dao.PrestamosDAO;

/**
 *
 * @author Hector Marquez
 */
@WebServlet("/usuariosDashboard")
public class UsuariosDashboardServlet extends HttpServlet  {
    
    private final ItemDAO itemDAO = new ItemDAO();
    private final PrestamosDAO prestamosDAO = new PrestamosDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Usuarios usuario = (Usuarios) session.getAttribute("usuario");

        if (usuario == null) {
            response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
            return;
        }

        // Obtener ítems y préstamos activos para el usuario logueado
        List<Item> items = itemDAO.listarTodos(); // Todos los ítems
        List<Prestamos> prestamosActivos = prestamosDAO.listarPrestamosPorUsuario(usuario.getIdUsuario()); // Préstamos del usuario

        // Pasar las listas y usuario como atributos
        request.setAttribute("items", items);
        request.setAttribute("prestamosActivos", prestamosActivos);
        request.setAttribute("usuario", usuario);

        request.getRequestDispatcher("/jsp/usuariosDashboard.jsp").forward(request, response);
    }

}
