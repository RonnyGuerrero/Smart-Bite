/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;


public class ListaUsuarios {

    private NodoUsuario cabeza;

    private static class NodoUsuario {

        Usuario usuario;
        NodoUsuario siguiente;

        NodoUsuario(Usuario u) {
            this.usuario = u;
        }
    }

    public void agregar(Usuario u) {
        NodoUsuario nuevo = new NodoUsuario(u);
        if (cabeza == null) {
            cabeza = nuevo;
        } else {
            NodoUsuario temp = cabeza;
            while (temp.siguiente != null) {
                temp = temp.siguiente;
            }
            temp.siguiente = nuevo;
        }
    }

    public void mostrar() {
        NodoUsuario temp = cabeza;
        while (temp != null) {
            System.out.println(temp.usuario);
            temp = temp.siguiente;
        }
    }

    public Usuario buscarPorUsuario(String nombreUsuario) {
        NodoUsuario temp = cabeza;
        while (temp != null) {
            if (temp.usuario.getUsuario().equalsIgnoreCase(nombreUsuario)) {
                return temp.usuario;
            }
            temp = temp.siguiente;
        }
        return null;
    }
}
