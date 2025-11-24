package Controller;

import Model.ListaUsuarios;
import Model.UsuarioDAO;
import Model.IngredientesDAO;
import Model.PlatillosDAO;
import Model.ListaIngredientes;
import Model.ListaPlatillos;
import Model.Usuario;

import View.*;

public class GlobalController {

    public void abrirAdmin(String codigoRestaurante, Usuario u) {

        Admin adminView = new Admin();

        ListaUsuarios listaUsuarios = new ListaUsuarios();
        UsuarioDAO usuarioDAO = new UsuarioDAO(codigoRestaurante);
        IngredientesDAO ingredientesDAO = new IngredientesDAO(codigoRestaurante);

        new AdminController(adminView, listaUsuarios, usuarioDAO, ingredientesDAO);

        adminView.setVisible(true);
    }

    public void abrirChef(String codigoRestaurante, Usuario u) {

        Chef chefView = new Chef();

        ListaPlatillos listaPlatillos = new ListaPlatillos();
        PlatillosDAO platillosDAO = new PlatillosDAO(codigoRestaurante);
        IngredientesDAO ingredientesDAO = new IngredientesDAO(codigoRestaurante);

        new ChefController(chefView, listaPlatillos, platillosDAO, ingredientesDAO);

        chefView.setVisible(true);
    }

    public void abrirBodega(String codigoRestaurante, Usuario u) {

        Bodega bodegaView = new Bodega();

        ListaIngredientes listaIngredientes = new ListaIngredientes();
        IngredientesDAO ingredientesDAO = new IngredientesDAO(codigoRestaurante);

        new BodegaController(bodegaView, listaIngredientes, ingredientesDAO);

        bodegaView.setVisible(true);
    }
}
