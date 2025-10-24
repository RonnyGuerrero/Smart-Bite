/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

public class ListaIngredientes {

    private NodoIngrediente cabeza;

    private static class NodoIngrediente {

        Ingredientes ingrediente;
        NodoIngrediente siguiente;

        NodoIngrediente(Ingredientes i) {
            this.ingrediente = i;
        }
    }

    public void agregar(Ingredientes i) {
        NodoIngrediente nuevo = new NodoIngrediente(i);
        if (cabeza == null) {
            cabeza = nuevo;
        } else {
            NodoIngrediente temp = cabeza;
            while (temp.siguiente != null) {
                temp = temp.siguiente;
            }
            temp.siguiente = nuevo;
        }
    }

    public void mostrar() {
        if (cabeza == null) {
            System.out.println("(No hay ingredientes)");
            return;
        }
        NodoIngrediente temp = cabeza;
        while (temp != null) {
            System.out.println(temp.ingrediente);
            temp = temp.siguiente;
        }
    }

    public Ingredientes buscarPorNombre(String nombre) {
        NodoIngrediente temp = cabeza;
        while (temp != null) {
            if (temp.ingrediente.getNombre().equalsIgnoreCase(nombre)) {
                return temp.ingrediente;
            }
            temp = temp.siguiente;
        }
        return null;
    }

    public boolean eliminarPorNombre(String nombre) {
        if (cabeza == null) {
            return false;
        }

        if (cabeza.ingrediente.getNombre().equalsIgnoreCase(nombre)) {
            cabeza = cabeza.siguiente;
            return true;
        }

        NodoIngrediente anterior = cabeza;
        NodoIngrediente actual = cabeza.siguiente;

        while (actual != null) {
            if (actual.ingrediente.getNombre().equalsIgnoreCase(nombre)) {
                anterior.siguiente = actual.siguiente;
                return true;
            }
            anterior = actual;
            actual = actual.siguiente;
        }

        return false;
    }

    public int contar() {
        int contador = 0;
        NodoIngrediente temp = cabeza;
        while (temp != null) {
            contador++;
            temp = temp.siguiente;
        }
        return contador;
    }
}
