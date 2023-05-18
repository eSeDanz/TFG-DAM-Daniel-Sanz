/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.Pedido;

/**
 *
 * @author Daniel Sanz Ferrer
 */
public class PedidoDAO {
    private final Conexion CON;
    private PreparedStatement ps;
    private ResultSet rs;
    private boolean resp;
    
    public PedidoDAO() {
        CON = Conexion.getInstancia();
    }
    
    public List<Pedido> listarPedidos() {
        List<Pedido> registros = new ArrayList();
        try {
            ps = CON.conectar().prepareStatement("SELECT * FROM pedido");
            rs = ps.executeQuery();
            while (rs.next()) {
                registros.add(new Pedido(rs.getInt(1), rs.getDate(2), rs.getDouble(3), rs.getString(4)));
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
            ps = null;
            rs = null;
            CON.desconectar();
        }
        return registros;
    }
    
    public boolean vaciarTabla() {
        resp = false;
        try {
            ps = CON.conectar().prepareStatement("TRUNCATE TABLE pedido CASCADE");
            if (ps.execute()) {
                resp = true;
            }
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
            ps = null;
            CON.desconectar();
        }
        return resp;
    }
}
