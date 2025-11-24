
package Controller;

import Model.*;
import View.Bodega;
import View.Chef;

public class GlobalController {

    private Chef vistaPlatillos;
    private ListaPlatillos listaPlatillos;
    private PlatillosDAO platillosDAO;
    private IngredientesDAO ingredientesDAO;

    private ChefController platillosController;

    public GlobalController() {

        // Vista
        vistaPlatillos = new Chef();

        // Lista
        listaPlatillos = new ListaPlatillos();

        // DAO
        platillosDAO = new PlatillosDAO("rest01");
        ingredientesDAO=new IngredientesDAO("rest01");

        // Controller
        platillosController = new ChefController(
                vistaPlatillos,
                listaPlatillos,
                platillosDAO,
                ingredientesDAO
        );

        // Mostrar la vista
        vistaPlatillos.setVisible(true);
    }
}
