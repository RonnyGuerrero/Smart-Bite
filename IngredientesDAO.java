/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.io.File;
import java.util.LinkedList;

public class IngredientesDAO extends ArchivoDAO<Ingredientes> {

    private LinkedList<Ingredientes> ingredientes;

    public IngredientesDAO(String codigoRestaurante) {
        super("C:/ronny/PAULA/restaurantes/" + codigoRestaurante + "/ingredientes.dat");
        new File("C:/ronny/PAULA/restaurantes/" + codigoRestaurante).mkdirs();
        this.ingredientes = cargar();
    }

    public void agregar(Ingredientes i) {
        ingredientes.add(i);
        guardar(ingredientes);
        System.out.println("✅ Ingrediente guardado");
    }

    public boolean eliminar(String nombre) {
        boolean eliminado = ingredientes.removeIf(ing -> ing.getNombre().equalsIgnoreCase(nombre));
        if (eliminado) {
            guardar(ingredientes);
        }
        return eliminado;
    }

    public Ingredientes buscar(String nombre) {
        for (Ingredientes i : ingredientes) {
            if (i.getNombre().equalsIgnoreCase(nombre)) {
                return i;
            }
        }
        return null;
    }

    public void listar() {
        if (ingredientes.isEmpty()) {
            System.out.println("(No hay ingredientes)");
        }
        for (Ingredientes i : ingredientes) {
            System.out.println(i);
        }
    }
}
