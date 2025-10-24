/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

public class ListaPlatillos {

    private NodoPlatillo cabeza;

    private static class NodoPlatillo {

        Platillos platillo;
        NodoPlatillo siguiente;

        NodoPlatillo(Platillos p) {
            this.platillo = p;
        }
    }

    public void agregar(Platillos p) {
        NodoPlatillo nuevo = new NodoPlatillo(p);
        if (cabeza == null) {
            cabeza = nuevo;
        } else {
            NodoPlatillo temp = cabeza;
            while (temp.siguiente != null) {
                temp = temp.siguiente;
            }
            temp.siguiente = nuevo;
        }
    }

    public void mostrar() {
        if (cabeza == null) {
            System.out.println("(Lista vacía)");
            return;
        }
        NodoPlatillo temp = cabeza;
        while (temp != null) {
            System.out.println(temp.platillo);
            temp = temp.siguiente;
        }
    }

    public Platillos buscarPorNombre(String nombre) {
        NodoPlatillo temp = cabeza;
        while (temp != null) {
            if (temp.platillo.getNombre().equalsIgnoreCase(nombre)) {
                return temp.platillo;
            }
            temp = temp.siguiente;
        }
        return null;
    }

    public boolean eliminarPorNombre(String nombre) {
        if (cabeza == null) {
            return false;
        }

        if (cabeza.platillo.getNombre().equalsIgnoreCase(nombre)) {
            cabeza = cabeza.siguiente;
            return true;
        }

        NodoPlatillo anterior = cabeza;
        NodoPlatillo actual = cabeza.siguiente;

        while (actual != null) {
            if (actual.platillo.getNombre().equalsIgnoreCase(nombre)) {
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
        NodoPlatillo temp = cabeza;
        while (temp != null) {
            contador++;
            temp = temp.siguiente;
        }
        return contador;
    }
}
