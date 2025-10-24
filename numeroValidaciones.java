/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package validaciones;

/**
 *
 * @author Usuario
 */
public class numeroValidaciones {
    public boolean esNumeroValido(String numero) {
        if (numero == null || numero.isEmpty()) return false;
        String regex = "^[0-9]+$";
        return numero.matches(regex);
    }
    
    public boolean esNumeroConLongitud(String numero, int longitud) {
        return esNumeroValido(numero) && numero.length() == longitud;
    }
}