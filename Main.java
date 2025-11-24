/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;
import View.*;
import Controller.*;

public class Main {
    public static void main(String[] args) {

    GlobalController global = new GlobalController();

    Login inicio = new Login();
    
    new PrincipalController(inicio, global);

    // Mostrar ventana
    inicio.setVisible(true);
}
}