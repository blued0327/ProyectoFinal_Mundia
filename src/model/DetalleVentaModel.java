<<<<<<< HEAD

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
=======
package model;

import java.math.BigDecimal;

public class DetalleVentaModel {

    private int id;
    private int ventaId;
    private int ticketId;
    private BigDecimal precio;
    private BigDecimal iva;

    //para mostrar en pantalla con joins
    private String numeroAsiento;
    private String seccion;

    //vacio
    public DetalleVentaModel() {}

    //todos
    public DetalleVentaModel(int id, int ventaId, int ticketId, BigDecimal precio, BigDecimal iva) {
        this.id       = id;
        this.ventaId  = ventaId;
        this.ticketId = ticketId;
        this.precio   = precio;
        this.iva      = iva;
    }

    //sin id, lo genera la bd
    public DetalleVentaModel(int ventaId, int ticketId, BigDecimal precio, BigDecimal iva) {
        this.ventaId  = ventaId;
        this.ticketId = ticketId;
        this.precio   = precio;
        this.iva      = iva;
    }

>>>>>>> 56fd2b3ae4ec3f9b864ba4ae813f685a619cf7fd
    public int getId() {
        return id;
    }

    public int getVentaId() {
        return ventaId;
    }

    public int getTicketId() {
        return ticketId;
    }

<<<<<<< HEAD
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
    
    
=======
    public BigDecimal getPrecio() {
        return precio;
    }

    public BigDecimal getIva() {
        return iva;
    }

    public String getNumeroAsiento() {
        return numeroAsiento;
    }

    public String getSeccion() {
        return seccion;
    }


>>>>>>> 56fd2b3ae4ec3f9b864ba4ae813f685a619cf7fd
}
