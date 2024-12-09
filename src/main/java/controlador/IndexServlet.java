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
import modelo.dao.ItemDAO;
import modelo.Item;

import java.io.IOException;
import java.util.List;

/**
 *
 * @author Hector Marquez
 */
@WebServlet("/index")
public class IndexServlet extends HttpServlet {
    private final ItemDAO itemDAO = new ItemDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
           
            List<Item> items = itemDAO.listarTodos();

            
            request.setAttribute("items", items);

           
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            
            request.setAttribute("error", "Error al cargar los datos.");
            request.getRequestDispatcher("/jsp/error.jsp").forward(request, response);
        }
    }
    
}
