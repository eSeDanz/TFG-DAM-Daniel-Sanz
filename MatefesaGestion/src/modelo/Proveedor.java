/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Daniel Sanz Ferrer
 */
public class Proveedor {
    
    private String id, correo, web;
    private double descuento;
    private int numCliente;

    public Proveedor() {
    }

    public Proveedor(String id) {
        this.id = id;
    }
    
    public Proveedor(String id, String correo, String web, double descuento, int numCliente) {
        this.id = id;
        this.correo = correo;
        this.web = web;
        this.descuento = descuento;
        this.numCliente = numCliente;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public int getNumCliente() {
        return numCliente;
    }

    public void setNumCliente(int numCliente) {
        this.numCliente = numCliente;
    }
}
