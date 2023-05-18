/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import controlador.ContenidoDAO;
import controlador.PedidoDAO;
import controlador.ProveedorDAO;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

/**
 *
 * @author Daniel Sanz Ferrer
 */
public class ContenidoControl {

    private final ContenidoDAO CDAO;
    private final PedidoDAO PDAO;
    private final ProveedorDAO PRODAO;
    private final String CLIENTE="Daniel Sanz Ferrer", RUTA="C:\\Users\\Dani\\Documents\\" , CORREO="11886285@ieselgrao.org", CONTRA="04061986";
    private Contenido cont;
    private ListaContenido listaCont;
    private DefaultTableModel modeloTabla;

    public ContenidoControl() {
        this.CDAO = new ContenidoDAO();
        this.PDAO = new PedidoDAO();
        this.PRODAO = new ProveedorDAO();
        this.cont = new Contenido();
        this.listaCont = new ListaContenido();
        UIManager.put("OptionPane.yesButtonText", "Si");
    }

    public ListaContenido getListaCont() {
        return listaCont;
    }

    public DefaultTableModel listarContenido() {
        List<Contenido> lista = listaCont.getListaContenido();
        String[] titulos = {"Referencia", "Descripción", "Precio", "Cantidad", "Total por Artículo", "Proveedor"};
        this.modeloTabla = new DefaultTableModel(null, titulos);
        String[] registro = new String[6];
        for (Contenido item : lista) {
            registro[0] = item.getArticulo().getReferencia();
            registro[1] = item.getArticulo().getDescripcion();
            registro[2] = String.valueOf(item.getArticulo().getPrecio());
            registro[3] = String.valueOf(item.getCantidad());
            registro[4] = String.valueOf(item.getTotalxArt());
            registro[5] = item.getArticulo().getId_proveedor();
            this.modeloTabla.addRow(registro);
        }
        return this.modeloTabla;
    }
    
    public DefaultTableModel listarContenido2() {
        List<Contenido> lista = new ArrayList();
        lista.addAll(CDAO.listarContenido());
        String[] titulos = {"ID Pedido", "Referencia", "Descripcion", "Cantidad", "Total por artículo"};
        this.modeloTabla = new DefaultTableModel(null, titulos);
        String[] registro = new String[5];
        for (Contenido item : lista) {
            registro[0] = String.valueOf(item.getNumPedido());
            registro[1] = item.getReferencia();
            registro[2] = item.getDescripcion();
            registro[3] = String.valueOf(item.getCantidad());
            registro[4] = String.valueOf(item.getTotalxArt());
            this.modeloTabla.addRow(registro);
        }
        return this.modeloTabla;
    }

    public DefaultTableModel recuperarContenido(int idPedido, String proveedor) {
        getListaCont().vaciarLista();
        List<Contenido> lista = CDAO.recuperarContenido(idPedido, proveedor);
        String[] titulos = {"Referencia", "Descripción", "Precio", "Cantidad", "Total por Artículo", "Proveedor"};
        this.modeloTabla = new DefaultTableModel(null, titulos);
        String[] registro = new String[6];
        for (Contenido item : lista) {
            getListaCont().insertarContenido(item);
            registro[0] = item.getArticulo().getReferencia();
            registro[1] = item.getArticulo().getDescripcion();
            registro[2] = String.valueOf(item.getArticulo().getPrecio());
            registro[3] = String.valueOf(item.getCantidad());
            registro[4] = String.valueOf(item.getTotalxArt());
            registro[5] = item.getArticulo().getId_proveedor();
            this.modeloTabla.addRow(registro);
        }
        return this.modeloTabla;
    }

    public DefaultTableModel listarPedidos() {
        List<Pedido> lista = new ArrayList();
        lista.addAll(PDAO.listarPedidos());
        String[] titulos = {"ID Pedido", "Fecha", "Precio Total", "Proveedor"};
        this.modeloTabla = new DefaultTableModel(null, titulos);
        String[] registro = new String[4];
        for (Pedido item : lista) {
            registro[0] = String.valueOf(item.getId_pedido());
            registro[1] = item.getFecha().toString();
            registro[2] = String.valueOf(item.getPrecioTotal());
            registro[3] = item.getIdProveedor();
            this.modeloTabla.addRow(registro);
        }
        return this.modeloTabla;
    }

