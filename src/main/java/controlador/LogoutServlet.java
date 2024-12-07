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
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    
  @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        cerrarSesion(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        cerrarSesion(request, response);
    }

    private void cerrarSesion(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().invalidate(); // Invalida la sesión
        response.sendRedirect(request.getContextPath() + "/jsp/login.jsp"); // Redirige al login
    }
    
}
