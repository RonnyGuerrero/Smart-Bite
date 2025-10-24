/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package validaciones;

/**
 *
 * @author Usuario
 */
public class letrasValidaciones {
    public boolean contieneSoloLetras(String texto) {
        if (texto == null || texto.isEmpty()) return false;
        String regex = "^[\\p{L} ]+$";
        return texto.matches(regex);
    }
}