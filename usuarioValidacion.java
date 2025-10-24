/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package validaciones;

/**
 *
 * @author Usuario
 */
public class usuarioValidacion {
    public boolean esUsuarioValido(String usuario) {
        if (usuario == null || usuario.isEmpty()) return false;
        if (usuario.length() < 4 || usuario.length() > 20) return false;
        
        String regex = "^[a-zA-Z0-9._-]+$";
        if (!usuario.matches(regex)) return false;
        
        if (usuario.startsWith(".") || usuario.endsWith(".") ||
            usuario.startsWith("_") || usuario.endsWith("_") ||
            usuario.startsWith("-") || usuario.endsWith("-")) {
            return false;
        }
        
        return !(usuario.contains("..") || usuario.contains("__") || 
                usuario.contains("--") || usuario.contains("._") || 
                usuario.contains("_-") || usuario.contains("-."));
    }
}