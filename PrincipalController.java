/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import View.Login;
import View.iniciarSesion;
import View.registrarse;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JInternalFrame;
import javax.swing.JDesktopPane;

public class PrincipalController {

    private final Login inicio;
    private final GlobalController global;

    public PrincipalController(Login inicio, GlobalController global) {
        this.inicio = inicio;
        this.global = global;

        initEventos();
    }

    private void initEventos() {

        // Abrir formulario de Login
        inicio.getBotonIniciarSesion().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                abrirLogin();
            }
        });

        // Abrir formulario de Register
        inicio.getBotonRegistrarse().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                abrirRegister();
            }
        });
    }

    private void abrirLogin() {
        iniciarSesion login = new iniciarSesion();

        new LoginController(login, global,inicio.getPanelVisual());

        inicio.getPanelVisual().removeAll();
        inicio.getPanelVisual().add(login);
        login.setVisible(true);
    }

    private void abrirRegister() {
        registrarse register = new registrarse();

        new RegisterController(register);

        inicio.getPanelVisual().removeAll();
        inicio.getPanelVisual().add(register);
        register.setVisible(true);
    }
}
