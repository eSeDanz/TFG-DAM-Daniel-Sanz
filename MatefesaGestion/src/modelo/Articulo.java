/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.math.BigInteger;
import java.util.Date;

/**
 *
 * @author Daniel Sanz Ferrer
 */
public class Articulo {
    
    private String referencia, referenciaAntigua, descripcion, id_proveedor;
    private int minimo;
    private BigInteger ean;
    private double precio, precioAntiguo;
    private Date fecha;

    public Articulo() {
    }

    public Articulo(String referencia, BigInteger ean, String descripcion, int minimo,  double precio, String id_proveedor, double precioAntiguo, Date fecha) {
        this.referencia = referencia;
        this.descripcion = descripcion;
        this.id_proveedor = id_proveedor;
        this.ean = ean;
        this.minimo = minimo;
        this.precio = precio;
        this.precioAntiguo = precioAntiguo;
        this.fecha = fecha;
    }
    
    public Articulo(String referencia, BigInteger ean, String descripcion, int minimo,  double precio, String id_proveedor) {
        this.referencia = referencia;
        this.ean = ean;
        this.descripcion = descripcion;
        this.minimo = minimo;
        this.precio = precio;
        this.id_proveedor = id_proveedor;        
    }

    public Articulo(String referencia, String descripcion, int minimo, double precio, String id_proveedor) {
        this.referencia = referencia;
        this.descripcion = descripcion;
        this.id_proveedor = id_proveedor;
        this.minimo = minimo;
        this.precio = precio;
    }

    public Articulo(String referencia, String descripcion, double precio, String id_proveedor) {
        this.referencia = referencia;
        this.descripcion = descripcion;
        this.precio = precio;
        this.id_proveedor = id_proveedor;
    }
    
    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getReferenciaAntigua() {
        return referenciaAntigua;
    }

    public void setReferenciaAntigua(String referenciaAntigua) {
        this.referenciaAntigua = referenciaAntigua;
    }
    
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getId_proveedor() {
        return id_proveedor;
    }

    public void setId_proveedor(String id_proveedor) {
        this.id_proveedor = id_proveedor;
    }

    public BigInteger getEan() {
        return ean;
    }

    public void setEan(BigInteger ean) {
        this.ean = ean;
    }

    public int getMinimo() {
        return minimo;
    }

    public void setMinimo(int minimo) {
        this.minimo = minimo;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double getPrecioAntiguo() {
        return precioAntiguo;
    }

    public void setPrecioAntiguo(double precioAntiguo) {
        this.precioAntiguo = precioAntiguo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha() {
        this.fecha = new Date();
    }    
}
