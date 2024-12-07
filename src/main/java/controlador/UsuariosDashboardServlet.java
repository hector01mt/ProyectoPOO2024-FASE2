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

/**
 *
 * @author Hector Marquez
 */
@WebServlet("/usuariosDashboard")
public class UsuariosDashboardServlet extends HttpServlet  {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session == null) {
            response.sendRedirect("login");
            return;
        }

        Usuarios usuario = (Usuarios) session.getAttribute("usuario");

        if (usuario == null || "Admin".equals(usuario.getTipoUsuario())) {
            response.sendRedirect("login");
        } else {
            request.setAttribute("usuario", usuario);
            request.getRequestDispatcher("/jsp/usuariosDashboard.jsp").forward(request, response);
        }
    }
    
}
