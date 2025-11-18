/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.IngredientesDAO;
import Model.ListaIngredientes;
import View.Bodega;

/**
 *
 * @author Usuario
 */
public class GlobalController {

    public void iniciarBodega() {
        Bodega vista = new Bodega();
        ListaIngredientes lista = new ListaIngredientes();
        IngredientesDAO dao = new IngredientesDAO("rest01");

        new BodegaController(vista, lista, dao);
        vista.setVisible(true);
    }
}