    public String insertar(Articulo art, int cantidad) {
        cont = new Contenido();
        cont.setArticulo(art);
        cont.setCantidad(cantidad);
        cont.setTotalxArt();
        if (listaCont.insertarContenido(cont)) {
            return "OK";
        } else {
            return "Error en el registro.";
        }
    }

    public String insertarTransaccion(ListaContenido listaCont) {
        if (CDAO.insertar(listaCont)) {
            return "OK";
        } else {
            return "Error en el registro.";
        }
    }

    public String actualizarCantidad(Articulo art, String referencia, int cantidad) {
        for (Contenido conte : listaCont.getListaContenido()) {
            if (conte.getArticulo().getReferencia().equals(referencia)) {
                conte.setArticulo(art);
                conte.setCantidad(cantidad);
                conte.setTotalxArt();
                return "OK";
            }
        }
        return "Error en el cambio de cantidad del artículo seleccionado.";
    }

    public String eliminarContenido(String referencia) {
        for (Contenido conte : listaCont.getListaContenido()) {
            if (conte.getArticulo().getReferencia().equals(referencia)) {
                if (listaCont.getListaContenido().remove(conte)) {
                    return "OK";
                }
            }
        }
        return "Error en la eliminación.";
    }

    public Double totalxlista(ListaContenido lista) {
        return lista.totalxlista();
    }

