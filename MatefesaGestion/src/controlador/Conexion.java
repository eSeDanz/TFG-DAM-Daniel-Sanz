/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Daniel Sanz Ferrer
 */
public class Conexion {
    private final String DRIVER="org.postgresql.Driver";
    private final String URL="jdbc:postgresql://localhost:5432/";
    private final String DB="mtfsg";
    private final String USER="usuario_matefesa";
    private final String PASSWORD="matefesa";
    public Connection conexion;
    public static Conexion instancia;
    
    private Conexion(){
        this.conexion=null;
    }
    
    public Connection conectar(){
        try {
            Class.forName(DRIVER);
            this.conexion=DriverManager.getConnection(URL+DB,USER,PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            System.exit(0);
        }
        return this.conexion;
    }
    
    public void desconectar(){
        try {
            this.conexion.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public synchronized static Conexion getInstancia(){
        if (instancia==null){
            instancia=new Conexion();
        }
        return instancia;
    }
}
