/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.LinkedList;


public class ListaUsuarios {
    private NodoUsuario cabeza;

    private static class NodoUsuario {
        Usuario usuario;
        NodoUsuario siguiente;

        NodoUsuario(Usuario u) {
            this.usuario = u;
        }
    }

    // Agregar usuario al final
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

    // Mostrar todos los usuarios
    public void mostrar() {
        if (cabeza == null) {
            System.out.println("(No hay usuarios en la lista)");
            return;
        }
        NodoUsuario temp = cabeza;
        while (temp != null) {
            System.out.println(temp.usuario);
            temp = temp.siguiente;
        }
    }

    // Buscar usuario por nombre
    public Usuario buscarPorNombre(String nombre) {
        NodoUsuario temp = cabeza;
        while (temp != null) {
            if (temp.usuario.getNombre().equalsIgnoreCase(nombre)) {
                return temp.usuario;
            }
            temp = temp.siguiente;
        }
        return null;
    }

    // Eliminar usuario por nombre
    public boolean eliminarPorNombre(String nombre) {
        if (cabeza == null) return false;

        if (cabeza.usuario.getNombre().equalsIgnoreCase(nombre)) {
            cabeza = cabeza.siguiente;
            return true;
        }

        NodoUsuario anterior = cabeza;
        NodoUsuario actual = cabeza.siguiente;

        while (actual != null) {
            if (actual.usuario.getNombre().equalsIgnoreCase(nombre)) {
                anterior.siguiente = actual.siguiente;
                return true;
            }
            anterior = actual;
            actual = actual.siguiente;
        }

        return false;
    }

    // 🔹 Convertir a LinkedList (para guardar en archivo)
    public LinkedList<Usuario> aLinkedList() {
        LinkedList<Usuario> lista = new LinkedList<>();
        NodoUsuario temp = cabeza;
        while (temp != null) {
            lista.add(temp.usuario);
            temp = temp.siguiente;
        }
        return lista;
    }
}
