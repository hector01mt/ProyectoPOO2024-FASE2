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
@WebServlet("/items")
public class ItemServlet extends HttpServlet {
     private final ItemDAO itemDAO = new ItemDAO();
     
     @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String criterio = request.getParameter("criterio");
        List<Item> items = (criterio == null || criterio.isEmpty())
                ? itemDAO.listarTodos()
                : itemDAO.listarTodos().stream()
                        .filter(item -> item.getTitulo().toLowerCase().contains(criterio.toLowerCase())
                        || item.getAutor().toLowerCase().contains(criterio.toLowerCase()))
                        .toList();

        request.setAttribute("items", items);
        request.setAttribute("criterio", criterio);

        request.getRequestDispatcher("/jsp/items.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");

        int idItem = 0;
        String idItemParam = request.getParameter("idItem");
        if (idItemParam != null && !idItemParam.isEmpty()) {
            idItem = Integer.parseInt(idItemParam);
        }

        if ("crear".equals(accion)) {
            String titulo = request.getParameter("titulo");
            String tipoItem = request.getParameter("tipoItem");
            String autor = request.getParameter("autor");
            int anioPublicacion = Integer.parseInt(request.getParameter("anioPublicacion"));
            String ubicacionFisica = request.getParameter("ubicacionFisica");

            Item nuevoItem = new Item(0, tipoItem, titulo, autor, null, null, anioPublicacion, ubicacionFisica);
            itemDAO.insertarItem(nuevoItem);

        } else if ("editar".equals(accion)) {
            String titulo = request.getParameter("titulo");
            String tipoItem = request.getParameter("tipoItem");
            String autor = request.getParameter("autor");
            int anioPublicacion = Integer.parseInt(request.getParameter("anioPublicacion"));
            String ubicacionFisica = request.getParameter("ubicacionFisica");

            Item itemEditado = new Item(idItem, tipoItem, titulo, autor, null, null, anioPublicacion, ubicacionFisica);
            itemDAO.actualizarItem(itemEditado);

        } else if ("eliminar".equals(accion)) {
            itemDAO.eliminarItem(idItem);

        } else if ("cargar".equals(accion)) {
            final int idItemFinal = idItem;
            Item itemSeleccionado = itemDAO.listarTodos().stream()
                    .filter(item -> item.getIdItem() == idItemFinal)
                    .findFirst()
                    .orElse(null);
            request.setAttribute("itemSeleccionado", itemSeleccionado);
        }

        response.sendRedirect("items");
    }

}
