/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package validaciones;

/**
 *
 * @author Usuario
 */
public class GmailValidaciones {
    public boolean esEmailValido(String email) {
        if (email == null || email.isEmpty()) return false;
        String regex = "^[A-Za-z0-9+_.-]+@gmail\\.com$";
        if (!email.matches(regex)) return false;
        
        String[] partes = email.split("@");
        String usuario = partes[0];
        if (usuario.startsWith(".") || usuario.endsWith(".")) return false;
        if (usuario.contains("..")) return false;
        return usuario.length() <= 64;
    }
}