    public boolean imprimirPDF() {
        int maxLinesPerPage = 56; // Número máximo de líneas por página
        int currentLine = 0; // Contador de líneas en la página actual
        int respuesta = JOptionPane.showConfirmDialog(null, "¿Seguro que deseas exportar el pedido a PDF? Este pedido se registrará en el histórico de pedidos de compra.", "Confirmación", JOptionPane.YES_NO_OPTION);
        if (respuesta == JOptionPane.YES_OPTION) {
            try {
                String proveedor = listaCont.getPrimerProveedor();
                String fecha = fechaActual();
                PDDocument document = new PDDocument();
                PDPage page = new PDPage();
                document.addPage(page);
                PDPageContentStream contentStream = new PDPageContentStream(document, page);
                //URL imageURL = getClass().getResource("/vista/images/logo600x600Gris.png");
                //PDImageXObject logo = PDImageXObject.createFromFile(imageURL.getPath(), document);
                
                //PDImageXObject logo = PDImageXObject.createFromFile("src\\vista\\images\\logo600x600Gris.png", document);
                //float logoSize = 50f;
                //float logoPositionX = page.getMediaBox().getWidth() - logoSize - 20f; // 50f es el margen derecho
                //float logoPositionY = page.getMediaBox().getHeight() - logoSize - 20f; // 10f es el margen superior
                //contentStream.drawImage(logo, logoPositionX, logoPositionY, logoSize, logoSize);
                contentStream.beginText();
                InputStream inputStream = getClass().getResourceAsStream("/modelo/fonts/calibrib.ttf");
                PDFont font = PDType0Font.load(document, inputStream);
                contentStream.setFont(font, 15);
                contentStream.newLineAtOffset(50, 750); // posicion de la cabecera
                contentStream.showText("Pedido " + proveedor + " " + fecha);
                inputStream = getClass().getResourceAsStream("/modelo/fonts/calibrii.ttf");
                font = PDType0Font.load(document, inputStream);
                contentStream.setFont(font, 13);
                contentStream.newLineAtOffset(0, -15); // espacio para el subtitulo
                List<Proveedor> lista = PRODAO.seleccionar();
                for (Proveedor pro : lista) {
                    if (proveedor.equals(pro.getId())) {
                        int numCliente = PRODAO.getNumeroCliente(pro.getId());
                        contentStream.showText("Cliente " + CLIENTE + " - " + numCliente);
                    }
                }
                inputStream = getClass().getResourceAsStream("/modelo/fonts/calibri.ttf");
                font = PDType0Font.load(document, inputStream);
                contentStream.setFont(font, 10);
                contentStream.newLineAtOffset(0, -18); // espacio para el siguiente texto
                contentStream.showText("Referencia");
                contentStream.newLineAtOffset(60, 0);
                contentStream.showText("Cantidad");
                contentStream.newLineAtOffset(50, 0);
                contentStream.showText("Descripción del artículo");
                contentStream.newLineAtOffset(-110, -12); // espacio para el siguiente texto
                for (Contenido cont : listaCont.getListaContenido()) {
                    String referencia, descripcion;
                    int cantidad;
                    referencia = cont.getArticulo().getReferencia();
                    descripcion = cont.getArticulo().getDescripcion();
                    cantidad = cont.getCantidad();
                    if (currentLine > maxLinesPerPage) {
                        contentStream.endText();
                        contentStream.close();
                        page = new PDPage();
                        document.addPage(page);
                        contentStream = new PDPageContentStream(document, page);
                        currentLine = 0;
                        contentStream.beginText();
                        contentStream.setFont(font, 10);
                        contentStream.newLineAtOffset(50, 750);
                        contentStream.showText("Referencia");
                        contentStream.newLineAtOffset(60, 0);
                        contentStream.showText("Cantidad");
                        contentStream.newLineAtOffset(50, 0);
                        contentStream.showText("Descripción del artículo");
                        contentStream.newLineAtOffset(-110, -12); // espacio para el siguiente texto
                    }
                    contentStream.showText(referencia);
                    contentStream.newLineAtOffset(60, 0);
                    contentStream.showText(String.valueOf(cantidad));
                    contentStream.newLineAtOffset(50, 0);
                    contentStream.showText(descripcion);
                    contentStream.newLineAtOffset(-110, -12); // espacio para el siguiente texto
                    currentLine++;
                }
                contentStream.endText();
                contentStream.close();
                guardarDocumento(document, proveedor, fecha);
                document.close();
                insertarTransaccion(listaCont);
                return true;
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        } else if (respuesta == JOptionPane.NO_OPTION) {
            JOptionPane.showMessageDialog(null, "No se exportará a PDF el contenido del pedido, ni se registrará en el histórico de pedidos de compra.", "Pedidos", JOptionPane.INFORMATION_MESSAGE);
        }
        return false;
    }

    public String enviarPorCorreo(File archivoAdjunto) {
        boolean enviado = false;
        try {
            String nombreArchivo = archivoAdjunto.getName();
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.user", CORREO);
            props.put("mail.smtp.auth", "true");
            //Establecemos la encriptación de la comunicación entre cliente y servidor
            props.put("mail.smtp.starttls.enable", "true");
            Session session = Session.getInstance(props, null);
            BodyPart texto = new MimeBodyPart();
            texto.setText("Buenas, te envío los artículos que necesito en el pdf adjunto, gracias.\n\nSaludos.\n\n"+CLIENTE+".");
            BodyPart adjunto = new MimeBodyPart();
            adjunto.setDataHandler(new DataHandler(new FileDataSource(archivoAdjunto)));
            adjunto.setFileName("Pedido de compra " + nombreArchivo);
            MimeMultipart mmp = new MimeMultipart();
            mmp.addBodyPart(texto);
            mmp.addBodyPart(adjunto);
            // Crear el mensaje de correo electrónico
            Message message = new MimeMessage(session);
            message.setDisposition(MimeMessage.INLINE);
            message.setFrom(new InternetAddress(CORREO));
            List<Proveedor> lista = PRODAO.seleccionar();
            for (Proveedor pro : lista) {
                if (nombreArchivo.startsWith(pro.getId())) {
                    String email = PRODAO.getEmail(pro.getId());
                    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
                    int numCliente = PRODAO.getNumeroCliente(pro.getId());
                    message.setSubject("Pedido de compra del cliente #" + numCliente);
                }
            }
            message.setContent(mmp);
            // Iniciar la sesión de correo electrónico y enviar el mensaje
            Transport transport = session.getTransport("smtp");
            transport.connect(CORREO, CONTRA);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            enviado = true;
        } catch (MessagingException me) {
            JOptionPane.showMessageDialog(null, "Ocurrió un error al enviar el correo electrónico: " + me.getMessage());
        }
        if (!enviado) {
            return "Hubo algún problema con el envío del correo electrónico.";
        } else {
            return "OK";
        }
    }

    public void guardarDocumento(PDDocument document, String proveedor, String fecha) throws IOException {
        String nombreArchivo = proveedor + " " + fecha + ".pdf"; // nombre del archivo con el número de documento guardado
        String ruta = RUTA + nombreArchivo;
        document.save(ruta);
        JOptionPane.showMessageDialog(null, "Se ha guardado el contenido del pedido en un archivo PDF.", "Pedidos", JOptionPane.INFORMATION_MESSAGE);
    }

    public String fechaActual() {
        Date fecha = new Date();
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy");
        String fechaFormateada = formatoFecha.format(fecha);
        return fechaFormateada;
    }
}
