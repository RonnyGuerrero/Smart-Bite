/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.Date;

public class Main {


    public static void main(String[] args) {

         String codigo = "R001";

        // DAO = archivo .dat
        IngredientesDAO dao = new IngredientesDAO(codigo);

        // Lista enlazada
        ListaIngredientes lista = new ListaIngredientes();
       
        // 1️⃣ CARGAR datos del archivo hacia la lista enlazada
        for (Ingredientes i : dao.getIngredientes()) {
            lista.agregar(i);
        }

        System.out.println("Ingredientes originales:");
        lista.mostrar();
        
        // 2️⃣ CREAR objeto con los nuevos datos
        Ingredientes nuevosDatos = new Ingredientes(
                
            0,
            "Harina",
            30,
            "kg",
            2700,
            new Date()
        );

        // 3️⃣ EDITAR dentro de la lista enlazada
        boolean editado = lista.editarIngrediente("Harina", nuevosDatos);

        if (editado) {
            System.out.println("\n✏ Ingrediente editado correctamente en la lista enlazada.");
        } else {
            System.out.println("\n No se encontró el ingrediente a editar.");
        }

        // 4️⃣ MOSTRAR lista después de editar
        System.out.println("\n Lista después de editar:");
        lista.mostrar();

        // 5️⃣ GUARDAR lista enlazada en archivo .dat
        dao.setIngredientes(lista.aLinkedList());

        System.out.println("\n Cambios guardados exitosamente en el archivo.");
    }}


