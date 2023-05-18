/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import controlador.UsuarioDAO;

/**
 *
 * @author Daniel Sanz Ferrer
 */
public class UsuarioControl {

    private final UsuarioDAO UDAO = new UsuarioDAO();

    public boolean existe(String usuario, String clave) {
        return UDAO.existe(usuario, clave);
    }
}
