/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;


import java.sql.*;

public class PlatillosDAO {

    public boolean insertarPlatillo(Platillos p) {
        String sql = "INSERT INTO platillos (nombre, categoria, precio, descripcion) VALUES (?, ?, ?, ?)";
        try (Connection con = ConexionSQL.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, p.getNombre());
            ps.setString(2, p.getCategoria());
            ps.setDouble(3, p.getPrecio());
            ps.setString(4, p.getDescripcion());
            ps.executeUpdate();

            System.out.println("Platillo agregado correctamente");
            return true;

        } catch (SQLException e) {
            System.out.println("Error al insertar platillo: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizarPlatillo(Platillos p) {
        String sql = "UPDATE platillos SET nombre = ?, categoria = ?, precio = ?, descripcion = ? WHERE id_platillo = ?";
        try (Connection con = ConexionSQL.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, p.getNombre());
            ps.setString(2, p.getCategoria());
            ps.setDouble(3, p.getPrecio());
            ps.setString(4, p.getDescripcion());
            ps.setInt(5, p.getIdPlatillo());

            int filas = ps.executeUpdate();
            if (filas > 0) {
                System.out.println("Platillo actualizado correctamente");
                return true;
            } else {
                System.out.println(" No se encontró el platillo con ID " + p.getIdPlatillo());
                return false;
            }

        } catch (SQLException e) {
            System.out.println("Error al actualizar platillo: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarPlatillo(int idPlatillo) {
        String sql = "DELETE FROM platillos WHERE id_platillo = ?";
        try (Connection con = ConexionSQL.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idPlatillo);
            int filas = ps.executeUpdate();

            if (filas > 0) {
                System.out.println(" Platillo eliminado correctamente");
                return true;
            } else {
                System.out.println("️ No existe un platillo con ese ID");
                return false;
            }

        } catch (SQLException e) {
            System.out.println(" Error al eliminar platillo: " + e.getMessage());
            return false;
        }
    }

    public Platillos obtenerPlatilloPorId(int idPlatillo) {
        String sql = "SELECT * FROM platillos WHERE id_platillo = ?";
        Platillos p = null;

        try (Connection con = ConexionSQL.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idPlatillo);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                p = new Platillos(
                        rs.getInt("id_platillo"),
                        rs.getString("nombre"),
                        rs.getString("categoria"),
                        rs.getDouble("precio"),
                        rs.getString("descripcion")
                );
            }

        } catch (SQLException e) {
            System.out.println(" Error al obtener platillo: " + e.getMessage());
        }

        return p;
    }

    public ListaPlatillos listarPlatillos() {
        ListaPlatillos lista = new ListaPlatillos();
        String sql = "SELECT * FROM platillos";

        try (Connection con = ConexionSQL.conectar();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Platillos p = new Platillos(
                        rs.getInt("id_platillo"),
                        rs.getString("nombre"),
                        rs.getString("categoria"),
                        rs.getDouble("precio"),
                        rs.getString("descripcion")
                );
                lista.agregar(p);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar platillos: " + e.getMessage());
        }

        return lista;
    }
}
