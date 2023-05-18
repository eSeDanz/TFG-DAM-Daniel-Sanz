/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import controlador.AuditoriaDAO;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Daniel Sanz Ferrer
 */
public class AuditoriaControl {
    private final AuditoriaDAO AUDAO;
    private DefaultTableModel modeloTabla;

    public AuditoriaControl() {
        this.AUDAO = new AuditoriaDAO();
    }
    
    public DefaultTableModel listar(String texto, String proveedor) {
        List<Articulo> lista = new ArrayList();
        lista.addAll(AUDAO.listar(texto, proveedor));
        String[] titulos = {"Referencia", "Código EAN", "Descripción", "Pedido mínimo", "Precio", "Proveedor", "Precio Anterior", "Fecha"};
        this.modeloTabla = new DefaultTableModel(null, titulos);
        String[] registro = new String[8];
        for (Articulo item : lista) {
            registro[0] = item.getReferencia();
            registro[1] = item.getEan().toString();
            registro[2] = item.getDescripcion();
            registro[3] = Integer.toString(item.getMinimo());
            registro[4] = Double.toString(item.getPrecio());
            registro[5] = item.getId_proveedor();
            registro[6] = Double.toString(item.getPrecioAntiguo());
            registro[7] = String.valueOf(item.getFecha());
            this.modeloTabla.addRow(registro);
        }
        return this.modeloTabla;
    }
}
