package Model;

import java.io.File;
import java.util.LinkedList;

public class UsuarioDAO extends ArchivoDAO<Usuario> {

    private LinkedList<Usuario> usuarios;

    public UsuarioDAO(String codigoRestaurante) {
        super("C:/ronny/PAULA/restaurantes/" + codigoRestaurante + "/usuarios.dat");

        new File("C:/ronny/PAULA/restaurantes/" + codigoRestaurante).mkdirs();

        this.usuarios = cargar();
    }

    public void agregar(Usuario u) {
        usuarios.add(u);
        guardar(usuarios);
        System.out.println("Usuario agregado y guardado");
    }

    public boolean eliminar(String nombreUsuario) {
        boolean eliminado = usuarios.removeIf(u -> u.getUsuario().equalsIgnoreCase(nombreUsuario));
        if (eliminado) {
            guardar(usuarios);
        }
        return eliminado;
    }

    public Usuario buscar(String nombreUsuario) {
        for (Usuario u : usuarios) {
            if (u.getUsuario().equalsIgnoreCase(nombreUsuario)) {
                return u;
            }
        }
        return null;
    }

    public void listar() {
        if (usuarios.isEmpty()) {
            System.out.println("(No hay usuarios)");
        }
        for (Usuario u : usuarios) {
            System.out.println(u);
        }
    }

    public LinkedList<Usuario> getUsuarios() {
        return usuarios;
    }
}
