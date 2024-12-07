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
import modelo.dao.UsuariosDAO;
import modelo.Usuarios;

import java.io.IOException;


/**
 *
 * @author Hector Marquez
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private final UsuariosDAO usuariosDAO = new UsuariosDAO();
    
     @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Redirigir al formulario de login
        request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String contrasena = request.getParameter("contrasena");

        try {
            Usuarios usuario = usuariosDAO.autenticarUsuario(email, contrasena);

            if (usuario != null) {
                // Guardar usuario en la sesión
                HttpSession session = request.getSession();
                session.setAttribute("usuario", usuario);

                // Redirigir según el tipo de usuario
                if ("Admin".equals(usuario.getTipoUsuario())) {
                    response.sendRedirect("adminDashboard"); // Admin Dashboard
                } else {
                    response.sendRedirect("usuariosDashboard"); // Usuarios Dashboard
                }
            } else {
                // Credenciales incorrectas
                request.setAttribute("error", "Credenciales incorrectas");
                request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al procesar el login");
        }
    }
    
}
