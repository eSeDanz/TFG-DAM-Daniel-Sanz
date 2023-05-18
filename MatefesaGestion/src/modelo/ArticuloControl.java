/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import controlador.ArticuloDAO;
import controlador.AuditoriaDAO;
import controlador.PedidoDAO;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

/**
 *
 * @author Daniel Sanz Ferrer
 */
public class ArticuloControl {

    private final ArticuloDAO ADAO;
    private final PedidoDAO PDAO;
    private final AuditoriaDAO AUDAO;
    private Articulo art;
    private DefaultTableModel modeloTabla;
    private int registrosMostrados;

    public ArticuloControl() {
        this.ADAO = new ArticuloDAO();
        this.PDAO = new PedidoDAO();
        this.AUDAO = new AuditoriaDAO();
        this.art = new Articulo();
        this.registrosMostrados = 0;
    }

    public DefaultTableModel listar(String texto, int totalPorPagina, int numPagina) {
        List<Articulo> lista = new ArrayList();
        lista.addAll(ADAO.listar(texto, totalPorPagina, numPagina));
        String[] titulos = {"Referencia", "Código EAN", "Descripción", "Pedido mínimo", "Precio", "Proveedor"};
        this.modeloTabla = new DefaultTableModel(null, titulos);
        String[] registro = new String[6];
        this.registrosMostrados = 0;
        for (Articulo item : lista) {
            registro[0] = item.getReferencia();
            registro[1] = item.getEan().toString();
            registro[2] = item.getDescripcion();
            registro[3] = Integer.toString(item.getMinimo());
            registro[4] = Double.toString(item.getPrecio());
            registro[5] = item.getId_proveedor();
            this.modeloTabla.addRow(registro);
            this.registrosMostrados = this.registrosMostrados + 1;
        }
        return this.modeloTabla;
    }

    public DefaultTableModel listar(String texto, String proveedor) {
        List<Articulo> lista = new ArrayList();
        lista.addAll(ADAO.listar(texto, proveedor));
        String[] titulos = {"Referencia", "Código EAN", "Descripción", "Pedido mínimo", "Precio", "Proveedor"};
        this.modeloTabla = new DefaultTableModel(null, titulos);
        String[] registro = new String[6];
        for (Articulo item : lista) {
            registro[0] = item.getReferencia();
            registro[1] = item.getEan().toString();
            registro[2] = item.getDescripcion();
            registro[3] = Integer.toString(item.getMinimo());
            registro[4] = Double.toString(item.getPrecio());
            registro[5] = item.getId_proveedor();
            this.modeloTabla.addRow(registro);
        }
        return this.modeloTabla;
    }
    
    public Articulo getArticulo(String referencia){
        return ADAO.getArticulo(referencia);
    }

    public String insertar(String referencia, BigInteger codigoEAN, String descripcion, int minimo, double precio, String proveedor) {
        if (ADAO.existe(referencia)) {
            return "Ya existe un artículo con esa referencia.";
        } else {
            art.setReferencia(referencia);
            art.setEan(codigoEAN);
            art.setDescripcion(descripcion);
            art.setMinimo(minimo);
            art.setPrecio(precio);
            art.setId_proveedor(proveedor);
            if (ADAO.insertar(art)) {
                return "OK";
            } else {
                return "Error en el registro.";
            }
        }
    }

    public String actualizar(String referencia, String referenciaAntigua, BigInteger codigoEAN, String descripcion, int minimo, double precio, String proveedor) {
        if (referencia.equals(referenciaAntigua)) {
            art.setReferencia(referencia);
            art.setReferenciaAntigua(referenciaAntigua);
            art.setEan(codigoEAN);
            art.setDescripcion(descripcion);
            art.setMinimo(minimo);
            art.setPrecio(precio);
            art.setId_proveedor(proveedor);
            if (ADAO.actualizar(art)) {
                return "OK";
            } else {
                return "Error en la actualización.";
            }
        } else {
            if (ADAO.existe(referencia)) {
                return "La referencia ya existe.";
            } else {
                art.setReferencia(referencia);
                art.setReferenciaAntigua(referenciaAntigua);
                art.setEan(codigoEAN);
                art.setDescripcion(descripcion);
                art.setMinimo(minimo);
                art.setPrecio(precio);
                art.setId_proveedor(proveedor);
                if (ADAO.actualizar(art)) {
                    return "OK";
                } else {
                    return "Error en la actualización.";
                }
            }
        }
    }

