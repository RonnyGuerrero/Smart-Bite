/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;
import View.*;
import Controller.*;

public class Main {


    public static void main(String[] args) {
       
        String codigoRestaurante = "rest01";

        // === DAO ===
        UsuarioDAO usuarioDAO = new UsuarioDAO(codigoRestaurante);
        IngredientesDAO ingredientesDAO = new IngredientesDAO(codigoRestaurante);

        // === Lista enlazada ===
        ListaUsuarios listaUsuarios = new ListaUsuarios();

        // Cargar usuarios existentes a la lista
        for (Usuario u : usuarioDAO.getUsuarios()) {
            listaUsuarios.agregar(u);
        }

        // === Vista Administrador ===
        Admin adminView = new Admin();

        // === Controller Administrador ===
        new AdminController(
                adminView,
                listaUsuarios,
                usuarioDAO,
                ingredientesDAO
        );

        // Abrir la pantalla de Administrador directamente
        adminView.setVisible(true);

    }

}

