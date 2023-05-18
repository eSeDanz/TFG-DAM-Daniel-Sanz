/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import modelo.Articulo;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Daniel Sanz Ferrer
 */
public class ArticuloDAO {

    private final Conexion CON;
    private PreparedStatement ps;
    private ResultSet rs;
    private boolean resp;

    public ArticuloDAO() {
        CON = Conexion.getInstancia();
    }

    public List<Articulo> listar(String texto, int totalPorPagina, int numPagina) {
        List<Articulo> registros = new ArrayList();
        try {
            ps = CON.conectar().prepareStatement("SELECT referencia, ean, descripcion, minimo, precio, id_proveedor FROM articulo WHERE UNACCENT(lower(descripcion)) LIKE UNACCENT(lower(?)) ORDER BY descripcion ASC LIMIT ? OFFSET ?");
            ps.setString(1, "%" + texto + "%");
            ps.setInt(2, totalPorPagina);
            ps.setInt(3, (numPagina - 1) * totalPorPagina);
            rs = ps.executeQuery();
            while (rs.next()) {
                registros.add(new Articulo(rs.getString(1), new BigInteger(rs.getBigDecimal(2).toBigInteger().toString()), rs.getString(3), rs.getInt(4), rs.getDouble(5), rs.getString(6)));
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

    public List<Articulo> listar(String texto, String proveedor) {
        List<Articulo> registros = new ArrayList();
        try {
            ps = CON.conectar().prepareStatement("SELECT referencia, ean, descripcion, minimo, precio, id_proveedor FROM articulo WHERE UNACCENT(lower(descripcion)) LIKE UNACCENT(lower(?)) AND id_proveedor = ? ORDER BY descripcion ASC");
            ps.setString(1, "%" + texto + "%");
            ps.setString(2, proveedor);
            rs = ps.executeQuery();
            while (rs.next()) {
                registros.add(new Articulo(rs.getString(1), new BigInteger(rs.getBigDecimal(2).toBigInteger().toString()), rs.getString(3), rs.getInt(4), rs.getDouble(5), rs.getString(6)));
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
            ps = CON.conectar().prepareStatement("SELECT referencia, ean, descripcion, minimo, precio, id_proveedor FROM articulo WHERE referencia = ?");//LIKE
            ps.setString(1, referencia);
            rs = ps.executeQuery();
            rs.next();
            art = new Articulo(rs.getString(1), rs.getString(3), rs.getInt(4), rs.getDouble(5), rs.getString(6));
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

    public boolean insertar(Articulo art) {
        resp = false;
        try {
            ps = CON.conectar().prepareStatement("INSERT INTO articulo (referencia,ean,descripcion,minimo,precio,id_proveedor) VALUES (?,?,?,?,?,?)");
            ps.setString(1, art.getReferencia());
            ps.setBigDecimal(2, new BigDecimal(art.getEan()));
            ps.setString(3, art.getDescripcion());
            ps.setInt(4, art.getMinimo());
            ps.setDouble(5, art.getPrecio());
            ps.setString(6, art.getId_proveedor());
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

    public boolean actualizar(Articulo art) {
        resp = false;
        try {
            ps = CON.conectar().prepareStatement("UPDATE articulo SET referencia=?, ean=?, descripcion=?, minimo=?, precio=?, id_proveedor=? WHERE referencia = ?");
                ps.setString(1, art.getReferencia());
                ps.setBigDecimal(2, new BigDecimal(art.getEan()));
                ps.setString(3, art.getDescripcion());
                ps.setInt(4, art.getMinimo());
                ps.setDouble(5, art.getPrecio());
                ps.setString(6, art.getId_proveedor());
                ps.setString(7, art.getReferenciaAntigua());
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

    public boolean actualizarPrecios(Double precio, String referencia) {
        resp = false;
        try {
            ps = CON.conectar().prepareStatement("UPDATE articulo SET precio = ? WHERE referencia = ?");
            ps.setDouble(1, precio);
            ps.setString(2, referencia);
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

    public boolean cambioPreciosProveedor(String proveedor, Double porcentaje) {
        resp = false;
        try {
            ps = CON.conectar().prepareStatement("UPDATE articulo SET precio = precio + ((precio * ?) / 100) WHERE id_proveedor = ?");
            ps.setDouble(1, porcentaje);
            ps.setString(2, proveedor);
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

    public boolean eliminar(String referencia) {
        resp = false;
        try {
            ps = CON.conectar().prepareStatement("DELETE FROM articulo WHERE referencia = ?");
            ps.setString(1, referencia);
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

    public boolean vaciarTabla() {
        resp = false;
        try {
            ps = CON.conectar().prepareStatement("TRUNCATE TABLE articulo CASCADE");
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

    public int total() {
        int totalRegistros = 0;
        try {
            ps = CON.conectar().prepareStatement("SELECT COUNT(referencia) AS count FROM articulo");
            rs = ps.executeQuery();
            while (rs.next()) {
                totalRegistros = rs.getInt("count");
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
        return totalRegistros;
    }

    public boolean existe(String referencia) {
        resp = false;
        try {
            ps = CON.conectar().prepareStatement("SELECT referencia FROM articulo WHERE referencia = ?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
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
    
    public double getPrecio(String referencia) {
        double precio = 0;
        try {
            ps = CON.conectar().prepareStatement("SELECT precio FROM articulo WHERE referencia = ?");
            ps.setString(1, referencia);
            rs = ps.executeQuery();
            rs.next();
            precio = rs.getDouble(1);
            ps.close();
            rs.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
            ps = null;
            rs = null;
            CON.desconectar();
        }
        return precio;
    }
}
