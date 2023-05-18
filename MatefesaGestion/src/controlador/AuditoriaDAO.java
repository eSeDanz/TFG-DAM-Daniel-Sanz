/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.Articulo;

/**
 *
 * @author Daniel Sanz Ferrer
 */
public class AuditoriaDAO {
    private final Conexion CON;
    private PreparedStatement ps;
    private ResultSet rs;
    private boolean resp;

    public AuditoriaDAO() {
        CON = Conexion.getInstancia();
    }
    
    public List<Articulo> listar(String texto, String proveedor) {
        List<Articulo> registros = new ArrayList();
        try {
            ps = CON.conectar().prepareStatement("SELECT referencia, ean, descripcion, minimo, precio, id_proveedor, precio_antiguo, fecha FROM auditoria WHERE UNACCENT(lower(descripcion)) LIKE UNACCENT(lower(?)) AND id_proveedor = ? ORDER BY descripcion ASC");
            ps.setString(1, "%" + texto + "%");
            ps.setString(2, proveedor);
            rs = ps.executeQuery();
            while (rs.next()) {
                //rs.getString(1);
                registros.add(new Articulo(rs.getString(1), new BigInteger(rs.getBigDecimal(2).toBigInteger().toString()), rs.getString(3), rs.getInt(4), rs.getDouble(5), rs.getString(6), rs.getDouble(7), rs.getDate(8)));
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
    
    public List<Articulo> getListaProveedor(String proveedor) {
        List<Articulo> registros = new ArrayList();
        try {
            ps = CON.conectar().prepareStatement("SELECT referencia, ean, descripcion, minimo, precio, id_proveedor, precio_antiguo, fecha FROM auditoria WHERE id_proveedor = ?");
            ps.setString(1, proveedor);
            rs = ps.executeQuery();
            while (rs.next()) {
                registros.add(new Articulo(rs.getString(1), new BigInteger(rs.getBigDecimal(2).toBigInteger().toString()), rs.getString(3), rs.getInt(4), rs.getDouble(5), rs.getString(6), rs.getDouble(7), rs.getDate(8)));
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
    
    public Articulo getArticulo(String referencia) {
        Articulo art = new Articulo();
        try {
            ps = CON.conectar().prepareStatement("SELECT referencia, ean, descripcion, minimo, precio, id_proveedor, precio_antiguo, fecha FROM auditoria WHERE referencia = ?");
            ps.setString(1, referencia);
            rs = ps.executeQuery();
            rs.next();
            art = new Articulo(rs.getString(1), new BigInteger(rs.getBigDecimal(2).toBigInteger().toString()), rs.getString(3), rs.getInt(4), rs.getDouble(5), rs.getString(6), rs.getDouble(7), rs.getDate(8));            
            ps.close();
            rs.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
            ps = null;
            rs = null;
            CON.desconectar();
        }
        return art;
    }
    
    public boolean existe(String referencia) {
        resp = false;
        try {
            ps = CON.conectar().prepareStatement("SELECT referencia FROM auditoria WHERE referencia = ?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ps.setString(1, referencia);
            rs = ps.executeQuery();
            rs.last();
            if (rs.getRow() > 0) {
                resp = true;
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
        return resp;
    }
}
