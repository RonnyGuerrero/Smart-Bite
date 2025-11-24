/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.Usuario;
import Model.UserNotConfirmedDAO;
import Model.UsuarioDAO;
import View.registrarse;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import java.util.LinkedList;

public class RegisterController {

    private final registrarse vista;
    private final UsuarioDAO userNotConfirmedDAO = new UsuarioDAO("usernotconfirmed");

    public RegisterController(registrarse vista) {
        this.vista = vista;

        initEventos();
    }

    private void initEventos() {

        vista.getBotonRegistrarse().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                registrarUsuario();
            }
        });
    }

    private void registrarUsuario() {

        // --- Obtener datos del formulario ---
        String nombre = vista.getTxtNombre().getText().trim();
        String apellido = vista.getTxtApellido().getText().trim();
        String id = vista.getTxtID().getText().trim();
        String telefono = vista.getTxtTelefono().getText().trim();
        String gmail = vista.getTxtGmail().getText().trim();
        String rol = vista.getComboRol().getSelectedItem().toString();
        String pass = vista.getTxtContraseña().getText().trim();
        String pass2 = vista.getTxtComfirmarContrasena().getText().trim();

        // --- Validaciones básicas ---
        if (nombre.isEmpty() || apellido.isEmpty() || id.isEmpty() || telefono.isEmpty() ||
                gmail.isEmpty() || pass.isEmpty() || pass2.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Completa todos los campos.");
            return;
        }

        if (!pass.equals(pass2)) {
            JOptionPane.showMessageDialog(vista, "Las contraseñas no coinciden.");
            return;
        }

        // --- Validar que el nombre.apellido NO exista ---
        String username = (nombre + "." + apellido).toLowerCase();

        for (Usuario u : userNotConfirmedDAO.getUsuarios()) {
            String full = (u.getNombre() + "." + u.getApellido()).toLowerCase();
            if (full.equals(username)) {
                JOptionPane.showMessageDialog(vista,
                        "Ya existe un usuario registrado con ese nombre y apellido.");
                return;
            }
        }

        // --- Crear el usuario ---
        Usuario nuevo = new Usuario();
        nuevo.setIdUsuario(Integer.parseInt(id));
        nuevo.setNombre(nombre);
        nuevo.setApellido(apellido);
        nuevo.setGmail(gmail);
        nuevo.setTelefono(telefono);
        nuevo.setRol(rol);
        nuevo.setContrasena(pass);
        nuevo.setRestaurante("");   // Aún no confirmado

        // --- Guardar en usernotconfirmed.dat ---
        userNotConfirmedDAO.agregar(nuevo);

        JOptionPane.showMessageDialog(vista,
                "Registro exitoso. Espera confirmación con el código del restaurante.");

        limpiarFormulario();
    }

    private void limpiarFormulario() {
        vista.getTxtNombre().setText("");
        vista.getTxtApellido().setText("");
        vista.getTxtID().setText("");
        vista.getTxtTelefono().setText("");
        vista.getTxtGmail().setText("");
        vista.getTxtContraseña().setText("");
        vista.getTxtComfirmarContrasena().setText("");
        vista.getComboRol().setSelectedIndex(0);
    }
}
