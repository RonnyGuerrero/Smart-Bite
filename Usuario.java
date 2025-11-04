package Model;

import java.io.Serializable;

public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    private int idUsuario;
    private String nombre;
    private String rol;
    private String usuario;
    private String contrasena;
    private String restaurante;  
    private String codigoAcceso;   

    public Usuario() {
    }

    public Usuario(int idUsuario, String nombre, String rol, String usuario, String contrasena,
            String restaurante, String codigoAcceso) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.rol = rol;
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.restaurante = restaurante;
        this.codigoAcceso = codigoAcceso;
    }

    // Getters y setters
    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getRestaurante() {
        return restaurante;
    }

    public void setRestaurante(String restaurante) {
        this.restaurante = restaurante;
    }

    public String getCodigoAcceso() {
        return codigoAcceso;
    }

    public void setCodigoAcceso(String codigoAcceso) {
        this.codigoAcceso = codigoAcceso;
    }

    @Override
    public String toString() {
        return nombre + " (" + rol + ") - " + restaurante;
    }
}
