/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.io.*;
import java.util.LinkedList;

public abstract class ArchivoDAO<T> {

    protected File archivo;

    public ArchivoDAO(String rutaArchivo) {
        this.archivo = new File(rutaArchivo);
    }

        protected void guardar(LinkedList<T> lista) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(archivo))) {
            out.writeObject(lista);
        } catch (IOException e) {
            System.err.println("Error al guardar: " + e.getMessage());
        }
    }

    protected LinkedList<T> cargar() {
        if (!archivo.exists()) {
            return new LinkedList<>();
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(archivo))) {
            return (LinkedList<T>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println(" No se pudo cargar: " + e.getMessage());
            return new LinkedList<>();
        }
    }
}
