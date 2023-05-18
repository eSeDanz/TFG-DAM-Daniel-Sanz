/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.Articulo;
import modelo.Contenido;
import modelo.ListaContenido;

/**
 *
 * @author Daniel Sanz Ferrer
 */
public class ContenidoDAO {

    private final Conexion CON;
    private final DecimalFormatSymbols SYMBOLS;
    private final DecimalFormat DF;
    private PreparedStatement ps;
    private ResultSet rs;
    private boolean resp;

    public ContenidoDAO() {
        CON = Conexion.getInstancia();
        SYMBOLS = new DecimalFormatSymbols();
        SYMBOLS.setDecimalSeparator('.');
        DF = new DecimalFormat("#.##", SYMBOLS);
    }

    public List<Contenido> listarContenido() {
        List<Contenido> registros = new ArrayList();
        try {
            ps = CON.conectar().prepareStatement("SELECT c.id_pedido, c.referencia, a.descripcion, c.cantidad, c.total_x_articulo FROM contenido c INNER JOIN articulo a ON c.referencia = a.referencia ORDER BY id_pedido ASC");
            rs = ps.executeQuery();
            while (rs.next()) {
                registros.add(new Contenido(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getDouble(5)));
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

    public boolean insertar(ListaContenido listaCont) {
        resp = false;
        double precioConDescuento = listaCont.totalConDescuento();
        double precioConImpuestos = listaCont.totalConImpuestos(precioConDescuento);
        String proveedor = listaCont.getPrimerProveedor();
        try {
            Connection con = CON.conectar();
            con.setAutoCommit(false);
            ps = con.prepareStatement("BEGIN");
            ps.executeUpdate();
            ps = con.prepareStatement("INSERT INTO pedido (id_pedido, fecha, precio_total, id_proveedor) VALUES (DEFAULT, DEFAULT, ?, ?)");
            ps.setDouble(1, precioConImpuestos);
            ps.setString(2, proveedor);
            ps.executeUpdate();
            for (Contenido conte : listaCont.getListaContenido()) {
                String referencia = conte.getArticulo().getReferencia();
                int cantidad = conte.getCantidad();
                double totalXArticuloConDescuento = conte.getTotalxArtConDescuento();
                double totalXArticuloConImpuestos = conte.getTotalxArtConImpuestos(totalXArticuloConDescuento);
                ps = con.prepareStatement("INSERT INTO contenido (id_pedido, referencia, cantidad, total_x_articulo) VALUES (currval('pedido_id_pedido_seq'), ?, ?, ?)");
                ps.setString(1, referencia);
                ps.setInt(2, cantidad);
                ps.setDouble(3, totalXArticuloConImpuestos);
                ps.executeUpdate();
            }
            ps = con.prepareStatement("COMMIT");
            if (ps.executeUpdate() > 0) {
                resp = true;
            }
            ps.close();
            con.setAutoCommit(true);
            JOptionPane.showMessageDialog(null, "Pedido registrado en el histórico de pedidos de compra correctamente.", "Pedidos", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
            ps = null;
            CON.desconectar();
        }
        return resp;
    }
    
    public List<Contenido> recuperarContenido(int idPedido, String proveedor) {
        List<Contenido> registros = new ArrayList();
        try {
            ps = CON.conectar().prepareStatement("SELECT c.referencia, a.descripcion, a.precio, c.cantidad FROM contenido c INNER JOIN articulo a ON c.referencia = a.referencia WHERE id_pedido = ? ORDER BY a.descripcion ASC");
            ps.setInt(1, idPedido);
            rs = ps.executeQuery();
            while (rs.next()) {
                String referencia = rs.getString(1);
                String descripcion = rs.getString(2);
                double precio = rs.getDouble(3);
                Articulo art = new Articulo(referencia, descripcion, precio, proveedor);
                int cantidad = rs.getInt(4);
                double totalXArt = Double.valueOf(DF.format(precio * cantidad));
                registros.add(new Contenido(art, cantidad, totalXArt));
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
}
