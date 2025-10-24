/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;


import java.sql.*;

public class IngredientesDAO {

    public boolean insertarIngrediente(Ingredientes i) {
        String sql = "INSERT INTO ingredientes (nombre, cantidad, unidad_medida, costo_unidad) VALUES (?, ?, ?, ?)";
        try (Connection con = ConexionSQL.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, i.getNombre());
            ps.setInt(2, i.getCantidad());
            ps.setString(3, i.getUnidadMedida());
            ps.setDouble(4, i.getCostoUnidad());
            ps.executeUpdate();

            System.out.println("Ingrediente agregado correctamente");
            return true;

        } catch (SQLException e) {
            System.out.println("Error al insertar ingrediente: " + e.getMessage());
            return false;
        }
    }

    public ListaIngredientes listarIngredientes() {
        ListaIngredientes lista = new ListaIngredientes();
        String sql = "SELECT * FROM ingredientes";

        try (Connection con = ConexionSQL.conectar();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Ingredientes i = new Ingredientes(
                        rs.getInt("id_ingrediente"),
                        rs.getString("nombre"),
                        rs.getInt("cantidad"),
                        rs.getString("unidad_medida"),
                        rs.getDouble("costo_unidad")
                );
                lista.agregar(i);
            }

        } catch (SQLException e) {
            System.out.println("rror al listar ingredientes: " + e.getMessage());
        }
        return lista;
    }

    public Ingredientes buscarPorId(int id) {
        Ingredientes i = null;
        String sql = "SELECT * FROM ingredientes WHERE id_ingrediente = ?";

        try (Connection con = ConexionSQL.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                i = new Ingredientes(
                        rs.getInt("id_ingrediente"),
                        rs.getString("nombre"),
                        rs.getInt("cantidad"),
                        rs.getString("unidad_medida"),
                        rs.getDouble("costo_unidad")
                );
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar ingrediente: " + e.getMessage());
        }
        return i;
    }

    public boolean actualizarIngrediente(Ingredientes i) {
        String sql = "UPDATE ingredientes SET nombre = ?, cantidad = ?, unidad_medida = ?, costo_unidad = ? WHERE id_ingrediente = ?";
        try (Connection con = ConexionSQL.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, i.getNombre());
            ps.setInt(2, i.getCantidad());
            ps.setString(3, i.getUnidadMedida());
            ps.setDouble(4, i.getCostoUnidad());
            ps.setInt(5, i.getIdIngrediente());
            int filas = ps.executeUpdate();

            if (filas > 0) {
                System.out.println("Ingrediente actualizado correctamente");
                return true;
            } else {
                System.out.println( "No se encontró el ingrediente con ID " + i.getIdIngrediente());
                return false;
            }

        } catch (SQLException e) {
            System.out.println("Error al actualizar ingrediente: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarIngrediente(int idIngrediente) {
        String sql = "DELETE FROM ingredientes WHERE id_ingrediente = ?";
        try (Connection con = ConexionSQL.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idIngrediente);
            int filas = ps.executeUpdate();

            if (filas > 0) {
                System.out.println("Ingrediente eliminado correctamente");
                return true;
            } else {
                System.out.println(" No existe un ingrediente con ese ID");
                return false;
            }

        } catch (SQLException e) {
            System.out.println("Error al eliminar ingrediente: " + e.getMessage());
            return false;
        }
    }
}