    public String cambioPreciosProveedor(String proveedor, double porcentaje) {
        if (ADAO.cambioPreciosProveedor(proveedor, porcentaje)) {
            return "OK";
        } else {
            return "Error en la actualización.";
        }
    }

    public String revertirPreciosProveedor(String proveedor) {
        List<Articulo> lista = AUDAO.getListaProveedor(proveedor);
        boolean fallo = false;
        for (Articulo arti : lista) {
            String referencia = arti.getReferencia();
            double precio = arti.getPrecioAntiguo();
            if (!ADAO.actualizarPrecios(precio, referencia)) {
                fallo = true;
            }
        }
        if (fallo) {
            return "Hubo algún problema recuperando los precios anteriores del proveedor seleccionado.";
        } else {
            return "OK";
        }
    }

    public String revertirPrecioArticulo(String referencia) {
        if (AUDAO.existe(referencia)) {
            Articulo arti = AUDAO.getArticulo(referencia);
            double precioAntiguo = arti.getPrecioAntiguo();
            if (ADAO.actualizarPrecios(precioAntiguo, referencia)) {
                return "OK";
            } else {
                return "Hubo algún problema recuperando el precio anterior del artículo seleccionado.";
            }
        } else {
            return "No hay ningún cambio de precio registrado en el artículo con la referencia seleccionada";
        }
    }

    public double calcularPorcentaje(double precio, double precioAntiguo) {
        return ((precio - precioAntiguo)*100)/precioAntiguo;
    }

    public String eliminar(String referencia) {
        if (ADAO.eliminar(referencia)) {
            return "OK";
        } else {
            return "Error en la actualización.";
        }
    }

    public String actualizarPrecios(Sheet hojaExcel) {
        boolean fallo = false;
        DataFormatter dataFormatter = new DataFormatter();
        Row row;
        for (int i = 1; i <= hojaExcel.getLastRowNum(); i++) {
            row = hojaExcel.getRow(i);
            Cell referenciaCell = row.getCell(0);
            String referencia = dataFormatter.formatCellValue(referenciaCell);
            double precio = row.getCell(4).getNumericCellValue();
            if (ADAO.existe(referencia)) {
                if (!ADAO.actualizarPrecios(precio, referencia)) {
                    fallo = true;
                }
            }
        }
        if (fallo) {
            return "Hubo algún problema con la actualización.";
        } else {
            return "OK";
        }
    }

    public String actualizarPreciosLifeStyle(Sheet hojaExcel) {
        boolean fallo = false;
        DataFormatter dataFormatter = new DataFormatter();
        Row row;
        for (int i = 2; i <= hojaExcel.getLastRowNum(); i++) {
            row = hojaExcel.getRow(i);
            Cell referenciaCell = row.getCell(2);
            String referencia = dataFormatter.formatCellValue(referenciaCell);
            double precio = row.getCell(7).getNumericCellValue();
            if (ADAO.existe(referencia)) {
                if (!ADAO.actualizarPrecios(precio, referencia)) {
                    fallo = true;
                }
            }
        }
        if (fallo) {
            return "Hubo algún problema con la actualización.";
        } else {
            return "OK";
        }
    }

    public String cargarArticulos(Sheet hojaExcel) {
        PDAO.vaciarTabla();
        ADAO.vaciarTabla();
        boolean fallo = false;
        DataFormatter dataFormatter = new DataFormatter();
        Row row = hojaExcel.getRow(1);
        for (int i = 1; i <= hojaExcel.getLastRowNum(); i++) {
            Cell referenciaCell = row.getCell(0);
            String referencia = dataFormatter.formatCellValue(referenciaCell);
            art.setReferencia(referencia);
            if (row.getCell(1) != null) {
                art.setEan(BigInteger.valueOf((long) row.getCell(1).getNumericCellValue()));
            } else {
                art.setEan(new BigInteger("0"));
            }
            art.setDescripcion(row.getCell(2).getStringCellValue());
            art.setMinimo((int) row.getCell(3).getNumericCellValue());
            art.setPrecio(row.getCell(4).getNumericCellValue());
            art.setId_proveedor(row.getCell(5).getStringCellValue());
            if (!ADAO.insertar(art)) {
                fallo = true;
            }
            row = hojaExcel.getRow(i + 1);
        }
        if (fallo) {
            return "Hubo algún problema con la carga de los artículos.";
        } else {
            return "OK";
        }
    }

    public double getPrecio(String referencia) {
        return ADAO.getPrecio(referencia);
    }

    public int total() {
        return ADAO.total();
    }

    public int totalMostrados() {
        return this.registrosMostrados;
    }
}
