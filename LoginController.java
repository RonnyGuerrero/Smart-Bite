package Controller;

import Model.*;
import View.*;
import java.awt.Window;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import java.io.File;
import java.util.LinkedList;
import javax.swing.SwingUtilities;

public class LoginController {

    private final iniciarSesion vista;
    private final GlobalController global;
    private final JDesktopPane desktop;

    // Archivo donde se guardan los usuarios pendientes de asignar restaurante
    private final UsuarioDAO userNotConfirmedDAO = new UsuarioDAO("usernotconfirmed");

    public LoginController(iniciarSesion vista, GlobalController global, JDesktopPane desktop) {
        this.vista = vista;
        this.global = global;
        this.desktop = desktop;

        initEventos();
    }

    private void initEventos() {

        vista.getBotonIniciar().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                intentarLogin();
            }
        });
    }

    private void intentarLogin() {

        String usuarioIngresado = vista.getTxtusuario().getText().trim();
        String contrasenaIngresada = String.valueOf(vista.getTxtContraseña().getPassword()).trim();
        String codigoRestauranteIngresado = vista.getTxtCodigo().getText().trim();

        if (usuarioIngresado.isEmpty() || contrasenaIngresada.isEmpty() || codigoRestauranteIngresado.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Completa todos los campos.");
            return;
        }

        // --- Buscar en USERNOTCONFIRMED ---
        Usuario usuarioPendiente = buscarEnUserNotConfirmed(usuarioIngresado, contrasenaIngresada);

        if (usuarioPendiente != null) {
            procesarUsuarioPendiente(usuarioPendiente, codigoRestauranteIngresado);
            return;
        }

        // --- Buscar en la carpeta del restaurante ---
        UsuarioDAO daoRest = new UsuarioDAO(codigoRestauranteIngresado);
        Usuario usuarioRest = buscarEnRestaurante(daoRest, usuarioIngresado, contrasenaIngresada);

        if (usuarioRest != null) {
            abrirVistaSegunRol(usuarioRest);
            cerrarVentanaPrincipal();
            return;
        }

        JOptionPane.showMessageDialog(vista, "Usuario o contraseña incorrectos.");
    }

    // -------------------------------------------
    // BUSCAR USUARIO EN usernotconfirmed.dat
    // -------------------------------------------

    private Usuario buscarEnUserNotConfirmed(String nombre, String pass) {

        for (Usuario u : userNotConfirmedDAO.getUsuarios()) {
            String full = (u.getNombre() + "." + u.getApellido()).toLowerCase();
            if (full.equals(nombre.toLowerCase()) && u.getContrasena().equals(pass)) {
                return u;
            }
        }
        return null;
    }

    // -------------------------------------------
    // PROCESAR USUARIO QUE AÚN NO TIENE RESTAURANTE
    // -------------------------------------------

    private void procesarUsuarioPendiente(Usuario u, String codigoRestaurante) {

        // Si es ADMIN → crear carpeta del restaurante automáticamente
        if (u.getRol().equalsIgnoreCase("ADMINISTRADOR")) {
            new File("C:/ronny/PAULA/restaurantes/" + codigoRestaurante).mkdirs();
        }

        // Asignar restaurante
        u.setRestaurante(codigoRestaurante);

        // Remover del archivo usernotconfirmed.dat
        LinkedList<Usuario> lista = userNotConfirmedDAO.getUsuarios();
        lista.removeIf(x -> x.getIdUsuario() == u.getIdUsuario());
        userNotConfirmedDAO.setUsuarios(lista);

        // Guardar en el archivo del restaurante
        UsuarioDAO daoRest = new UsuarioDAO(codigoRestaurante);
        daoRest.agregar(u);  // <--- YA ESTÁ CONFIRMADO 🔥

        // Abrir vista correspondiente
        abrirVistaSegunRol(u);

        // Cerrar ventana principal
        cerrarVentanaPrincipal();
    }

    // -------------------------------------------
    // BUSCAR USUARIO EN RESTAURANTE
    // -------------------------------------------

    private Usuario buscarEnRestaurante(UsuarioDAO dao, String nombre, String pass) {

        for (Usuario u : dao.getUsuarios()) {
            String full = (u.getNombre() + "." + u.getApellido()).toLowerCase();
            if (full.equals(nombre.toLowerCase()) && u.getContrasena().equals(pass)) {
                return u;
            }
        }
        return null;
    }

    // -------------------------------------------
    // ABRIR VISTA SEGÚN ROL
    // -------------------------------------------

    private void abrirVistaSegunRol(Usuario u) {

        String rest = u.getRestaurante();

        switch (u.getRol().toUpperCase()) {

            case "ADMINISTRADOR":
                global.abrirAdmin(rest, u);
                break;

            case "CHEF":
                global.abrirChef(rest, u);
                break;

            case "BODEGA":
                global.abrirBodega(rest, u);
                break;

            default:
                JOptionPane.showMessageDialog(vista, "Rol desconocido: " + u.getRol());
        }
    }

    // -------------------------------------------
    // CERRAR TODA LA VENTANA PRINCIPAL
    // -------------------------------------------

    private void cerrarVentanaPrincipal() {

        Window ventana = SwingUtilities.getWindowAncestor(desktop);

        if (ventana != null) {
            ventana.dispose();   // Cierra TODO
        }
    }
}
