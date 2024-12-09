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
import modelo.dao.PrestamosDAO;
import modelo.Prestamos;
import modelo.Usuarios;

import java.io.IOException;
import java.sql.Date;

/**
 *
 * @author Hector Marquez
 */
@WebServlet("/usuarioPrestamo")
public class UsuarioPrestamoServlet extends HttpServlet  {
    private final PrestamosDAO prestamosDAO = new PrestamosDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Usuarios usuario = (Usuarios) session.getAttribute("usuario");

        if (usuario == null) {
            response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
            return;
        }

        try {
            int idUsuario = usuario.getIdUsuario();
            int idItem = Integer.parseInt(request.getParameter("idItem"));
            Date fechaPrestamo = new Date(System.currentTimeMillis());
            Date fechaDevolucion = new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000);

            Prestamos prestamo = new Prestamos(0, idUsuario, idItem, fechaPrestamo, fechaDevolucion, false, 0.0);
            if (prestamosDAO.insertarPrestamo(prestamo)) {
                session.setAttribute("mensaje", "Préstamo registrado exitosamente.");
            } else {
                session.setAttribute("error", "Error al registrar el préstamo.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("error", "Ocurrió un error al procesar la solicitud.");
        }

        response.sendRedirect(request.getContextPath() + "/usuariosDashboard");
    }
    
}
