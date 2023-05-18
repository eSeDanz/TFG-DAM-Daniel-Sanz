/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;

/**
 *
 * @author Daniel Sanz Ferrer
 */
public class Contenido {

    private final ProveedorControl PROCON;
    private final DecimalFormatSymbols SYMBOLS;
    private final DecimalFormat DF;
    private Articulo articulo;
    private int cantidad, numPedido;
    private double totalxArt;
    private String referencia, descripcion;

    public Contenido() {
        PROCON = new ProveedorControl();
        SYMBOLS = new DecimalFormatSymbols();
        SYMBOLS.setDecimalSeparator('.');
        DF = new DecimalFormat("#.##", SYMBOLS);
    }

    public Contenido(Articulo articulo, int cantidad, double totalxart) {
        PROCON = new ProveedorControl();
        this.articulo = articulo;
        this.referencia = articulo.getReferencia();
        this.cantidad = cantidad;
        this.totalxArt = totalxart;
        SYMBOLS = new DecimalFormatSymbols();
        SYMBOLS.setDecimalSeparator('.');
        DF = new DecimalFormat("#.##", SYMBOLS);
    }

    public Contenido(int numPedido, String referencia, String descripcion, int cantidad, double totalxart) {
        PROCON = new ProveedorControl();
        this.numPedido = numPedido;
        this.referencia = referencia;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.totalxArt = totalxart;
        SYMBOLS = new DecimalFormatSymbols();
        SYMBOLS.setDecimalSeparator('.');
        DF = new DecimalFormat("#.##", SYMBOLS);
    }

    public Articulo getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getTotalxArt() {
        return totalxArt;
    }

    public double getTotalxArtConDescuento() {
        double resultado = getTotalxArt();
        double descuento = 0;
        List<Proveedor> lista = PROCON.seleccionarLista();
        for (Proveedor pro : lista) {
            if (articulo.getId_proveedor().equals(pro.getId())) {
                double porcentaje = PROCON.getDescuento(pro.getId());
                descuento = (resultado * porcentaje) / 100;
                resultado -= descuento;
            }
        }
        return Double.valueOf(DF.format(resultado));
    }

    public double IVA(double totalConDescuento) {
        return ((totalConDescuento * 21) / 100);
    }

    public double recargoEquivalencia(double totalConDescuento) {
        return ((totalConDescuento * 5.20) / 100);
    }

    public double getTotalxArtConImpuestos(double totalXArtConDescuento) {
        return Double.valueOf(DF.format(totalXArtConDescuento + IVA(totalXArtConDescuento) + recargoEquivalencia(totalXArtConDescuento)));
    }

    public void setTotalxArt() {
        double resultado = articulo.getPrecio() * cantidad;
        String resultadoFormateado = DF.format(resultado);
        this.totalxArt = Double.valueOf(resultadoFormateado);
    }

    public int getNumPedido() {
        return numPedido;
    }

    public String getReferencia() {
        return referencia;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
