/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import modelo.Proveedor;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Daniel Sanz Ferrer
 */
public class ProveedorDAO {
    private final Conexion CON;
    private PreparedStatement ps;
    private ResultSet rs;
    private boolean resp;
    
    public ProveedorDAO(){
        CON=Conexion.getInstancia();
    }
    
    public List<Proveedor> listar(String texto, int totalPorPagina, int numPagina) {
        List<Proveedor> registros=new ArrayList();
        try {
            ps=CON.conectar().prepareStatement("SELECT id_proveedor, correo, web, descuento, numero_cliente FROM proveedor WHERE UNACCENT(lower(id_proveedor)) LIKE UNACCENT(lower(?)) ORDER BY id_proveedor ASC LIMIT ? OFFSET ?");
            ps.setString(1,"%"+ texto +"%");    
            ps.setInt(2, totalPorPagina);
            ps.setInt(3, (numPagina-1)*totalPorPagina);
            rs=ps.executeQuery();
            while(rs.next()){
                registros.add(new Proveedor(rs.getString(1),rs.getString(2),rs.getString(3),rs.getDouble(4),rs.getInt(5)));
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally{
            ps=null;
            rs=null;
            CON.desconectar();
        }
        return registros;
    }
    
    public List<Proveedor> seleccionar() {
        List<Proveedor> registros=new ArrayList();
        try {
            ps=CON.conectar().prepareStatement("SELECT id_proveedor FROM proveedor ORDER BY id_proveedor ASC");
            rs=ps.executeQuery();
            while(rs.next()){
                registros.add(new Proveedor(rs.getString(1)));
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally{
            ps=null;
            rs=null;
            CON.desconectar();
        }
        return registros;
    }

    public boolean insertar(Proveedor pro) {
        resp=false;
        try {
            ps=CON.conectar().prepareStatement("INSERT INTO proveedor (id_proveedor, correo, web, descuento, numero_cliente) VALUES (?,?,?,?,?)");
            ps.setString(1, pro.getId());
            ps.setString(2, pro.getCorreo());
            ps.setString(3, pro.getWeb());
            ps.setDouble(4, pro.getDescuento());
            ps.setInt(5, pro.getNumCliente());
            if (ps.executeUpdate()>0){
                resp=true;
            }
            ps.close();
        }  catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally{
            ps=null;
            CON.desconectar();
        }
        return resp;
    }

    public boolean actualizar(Proveedor pro, String nombreAntiguo) {
        resp=false;
        try {
            ps=CON.conectar().prepareStatement("UPDATE proveedor SET id_proveedor=?, correo=?, web=?, descuento=?, numero_cliente=? WHERE id_proveedor=?");
            ps.setString(1, pro.getId());
            ps.setString(2, pro.getCorreo());
            ps.setString(3, pro.getWeb());
            ps.setDouble(4, pro.getDescuento());
            ps.setInt(5, pro.getNumCliente());
            ps.setString(6, nombreAntiguo);
            if (ps.executeUpdate()>0){
                resp=true;
            }
            ps.close();
        }  catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally{
            ps=null;
            CON.desconectar();
        }
        return resp;
    }

    public boolean eliminar(String proveedor) {
        resp = false;
        try {
            ps = CON.conectar().prepareStatement("DELETE FROM proveedor WHERE id_proveedor = ?");
            ps.setString(1, proveedor);
            if (ps.executeUpdate() > 0) {
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
    
    public int total() {
        int totalRegistros=0;
        try {
            ps=CON.conectar().prepareStatement("SELECT COUNT(id_proveedor) AS count FROM proveedor");            
            rs=ps.executeQuery();
            
            while(rs.next()){
                totalRegistros=rs.getInt("count");
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
        return totalRegistros;
    }

    public boolean existe(String idPro) {
        resp=false;
        try {
            ps=CON.conectar().prepareStatement("SELECT id_proveedor FROM proveedor WHERE id_proveedor=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ps.setString(1, idPro);
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
    
    public String getEmail(String id_proveedor){
        String email = "";
        try {
            ps=CON.conectar().prepareStatement("SELECT correo FROM proveedor WHERE id_proveedor = ?");
            ps.setString(1, id_proveedor);
            rs=ps.executeQuery();
            rs.next();
            email = (rs.getString(1));
            ps.close();
            rs.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally{
            ps=null;
            rs=null;
            CON.desconectar();
        }
        return email;
    }
    
    public double getDescuento(String id_proveedor){
        double descuento = 0;
        try {
            ps=CON.conectar().prepareStatement("SELECT descuento FROM proveedor WHERE id_proveedor = ?");
            ps.setString(1, id_proveedor);
            rs=ps.executeQuery();
            rs.next();
            descuento = (rs.getDouble(1));
            ps.close();
            rs.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally{
            ps=null;
            rs=null;
            CON.desconectar();
        }
        return descuento;
    }
    
    public int getNumeroCliente(String id_proveedor){
        int numCliente = 0;
        try {
            ps=CON.conectar().prepareStatement("SELECT numero_cliente FROM proveedor WHERE id_proveedor = ?");
            ps.setString(1, id_proveedor);
            rs=ps.executeQuery();
            rs.next();
            numCliente = (rs.getInt(1));
            ps.close();
            rs.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally{
            ps=null;
            rs=null;
            CON.desconectar();
        }
        return numCliente;
    }
}
