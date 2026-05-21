
import java.time.LocalDate;
import java.util.ArrayList;


public class VentasModel {
    
     private int id;
    private LocalDate fechaCompra;
    private ClienteModel cliente;  //Informacio+ón de la tabla de Ronald
    private UsuarioModel usuarioVenta;  //Información de la tabla de marín
    private PartidoModel partido;  // Información de la tabla de luispe
    private ArrayList<Boletos> ticketsComprados; // Información de la tabla de boletos(Ronald tambien)
    private double subtotal;
    private double iva;
    private double descuento;
    private double total;

    //Constructor con parámetros 
    public VentasModel(int id, LocalDate fechaCompra, ClienteModel cliente, UsuarioModel usuarioVenta, PartidoModel partido, ArrayList<Boletos> ticketsComprados, double subtotal, double iva, double descuento, double total) {
        this.id = id;
        this.fechaCompra = fechaCompra;
        this.cliente = cliente;
        this.usuarioVenta = usuarioVenta;
        this.partido = partido;
        this.ticketsComprados = ticketsComprados;
        this.subtotal = subtotal;
        this.iva = iva;
        this.descuento = descuento;
        this.total = total;
    }

    //Metodos GET Y SET
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(LocalDate fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public ClienteModel getCliente() {
        return cliente;
    }

    public void setCliente(ClienteModel cliente) {
        this.cliente = cliente;
    }

    public UsuarioModel getUsuarioVenta() {
        return usuarioVenta;
    }

    public void setUsuarioVenta(UsuarioModel usuarioVenta) {
        this.usuarioVenta = usuarioVenta;
    }

    public PartidoModel getPartido() {
        return partido;
    }

    public void setPartido(PartidoModel partido) {
        this.partido = partido;
    }

    public ArrayList<Boletos> getTicketsComprados() {
        return ticketsComprados;
    }

    public void setTicketsComprados(ArrayList<Boletos> ticketsComprados) {
        this.ticketsComprados = ticketsComprados;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getIva() {
        return iva;
    }

    public void setIva(double iva) {
        this.iva = iva;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

        
}