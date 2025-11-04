/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

public class Main {

    public static void main(String[] args) {

        PlatillosDAO platilloDAO = new PlatillosDAO("R001");

// Agregar varios
        platilloDAO.listar();

    }
}
