/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.io.File;
import java.util.LinkedList;

public class PlatillosDAO extends ArchivoDAO<Platillos> {

    private LinkedList<Platillos> platillos;

    public PlatillosDAO(String codigoRestaurante) {
        super("C:/ronny/PAULA/restaurantes/" + codigoRestaurante + "/platillos.dat");
        new File("C:/ronny/PAULA/restaurantes/" + codigoRestaurante).mkdirs();
        this.platillos = cargar();
    }

    public void agregar(Platillos p) {
        platillos.add(p);
        guardar(platillos);
        System.out.println("✅ Platillo guardado");
    }

    public boolean eliminar(String nombre) {
        boolean eliminado = platillos.removeIf(p -> p.getNombre().equalsIgnoreCase(nombre));
        if (eliminado) {
            guardar(platillos);
        }
        return eliminado;
    }

    public Platillos buscar(String nombre) {
        for (Platillos p : platillos) {
            if (p.getNombre().equalsIgnoreCase(nombre)) {
                return p;
            }
        }
        return null;
    }

    public void listar() {
        if (platillos.isEmpty()) {
            System.out.println("(No hay platillos)");
        }
        for (Platillos p : platillos) {
            System.out.println(p);
        }
    }
     public LinkedList<Platillos> getPlatillos() {
        return platillos;
    }

    // 🔹 Reemplazar lista completa (para guardar después de editar o eliminar varios)
    public void setPlatillos(LinkedList<Platillos> listaActualizada) {
        this.platillos = listaActualizada;
        guardar(this.platillos);
    }
}

