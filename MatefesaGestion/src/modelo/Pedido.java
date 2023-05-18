/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.Date;

/**
 *
 * @author Daniel Sanz Ferrer
 */
public class Pedido {
    
    private int id_pedido;
    private Date fecha;
    private double precioTotal;
    private String idProveedor;

    public Pedido() {
        this.fecha = new Date();
    }

    public Pedido(int id_pedido, Date fecha, double precioTotal, String idProveedor) {
        this.id_pedido = id_pedido;
        this.fecha = fecha;
        this.precioTotal = precioTotal;
        this.idProveedor = idProveedor;
    }

    public int getId_pedido() {
        return id_pedido;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public double getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(double precioTotal) {
        this.precioTotal = precioTotal;
    }

    public String getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(String idProveedor) {
        this.idProveedor = idProveedor;
    }   
}
