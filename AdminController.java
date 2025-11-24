/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.*;
import View.Admin; // ajusta si tu clase de vista se llama distinto

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;

public class AdminController {

    private Admin vista;
    private ListaUsuarios listaUsuarios;
    private UsuarioDAO usuarioDAO;
    private IngredientesDAO ingredientesDAO;

    public AdminController(Admin vista,
            ListaUsuarios listaUsuarios,
            UsuarioDAO usuarioDAO,
            IngredientesDAO ingredientesDAO) {
        this.vista = vista;
        this.listaUsuarios = listaUsuarios;
        this.usuarioDAO = usuarioDAO;
        this.ingredientesDAO = ingredientesDAO;

        cargarUsuariosDesdeDAO();
        cargarTablaUsuarios();
        cargarTablaIngredientesAdmin();

        this.vista.getBotonAgregar().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                abrirDialogoAgregarUsuario();
            }
        });

        this.vista.getBotonRegistrarse().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                confirmarAgregarUsuario();
            }
        });

        this.vista.getBotonBuscarUsuario().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                buscarUsuario();
            }
        });

        this.vista.getBotonEditar().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!vista.getDialogoEditarUsu().isVisible()) {
                    abrirDialogoEditarUsuario();
                } else {
                    confirmarEditarUsuario();
                }
            }
        });

        this.vista.getBotonElminar().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                eliminarUsuario();
            }
        });

        this.vista.getBotonEditarIngrediente().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                abrirDialogoEditarIngrediente();
            }
        });

        this.vista.getBotonEditaringrediente().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                confirmarEditarIngrediente();
            }
        });

        this.vista.getBotonCancelaringrediente().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                vista.getDialogoEditarIng().dispose();
            }
        });

        this.vista.getBotonElminarIngrediente().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                eliminarIngredienteAdmin();
            }
        });

        this.vista.getBotonBuscarIngrediente().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                buscarIngredienteAdmin();
            }
        });
    }

    private void cargarUsuariosDesdeDAO() {
        listaUsuarios = new ListaUsuarios();
        LinkedList<Usuario> users = usuarioDAO.getUsuarios();
        if (users != null) {
            for (Usuario u : users) {
                listaUsuarios.agregar(u);
            }
        }
    }

    private void guardarListaEnDAO() {
        LinkedList<Usuario> ll = listaUsuarios.aLinkedList();
        usuarioDAO.setUsuarios(ll);
    }

    private int siguienteIdUsuario() {
        int max = 0;
        LinkedList<Usuario> users = usuarioDAO.getUsuarios();
        if (users != null) {
            for (Usuario u : users) {
                if (u.getIdUsuario() > max) {
                    max = u.getIdUsuario();
                }
            }
        }
        return max + 1;
    }

    private void cargarTablaUsuarios() {
        DefaultTableModel model = (DefaultTableModel) vista.getTablaUsuarios().getModel();
        model.setRowCount(0);
        LinkedList<Usuario> users = usuarioDAO.getUsuarios();
        if (users == null) {
            return;
        }

        for (Usuario u : users) {
            model.addRow(new Object[]{
                u.getIdUsuario(),
                u.getNombre(),
                u.getApellido(),
                u.getRol(),
                u.getContrasena(),
                u.getGmail(),
                u.getTelefono(),
                u.getRestaurante(),
                u.getCodigoAcceso()
            });
        }
    }

    private void cargarTablaIngredientesAdmin() {
        DefaultTableModel model = (DefaultTableModel) vista.getTablaIngredientes().getModel();
        model.setRowCount(0);
        LinkedList<Ingredientes> ings = ingredientesDAO.getIngredientes();
        if (ings == null) {
            return;
        }

        for (Ingredientes ing : ings) {
            model.addRow(new Object[]{
                ing.getIdIngrediente(),
                ing.getNombre(),
                ing.getCantidad(),
                ing.getUnidadMedida(),
                ing.getCostoUnidad(),
                ing.getFechaVencimiento()
            });
        }
    }

    private void abrirDialogoAgregarUsuario() {
        vista.getTxtNombre().setText("");
        vista.getTxtApellido().setText("");
        vista.getTxtGmail().setText("");
        vista.getTxtTelefono().setText("");
        vista.getTxtContraseña().setText("");
        vista.getTxtComfirmarContrasena().setText("");
        vista.getTxtID().setText(String.valueOf(siguienteIdUsuario()));
        vista.getComboRol().setSelectedIndex(0);
        vista.getDialogoAgregarUsu().setSize(500, 380);
        vista.getDialogoAgregarUsu().setLocationRelativeTo(null);
        vista.getDialogoAgregarUsu().setVisible(true);
    }

    private void confirmarAgregarUsuario() {
        try {
            String nombre = vista.getTxtNombre().getText().trim();
            String apellido = vista.getTxtApellido().getText().trim();
            String gmail = vista.getTxtGmail().getText().trim();
            String telefono = vista.getTxtTelefono().getText().trim();
            String contrasena = vista.getTxtContraseña().getText();
            String confirmar = vista.getTxtComfirmarContrasena().getText();
            String rol = (String) vista.getComboRol().getSelectedItem();
            String restaurante = ""; 
            String codigoAcceso = ""; 

            if (nombre.isEmpty() || apellido.isEmpty() || gmail.isEmpty() || contrasena.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Complete los campos obligatorios (nombre, apellido, gmail, contraseña).");
                return;
            }

            if (!contrasena.equals(confirmar)) {
                JOptionPane.showMessageDialog(null, "La contraseña y la confirmación no coinciden.");
                return;
            }

            // Verificar duplicado por gmail
            if (existeUsuarioPorGmail(gmail)) {
                JOptionPane.showMessageDialog(null, "Ya existe un usuario con ese Gmail.");
                return;
            }

            Usuario u = new Usuario();
            u.setIdUsuario(siguienteIdUsuario());
            u.setNombre(nombre);
            u.setApellido(apellido);
            u.setGmail(gmail);
            u.setTelefono(telefono);
            u.setContrasena(contrasena);
            u.setRol(rol != null ? rol : "");
            u.setRestaurante(restaurante);
            u.setCodigoAcceso(codigoAcceso);

            listaUsuarios.agregar(u);
            guardarListaEnDAO();

            cargarTablaUsuarios();
            vista.getDialogoAgregarUsu().dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al agregar usuario: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private boolean existeUsuarioPorGmail(String gmail) {
        LinkedList<Usuario> users = usuarioDAO.getUsuarios();
        if (users == null) {
            return false;
        }
        for (Usuario u : users) {
            if (u.getGmail() != null && u.getGmail().equalsIgnoreCase(gmail)) {
                return true;
            }
        }
        return false;
    }

    private void abrirDialogoEditarUsuario() {
        int fila = vista.getTablaUsuarios().getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Seleccione un usuario para editar.");
            return;
        }

        DefaultTableModel model = (DefaultTableModel) vista.getTablaUsuarios().getModel();
        int id = Integer.parseInt(model.getValueAt(fila, 0).toString());

        // buscar usuario en DAO por id
        Usuario encontrado = buscarUsuarioPorId(id);
        if (encontrado == null) {
            JOptionPane.showMessageDialog(null, "Usuario no encontrado.");
            return;
        }

        vista.getTxtIDeditar().setText(String.valueOf(encontrado.getIdUsuario()));
        vista.getTxtNombreEditar().setText(encontrado.getNombre());
        vista.getTxtApellidoEditar().setText(encontrado.getApellido());
        vista.getTxtGmailEditar().setText(encontrado.getGmail());
        vista.getTxtTelefonoEditar().setText(encontrado.getTelefono());
        vista.getTxtContraseñaEditar().setText(encontrado.getContrasena());
        vista.getTxtComfirmarContrasenaEditar().setText(encontrado.getContrasena());
        // set rol
        String rol = encontrado.getRol();
        if (rol != null) {
            vista.getComboRolEditar().setSelectedItem(rol);
        }

        vista.getDialogoEditarUsu().setSize(500, 380);
        vista.getDialogoEditarUsu().setLocationRelativeTo(null);
        vista.getDialogoEditarUsu().setVisible(true);
    }

    private void confirmarEditarUsuario() {
        try {
            int id = Integer.parseInt(vista.getTxtIDeditar().getText());
            String nombre = vista.getTxtNombreEditar().getText().trim();
            String apellido = vista.getTxtApellidoEditar().getText().trim();
            String gmail = vista.getTxtGmailEditar().getText().trim();
            String telefono = vista.getTxtTelefonoEditar().getText().trim();
            String contrasena = vista.getTxtContraseñaEditar().getText();
            String confirmar = vista.getTxtComfirmarContrasenaEditar().getText();
            String rol = (String) vista.getComboRolEditar().getSelectedItem();

            if (nombre.isEmpty() || apellido.isEmpty() || gmail.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Complete los campos obligatorios.");
                return;
            }
            if (!contrasena.equals(confirmar)) {
                JOptionPane.showMessageDialog(null, "La contraseña y la confirmación no coinciden.");
                return;
            }

            Usuario conflict = buscarUsuarioPorGmail(gmail);
            if (conflict != null && conflict.getIdUsuario() != id) {
                JOptionPane.showMessageDialog(null, "El Gmail ya pertenece a otro usuario.");
                return;
            }
            Usuario u = new Usuario();
            u.setIdUsuario(id);
            u.setNombre(nombre);
            u.setApellido(apellido);
            u.setGmail(gmail);
            u.setTelefono(telefono);
            u.setContrasena(contrasena);
            u.setRol(rol != null ? rol : "");
            // restaurante y codigoAcceso se mantienen como estuvieron (si los manejas añade getters y campos)
            // buscar en la lista actual para mantener restaurante/codigoAcceso si existen
            Usuario prev = buscarUsuarioPorId(id);
            if (prev != null) {
                u.setRestaurante(prev.getRestaurante());
                u.setCodigoAcceso(prev.getCodigoAcceso());
            } else {
                u.setRestaurante("");
                u.setCodigoAcceso("");
            }

            // Reemplazar en LinkedList del DAO
            LinkedList<Usuario> users = usuarioDAO.getUsuarios();
            boolean encontrado = false;
            for (int i = 0; users != null && i < users.size(); i++) {
                if (users.get(i).getIdUsuario() == id) {
                    users.set(i, u);
                    encontrado = true;
                    break;
                }
            }
            if (encontrado) {
                usuarioDAO.setUsuarios(users);
                cargarUsuariosDesdeDAO();
                cargarTablaUsuarios();
                vista.getDialogoEditarUsu().dispose();
                JOptionPane.showMessageDialog(null, "Usuario editado correctamente.");
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró el usuario para editar.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al editar usuario: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private Usuario buscarUsuarioPorId(int id) {
        LinkedList<Usuario> users = usuarioDAO.getUsuarios();
        if (users == null) {
            return null;
        }
        for (Usuario u : users) {
            if (u.getIdUsuario() == id) {
                return u;
            }
        }
        return null;
    }

    private Usuario buscarUsuarioPorGmail(String gmail) {
        LinkedList<Usuario> users = usuarioDAO.getUsuarios();
        if (users == null) {
            return null;
        }
        for (Usuario u : users) {
            if (u.getGmail() != null && u.getGmail().equalsIgnoreCase(gmail)) {
                return u;
            }
        }
        return null;
    }

    private void eliminarUsuario() {
        int fila = vista.getTablaUsuarios().getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Seleccione un usuario para eliminar.");
            return;
        }

        DefaultTableModel model = (DefaultTableModel) vista.getTablaUsuarios().getModel();
        int id = Integer.parseInt(model.getValueAt(fila, 0).toString());
        String nombre = model.getValueAt(fila, 1).toString();

        int confirm = JOptionPane.showConfirmDialog(null,
                "¿Eliminar usuario '" + nombre + "'?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        LinkedList<Usuario> users = usuarioDAO.getUsuarios();
        boolean eliminado = false;
        if (users != null) {
            for (int i = 0; i < users.size(); i++) {
                if (users.get(i).getIdUsuario() == id) {
                    users.remove(i);
                    eliminado = true;
                    break;
                }
            }
        }
        if (eliminado) {
            usuarioDAO.setUsuarios(users);
            cargarUsuariosDesdeDAO();
            cargarTablaUsuarios();
            JOptionPane.showMessageDialog(null, "Usuario eliminado.");
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo eliminar el usuario (no encontrado).");
        }
    }

    private void buscarUsuario() {
        String q = vista.getTXTbuscar().getText();
        DefaultTableModel model = (DefaultTableModel) vista.getTablaUsuarios().getModel();
        model.setRowCount(0);

        if (q == null || q.trim().isEmpty()) {
            cargarTablaUsuarios();
            return;
        }

        LinkedList<Usuario> users = usuarioDAO.getUsuarios();
        if (users == null) {
            return;
        }
        for (Usuario u : users) {
            if ((u.getNombre() != null && u.getNombre().toLowerCase().contains(q.toLowerCase()))
                    || (u.getGmail() != null && u.getGmail().toLowerCase().contains(q.toLowerCase()))) {
                model.addRow(new Object[]{
                    u.getIdUsuario(),
                    u.getNombre(),
                    u.getApellido(),
                    u.getRol(),
                    u.getGmail(),
                    u.getTelefono(),
                    u.getRestaurante(),
                    u.getCodigoAcceso()
                });
            }
        }
    }

    private void abrirDialogoEditarIngrediente() {
        int fila = vista.getTablaIngredientes().getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Seleccione un ingrediente para editar.");
            return;
        }

        DefaultTableModel model = (DefaultTableModel) vista.getTablaIngredientes().getModel();
        String nombre = model.getValueAt(fila, 1).toString();

        Ingredientes ing = ingredientesDAO.buscar(nombre);
        if (ing == null) {
            JOptionPane.showMessageDialog(null, "Ingrediente no encontrado.");
            return;
        }

        vista.getTxtIDingrediente().setText(String.valueOf(ing.getIdIngrediente()));
        vista.getTxtNombreingrediente().setText(ing.getNombre());
        vista.getTxtUnidadMedidaingrediente().setText(ing.getUnidadMedida());
        vista.getTxtCantidadingrediente().setText(String.valueOf(ing.getCantidad()));
        vista.getTxtCostoingrediente().setText(String.valueOf(ing.getCostoUnidad()));
        vista.getTxtVencimientoingrediente().setText(ing.getFechaVencimiento());

        vista.getDialogoEditarIng().setSize(420, 380);
        vista.getDialogoEditarIng().setLocationRelativeTo(null);
        vista.getDialogoEditarIng().setVisible(true);
    }

    private void confirmarEditarIngrediente() {
        try {
            String idTxt = vista.getTxtIDingrediente().getText();
            String nombreNuevo = vista.getTxtNombreingrediente().getText().trim();
            String unidad = vista.getTxtUnidadMedidaingrediente().getText().trim();
            String cantidadTxt = vista.getTxtCantidadingrediente().getText().trim();
            String costoTxt = vista.getTxtCostoingrediente().getText().trim();
            String fecha = vista.getTxtVencimientoingrediente().getText().trim();

            if (nombreNuevo.isEmpty()) {
                JOptionPane.showMessageDialog(null, "El nombre no puede estar vacío.");
                return;
            }

            int cantidad = Integer.parseInt(cantidadTxt);
            double costo = Double.parseDouble(costoTxt);

            Ingredientes nuevos = new Ingredientes();
            nuevos.setNombre(nombreNuevo);
            nuevos.setUnidadMedida(unidad);
            nuevos.setCantidad(cantidad);
            nuevos.setCostoUnidad(costo);
            nuevos.setFechaVencimiento(fecha);

            // Usamos el nombre original para buscar y editar (ingredientesDAO.editarIngrediente usa nombre)
            // Sin embargo, si se modifica el nombre, la DAO lo actualizará internamente.
            String nombreOriginal = null;
            int fila = vista.getTablaIngredientes().getSelectedRow();
            if (fila != -1) {
                nombreOriginal = vista.getTablaIngredientes().getValueAt(fila, 1).toString();
            }

            if (nombreOriginal == null) {
                JOptionPane.showMessageDialog(null, "No se pudo determinar el ingrediente original.");
                return;
            }

            boolean ok = ingredientesDAO.editarIngrediente(nombreOriginal, nuevos);
            if (ok) {
                cargarTablaIngredientesAdmin();
                vista.getDialogoEditarIng().dispose();
                JOptionPane.showMessageDialog(null, "Ingrediente editado correctamente.");
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo editar el ingrediente.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Cantidad o costo en formato inválido.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al editar ingrediente: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void eliminarIngredienteAdmin() {
        int fila = vista.getTablaIngredientes().getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Seleccione un ingrediente para eliminar.");
            return;
        }

        DefaultTableModel model = (DefaultTableModel) vista.getTablaIngredientes().getModel();
        String nombre = model.getValueAt(fila, 1).toString();

        int confirm = JOptionPane.showConfirmDialog(null,
                "¿Eliminar ingrediente '" + nombre + "'?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        boolean eliminado = ingredientesDAO.eliminar(nombre);
        if (eliminado) {
            cargarTablaIngredientesAdmin();
            JOptionPane.showMessageDialog(null, "Ingrediente eliminado.");
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo eliminar el ingrediente (no encontrado).");
        }
    }

    private void buscarIngredienteAdmin() {
        String q = vista.getTXTbuscar1().getText();
        DefaultTableModel model = (DefaultTableModel) vista.getTablaIngredientes().getModel();
        model.setRowCount(0);

        if (q == null || q.trim().isEmpty()) {
            cargarTablaIngredientesAdmin();
            return;
        }

        Ingredientes ing = ingredientesDAO.buscar(q);
        if (ing != null) {
            model.addRow(new Object[]{
                ing.getIdIngrediente(),
                ing.getNombre(),
                ing.getUnidadMedida(),
                ing.getCantidad(),
                ing.getCostoUnidad(),
                ing.getFechaVencimiento()
            });
        } else {
            JOptionPane.showMessageDialog(null, "Ingrediente no encontrado.");
        }
    }
}
