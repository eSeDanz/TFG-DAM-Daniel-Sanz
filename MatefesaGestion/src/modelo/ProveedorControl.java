/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import controlador.ProveedorDAO;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Daniel Sanz Ferrer
 */
public class ProveedorControl {
    private final ProveedorDAO PRODAO;
    private Proveedor pro;
    private DefaultTableModel modeloTabla;
    public int registrosMostrados;
    
    public ProveedorControl(){
        this.PRODAO=new ProveedorDAO();
        this.pro=new Proveedor();
        this.registrosMostrados=0;
    }
    
    public DefaultTableModel listar(String texto, int totalPorPagina, int numPagina){
        List<Proveedor> lista=new ArrayList();
        lista.addAll(PRODAO.listar(texto, totalPorPagina, numPagina));
        String[] titulos={"Proveedor","Correo Electrónico","Página Web","Descuento %","Número de Cliente"};
        this.modeloTabla=new DefaultTableModel(null,titulos);                
        String[] registro = new String[5];        
        this.registrosMostrados=0;
        for (Proveedor prov:lista){
            registro[0]= prov.getId();
            registro[1]= prov.getCorreo();
            registro[2]= prov.getWeb();
            registro[3]= String.valueOf(prov.getDescuento());
            registro[4]= String.valueOf(prov.getNumCliente());
            this.modeloTabla.addRow(registro);
            this.registrosMostrados=this.registrosMostrados+1;
        }
        return this.modeloTabla;
    }
    
    public DefaultComboBoxModel seleccionar() {
        DefaultComboBoxModel items = new DefaultComboBoxModel();
        List<Proveedor> lista = PRODAO.seleccionar();
        for (Proveedor item : lista) {
            items.addElement(item.getId());
        }
        return items;
    }
    
    public List<Proveedor> seleccionarLista() {
        return PRODAO.seleccionar();
    }
       
    public String insertar(String nombre, String correo, String web, double descuento, int numCliente){
        if (PRODAO.existe(nombre)){
            return "El registro ya existe.";
        }else{
            pro.setId(nombre);
            pro.setCorreo(correo);
            pro.setWeb(web); 
            pro.setDescuento(descuento);
            pro.setNumCliente(numCliente);
            if (PRODAO.insertar(pro)){
                return "OK";
            }else{
                return "Error en el registro.";
            }
        }
    }
    
    public String actualizar(String nombre, String nombreAnt, String email, String web, double descuento, int numCliente){
        if (nombre.equals(nombreAnt)){
            pro.setId(nombre);
            pro.setCorreo(email);
            pro.setWeb(web);
            pro.setDescuento(descuento);
            pro.setNumCliente(numCliente);
            if(PRODAO.actualizar(pro, nombreAnt)){
                return "OK";
            }else{
                return "Error en la actualización.";
            }
        }else{
            if (PRODAO.existe(nombre)){
                return "El registro ya existe.";
            }else{
                pro.setId(nombre);
                pro.setCorreo(email);
                pro.setWeb(web);
                pro.setDescuento(descuento);
                pro.setNumCliente(numCliente);
                if (PRODAO.actualizar(pro, nombreAnt)){
                    return "OK";
                }else{
                    return "Error en la actualización.";
                }
            }
        }
    }
    
    public String eliminar(String referencia) {
        if (PRODAO.eliminar(referencia)) {
            return "OK";
        } else {
            return "Error en la actualización.";
        }
    }
    
    public int total(){
        return PRODAO.total();
    }
    
    public int totalMostrados(){
        return this.registrosMostrados;
    }
    
    public String getEmail(String id_proveedor){
        return PRODAO.getEmail(id_proveedor);
    }
    
    public double getDescuento(String id_proveedor){
        return PRODAO.getDescuento(id_proveedor);
    }
    
    public int getNumeroCliente(String id_proveedor){
        return PRODAO.getNumeroCliente(id_proveedor);
    }
}
