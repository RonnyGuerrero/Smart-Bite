package Controller;

import Model.Ingredientes;
import Model.ListaIngredientes;
import Model.IngredientesDAO;
import View.Bodega;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.table.DefaultTableModel;

public class BodegaController {

    private Bodega vista;
    private ListaIngredientes lista;
    private IngredientesDAO dao;

    public BodegaController(Bodega vista, ListaIngredientes lista, IngredientesDAO dao) {
        this.vista = vista;
        this.lista = lista;
        this.dao = dao;

        cargarIngredientesIniciales();
        cargarTabla();

        vista.getBotonAgregarIngrediente().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                abrirDialogoAgregar();

            }
        });

        vista.getBotoningredienteAgregar().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                agregarIngrediente();
            }
        });

        vista.getBotonCancelaringredienteAgregar().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                vista.getDialogoAgregarIng().dispose();
            }
        });

        vista.getBotonElminarIngrediente().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                eliminarIngrediente();
            }
        });

        vista.getBotonEditarIng().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                abrirDialogoEditar();
            }
        });

        vista.getBotonEditaringrediente().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                editarIngrediente();
            }
        });
        vista.getBotonCancelaringrediente().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                vista.getDialogoEditarIng().dispose();
            }
        });

        vista.getBotonBuscarIngrediente().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                buscarIngrediente();
            }
        });
    }

    private void cargarIngredientesIniciales() {
        for (Ingredientes i : dao.getIngredientes()) {
            lista.agregar(i);
        }
    }

    private void cargarTabla() {
        DefaultTableModel model = (DefaultTableModel) vista.getTablaIngredientes().getModel();
        model.setRowCount(0);

        for (Ingredientes ing : dao.getIngredientes()) {
            model.addRow(new Object[]{
                ing.getIdIngrediente(),
                ing.getNombre(),
                ing.getCantidad(),
                ing.getUnidadMedida(),
                ing.getCostoUnidad(),
                ing.getFechaVencimiento()
            });
        }
    }

    private void agregarIngrediente() {
        Ingredientes ing = new Ingredientes(
                Integer.parseInt(vista.getTxtIDingredienteAgregar().getText()),
                vista.getTxtNombreingredienteAgregar().getText(),
                Integer.parseInt(vista.getTxtCantidadingredienteAgregar().getText()),
                vista.getTxtUnidadMedidaingredienteAgregar().getText(),
                Double.parseDouble(vista.getTxtCostoingredienteAgregar().getText()),
                vista.getTxtVencimientoingredienteAgregar().getText()
        );

        lista.agregar(ing);
        dao.agregar(ing);

        cargarTabla();
        vista.getDialogoAgregarIng().dispose();
    }

    private void eliminarIngrediente() {
        int fila = vista.getTablaIngredientes().getSelectedRow();
        if (fila == -1) {
            System.out.println("Seleccione un ingrediente.");
            return;
        }

        String nombre = vista.getTablaIngredientes().getValueAt(fila, 1).toString();

        lista.eliminarPorNombre(nombre);
        dao.eliminar(nombre);

        cargarTabla();
    }

    private void abrirDialogoEditar() {
        int fila = vista.getTablaIngredientes().getSelectedRow();
        if (fila == -1) {
            System.out.println("Seleccione un ingrediente para editar.");
            return;
        }

        String nombre = vista.getTablaIngredientes().getValueAt(fila, 0).toString();
        Ingredientes ing = dao.buscar(nombre);

        if (ing != null) {
            vista.getTxtNombreingrediente().setText(ing.getNombre());
            vista.getTxtIDingrediente().setText(String.valueOf(String.valueOf(ing.getIdIngrediente())));
            vista.getTxtCantidadingrediente().setText(String.valueOf(ing.getCantidad()));
            vista.getTxtUnidadMedidaingrediente().setText(ing.getUnidadMedida());
            vista.getTxtCostoingrediente().setText(String.valueOf(ing.getCostoUnidad()));
            vista.getTxtVencimientoingrediente().setText(ing.getFechaVencimiento());
            vista.getDialogoEditarIng().setSize(404, 380);
            vista.getDialogoEditarIng().setVisible(true);
        }
    }

    private void abrirDialogoAgregar() {
        vista.getDialogoAgregarIng().setSize(404, 380);
        vista.getDialogoAgregarIng().setLocationRelativeTo(null);
        vista.getDialogoAgregarIng().setVisible(true);
        
        vista.getTxtCantidadingredienteAgregar().setText("");
        vista.getTxtCostoingredienteAgregar().setText("");
        vista.getTxtIDingredienteAgregar().setText("");
        vista.getTxtUnidadMedidaingredienteAgregar().setText("");
        vista.getTxtNombreingredienteAgregar().setText("");
        vista.getTxtVencimientoingredienteAgregar().setText("");
    }

    private void editarIngrediente() {
        int fila = vista.getTablaIngredientes().getSelectedRow();
        if (fila == -1) {
            System.out.println("Seleccione un ingrediente.");
            return;
        }

        String nombreOriginal = vista.getTablaIngredientes().getValueAt(fila, 0).toString();

        Ingredientes nuevos = new Ingredientes(
                Integer.parseInt(vista.getTxtIDingrediente().getText()),
                vista.getTxtNombreingrediente().getText(),
                Integer.parseInt(vista.getTxtCantidadingrediente().getText()),
                vista.getTxtUnidadMedidaingrediente().getText(),
                Double.parseDouble(vista.getTxtCostoingrediente().getText()),
                vista.getTxtVencimientoingrediente().getText()
        );

        lista.editarIngrediente(nombreOriginal, nuevos);
        dao.editarIngrediente(nombreOriginal, nuevos);

        cargarTabla();
        vista.getDialogoEditarIng().dispose();
    }

    private void buscarIngrediente() {
        String nombre = vista.getTXTbuscar().getText();
        Ingredientes ing = dao.buscar(nombre);

        DefaultTableModel model = (DefaultTableModel) vista.getTablaIngredientes().getModel();
        model.setRowCount(0);
        
        if (ing != null) {
            model.addRow(new Object[]{
                ing.getIdIngrediente(),
                ing.getNombre(),
                ing.getCantidad(),
                ing.getUnidadMedida(),
                ing.getCostoUnidad(),
                ing.getFechaVencimiento()
            });
        } else {
            System.out.println("No se encontró el ingrediente.");
        }
    }

}
