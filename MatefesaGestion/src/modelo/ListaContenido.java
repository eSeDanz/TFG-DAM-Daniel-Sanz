/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Daniel Sanz Ferrer
 */
public class ListaContenido {

    private final ProveedorControl PROCON = new ProveedorControl();
    private final DecimalFormatSymbols SYMBOLS;
    private final DecimalFormat DF;
    private ArrayList<Contenido> listaContenido;

    public ListaContenido() {
        this.listaContenido = new ArrayList();
        SYMBOLS = new DecimalFormatSymbols();
        SYMBOLS.setDecimalSeparator('.');
        DF = new DecimalFormat("#.##", SYMBOLS);
    }

    public Contenido getContenido(String referencia) {
        for (Contenido cont : listaContenido) {
            if (cont.getArticulo().getReferencia().equals(referencia)) {
                return cont;
            }
        }
        return null;
    }

    public ArrayList<Contenido> getListaContenido() {
        return listaContenido;
    }

    public boolean insertarContenido(Contenido contenido) {
        return this.listaContenido.add(contenido);
    }

    public String getPrimerProveedor() {
        return listaContenido.get(0).getArticulo().getId_proveedor();
    }

    public boolean existe(String referencia) {
        for (Contenido cont : listaContenido) {
            if (cont.getArticulo().getReferencia().equals(referencia)) {
                return true;
            }
        }
        return false;
    }

    public double totalxlista() {
        double total = 0;
        for (Contenido cont : listaContenido) {
            total += cont.getTotalxArt();
        }
        total = Double.valueOf(DF.format(total));
        return total;
    }

    public Double descuento(Double totalSinDescuento) {
        if (getListaContenido().isEmpty()) {
            return Double.valueOf(DF.format(0.0));
        } else {
            double descuento;
            List<Proveedor> lista = PROCON.seleccionarLista();
            for(Proveedor pro: lista){
                if(getPrimerProveedor().equals(pro.getId())){
                    double porcentaje = PROCON.getDescuento(pro.getId());
                    descuento = (totalSinDescuento * porcentaje) / 100;
                    return Double.valueOf(DF.format(descuento));
                }
            }
        }
        return Double.valueOf(DF.format(0.0));
    }

    public double IVA(double totalConDescuento) {
        return Double.valueOf(DF.format((totalConDescuento * 21) / 100));
    }

    public double recargoEquivalencia(double totalConDescuento) {
        return Double.valueOf(DF.format((totalConDescuento * 5.20) / 100));
    }

    public double totalConImpuestos(double totalConDescuento) {
        return Double.valueOf(DF.format(totalConDescuento + IVA(totalConDescuento) + recargoEquivalencia(totalConDescuento)));
    }

    public double totalConDescuento() {
        double totalSinDescuento = totalxlista();
        double totalConDescuento;
        double descuento;
        List<Proveedor> lista = PROCON.seleccionarLista();
            for(Proveedor pro: lista){
                if(getPrimerProveedor().equals(pro.getId())){
                    double porcentaje = PROCON.getDescuento(pro.getId());
                    descuento = (totalSinDescuento * porcentaje) / 100;
                    totalConDescuento = Double.valueOf(DF.format(totalSinDescuento - descuento));
                    return totalConDescuento;
                }
            }
        return Double.valueOf(DF.format(0.0));
    }

    public void vaciarLista() {
        listaContenido.clear();
    }

    public int longitud() {
        return listaContenido.size();
    }
}
