/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.util.List;
import modelo.Item;
import modelo.dao.ItemDAO;


public class ItemController {
   private final ItemDAO itemDAO;

    // Constructor para inicializar el DAO
    public ItemController() {
        this.itemDAO = new ItemDAO();
    }

    // Método para agregar un nuevo item
    public boolean agregarItem(Item item) {
        return itemDAO.insertarItem(item);
    }

    // Método para buscar items por título o autor
    public List<Item> buscarItems(String criterioBusqueda) {
        return itemDAO.buscarItems(criterioBusqueda);
    }

    // Método para eliminar un item por ID
    public boolean eliminarItem(int idItem) {
        return itemDAO.eliminarItem(idItem);
    }

    // Método para actualizar un item
    public boolean actualizarItem(Item item) {
        return itemDAO.actualizarItem(item);
    }

    // Método para listar todos los items
    public List<Item> listarItems() {
        return itemDAO.listarTodos();
    }
    
}
