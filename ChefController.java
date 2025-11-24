package Controller;

import Model.*;
import View.Chef; // cambia por el nombre real de tu clase de vista

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.ImageIcon;
import java.io.File;



public class ChefController{

    private Chef vista;
    private IngredientesDAO ingredientesDAO;
    private ListaPlatillos lista;
    private PlatillosDAO dao;

    public ChefController(Chef vista, ListaPlatillos lista, PlatillosDAO dao,IngredientesDAO ingredientesDAO) {
        this.vista = vista;
        this.lista = lista;
        this.dao = dao;
        this.ingredientesDAO = ingredientesDAO;

        cargarTablaIngredientes();

        // cargar en lista desde DAO
        cargarPlatillosIniciales();
        cargarTabla();

        // eventos
        this.vista.getBotonAgrega().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                agregarPlatillo();
            }
        });

        this.vista.getBotonEditar().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                editarPlatillo();
            }
        });

        this.vista.getBotonEliminar().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                eliminarPlatillo();
            }
        });

        // si tienes botón para elegir imagen (abre file chooser)
        this.vista.getBotonImagen().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                seleccionarImagen();
            }
        });

        // cuando seleccionen fila en la tabla, llenar campos
        this.vista.getTablaPlatillos().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                filaSeleccionadaCargarCampos();
            }
        });
    }


    private void cargarPlatillosIniciales() {
        for (Platillos p : dao.getPlatillos()) {
            lista.agregar(p);
        }
    }

    private void cargarTabla() {
        DefaultTableModel model = (DefaultTableModel) vista.getTablaPlatillos().getModel();
        model.setRowCount(0);

        for (Platillos p : dao.getPlatillos()) {
            model.addRow(new Object[]{
                p.getIdPlatillo(),      // id
                p.getNombre(),
                p.getCategoria(),
                p.getPrecio(),
                p.getDescripcion()
            });
        }
    }

    private int siguienteId() {
        int max = 0;
        LinkedList<Platillos> listaDao = dao.getPlatillos();
        if (listaDao != null) {
            for (Platillos p : listaDao) {
                if (p.getIdPlatillo() > max) max = p.getIdPlatillo();
            }
        }
        return max + 1;
    }


    private void agregarPlatillo() {
        try {
            String nombre = vista.getTxtNombreChef().getText().trim();
            String categoria = vista.getTxtCategoriaChef().getText().trim();
            String precioTxt = vista.getTxtPrecioChef().getText().trim();
            String rutaImagen = vista.getTxtRutaImagen().getText().trim();
            String descripcion = vista.getTxtareaDescripcion().getText().trim();

            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(null, "El nombre no puede estar vacío.");
                return;
            }

            double precio;
            try {
                precio = Double.parseDouble(precioTxt);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Precio inválido. Use formato numérico (ej. 12.50).");
                return;
            }

            Platillos p = new Platillos();
            p.setIdPlatillo(siguienteId());
            p.setNombre(nombre);
            p.setCategoria(categoria);
            p.setPrecio(precio);
            p.setDescripcion(descripcion);
            // si tu modelo tiene campo rutaImagen, agrégalo; si no, lo omitimos.
            // p.setRutaImagen(rutaImagen);

            lista.agregar(p);
            dao.agregar(p);

            cargarTabla();
            limpiarCampos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al agregar platillo: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void editarPlatillo() {
        int fila = vista.getTablaPlatillos().getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Seleccione un platillo en la tabla para editar.");
            return;
        }

        try {
            DefaultTableModel model = (DefaultTableModel) vista.getTablaPlatillos().getModel();
            int idOriginal = Integer.parseInt(model.getValueAt(fila, 0).toString());

            String nombre = vista.getTxtNombreChef().getText().trim();
            String categoria = vista.getTxtCategoriaChef().getText().trim();
            String precioTxt = vista.getTxtPrecioChef().getText().trim();
            String descripcion = vista.getTxtareaDescripcion().getText().trim();
            // String rutaImagen = vista.getTxtRutaImagen().getText().trim();

            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(null, "El nombre no puede estar vacío.");
                return;
            }

            double precio;
            try {
                precio = Double.parseDouble(precioTxt);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Precio inválido. Use formato numérico (ej. 12.50).");
                return;
            }

            Platillos nuevos = new Platillos();
            nuevos.setIdPlatillo(idOriginal);
            nuevos.setNombre(nombre);
            nuevos.setCategoria(categoria);
            nuevos.setPrecio(precio);
            nuevos.setDescripcion(descripcion);
            // nuevos.setRutaImagen(rutaImagen);

            // actualizar en lista enlazada: buscado por nombre original o por id
            // Aquí DAO no tiene método editar; actualizamos la LinkedList y guardamos:
            LinkedList<Platillos> listaActual = dao.getPlatillos();
            boolean encontrado = false;
            for (int i = 0; i < listaActual.size(); i++) {
                if (listaActual.get(i).getIdPlatillo() == idOriginal) {
                    listaActual.set(i, nuevos);
                    encontrado = true;
                    break;
                }
            }
            if (encontrado) {
                dao.setPlatillos(listaActual);
                reconstruirListaDesdeDAO();
                cargarTabla();
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró el platillo para editar.");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al editar: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void eliminarPlatillo() {
        int fila = vista.getTablaPlatillos().getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Seleccione un platillo para eliminar.");
            return;
        }

        DefaultTableModel model = (DefaultTableModel) vista.getTablaPlatillos().getModel();
        String nombre = model.getValueAt(fila, 1).toString(); // columna 1 -> nombre

        int confirm = JOptionPane.showConfirmDialog(null,
                "¿Eliminar platillo \"" + nombre + "\"?", "Confirmar", JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) return;

        lista.eliminarPorNombre(nombre);   
        dao.eliminar(nombre);             
        cargarTabla();
        limpiarCampos();
    }


    private void filaSeleccionadaCargarCampos() {
        int fila = vista.getTablaPlatillos().getSelectedRow();
        if (fila == -1) return;

        DefaultTableModel model = (DefaultTableModel) vista.getTablaPlatillos().getModel();
        try {
            vista.getTxtNombreChef().setText(model.getValueAt(fila, 1).toString());
            vista.getTxtCategoriaChef().setText(model.getValueAt(fila, 2).toString());
            vista.getTxtPrecioChef().setText(model.getValueAt(fila, 3).toString());
            vista.getTxtareaDescripcion().setText(model.getValueAt(fila, 4).toString());
        } catch (Exception ex) {
         
        }
    }

    private void limpiarCampos() {
        vista.getTxtNombreChef().setText("");
        vista.getTxtCategoriaChef().setText("");
        vista.getTxtPrecioChef().setText("");
        vista.getTxtRutaImagen().setText("");
        vista.getTxtareaDescripcion().setText("");
    }

    private void reconstruirListaDesdeDAO() {
        ListaPlatillosClearAndFill();
    }

    private void ListaPlatillosClearAndFill() {
        ListaPlatillos nueva = new ListaPlatillos();
        for (Platillos p : dao.getPlatillos()) {
            nueva.agregar(p);
        }
        this.lista = nueva;
    }

    private void seleccionarImagen() {
        javax.swing.JFileChooser fc = new javax.swing.JFileChooser();
        int res = fc.showOpenDialog(null);
        if (res == javax.swing.JFileChooser.APPROVE_OPTION) {
            File f = fc.getSelectedFile();
            vista.getTxtRutaImagen().setText(f.getAbsolutePath());
            try {
                ImageIcon icon = new ImageIcon(f.getAbsolutePath());
                vista.getLabelImagenDemostracion().setIcon(icon);
            } catch (Exception ex) {
                
            }
        }
    }
    private void cargarTablaIngredientes() {

    DefaultTableModel modelo = (DefaultTableModel) vista.getTablaIngredientes().getModel();
    modelo.setRowCount(0);

    for (Ingredientes ing : ingredientesDAO.getIngredientes()) {
        modelo.addRow(new Object[]{
                ing.getIdIngrediente(),
                ing.getNombre(),
                ing.getUnidadMedida(),
                ing.getCantidad(),
                ing.getCostoUnidad(),
                ing.getFechaVencimiento()
        });
    }
}

}
