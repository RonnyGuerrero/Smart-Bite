/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;


/**
 *
 * @author Usuario
 */
public class Main {

    public static void main(String[] args) {
        UsuarioDAO usu = new UsuarioDAO();

        Usuario nuevo = new Usuario(0, "ronny", "guerrero", "administrador", "ranasg", "4321");
        
        usu.listaUsuarios();
    }
}
