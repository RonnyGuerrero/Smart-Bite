/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.io.File;
import java.util.LinkedList;

public class UserNotConfirmedDAO extends ArchivoDAO<Usuario> {

    private LinkedList<Usuario> usuariosPendientes;

    public UserNotConfirmedDAO() {
        super("C:/ronny/PAULA/restaurantes/usernotconfirmed.dat");
        new File("C:/ronny/PAULA/restaurantes").mkdirs();
        this.usuariosPendientes = cargar();
    }

    public LinkedList<Usuario> getUsuarios() {
        return usuariosPendientes;
    }

    public void setUsuarios(LinkedList<Usuario> lista) {
        this.usuariosPendientes = lista;
        guardar(this.usuariosPendientes);
    }

    public void agregar(Usuario u) {
        usuariosPendientes.add(u);
        guardar(usuariosPendientes);
    }

    public boolean eliminarPorGmail(String gmail) {
        boolean eliminado = usuariosPendientes.removeIf(u -> u.getGmail() != null && u.getGmail().equalsIgnoreCase(gmail));
        if (eliminado) guardar(usuariosPendientes);
        return eliminado;
    }

        public Usuario buscarPorGmail(String gmail) {
        if (usuariosPendientes == null) return null;
        for (Usuario u : usuariosPendientes) {
            if (u.getGmail() != null && u.getGmail().equalsIgnoreCase(gmail)) return u;
        }
        return null;
    }
}

