/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import modelo.Conexion;
import modelo.Item;

/**
 *
 * @author Hector Marquez
 */
public class ItemDAO {
  // Método para insertar un nuevo item
    public boolean insertarItem(Item item) {
        String sql = "INSERT INTO items (tipoItem, titulo, autor, editorial, genero, anioPublicacion, ubicacionFisica) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, item.getTipoItem());
            stmt.setString(2, item.getTitulo());
            stmt.setString(3, item.getAutor());
            stmt.setString(4, item.getEditorial());
            stmt.setString(5, item.getGenero());
            stmt.setInt(6, item.getAnioPublicacion());
            stmt.setString(7, item.getUbicacionFisica());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para buscar items por título o autor
    public List<Item> buscarItems(String criterioBusqueda) {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT * FROM items WHERE titulo LIKE ? OR autor LIKE ?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + criterioBusqueda + "%");
            stmt.setString(2, "%" + criterioBusqueda + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    items.add(mapResultSetToItem(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    // Método para eliminar un item por ID
    public boolean eliminarItem(int idItem) {
        String sql = "DELETE FROM items WHERE idItem = ?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idItem);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para actualizar un item
    public boolean actualizarItem(Item item) {
        String sql = "UPDATE items SET tipoItem = ?, titulo = ?, autor = ?, editorial = ?, genero = ?, anioPublicacion = ?, ubicacionFisica = ? WHERE idItem = ?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, item.getTipoItem());
            stmt.setString(2, item.getTitulo());
            stmt.setString(3, item.getAutor());
            stmt.setString(4, item.getEditorial());
            stmt.setString(5, item.getGenero());
            stmt.setInt(6, item.getAnioPublicacion());
            stmt.setString(7, item.getUbicacionFisica());
            stmt.setInt(8, item.getIdItem());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para listar todos los items
    public List<Item> listarTodos() {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT * FROM items";
        try (Connection conn = Conexion.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                items.add(mapResultSetToItem(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }
    
    public String obtenerTituloItem(int idItem) {
        String tituloItem = "";
        String sql = "SELECT titulo FROM items WHERE idItem = ?";

        try (Connection conn = Conexion.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idItem);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    tituloItem = rs.getString("titulo");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tituloItem;
    }

    // Método auxiliar para mapear un ResultSet a un objeto Item
    private Item mapResultSetToItem(ResultSet rs) throws SQLException {
        return new Item(
                rs.getInt("idItem"),
                rs.getString("tipoItem"),
                rs.getString("titulo"),
                rs.getString("autor"),
                rs.getString("editorial"),
                rs.getString("genero"),
                rs.getInt("anioPublicacion"),
                rs.getString("ubicacionFisica")
        );
    }
}
