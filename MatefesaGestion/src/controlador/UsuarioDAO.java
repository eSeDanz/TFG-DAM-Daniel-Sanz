/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Daniel Sanz Ferrer
 */
public class UsuarioDAO {
    private final Conexion CON;
    private PreparedStatement ps;
    private ResultSet rs;
    
    public UsuarioDAO(){
        CON=Conexion.getInstancia();
    }

    public boolean existe(String usuario, String clave) {
        boolean resp=false;
        try {
            ps=CON.conectar().prepareStatement("SELECT * FROM usuario WHERE usuario=? AND clave=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ps.setString(1, usuario);
            ps.setString(2, clave);
            rs=ps.executeQuery();
            rs.last();
            if(rs.getRow()>0){
                resp=true;
            }           
            ps.close();
            rs.close();
        }  catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally{
            ps=null;
            rs=null;
            CON.desconectar();
        }
        return resp;
    }
}
