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

    public int getId() {
        return id;
    }

    public int getVentaId() {
        return ventaId;
    }

    public int getTicketId() {
        return ticketId;
    }

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


}
