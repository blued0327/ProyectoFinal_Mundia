
package model;


public class DetalleVentaModel {
    
    private int id;
    private int ventaId;
    private int ticketId;
    private double precio;
    private double iva;

    //Constructor vacio que probablemente nos va a servir después
    public DetalleVentaModel() {
    }
    
    //Constructor con parámetros
    public DetalleVentaModel(int id, int ventaId, int ticketId, double precio, double iva) {
        this.id = id;
        this.ventaId = ventaId;
        this.ticketId = ticketId;
        this.precio = precio;
        this.iva = iva;
    }
    
    //Agregamos el metodo GET
    public int getId() {
        return id;
    }

    public int getVentaId() {
        return ventaId;
    }

    public int getTicketId() {
        return ticketId;
    }

    public double getPrecio() {
        return precio;
    }

    public double getIva() {
        return iva;
    }
    
    
  //Y ahora el metodo SET
    public void setId(int id) {
        this.id = id;
    }

    public void setVentaId(int ventaId) {
        this.ventaId = ventaId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setIva(double iva) {
        this.iva = iva;
    }
    
    
}
